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
    @SerializedName("database_configuration")
    val databaseConfiguration: BotDatabaseConfiguration,
    @SerializedName("tasks")
    val tasks: BotTaskConfiguration,
    @SerializedName("invite_configuration")
    val inviteConfiguration: BotInviteConfiguration
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

data class BotDatabaseConfiguration(
    @SerializedName("jdbc_url")
    val jdbcUrl: String,
    @SerializedName("database_username")
    val databaseUsername: String,
    @SerializedName("database_password")
    val databasePassword: String,
)

data class BotTaskConfiguration(
    @SerializedName("json_processor")
    val jsonProcessorEnabled: Boolean,
    @SerializedName("member_processor")
    val memberProcessorEnabled: Boolean,
    @SerializedName("invite_processor")
    val inviteProcessorEnabled: Boolean,
)

data class BotInviteConfiguration(
    @SerializedName("new_member_info_channel_id")
    val newMemberInfoChannelId: Long,
    @SerializedName("number_invites_on_hand")
    val readyInviteCount: Int,
)