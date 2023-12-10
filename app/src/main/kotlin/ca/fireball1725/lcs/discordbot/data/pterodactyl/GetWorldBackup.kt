package ca.fireball1725.lcs.discordbot.data.pterodactyl

import com.google.gson.annotations.SerializedName

data class GetWorldBackup (
    @SerializedName("data")
    var worldBackupData: ArrayList<GetWorldBackupData> = arrayListOf(),

    @SerializedName("meta")
    var worldBackupMetadata: GetWorldBackupMeta = GetWorldBackupMeta()
)

data class GetWorldBackupData (
    @SerializedName("attributes")
    var worldBackupDataAttributes: GetWorldBackupDataAttributes = GetWorldBackupDataAttributes()
)

data class GetWorldBackupMeta (
    @SerializedName("pagination")
    var worldBackupMetaPagination: GetWorldBackupMetaPagination = GetWorldBackupMetaPagination()
)

data class GetWorldBackupDataAttributes (
    @SerializedName("uuid")
    var uuid: String? = null,

    @SerializedName("is_successful")
    var isSuccessful: Boolean? = null,

    @SerializedName("is_locked")
    var isLocked: Boolean? = null,

    @SerializedName("name")
    var name: String? = null,

    @SerializedName("ignored_files")
    var ignoredFiles: ArrayList<String> = arrayListOf(),

    @SerializedName("checksum")
    var checksum: String? = null,

    @SerializedName("bytes")
    var bytes: Long? = null,

    @SerializedName("created_at")
    var createdAt: String? = null,

    @SerializedName("completed_at")
    var completedAt: String? = null
)

data class GetWorldBackupMetaPagination (
    @SerializedName("total")
    var total: Int? = null,

    @SerializedName("count")
    var count: Int? = null,

    @SerializedName("per_page")
    var perPage: Int? = null,

    @SerializedName("current_page")
    var currentPage: Int? = null,

    @SerializedName("total_pages")
    var totalPages: Int? = null,

    // @SerializedName("links") var links: Links? = Links()
)