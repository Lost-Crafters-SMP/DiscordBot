package ca.fireball1725.lcs.discordbot.mcserver

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response

class Pterodactyl constructor(
        private val apiKey: String,
        private val serverUrl: String
    ) {
    private val client = OkHttpClient()

    fun sendCommand(serverId: String, command: String) {
        val commandUrl = "${serverUrl}/api/client/servers/${serverId}/command"

        val json = """
            {
              "command": "$command"
            }
        """.trimIndent()

        val request = Request.Builder()
            .url(commandUrl)
            .addHeader("Content-Type", "application/json")
            .addHeader("Accept", "application/json")
            .addHeader("Authorization", "Bearer $apiKey")
            .post(json.toRequestBody(MEDIA_TYPE_JSON))
            .build()

        val response = processRequest(request)

        // todo: check response code maybe
    }

    private fun processRequest(request: Request): Response {
        // todo: add some retry logic if there is a timeout
        val response = client.newCall(request).execute()

        response.close()

        return response
    }

    companion object {
        val MEDIA_TYPE_JSON = "application/json; charset=utf-8".toMediaType()
    }

}

