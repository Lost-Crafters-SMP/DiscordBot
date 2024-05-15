/*
 * Created for the Lost Crafters SMP (https://www.lostcrafterssmp.com)
 * Licensed under the GNU Affero General Public License v3.0
 * See LICENSE.txt for full license information
 */

package ca.fireball1725.lcs.discordbot.data.databasenew

import org.ktorm.schema.Table
import org.ktorm.schema.uuid
import org.ktorm.schema.varchar

class membersdb {
    object members : Table<Nothing>("members") {
        val member_id = uuid("member_id").primaryKey()
        val display_name = varchar("display_username")
    }
}