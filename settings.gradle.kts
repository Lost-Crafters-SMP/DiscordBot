/*
 * Created for the Lost Crafters SMP (https://www.lostcrafterssmp.com)
 * Licensed under the GNU Affero General Public License v3.0
 * See LICENSE.txt for full license information
 */

plugins {
    // Apply the foojay-resolver plugin to allow automatic download of JDKs
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.7.0"
}

var appGroup = "ca.fireball1725.lcs.discordbot"
var appVersion = "0.1.0"
var appDescription = "Lost Crafters SMP Discord Bot"
var appName = "DiscordBot"

rootProject.name = "DiscordBot"
include("app")
