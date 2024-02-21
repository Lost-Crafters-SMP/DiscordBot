/*
 * Created for the Lost Crafters SMP (https://www.lostcrafterssmp.com)
 * Licensed under the GNU Affero General Public License v3.0
 * See LICENSE.txt for full license information
 */

package ca.fireball1725.lcs.discordbot.tasks

import ca.fireball1725.lcs.discordbot.data.database.Server
import ca.fireball1725.lcs.discordbot.data.pterodactyl.GetDirectoryList
import ca.fireball1725.lcs.discordbot.getDatabase
import ca.fireball1725.lcs.discordbot.getPterodactyl
import io.ktor.util.date.getTimeMillis
import java.time.OffsetDateTime
import java.util.UUID

class JsonStatsProcessor {
    fun processJsonStats() {
        val servers: MutableList<Server> = getDatabase().getServers()

        servers.forEach {
            val pterodactylId = it.pterodactyl_id.toString().split("-")[0]
            val serverActive = it.server_live
            val serverId: UUID = it.server_id

            if (serverActive) {
                println("Server Id: ${pterodactylId} Server Active: ${serverActive}")

                val result: GetDirectoryList? = getPterodactyl().getDirectoryList(pterodactylId, "/world/stats")
                if (result != null && result.data.size > 0) {
                    result.data.forEach {
                        val filename: String? = it.attributes!!.name
                        if (filename != null) {
                            val isFileNameUUID = filename.removeSuffix(".json").matches(Regex("\\b[A-Fa-f0-9]{8}-[A-Fa-f0-9]{4}-[A-Fa-f0-9]{4}-[A-Fa-f0-9]{4}-[A-Fa-f0-9]{12}\\b"))
                            if (isFileNameUUID) {
                                val playerUUID: UUID = UUID.fromString(filename.removeSuffix(".json"))
                                val playerStats: String? = getPterodactyl().getFileContents(pterodactylId, "/world/stats/${filename}")
                                if (playerStats != null) {
                                    println("Processing File: ${filename}")
                                    getDatabase().updatePlayerStats(playerUUID, serverId, OffsetDateTime.now(), playerStats)
                                }
                            }
                        }
                    }
                }

                // Check to see if this is a vault hunters server
                if (it.gamemode == "VH") {
                    val result: GetDirectoryList? = getPterodactyl().getDirectoryList(pterodactylId, "/world/data")
                    if (result != null && result.data.size > 0) {
                        result.data.forEach {
                            val filename: String? = it.attributes!!.name
                            if (filename != null && filename.endsWith(".json")) {
                                val fileContents: String? = getPterodactyl().getFileContents(pterodactylId, "/world/data/${filename}")
                                if (fileContents != null) {
                                    println("Processing File: ${filename}")
                                    getDatabase().updateOtherData(serverId, filename.removeSuffix(".json"), OffsetDateTime.now(), fileContents)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}