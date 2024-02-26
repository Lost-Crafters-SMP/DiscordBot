/*
 * Created for the Lost Crafters SMP (https://www.lostcrafterssmp.com)
 * Licensed under the GNU Affero General Public License v3.0
 * See LICENSE.txt for full license information
 */

package ca.fireball1725.lcs.discordbot.data.pterodactyl

import com.google.gson.annotations.SerializedName

data class GetDirectoryList (
    @SerializedName("object")
    var obj: String? = null,

    @SerializedName("data")
    var data: ArrayList<GetDirectoryListData> = arrayListOf(),
)

data class GetDirectoryListData (
    @SerializedName("object")
    var obj: String? = null,

    @SerializedName("attributes")
    var attributes: GetDirectoryListAttributes? = GetDirectoryListAttributes()
)

data class GetDirectoryListAttributes (
    @SerializedName("name")
    var name: String? = null,

    @SerializedName("mode")
    var mode: String? = null,

    @SerializedName("size")
    var size: Long? = null,

    @SerializedName("is_file")
    var isFile: Boolean? = null,

    @SerializedName("is_symlink")
    var isSymLink: Boolean? = null,

    @SerializedName("is_editable")
    var isEditable: Boolean? = null,

    @SerializedName("mimetype")
    var mimetype: String? = null,

    @SerializedName("created_at")
    var createdAt: String? = null,

    @SerializedName("modified_at")
    var modifiedAt: String? = null,
)
