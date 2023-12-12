/*
 * Created for the Lost Crafters SMP (https://www.lostcrafterssmp.com)
 * Licensed under the GNU Affero General Public License v3.0
 * See LICENSE.txt for full license information
 */

package ca.fireball1725.lcs.discordbot.listeners

import ca.fireball1725.lcs.discordbot.botConfig
import ca.fireball1725.lcs.discordbot.whitelist.WhitelistProcessor
import dev.kord.core.event.message.ReactionAddEvent
import dev.kord.core.event.message.ReactionRemoveEvent
import me.jakejmattson.discordkt.dsl.listeners

fun registerListeners() = listeners {
    // Whitelist
    if (botConfig().whitelist.enabled) {
        on<ReactionAddEvent> { WhitelistProcessor().reactionAddEvent(this) }
        on<ReactionRemoveEvent> { WhitelistProcessor().reactionRemoveEvent(this) }
    }
}
