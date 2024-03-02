/*
 * Created for the Lost Crafters SMP (https://www.lostcrafterssmp.com)
 * Licensed under the GNU Affero General Public License v3.0
 * See LICENSE.txt for full license information
 */

package ca.fireball1725.lcs.discordbot.tasks

import ca.fireball1725.lcs.discordbot.botConfig
import ca.fireball1725.lcs.discordbot.getDatabase
import ca.fireball1725.lcs.discordbot.getGuilds
import ca.fireball1725.lcs.discordbot.helpers.RoleHelper
import dev.kord.core.entity.Guild
import kotlinx.coroutines.flow.toList

class MembersProcessor {

    //private val smpMemberRoleId = botConfig().whitelist.smpMemberRoleId
    suspend fun updateMembersTable() {
        if (! getGuilds().isNullOrEmpty()) {
            val guild: Guild = getGuilds()!![0]
            println("Processing members from ${guild.name}")
            val guildMembers = mutableListOf<Long>()

            // Add members to the database that don't exist
            guild.members.toList().forEach {
                var discordMemberId = it.id.value
                if (RoleHelper().doesMemberHaveRole(it, 1143743030541680662U)) {
                    println("Processing Discord User: ${it.displayName} Id: ${discordMemberId}")
                    getDatabase().updateMemberByDiscordId(discordMemberId, it.displayName)
                }
                guildMembers.add(it.id.value.toLong())

                // Add member roles to database
                getDatabase().deleteDiscordRoles(discordMemberId)
                it.roles.toList().forEach {
                    getDatabase().addDiscordRole(discordMemberId, it.id.value)
                }
            }

            // Disable members that left the discord
            val membersList = getDatabase().getMembers()
            if (membersList != null && membersList.size > 0) {
                membersList.forEach {
                    if (!guildMembers.contains(it.discord_id)) {
                        println("Disabling Discord User: ${it.display_username} Id: ${it.discord_id}")
                        getDatabase().disableMember(it.member_id)
                    }
                }
            }
        }
    }
}