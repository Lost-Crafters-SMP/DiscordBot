/*
 * Created for the Lost Crafters SMP (https://www.lostcrafterssmp.com)
 * Licensed under the GNU Affero General Public License v3.0
 * See LICENSE.txt for full license information
 */

package ca.fireball1725.lcs.discordbot.mcserver

import ca.fireball1725.lcs.discordbot.data.pterodactyl.GetDirectoryList
import ca.fireball1725.lcs.discordbot.data.pterodactyl.GetDownloadBackup
import ca.fireball1725.lcs.discordbot.data.pterodactyl.GetWorldBackup
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.io.IOException

class Pterodactyl(
    private val apiKey: String,
    private val serverUrl: String,
) {
    private val client = OkHttpClient()

    fun sendCommand(
        serverId: String,
        command: String,
    ) {
        val commandUrl = "$serverUrl/api/client/servers/$serverId/command"

        val json =
            """
            {
              "command": "$command"
            }
            """.trimIndent()

        val request =
            Request.Builder()
                .url(commandUrl)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .addHeader("Authorization", "Bearer $apiKey")
                .post(json.toRequestBody(MEDIA_TYPE_JSON))
                .build()

        val response = processRequest(request)

        // todo: check response code maybe
    }

    fun getDirectoryList(
        serverId: String,
        path: String
    ): GetDirectoryList? {
        val commandUrl = "$serverUrl/api/client/servers/$serverId/files/list?directory=${path.replace("/", "%2F")}"

        val request =
            Request.Builder()
                .url(commandUrl)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .addHeader("Authorization", "Bearer $apiKey")
                .get()
                .build()

        val response = client.newCall(request).execute()

        response.use { // use statement ensures the response is closed
            if (response.code != 200) {
                return null
            }

            response.body ?: return null

            return Gson().fromJson(response.body!!.string(), GetDirectoryList::class.java)
        }
    }

    fun getFileContents(
        serverId: String,
        file: String
    ): String? {
        val commandUrl = "$serverUrl/api/client/servers/$serverId/files/contents?file=${file.replace("/", "%2F")}"

        val request =
            Request.Builder()
                .url(commandUrl)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .addHeader("Authorization", "Bearer $apiKey")
                .get()
                .build()

        val response = client.newCall(request).execute()



        response.use { // use statement ensures the response is closed
            if (response.code != 200) {
                return null
            }

            val responseBody = response.body ?: return null

            return responseBody.string()
        }
    }

    fun getServerBackup(serverId: String): GetWorldBackup? {
        val commandUrl = "$serverUrl/api/client/servers/$serverId/backups"

        val request =
            Request.Builder()
                .url(commandUrl)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .addHeader("Authorization", "Bearer $apiKey")
                .get()
                .build()

        val response = client.newCall(request).execute()

        response.use { // use statement ensures the response is closed
            if (response.code != 200) {
                return null
            }

            val responseBody = response.body ?: return null

            return Gson().fromJson(responseBody.string(), GetWorldBackup::class.java)
        }
    }

    fun getDownloadBackup(
        serverId: String,
        backupId: String,
    ): String? {
        val commandUrl = "$serverUrl/api/client/servers/$serverId/backups/$backupId/download"

        val request =
            Request.Builder()
                .url(commandUrl)
//            .addHeader("Content-Type", "application/json")
//            .addHeader("Accept", "application/json")
                .addHeader("Authorization", "Bearer $apiKey")
                .get()
                .build()

        val response = client.newCall(request).execute()

        response.use { // use statement ensures the response is closed
            if (response.code != 200) {
                return null
            }

            response.body ?: return null

            val downloadBackupResponse = Gson().fromJson(response.body!!.string(), GetDownloadBackup::class.java)

            return downloadBackupResponse.downloadBackupAttributes!!.url
        }
    }

    private fun processRequest(request: Request): Response {
        // todo: add some retry logic if there is a timeout
//        val response = client.newCall(request).execute()
//
//        response.close()
//
//        return response

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            return response
        }
    }

    companion object {
        val MEDIA_TYPE_JSON = "application/json; charset=utf-8".toMediaType()
    }
}
