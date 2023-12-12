/*
 * Created for the Lost Crafters SMP (https://www.lostcrafterssmp.com)
 * Licensed under the GNU Affero General Public License v3.0
 * See LICENSE.txt for full license information
 */

package ca.fireball1725.lcs.discordbot.services

import dev.kord.common.entity.Permission
import dev.kord.common.entity.Permissions

object BotPermissions {
    val GUILD_OWNER = Permissions(Permission.ManageGuild)
    val STAFF = Permissions(Permission.ManageMessages)
    val EVERYONE = Permissions(Permission.UseApplicationCommands)
}
