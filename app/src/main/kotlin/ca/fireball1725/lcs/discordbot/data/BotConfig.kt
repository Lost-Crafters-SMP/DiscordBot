package ca.fireball1725.lcs.discordbot.data

data class BotConfig(
    val token: String,
    val pterodactylToken: String,
    val pterodactylUrl: String,
    val whitelist: BotConfigWhitelist
)
