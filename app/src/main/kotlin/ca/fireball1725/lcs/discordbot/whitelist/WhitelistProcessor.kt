package ca.fireball1725.lcs.discordbot.whitelist

import ca.fireball1725.lcs.discordbot.botConfig
import ca.fireball1725.lcs.discordbot.helpers.MinecraftUserObject
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
        if (reactionAddEvent.channelId.value != botConfig().whitelist.whitelistChannelId.toULong())
            return

        // Validate that the user isn't the bot
        if (reactionAddEvent.getUser().isBot)
            return

        // todo: add logging maybe
        println("Emoji Name: ${reactionAddEvent.emoji.name}")

        val userName = reactionAddEvent.getMessage().content
        println("Whitelist: $userName")

        // Take an action depending on the emoji
        when(reactionAddEvent.emoji.name) {
            "\uD83D\uDC4D" -> { // Thumbs Up
                if (!validateAuthorization(reactionAddEvent))
                    return
                whitelistUser(userName, MemberType.REGULAR_MEMBER, reactionAddEvent)
            }
            "\uD83D\uDCF7" -> { // Camera Emoji
                if (!validateAuthorization(reactionAddEvent))
                    return
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
        val inGroup = RoleHelper().doesMemberHaveRole(
            reactionAddEvent.getUserAsMember(),
            botConfig().whitelist.whitelistAuthorizedRoleId.toULong()
        )

        // if the user isn't authorized let them know and remove the reaction
        if (!inGroup) {
            reactionAddEvent.getMessage().deleteReaction(reactionAddEvent.userId, reactionAddEvent.emoji)
            reactionAddEvent.getUser().sendPrivateMessage("You do not have permission to whitelist users")
        }

        return inGroup
    }

    private suspend fun whitelistUser(username: String, accountType: MemberType, reactionAddEvent: ReactionAddEvent) {
        // todo: at some point add database
        val successEmoji = Emojis["✅"]!!

        // Convert minecraft username to uuid
        val minecraftUser = MinecraftUserObject().getMinecraftUserFromUsername(username)

        // validate that we have a valid minecraft user
        if (minecraftUser == null) {
            val messagePoster = reactionAddEvent.getMessage().author!!.mention

            reactionAddEvent.getMessage().deleteReaction(reactionAddEvent.userId, reactionAddEvent.emoji)
            reactionAddEvent.getChannel().createMessage(
                "${messagePoster}: `${username}` is not a valid minecraft account, " +
                        "please check the account name and try again..."
            )
            return
        }

        // todo: actually whitelist here ...

        reactionAddEvent.getMessage().addReactions(ReactionEmoji.from(successEmoji))
    }
}
