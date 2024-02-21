/*
 * Created for the Lost Crafters SMP (https://www.lostcrafterssmp.com)
 * Licensed under the GNU Affero General Public License v3.0
 * See LICENSE.txt for full license information
 */

group = "ca.fireball1725.lcs.discordbot"
version = "0.1.0"
description = "Lost Crafters SMP Discord Bot"

plugins {
    kotlin("plugin.serialization") version "1.9.21"
    alias(libs.plugins.jvm)
    application
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("me.jakejmattson", "DiscordKt", "0.23.4")
    implementation("com.squareup.okhttp3", "okhttp", "4.11.0")
    implementation("com.google.code.gson", "gson", "2.8.5")

    implementation("com.fasterxml.jackson.core", "jackson-databind", "2.16.+")
    implementation("com.fasterxml.jackson.module", "jackson-module-kotlin", "2.16.+")
    implementation("com.fasterxml.jackson.dataformat", "jackson-dataformat-yaml", "2.16.+")

    implementation("org.postgresql:postgresql:42.7.1")

    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation(libs.junit.jupiter.engine)
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    implementation(libs.guava)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

application {
    mainClass.set("ca.fireball1725.lcs.discordbot.AppKt")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

tasks {
    compileKotlin {
        // kotlinOptions.jvmTarget = "1.18"
        dependsOn("writeProperties")
    }

    register<WriteProperties>("writeProperties") {
        property("name", project.name)
        property("description", project.description.toString())
        property("version", version.toString())
        property("url", "")
        setOutputFile("src/main/resources/bot.properties")
    }

    named<ProcessResources>("processResources") {
        dependsOn("writeProperties")
    }
}
