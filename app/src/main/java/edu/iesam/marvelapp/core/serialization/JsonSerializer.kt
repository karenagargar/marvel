package edu.iesam.marvelapp.core.serialization

interface JsonSerializer<T> {
    fun toJson(obj: T): String
    fun fromJson(json: String): T
}
