package ru.vladlin.ram_reader.domain.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CharacterModel(
    @Json(name = "id") val id: Long,
    @Json(name = "name") val name: String?,
    @Json(name = "type") val type: String?,
    @Json(name = "url") val url: String?,
    @Json(name = "status") val status: String?,
    @Json(name = "gender") val gender: String?,
    @Json(name = "created") val created: String?,
    @Json(name = "image") val image: String?,
    @Json(name = "species") val species: String?
)
