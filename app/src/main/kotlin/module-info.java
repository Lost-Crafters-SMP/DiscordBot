module DiscordBot.app.main {
    requires java.datatransfer;
    requires DiscordKt;
    requires annotations;
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.dataformat.yaml;
    requires com.fasterxml.jackson.kotlin;
    requires emoji;
    requires gson;
    requires kord.common;
    requires kord.core;
    requires kord.gateway;
    requires kord.rest;
    requires kotlin.logging.jvm;
    requires kotlin.reflect;
    requires kotlin.stdlib;
    requires kotlinx.coroutines.core.jvm;
    requires kotlinx.serialization.core;
    requires kotlinx.serialization.json;
    requires okhttp3;
}