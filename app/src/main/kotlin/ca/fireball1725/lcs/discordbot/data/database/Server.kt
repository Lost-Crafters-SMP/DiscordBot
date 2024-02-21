/*
 * Created for the Lost Crafters SMP (https://www.lostcrafterssmp.com)
 * Licensed under the GNU Affero General Public License v3.0
 * See LICENSE.txt for full license information
 */

package ca.fireball1725.lcs.discordbot.data.database

import java.util.UUID

data class Server(
    val server_id: UUID,
    val pterodactyl_id: UUID,
    val server_name: String,
    val description: String,
    val gamemode: String?,
    val pack_url: String?,
    val live_map_url: String?,
    val server_live: Boolean,
    val server_started: String?,
    val server_finished: String?,
    val game_name: String?,
    val game_url: String?,
    val game_icon: String?,
)
