/*
 * Created for the Lost Crafters SMP (https://www.lostcrafterssmp.com)
 * Licensed under the GNU Affero General Public License v3.0
 * See LICENSE.txt for full license information
 */

package ca.fireball1725.lcs.discordbot.commands

import ca.fireball1725.lcs.discordbot.getDatabase


class EditProfile {
    fun getEditProfileLink(discordId:ULong): String {
        val member = getDatabase().getMemberFromDiscordId(discordId)

        return if (member != null) {
            val token = getDatabase().createSessionToken(member.member_id)

            val url = "https://www.lostcrafterssmp.com/login/${token}"

            var resultString = ""
            resultString += "# Edit Profile\n"
            resultString += "Click [Here](<$url>) to edit your profile\n"
            resultString
        } else {
            "Error"
        }
    }
}