/*
 * Created for the Lost Crafters SMP (https://www.lostcrafterssmp.com)
 * Licensed under the GNU Affero General Public License v3.0
 * See LICENSE.txt for full license information
 */

package ca.fireball1725.lcs.discordbot.data.config

import com.google.gson.annotations.SerializedName

data class BotConfig(
    @SerializedName("discord_token")
    val discordToken: String,

    @SerializedName("pterodactyl_token")
    val pterodactylToken: String,

    @SerializedName("pterodactyl_url")
    val pterodactylUrl: String,

    @SerializedName("is_dev_env")
    val isDevelopmentEnvironment: Boolean,

    @SerializedName("whitelist")
    val whitelist: BotConfigConfigWhitelist,

    @SerializedName("backup_download")
    val backupDownload: BotConfigConfigBackupDownload,
)

data class BotConfigConfigWhitelist(
    @SerializedName("enabled")
    val enabled: Boolean,

    @SerializedName("whitelist_channel_id")
    val channelId: Long,

    @SerializedName("whitelist_authorized_role_id")
    val authorizedRoleId: Long,
)

data class BotConfigConfigBackupDownload(
    @SerializedName("enabled")
    val enabled: Boolean,
)
