/*
 * Created for the Lost Crafters SMP (https://www.lostcrafterssmp.com)
 * Licensed under the GNU Affero General Public License v3.0
 * See LICENSE.txt for full license information
 */

package ca.fireball1725.lcs.discordbot.data.invitebot

import com.google.gson.annotations.SerializedName

data class InviteBotMessageMember(
    @SerializedName("avatar"                       ) var avatar                     : String?           = null,
    @SerializedName("communication_disabled_until" ) var communicationDisabledUntil : String?           = null,
    @SerializedName("flags"                        ) var flags                      : Int?              = null,
    @SerializedName("joined_at"                    ) var joinedAt                   : String?           = null,
    @SerializedName("nick"                         ) var nick                       : String?           = null,
    @SerializedName("pending"                      ) var pending                    : Boolean?          = null,
    @SerializedName("premium_since"                ) var premiumSince               : String?           = null,
    @SerializedName("roles"                        ) var roles                      : ArrayList<String> = arrayListOf(),
    @SerializedName("unusual_dm_activity_until"    ) var unusualDmActivityUntil     : String?           = null,
    @SerializedName("user"                         ) var user                       : InviteBotMessageUser?             = InviteBotMessageUser(),
    @SerializedName("mute"                         ) var mute                       : Boolean?          = null,
    @SerializedName("deaf"                         ) var deaf                       : Boolean?          = null
)
