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
