/*
 * Created for the Lost Crafters SMP (https://www.lostcrafterssmp.com)
 * Licensed under the GNU Affero General Public License v3.0
 * See LICENSE.txt for full license information
 */

package ca.fireball1725.lcs.discordbot.helpers

import ca.fireball1725.lcs.discordbot.data.MinecraftUser
import com.google.gson.Gson
import okhttp3.*
import java.util.UUID

class MinecraftUserHelper {
    private val client = OkHttpClient()

    fun getMinecraftUserFromUsername(username: String): MinecraftUser? {
        val url = "https://playerdb.co/api/player/minecraft/${username}"

        val request = Request.Builder()
            .url(url)
            .addHeader("Content-Type","application/json")
            .build()

        val response = client.newCall(request).execute()

        if (response.code != 200) // if the response isn't 200 OK then return null
            return null

        val minecraftUserObject = Gson().fromJson(response.body!!.string(), MinecraftUser::class.java)

        // Close request
        response.close()

        return minecraftUserObject
    }

    fun getMinecraftUserFromUUID(uuid: UUID): MinecraftUser? {
        return getMinecraftUserFromUsername(uuid.toString())
    }
}
