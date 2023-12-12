/*
 * Created for the Lost Crafters SMP (https://www.lostcrafterssmp.com)
 * Licensed under the GNU Affero General Public License v3.0
 * See LICENSE.txt for full license information
 */

package ca.fireball1725.lcs.discordbot.listeners

import ca.fireball1725.lcs.discordbot.botConfig
import ca.fireball1725.lcs.discordbot.commands.DownloadBackup
import ca.fireball1725.lcs.discordbot.getServers
import me.jakejmattson.discordkt.arguments.ChoiceArg
import me.jakejmattson.discordkt.commands.commands

fun registerGeneralCommands() =
    commands("general") {
        val prefix = if (botConfig().isDevelopmentEnvironment) "dev" else ""

        // Download world save
        val downloadWorldSaveServers =
            getServers().filter { (_, server) -> server.isBackupDownloadEnabled() }
                .map { (_, server) -> server.getServerPrettyName() }

        if (botConfig().backupDownload.enabled) {
            slash(prefix + "DownloadWorldSave", "Get a link to download the last backup from the minecraft server") {
                execute(ChoiceArg("Server", "Select the server", *downloadWorldSaveServers.toTypedArray())) {
                    val (first) = args
                    val result = DownloadBackup().getWorldBackup(first)

                    println("Result: $result")

                    if (result != null) {
                        respond(result)
                    } else {
                        respond("There was an error")
                    }
                }
            }
        }
    }
