/*
 * Created for the Lost Crafters SMP (https://www.lostcrafterssmp.com)
 * Licensed under the GNU Affero General Public License v3.0
 * See LICENSE.txt for full license information
 */

package ca.fireball1725.lcs.discordbot.helpers

class MathHelper {
    fun humanReadableFileSize(bytes: Long): String {
        if (bytes < 1024) return "$bytes B" // Bytes
        var size = bytes.toDouble()
        val units = listOf("KB", "MB", "GB", "TB", "PB", "EB")
        val unitIndex = (Math.log(size) / Math.log(1024.0)).toInt()
        size /= Math.pow(1024.0, unitIndex.toDouble())
        return String.format("%.1f %s", size, units[unitIndex - 1])
    }
}