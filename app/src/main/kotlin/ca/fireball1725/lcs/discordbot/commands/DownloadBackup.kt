/*
 * Created for the Lost Crafters SMP (https://www.lostcrafterssmp.com)
 * Licensed under the GNU Affero General Public License v3.0
 * See LICENSE.txt for full license information
 */

package ca.fireball1725.lcs.discordbot.commands

import ca.fireball1725.lcs.discordbot.data.pterodactyl.GetWorldBackupDataAttributes
import ca.fireball1725.lcs.discordbot.getPterodactyl
import ca.fireball1725.lcs.discordbot.getServer
import ca.fireball1725.lcs.discordbot.helpers.MathHelper
import ca.fireball1725.lcs.discordbot.helpers.ServerHelper
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class DownloadBackup {
    fun GetWorldBackup(server: String): String? {
        val serverId = ServerHelper().getServerIdFromPrettyName(server) ?: return null
        val server = getServer(serverId)

        val backups = getPterodactyl().getServerBackup(serverId)

        var lastBackup: GetWorldBackupDataAttributes? = null
        backups!!.worldBackupData.forEach {
            lastBackup = it.worldBackupDataAttributes
            if (it.worldBackupDataAttributes.isSuccessful != null && it.worldBackupDataAttributes.isSuccessful!!) {
                return@forEach
            }
        }

        if (lastBackup == null) {
            return null
        }

        val downloadUrl = getPterodactyl().getDownloadBackup(serverId, lastBackup!!.uuid!!)

        val pattern = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX")
        val timeStamp = LocalDateTime.parse(lastBackup!!.completedAt, pattern)
        val unixTimeStamp = timeStamp.toEpochSecond(ZoneOffset.UTC)

        var resultString = ""
        resultString += "# ${server!!.getServerPrettyName()} World Backup\n"
        resultString += "Backup completed at: <t:$unixTimeStamp:f>\n"
        resultString += "Filesize: ${MathHelper().humanReadableFileSize(lastBackup!!.bytes!!)}\n"
        resultString += "[Download](<$downloadUrl>)\n"
        resultString += "_(download link is one time use and is valid for 15 minutes)_"
        return resultString
    }
}
