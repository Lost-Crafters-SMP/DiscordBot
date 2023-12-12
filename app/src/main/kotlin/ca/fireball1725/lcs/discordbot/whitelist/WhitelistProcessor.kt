/*
 * Created for the Lost Crafters SMP (https://www.lostcrafterssmp.com)
 * Licensed under the GNU Affero General Public License v3.0
 * See LICENSE.txt for full license information
 */

package ca.fireball1725.lcs.discordbot.whitelist

import ca.fireball1725.lcs.discordbot.botConfig
import ca.fireball1725.lcs.discordbot.getPterodactyl
import ca.fireball1725.lcs.discordbot.getServers
import ca.fireball1725.lcs.discordbot.helpers.MinecraftUserHelper
import ca.fireball1725.lcs.discordbot.helpers.RoleHelper
import dev.kord.core.entity.ReactionEmoji
import dev.kord.core.event.message.ReactionAddEvent
import dev.kord.core.event.message.ReactionRemoveEvent
import dev.kord.x.emoji.Emojis
import dev.kord.x.emoji.from
import me.jakejmattson.discordkt.extensions.addReactions
import me.jakejmattson.discordkt.extensions.sendPrivateMessage

class WhitelistProcessor {
    suspend fun reactionAddEvent(reactionAddEvent: ReactionAddEvent) {
        // Validate that the message is in the whitelist channel
        if (reactionAddEvent.channelId.value != botConfig().whitelist!!.channelId!!.toULong()) {
            return
        }

        // Validate that the user isn't the bot
        if (reactionAddEvent.getUser().isBot) {
            return
        }

        // todo: add logging maybe

        val userName = reactionAddEvent.getMessage().content

        // todo: validate the message doesn't have a ✅ from the bot already

        // Take an action depending on the emoji
        when (reactionAddEvent.emoji.name) {
            "\uD83D\uDC4D" -> { // Thumbs Up
                if (!validateAuthorization(reactionAddEvent)) {
                    return
                }
                whitelistUser(userName, MemberType.REGULAR_MEMBER, reactionAddEvent)
            }
            "\uD83D\uDCF7" -> { // Camera Emoji
                if (!validateAuthorization(reactionAddEvent)) {
                    return
                }
                whitelistUser(userName, MemberType.CAMERA_ACCOUNT, reactionAddEvent)
            }
            else -> {
                // take no action, as there are no other emoji roles
            }
        }
    }

    fun reactionRemoveEvent(reactionRemoveEvent: ReactionRemoveEvent) {
        // todo: remove whitelist if the reaction is removed maybe?
    }

    private suspend fun validateAuthorization(reactionAddEvent: ReactionAddEvent): Boolean {
        // Validate the user has permission to whitelist a user
        val inGroup =
            RoleHelper().doesMemberHaveRole(
                reactionAddEvent.getUserAsMember(),
                botConfig().whitelist!!.authorizedRoleId!!.toULong(),
            )

        // if the user isn't authorized let them know and remove the reaction
        if (!inGroup) {
            reactionAddEvent.getMessage().deleteReaction(reactionAddEvent.userId, reactionAddEvent.emoji)
            reactionAddEvent.getUser().sendPrivateMessage("You do not have permission to whitelist users")
        }

        return inGroup
    }

    private suspend fun whitelistUser(
        username: String,
        accountType: MemberType,
        reactionAddEvent: ReactionAddEvent,
    ) {
        // todo: at some point add database
        val successEmoji = Emojis["✅"]!!

        // Convert minecraft username to uuid
        val minecraftUser = MinecraftUserHelper().getMinecraftUserFromUsername(username)

        // validate that we have a valid minecraft user
        if (minecraftUser == null) {
            val messagePoster = reactionAddEvent.getMessage().author!!.mention

            reactionAddEvent.getMessage().deleteReaction(reactionAddEvent.userId, reactionAddEvent.emoji)
            reactionAddEvent.getChannel().createMessage(
                "$messagePoster: `$username` is not a valid minecraft account, " +
                    "please check the account name and try again...",
            )
            return
        }

        // whitelist server account
        getServers().forEach { (_, server) ->
            if (server.isWhitelistPlayerEnabled()) {
                getPterodactyl().sendCommand(
                    server.getServerId(),
                    "/whitelist add $username",
                )
            }
        }

        // whitelist camera account
        if (accountType == MemberType.CAMERA_ACCOUNT) {
            getServers().forEach { (_, server) ->
                if (server.isWhitelistCameraEnabled()) {
                    getPterodactyl().sendCommand(
                        server.getServerId(),
                        "/lp user ${minecraftUser.data.player.id} group add camera",
                    )
                    getPterodactyl().sendCommand(
                        server.getServerId(),
                        "/scoreboard players set $username player_tags 99",
                    )
                }
            }
        }

        reactionAddEvent.getMessage().addReactions(ReactionEmoji.from(successEmoji))
    }
}
