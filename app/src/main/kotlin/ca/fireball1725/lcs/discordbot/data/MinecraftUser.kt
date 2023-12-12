/*
 * Created for the Lost Crafters SMP (https://www.lostcrafterssmp.com)
 * Licensed under the GNU Affero General Public License v3.0
 * See LICENSE.txt for full license information
 */

package ca.fireball1725.lcs.discordbot.data

data class MinecraftUser(
    val code: String,
    val message: String,
    val data: MinecraftUserData,
    val success: Boolean
)
