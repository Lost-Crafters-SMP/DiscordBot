/*
 * Created for the Lost Crafters SMP (https://www.lostcrafterssmp.com)
 * Licensed under the GNU Affero General Public License v3.0
 * See LICENSE.txt for full license information
 */

package ca.fireball1725.lcs.discordbot.helpers

import ca.fireball1725.lcs.discordbot.botConfig
import org.ktorm.database.Database

class DatabaseNew {
    fun connect(): Database {
        return Database.connect(
            url = botConfig().databaseConfiguration.jdbcUrl,
            driver = "org.postgresql.Driver",
            user = botConfig().databaseConfiguration.databaseUsername,
            password = botConfig().databaseConfiguration.databasePassword
        )
    }
}
