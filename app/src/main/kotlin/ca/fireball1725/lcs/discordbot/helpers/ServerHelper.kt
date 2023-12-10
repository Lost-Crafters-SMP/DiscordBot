package ca.fireball1725.lcs.discordbot.helpers

import ca.fireball1725.lcs.discordbot.getServers
import ca.fireball1725.lcs.discordbot.mcserver.Server

class ServerHelper {
    fun getServerIdFromPrettyName(prettyName: String): String? {
        getServers().forEach { (_, server) ->
            if (server.getServerPrettyName().equals(prettyName))
                return server.getServerId()
        }

        return null
    }

    fun getServerFromPrettyName(prettyName: String): Server? {
        getServers().forEach { (_, server) ->
            if (server.getServerPrettyName().equals(prettyName))
                return server
        }

        return null
    }
}