/*
 * Created for the Lost Crafters SMP (https://www.lostcrafterssmp.com)
 * Licensed under the GNU Affero General Public License v3.0
 * See LICENSE.txt for full license information
 */

package ca.fireball1725.lcs.discordbot.data.pterodactyl

import com.google.gson.annotations.SerializedName

data class GetDownloadBackup(
    @SerializedName("attributes")
    val downloadBackupAttributes: GetDownloadBackupAttributes? = GetDownloadBackupAttributes()
)

data class GetDownloadBackupAttributes (
    @SerializedName("url")
    var url: String? = null
)
