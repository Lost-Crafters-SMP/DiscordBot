package ca.fireball1725.lcs.discordbot.mcserver

class Server constructor(
    private val serverId: String,
    private val serverPrettyName: String,
    private val whitelistPlayerEnabled: Boolean = true,
    private val whitelistCameraEnabled: Boolean = false,
    private val backupDownloadEnabled: Boolean = false
){
    fun getServerId(): String {
        return this.serverId
    }

    fun getServerPrettyName(): String {
        return this.serverPrettyName
    }

    fun isWhitelistPlayerEnabled(): Boolean {
        return this.whitelistPlayerEnabled
    }

    fun isWhitelistCameraEnabled(): Boolean {
        return this.whitelistCameraEnabled
    }

    fun isBackupDownloadEnabled(): Boolean {
        return this.backupDownloadEnabled
    }
}