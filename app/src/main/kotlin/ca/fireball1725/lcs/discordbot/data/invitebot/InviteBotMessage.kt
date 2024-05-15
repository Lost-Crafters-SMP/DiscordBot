/*
 * Created for the Lost Crafters SMP (https://www.lostcrafterssmp.com)
 * Licensed under the GNU Affero General Public License v3.0
 * See LICENSE.txt for full license information
 */

package ca.fireball1725.lcs.discordbot.data.invitebot

import com.google.gson.annotations.SerializedName

data class InviteBotMessage(
    @SerializedName("member"             ) var member           : InviteBotMessageMember? = InviteBotMessageMember(),
    @SerializedName("source_invite_code" ) var sourceInviteCode : String? = null,
    @SerializedName("join_source_type"   ) var joinSourceType   : Int?    = null,
    @SerializedName("inviter_id"         ) var inviterId        : String? = null
)
