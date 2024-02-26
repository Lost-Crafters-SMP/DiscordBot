/*
 * Created for the Lost Crafters SMP (https://www.lostcrafterssmp.com)
 * Licensed under the GNU Affero General Public License v3.0
 * See LICENSE.txt for full license information
 */

package ca.fireball1725.lcs.discordbot.helpers

import com.google.gson.JsonParser
import java.time.OffsetDateTime
import java.time.Instant
import java.time.ZoneOffset

class TimeHelper {
    fun getTimestampAsOffsetDateTime(jsonString: String): OffsetDateTime {
        val jsonObject = JsonParser().parse(jsonString).asJsonObject
        val timestamp = jsonObject.get("last_updated").asLong
        return OffsetDateTime.ofInstant(Instant.ofEpochSecond(timestamp), ZoneOffset.UTC)
    }
}