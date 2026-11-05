package edu.iesam.marvelapp.core.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

class KotlinJsonSerializer<T>(
    private val serializer: KSerializer<T>,
    private val json: Json = Json { ignoreUnknownKeys = true }
) : JsonSerializer<T> {

    override fun toJson(obj: T): String = json.encodeToString(serializer, obj)

    override fun fromJson(json: String): T = this.json.decodeFromString(serializer, json)

    companion object {
        inline fun <reified T> create(
            json: Json = Json { ignoreUnknownKeys = true }
        ): KotlinJsonSerializer<T> = KotlinJsonSerializer(serializer<T>(), json)
    }
}