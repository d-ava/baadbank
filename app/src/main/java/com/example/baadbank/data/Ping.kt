package com.example.baadbank.data


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Ping(
    @Json(name = "gecko_says")
    val geckoSays: String
)