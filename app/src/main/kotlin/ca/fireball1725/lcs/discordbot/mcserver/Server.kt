/*
 * Created for the Lost Crafters SMP (https://www.lostcrafterssmp.com)
 * Licensed under the GNU Affero General Public License v3.0
 * See LICENSE.txt for full license information
 */

package ca.fireball1725.lcs.discordbot.mcserver

class Server(
    private val serverId: String,
    private val serverPrettyName: String,
    private val whitelistPlayerEnabled: Boolean = true,
    private val whitelistCameraEnabled: Boolean = false,
    private val backupDownloadEnabled: Boolean = false,
) {
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
