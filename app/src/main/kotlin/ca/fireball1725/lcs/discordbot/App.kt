/*
 * Created for the Lost Crafters SMP (https://www.lostcrafterssmp.com)
 * Licensed under the GNU Affero General Public License v3.0
 * See LICENSE.txt for full license information
 */

package ca.fireball1725.lcs.discordbot

import ca.fireball1725.lcs.discordbot.data.Configuration
import ca.fireball1725.lcs.discordbot.data.config.BotConfig
import ca.fireball1725.lcs.discordbot.helpers.Database
import ca.fireball1725.lcs.discordbot.mcserver.Pterodactyl
import ca.fireball1725.lcs.discordbot.mcserver.Server
import ca.fireball1725.lcs.discordbot.services.BotPermissions
import ca.fireball1725.lcs.discordbot.tasks.JsonStatsProcessor
import ca.fireball1725.lcs.discordbot.tasks.MembersProcessor
import com.google.gson.Gson
import dev.kord.common.annotation.KordPreview
import dev.kord.core.entity.Guild
import dev.kord.gateway.Intents
import dev.kord.gateway.PrivilegedIntent
import dev.kord.x.emoji.Emojis
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import me.jakejmattson.discordkt.dsl.CommandException
import me.jakejmattson.discordkt.dsl.ListenerException
import me.jakejmattson.discordkt.dsl.bot
import me.jakejmattson.discordkt.locale.Language
import java.awt.Color
import java.io.Reader
import java.nio.file.FileSystems
import java.nio.file.Files
import java.nio.file.Path
import java.util.Timer
import kotlin.concurrent.scheduleAtFixedRate

private val configPath = FileSystems.getDefault().getPath("config", "botConfig.json")
private val botConfig: BotConfig = loadBotConfig(configPath)

private val pterodactyl: Pterodactyl = Pterodactyl(botConfig.pterodactylToken, botConfig.pterodactylUrl)

private val servers: MutableMap<String, Server> = mutableMapOf()

private val database: Database = Database()

private var guilds: List<Guild>? = null

@OptIn(PrivilegedIntent::class)
@KordPreview
suspend fun main(args: Array<String>) {
    // Connect to the database
    database.connect()

    // todo: Load the servers from database
    // todo: really need to do this...

//    servers["b1107111"] =
//        Server(
//            "b1107111",
//            "SMP Season 1",
//            whitelistCameraEnabled = true,
//            backupDownloadEnabled = true,
//        )

    servers["80966603"] =
        Server(
            "80966603",
            "Creative Test Server",
        )

    servers["7f01a766"] =
        Server(
            "7f01a766",
            "Vault Hunters",
        )

//    servers["e932250f"] =
//        Server(
//            "e932250f",
//            "FTB Arcanum Institution",
//        )

    // println(App().greeting)
    bot(botConfig.discordToken) {
        val configuration = data("config/config.json") { Configuration() }

        prefix {
            configuration.prefix
        }

        configure {
            // Allow a mention to be used in front of commands ('@Bot help').
            mentionAsPrefix = true

            // Whether to show registered entity information on startup.
            logStartup = true

            // Whether to generate documentation for registered commands.
            documentCommands = true

            // Whether to recommend commands when an invalid one is invoked.
            recommendCommands = true

            // Allow users to search for a command by typing 'search <command name>'.
            searchCommands = true

            // Remove a command invocation message after the command is executed.
            deleteInvocation = true

            // Allow slash commands to be invoked as text commands.
            dualRegistry = true

            // An emoji added when a command is invoked (use 'null' to disable this).
            commandReaction = Emojis.eyes

            // A color constant for your bot - typically used in embeds.
            theme = Color(0xFF69B4)

            // Configure the Discord Gateway intents for your bot.
            intents = Intents.all

            // Set the default permission required for slash commands.
            defaultPermissions = BotPermissions.EVERYONE
        }

        onException {
            if (exception is IllegalArgumentException) {
                return@onException
            }

            when (this) {
                is CommandException -> println("Exception '${exception::class.simpleName}' in command ${event.command?.name}")
                is ListenerException -> println("Exception '${exception::class.simpleName}' in listener ${event::class.simpleName}")
            }
        }

        // The Discord presence shown on your bot.
        presence {
            playing("Lost Crafters SMP Discord Bot - v0.1.0")
        }

        // This is run once the bot has finished setup and logged in.
        onStart {
            guilds = kord.guilds.toList()
            println("Guilds: ${guilds!!.joinToString { it.name }}")
        }

        // Configure the locale for this bot.
        localeOf(Language.EN) {
            helpName = "Help"
            helpCategory = "Utility"
            commandRecommendation = "Recommendation: {0}"
        }

        // Schedule processing json files
        Timer().scheduleAtFixedRate(10000, 1000 * 60 * 15) { // 15 minutes
            JsonStatsProcessor().processJsonStats()
        }

        // Schedule member updates
        Timer().scheduleAtFixedRate(10000, 1000 * 60 * 15) {// 15 minutes
            runBlocking {
                MembersProcessor().updateMembersTable()
            }
        }
    }
}

private fun loadBotConfig(path: Path): BotConfig {
    val reader: Reader = Files.newBufferedReader(path)
    return Gson().fromJson(reader, BotConfig::class.java)
}

fun botConfig(): BotConfig {
    return botConfig
}

fun getPterodactyl(): Pterodactyl {
    return pterodactyl
}

fun getServers(): MutableMap<String, Server> {
    return servers
}

fun getServer(serverId: String): Server? {
    return if (serverId.contains(serverId)) {
        servers[serverId]
    } else {
        null
    }
}

fun getDatabase(): Database {
    return database
}

fun getGuilds(): List<Guild>? {
    return guilds
}
