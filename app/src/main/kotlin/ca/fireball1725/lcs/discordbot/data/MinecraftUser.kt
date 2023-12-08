package ca.fireball1725.lcs.discordbot.data

data class MinecraftUser(
    val code: String,
    val message: String,
    val data: MinecraftUserData,
    val success: Boolean
)
