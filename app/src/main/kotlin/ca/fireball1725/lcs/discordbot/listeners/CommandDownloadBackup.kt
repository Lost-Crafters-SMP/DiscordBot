package ca.fireball1725.lcs.discordbot.listeners

import ca.fireball1725.lcs.discordbot.commands.DownloadBackup
import ca.fireball1725.lcs.discordbot.getServers
import me.jakejmattson.discordkt.arguments.ChoiceArg
import me.jakejmattson.discordkt.commands.commands

fun registerGeneralCommands() = commands("general") {
    // Download world save
    val downloadWorldSaveServers = getServers().filter { (_, server) -> server.isBackupDownloadEnabled() }
        .map { (_, server) -> server.getServerPrettyName() }

    if (false) {
        slash("DownloadWorldSave", "Get a link to download the last backup from the minecraft server") {
            execute(ChoiceArg("Server", "Select the server", *downloadWorldSaveServers.toTypedArray())) {
                val (first) = args
                val result = DownloadBackup().GetWorldBackup(first)

                println("Result: ${result}")

                if (result != null)
                    respond(result)
                else
                    respond("There was an error")
            }
        }
    }
}
