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

            guild.members.toList().forEach {
                if (RoleHelper().doesMemberHaveRole(it, 1143743030541680662U)) {
                    println("Processing Discord User: ${it.displayName} Id: ${it.id.value}")
                    getDatabase().updateMemberByDiscordId(it.id.value, it.displayName)
                }
            }
        }
    }
}