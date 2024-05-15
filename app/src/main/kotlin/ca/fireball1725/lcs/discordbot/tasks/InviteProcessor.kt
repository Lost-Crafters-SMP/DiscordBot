/*
 * Created for the Lost Crafters SMP (https://www.lostcrafterssmp.com)
 * Licensed under the GNU Affero General Public License v3.0
 * See LICENSE.txt for full license information
 */

package ca.fireball1725.lcs.discordbot.tasks

import ca.fireball1725.lcs.discordbot.botConfig
import ca.fireball1725.lcs.discordbot.data.databasenew.MemberInvitesTable
import ca.fireball1725.lcs.discordbot.data.invitebot.InviteBotMessage
import ca.fireball1725.lcs.discordbot.getGuilds
import ca.fireball1725.lcs.discordbot.getInvites
import ca.fireball1725.lcs.discordbot.getNewDatabase
import ca.fireball1725.lcs.discordbot.updateInvites
import com.google.gson.Gson
import dev.kord.common.entity.Snowflake
import dev.kord.core.behavior.channel.createInvite
import dev.kord.core.behavior.getChannelOf
import dev.kord.core.entity.ReactionEmoji
import dev.kord.core.entity.channel.TextChannel
import dev.kord.core.event.guild.MemberJoinEvent
import dev.kord.core.event.message.MessageCreateEvent
import dev.kord.x.emoji.Emojis
import dev.kord.x.emoji.from
import kotlinx.coroutines.flow.toList
import org.ktorm.dsl.count
import org.ktorm.dsl.eq
import org.ktorm.dsl.from
import org.ktorm.dsl.insert
import org.ktorm.dsl.isNull
import org.ktorm.dsl.map
import org.ktorm.dsl.select
import org.ktorm.dsl.update
import org.ktorm.dsl.where
import kotlin.time.Duration

class InviteProcessor {
    suspend fun generateInviteCode(): String {
        val inviteChannelId = 1143743031212773387U

        val channel = getGuilds()?.get(0)?.getChannelOf<TextChannel>(Snowflake(inviteChannelId))

        val invite = channel!!.createInvite {
            maxAge = Duration.ZERO
            maxUses = 1
            unique = true
        }

        return invite.code
    }

    /**
     * Check to make sure there are enough invite codes on standby
     */
    suspend fun checkForInviteCodes() {
        val database = getNewDatabase()

        val freeInviteCount = database
            .from(MemberInvitesTable.MemberInvites)
            .select(count())
            .where { MemberInvitesTable.MemberInvites.memberId.isNull() }
            .map { row -> row.getInt(1) }
            .firstOrNull() ?: 0

        println("Found ${freeInviteCount} unused invite codes")

        for (i in 1 .. (botConfig().inviteConfiguration.readyInviteCount - freeInviteCount)) {
            println("Generating invite code")
            val inviteCode = generateInviteCode()
            database.insert(MemberInvitesTable.MemberInvites) {
                set(it.inviteCode, inviteCode)
            }
            println("Saved ${inviteCode}")
        }
    }

    /**
     *
     */
    suspend fun memberJoinEvent(memberJoinEvent: MemberJoinEvent) {
        val guild = memberJoinEvent.getGuild()
        val newInvites = guild.invites.toList().associateBy { it.code }
        val database = getNewDatabase()

        val usedInvite = getInvites().filter { it.key !in newInvites }

        for ((code, invite) in usedInvite) {
            println("${memberJoinEvent.member.displayName} joined using invite code ${invite.code}")

            database.update(MemberInvitesTable.MemberInvites) {
                set(it.inviteUsed, true)
                set(it.inviteUsedBy, memberJoinEvent.member.id.value.toLong())
                where { it.inviteCode eq invite.code }
            }
        }

        updateInvites()
    }


    suspend fun channelMessageEvent(messageCreateEvent: MessageCreateEvent) {
        val guild = messageCreateEvent.getGuild()
        val message = messageCreateEvent.message.content
        val database = getNewDatabase()

        // Validate we are in the correct channel
        if (messageCreateEvent.message.channelId.value.toLong() != botConfig().inviteConfiguration.newMemberInfoChannelId)
            return

        // Check to see if the message is a json message
        if (!message.startsWith("```json"))
            return

        // Serialise the message and parse it
        try {
            val jsonMessage = Gson().fromJson(
                message.replace("```json", "").replace("```", "").replace("\n", ""),
                InviteBotMessage::class.java
            )

            database.update(MemberInvitesTable.MemberInvites) {
                set(it.inviteUsed, true)
                set(it.inviteUsedBy, jsonMessage.member!!.user!!.id!!.toLong())
                where { it.inviteCode eq jsonMessage.sourceInviteCode.toString() }
            }

            messageCreateEvent.message.addReaction(ReactionEmoji.from(Emojis["✅"]!!))
        } catch (e: Exception) {
            println(">>> Exception... ")
            println(e.message)
        }
    }
}
