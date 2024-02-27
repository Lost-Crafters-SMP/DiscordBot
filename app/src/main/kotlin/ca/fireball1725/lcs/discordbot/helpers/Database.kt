/*
 * Created for the Lost Crafters SMP (https://www.lostcrafterssmp.com)
 * Licensed under the GNU Affero General Public License v3.0
 * See LICENSE.txt for full license information
 */

package ca.fireball1725.lcs.discordbot.helpers

import ca.fireball1725.lcs.discordbot.botConfig
import ca.fireball1725.lcs.discordbot.data.database.Member
import ca.fireball1725.lcs.discordbot.data.database.Server
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.SQLType
import java.sql.Timestamp
import java.sql.Types
import java.time.OffsetDateTime
import java.util.UUID

class Database {
    private var connection: Connection? = null
    fun connect() {
        val jdbcUrl = botConfig().databaseConfiguration.jdbcUrl
        connection = DriverManager.getConnection(
            jdbcUrl,
            botConfig().databaseConfiguration.databaseUsername,
            botConfig().databaseConfiguration.databasePassword
        )

        if (connection != null && connection!!.isValid(0)) {
            println("[Database] Connection to database is valid")
        } else if (connection == null) {
            println("[Database] Connection is null")
        } else {
            println("[Database] Failed to connect to database")
        }
    }

    fun getMembers(): MutableList<Member> {
        val query = connection!!.prepareStatement("SELECT * FROM members")

        val resultSet = query.executeQuery()
        val members = mutableListOf<Member>()

        while (resultSet.next()) {
            val member = Member(
                member_id = UUID.fromString(resultSet.getString("member_id")),
                display_username = resultSet.getString("display_username"),
                discord_id = resultSet.getLong("discord_id"),
                pronouns = resultSet.getString("pronouns"),
                country = resultSet.getString("country"),
                description = resultSet.getString("description"),
                show_on_website = resultSet.getBoolean("show_on_website")
            )
            members.add(member)
        }

        return members
    }

    fun getMemberFromDiscordId(discordId: ULong): Member? {
        val query = connection!!.prepareStatement("SELECT * FROM members where discord_id=${discordId}")

        val resultSet = query.executeQuery()
        val members = mutableListOf<Member>()

        while (resultSet.next()) {
            val member = Member(
                member_id = UUID.fromString(resultSet.getString("member_id")),
                display_username = resultSet.getString("display_username"),
                discord_id = resultSet.getLong("discord_id"),
                pronouns = resultSet.getString("pronouns"),
                country = resultSet.getString("country"),
                description = resultSet.getString("description"),
                show_on_website = resultSet.getBoolean("show_on_website")
            )
            members.add(member)
        }

        if (members.size > 0) {
            return members[0]
        } else {
            return null
        }
    }

    fun createSessionToken(memberId: UUID): UUID? {
        val query = connection!!.prepareStatement(
            "INSERT INTO sessions (\"member_uuid\")" +
                    "VALUES ('${memberId}')" +
                    "RETURNING \"session_uuid\""
        )

        val resultSet = query.executeQuery()

        var loginToken: UUID? = null
        while (resultSet.next()) {
            loginToken = UUID.fromString(resultSet.getString("session_uuid"))
        }

        if (loginToken != null)
            return loginToken;

        return null
    }

    fun updateMemberByDiscordId(
        discordId: ULong,
        displayName: String,
    ) {
        val query = connection!!.prepareStatement(
            "INSERT INTO members " +
                    "(discord_id, display_username, show_on_website) " +
                    "VALUES(?, ?, ?) " +
                    "ON CONFLICT (discord_id) DO NOTHING"
        )
        query.setObject(1, discordId.toLong())
        query.setObject(2, displayName)
        query.setBoolean(3, false)
        query.executeUpdate()
    }

    fun addMinecraftAccount(
        memberId: UUID,
        minecraftAccountId: UUID,
        minecraftUserName: String,
        accountCamera: Boolean = false,
        accountDefault: Boolean = false
    ) {
        val query = connection!!.prepareStatement(
            "INSERT INTO minecraft_accounts " +
            "(member_uuid, minecraft_account_uuid, minecraft_username, camera, \"default\") " +
            "VALUES(?, ?, ?, ?, ?) " +
            "ON CONFLICT (minecraft_account_uuid) DO NOTHING"
        )
        query.setObject(1, memberId)
        query.setObject(2, minecraftAccountId)
        query.setString(3, minecraftUserName)
        query.setBoolean(4, accountCamera)
        query.setBoolean(5, accountDefault)
        query.executeUpdate()
    }

    fun updateProfileName(
        memberId: UUID,
        profileName: String,
    ) {
        val query = connection!!.prepareStatement(
            "UPDATE members " +
                    "SET display_username = '${profileName}' "+
                    "WHERE member_id = '${memberId}'"
        )
        query.executeUpdate()
    }

    fun updateWebsiteVisibility(
        memberId: UUID,
        visible: Boolean,
    ) {
        val query = connection!!.prepareStatement(
            "UPDATE members " +
            "SET show_on_website = '${visible}' " +
            "WHERE member_id = '${memberId}'"
        )
        query.executeUpdate()
    }

    fun getMinecraftAccountCount(
        memberId: UUID,
    ): Int {
        val query = connection!!.prepareStatement(
            "SELECT record_uuid " +
                    "FROM minecraft_accounts " +
                    "WHERE member_uuid='${memberId}'"
        )
        val resultSet = query.executeQuery()

        var size: Int = 0
        while(resultSet.next()) {
            size++
        }
        return size
    }

    fun updateJsonFile(
        serverId: UUID,
        fileName: String,
        filePath: String,
        fileTimeStamp: OffsetDateTime,
        fileContent: String
    ) {
        val query = connection!!.prepareStatement(
            "INSERT INTO server_files " +
                    "(server_uuid, file_name, file_path, file_timestamp, file_content) " +
                    "VALUES (?, ?, ?, ?, ?) " +
                    "ON CONFLICT (server_uuid, file_name, file_path) DO UPDATE " +
                    "SET file_timestamp = EXCLUDED.file_timestamp, file_content = EXCLUDED.file_content"
        )
        query.setObject(1, serverId)
        query.setString(2, fileName)
        query.setString(3, filePath)
        query.setObject(4, fileTimeStamp)
        query.setObject(5, fileContent)
        query.executeUpdate()
    }

    fun getServers(): MutableList<Server> {
        val query = connection!!.prepareStatement(
            "SELECT server_id, pterodactyl_id, server_name, description, gamemode, pack_url, live_map_url, server_live, server_started, server_finished, game_name, game_url, game_icon " +
                    "FROM servers " +
                    "LEFT JOIN games ON servers.game_id = games.game_id "
        )

        val resultSet = query.executeQuery()
        val servers = mutableListOf<Server>()

        while (resultSet.next()) {
            val server = Server(
                server_id = UUID.fromString(resultSet.getString("server_id")),
                pterodactyl_id = UUID.fromString(resultSet.getString("pterodactyl_id")),
                server_name = resultSet.getString("server_name"),
                description = resultSet.getString("description"),
                gamemode = resultSet.getString("gamemode"),
                pack_url = resultSet.getString("pack_url"),
                live_map_url = resultSet.getString("live_map_url"),
                server_live = resultSet.getBoolean("server_live"),
                server_started = resultSet.getString("server_started"),
                server_finished = resultSet.getString("server_finished"),
                game_name = resultSet.getString("game_name"),
                game_url = resultSet.getString("game_url"),
                game_icon = resultSet.getString("game_icon"),
            )
            servers.add(server)
        }

        return servers
    }
}