/*
 * Created for the Lost Crafters SMP (https://www.lostcrafterssmp.com)
 * Licensed under the GNU Affero General Public License v3.0
 * See LICENSE.txt for full license information
 */

package ca.fireball1725.lcs.discordbot.listeners

import ca.fireball1725.lcs.discordbot.botConfig
import ca.fireball1725.lcs.discordbot.commands.DownloadBackup
import ca.fireball1725.lcs.discordbot.commands.EditProfile
import ca.fireball1725.lcs.discordbot.helpers.MathHelper
import me.jakejmattson.discordkt.arguments.ChoiceArg
import me.jakejmattson.discordkt.commands.commands

fun registerProfileCommands() =
    commands("profile") {
        val prefix = if (botConfig().isDevelopmentEnvironment) "dev" else ""

        // Set Country
        slash("${prefix}EditProfile", "Edit your profile") {
            execute() {
                val result = EditProfile().getEditProfileLink(this.author.id.value)

                if (result != null) {
                    respond(result)
                } else {
                    respond("There was an error")
                }
            }
        }
    }
