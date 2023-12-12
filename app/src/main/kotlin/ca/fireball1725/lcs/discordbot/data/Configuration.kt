/*
 * Created for the Lost Crafters SMP (https://www.lostcrafterssmp.com)
 * Licensed under the GNU Affero General Public License v3.0
 * See LICENSE.txt for full license information
 */

package ca.fireball1725.lcs.discordbot.data

import dev.kord.common.entity.Snowflake
import kotlinx.serialization.Serializable
import me.jakejmattson.discordkt.commands.commands
import me.jakejmattson.discordkt.dsl.Data
import me.jakejmattson.discordkt.extensions.pfpUrl

@Serializable
data class Configuration(
    val botOwner: Snowflake = Snowflake(93194491795607552),
    val prefix: String = "smp!",
) : Data()

fun dataCommands(configuration: Configuration) =
    commands("Info") {
        slash("BotInfo", "Display bot information") {
            execute {
                val owner = discord.kord.getUser(configuration.botOwner)!!

                respond {
                    title = owner.username
                    description = "Todo, something useful"

                    thumbnail {
                        url = owner.pfpUrl
                    }
                }
            }
        }
    }
