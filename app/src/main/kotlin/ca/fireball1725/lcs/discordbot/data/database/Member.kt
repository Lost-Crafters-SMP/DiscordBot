/*
 * Created for the Lost Crafters SMP (https://www.lostcrafterssmp.com)
 * Licensed under the GNU Affero General Public License v3.0
 * See LICENSE.txt for full license information
 */

package ca.fireball1725.lcs.discordbot.data.database

import java.util.UUID

data class Member(
    val member_id: UUID,
    val display_username: String,
    val discord_id: Long,
    val pronouns: String?,
    val country: String?,
    val description: String?,
    val show_on_website: Boolean,
    val join_season: Int,
)
