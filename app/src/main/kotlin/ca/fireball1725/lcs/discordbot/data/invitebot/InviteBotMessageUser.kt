/*
 * Created for the Lost Crafters SMP (https://www.lostcrafterssmp.com)
 * Licensed under the GNU Affero General Public License v3.0
 * See LICENSE.txt for full license information
 */

package ca.fireball1725.lcs.discordbot.data.invitebot

import com.google.gson.annotations.SerializedName

data class InviteBotMessageUser(
    @SerializedName("id"                     ) var id                   : String?  = null,
    @SerializedName("username"               ) var username             : String?  = null,
    @SerializedName("avatar"                 ) var avatar               : String?  = null,
    @SerializedName("discriminator"          ) var discriminator        : String?  = null,
    @SerializedName("public_flags"           ) var publicFlags          : Int?     = null,
    @SerializedName("flags"                  ) var flags                : Int?     = null,
    @SerializedName("bot"                    ) var bot                  : Boolean? = null,
    @SerializedName("banner"                 ) var banner               : String?  = null,
    @SerializedName("accent_color"           ) var accentColor          : String?  = null,
    @SerializedName("global_name"            ) var globalName           : String?  = null,
    @SerializedName("avatar_decoration_data" ) var avatarDecorationData : String?  = null,
    @SerializedName("banner_color"           ) var bannerColor          : String?  = null,
    @SerializedName("clan"                   ) var clan                 : String?  = null
)
