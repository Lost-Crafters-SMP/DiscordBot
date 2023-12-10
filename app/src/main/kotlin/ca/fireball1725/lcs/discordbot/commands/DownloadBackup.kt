package ca.fireball1725.lcs.discordbot.commands

import ca.fireball1725.lcs.discordbot.getPterodactyl
import ca.fireball1725.lcs.discordbot.helpers.MathHelper
import ca.fireball1725.lcs.discordbot.helpers.ServerHelper

class DownloadBackup {
    fun GetWorldBackup(server: String): String? {
        val serverId = ServerHelper().getServerIdFromPrettyName(server) ?: return null

        val backups = getPterodactyl().getServerBackup(serverId)

        val lastBackup = backups!!.worldBackupData.last().worldBackupDataAttributes
        val downloadUrl = getPterodactyl().getDownloadBackup(serverId, lastBackup.uuid!!)

        val resultString = "Backup Completed At: ${lastBackup.completedAt}, Size: ${MathHelper().humanReadableFileSize(lastBackup.bytes!!)}, Url: ${downloadUrl}"
        //return "Sorry, no backup exists to download..."
        return resultString
    }
}
