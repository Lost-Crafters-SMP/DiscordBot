package ca.fireball1725.lcs.discordbot.data

data class BotConfigWhitelist(
    val enabled: Boolean,
    val whitelistChannelId: Long,
    val whitelistAuthorizedRoleId: Long
)
