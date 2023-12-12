/*
 * Created for the Lost Crafters SMP (https://www.lostcrafterssmp.com)
 * Licensed under the GNU Affero General Public License v3.0
 * See LICENSE.txt for full license information
 */

package ca.fireball1725.lcs.discordbot.data

import java.util.UUID

data class MinecraftUserDataPlayer(
    val username: String,
    val id: UUID,
    val raw_id: String,
    val avatar: String
)
