/*
 * Created for the Lost Crafters SMP (https://www.lostcrafterssmp.com)
 * Licensed under the GNU Affero General Public License v3.0
 * See LICENSE.txt for full license information
 */

package ca.fireball1725.lcs.discordbot.data.databasenew

import org.ktorm.schema.Table
import org.ktorm.schema.boolean
import org.ktorm.schema.long
import org.ktorm.schema.uuid
import org.ktorm.schema.varchar

class MemberInvitesTable {
    object MemberInvites : Table<Nothing>("member_invites") {
        val inviteId = uuid("invite_uuid").primaryKey()
        val inviteCode = varchar("invite_code")
        val memberId = uuid("member_uuid")
        val inviteUsed = boolean("invite_used")
        val inviteUsedBy = long("invite_used_by")
    }
}
