package com.example.baadbank.data


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ConvertValue(
    @Json(name = "from")
    val from: String,
    @Json(name = "to")
    val to: String,
    @Json(name = "amount")
    val amount: Int,
    @Json(name = "value")
    val value: Double
)