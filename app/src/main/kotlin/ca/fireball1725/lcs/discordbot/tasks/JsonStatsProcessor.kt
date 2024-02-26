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
import ca.fireball1725.lcs.discordbot.helpers.TimeHelper
import java.time.OffsetDateTime
import java.util.UUID

class JsonStatsProcessor {
    fun getLevelName(serverProperties: String): String? {
        val regex = "level-name=([^\n\r]*)".toRegex()
        val matchResult = regex.find(serverProperties)
        return matchResult?.groups?.get(1)?.value
    }

    fun processServerDirectory(serverId: UUID, path: String, pterodactylId: UUID, isRoot: Boolean = false, inputTimestamp: OffsetDateTime = OffsetDateTime.now()) {
        if (path.contains("lootr")) // skip lootr directory as we don't need this and it's large
            return

        val pterodactylServerId = pterodactylId.toString().split("-")[0];

        var fileTimestamp: OffsetDateTime = inputTimestamp
        // get the last update time from the last_update.json
        if (isRoot) {
            val lastUpdateJson = getPterodactyl().getFileContents(pterodactylServerId, "${path}/last_update.json")
            if (lastUpdateJson != null)
                fileTimestamp = TimeHelper().getTimestampAsOffsetDateTime(lastUpdateJson)
        }

        // Get files
        val result: GetDirectoryList? = getPterodactyl().getDirectoryList(pterodactylServerId, path)
        if (result != null && result.data.size > 0) {
            result.data.forEach {
                val filename: String? = it.attributes!!.name
                val isFile: Boolean? = it.attributes!!.isFile

                // Validate that we have a valid file
                if (filename != null && isFile != null) {

                    // Deal with a subdirectory
                    if (!isFile) {
                        processServerDirectory(serverId, "${path}/${filename}", pterodactylId, inputTimestamp = fileTimestamp)
                    }

                    // Process file
                    if (isFile) {
                        var filenameString = filename.substringBeforeLast(".")
                        val fileExtention = filename.substringAfterLast(".").lowercase()

                        if (filenameString.endsWith(".dat"))
                            filenameString = filenameString.substringBeforeLast(".")

                        if (fileExtention == "json" && filenameString != "last_update") {
                            val fileContents: String? = getPterodactyl().getFileContents(pterodactylServerId, "${path}/${filename}")
                            if (fileContents != null) {
                                println("Processing ${path}/${filename}...")
                                getDatabase().updateJsonFile(serverId, filenameString, path, fileTimestamp, fileContents)
                            }
                        }
                    }
                }
            }
        }
    }

    fun processJsonStats() {
        val servers: MutableList<Server> = getDatabase().getServers()

        servers.forEach {
            val pterodactylId = it.pterodactyl_id.toString().split("-")[0]
            val serverActive = it.server_live

            val serverProperties = getPterodactyl().getFileContents(pterodactylId, "/server.properties")

            if (serverActive && serverProperties != null) {
                val levelName = getLevelName(serverProperties) ?: "world"

                println("Server Id: ${pterodactylId} Server Active: ${serverActive} Level Name: ${levelName}")

                processServerDirectory(it.server_id, "/${levelName}/playerdata", it.pterodactyl_id, isRoot = true)
                processServerDirectory(it.server_id, "/${levelName}/stats", it.pterodactyl_id, isRoot = true)
                processServerDirectory(it.server_id, "/${levelName}/data", it.pterodactyl_id, isRoot = true)
                if (it.gamemode == "VH") {
                    // this is specific to vault hunters servers
                    processServerDirectory(it.server_id, "/playerSnapshots", it.pterodactyl_id, isRoot = true)
                }
            }
        }
    }
}