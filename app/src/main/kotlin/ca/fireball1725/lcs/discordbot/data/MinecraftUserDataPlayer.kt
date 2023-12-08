package ca.fireball1725.lcs.discordbot.data

import java.util.UUID

data class MinecraftUserDataPlayer(
    val username: String,
    val id: UUID,
    val raw_id: String,
    val avatar: String
)
