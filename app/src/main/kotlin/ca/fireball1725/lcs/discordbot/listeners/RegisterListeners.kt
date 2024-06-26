/*
 * Created for the Lost Crafters SMP (https://www.lostcrafterssmp.com)
 * Licensed under the GNU Affero General Public License v3.0
 * See LICENSE.txt for full license information
 */

package ca.fireball1725.lcs.discordbot.listeners

import ca.fireball1725.lcs.discordbot.botConfig
import ca.fireball1725.lcs.discordbot.tasks.InviteProcessor
import ca.fireball1725.lcs.discordbot.tasks.MembersProcessor
import ca.fireball1725.lcs.discordbot.whitelist.WhitelistProcessor
import dev.kord.core.event.guild.MemberJoinEvent
import dev.kord.core.event.guild.MemberLeaveEvent
import dev.kord.core.event.message.MessageCreateEvent
import dev.kord.core.event.message.ReactionAddEvent
import dev.kord.core.event.message.ReactionRemoveEvent
import me.jakejmattson.discordkt.dsl.listeners

fun registerListeners() =
    listeners {
        // Whitelist
        if (botConfig().whitelist!!.enabled) {
            on<ReactionAddEvent> { WhitelistProcessor().reactionAddEvent(this) }
            on<ReactionRemoveEvent> { WhitelistProcessor().reactionRemoveEvent(this) }
        }

        if (botConfig().tasks.inviteProcessorEnabled) {
            println("Registered...")
            on<MemberJoinEvent> { InviteProcessor().memberJoinEvent(this) } // refresh invite codes
            on<MessageCreateEvent> { InviteProcessor().channelMessageEvent(this) } // check for invite metadata message
        }

        // Update the members table when someone joins or leaves
        on<MemberJoinEvent> { MembersProcessor().updateMembersTable() }
        on<MemberLeaveEvent> { MembersProcessor().updateMembersTable() }
    }
