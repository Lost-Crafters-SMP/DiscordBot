/*
 * Created for the Lost Crafters SMP (https://www.lostcrafterssmp.com)
 * Licensed under the GNU Affero General Public License v3.0
 * See LICENSE.txt for full license information
 */

package ca.fireball1725.lcs.discordbot.helpers

import dev.kord.core.entity.Member
import kotlinx.coroutines.flow.collectLatest

class RoleHelper {
    suspend fun doesMemberHaveRole(member: Member?, roleId: ULong): Boolean {
        if (member == null)
            return false

        val roleIds: MutableList<ULong> = mutableListOf()
        member.roles.collect {
                Value -> roleIds.add(Value.id.value)
        }

        return roleIds.contains(roleId)
    }
}
