package com.example.baadbank.data


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CommercialRates(
    @Json(name = "base")
    val base: String,
    @Json(name = "commercialRatesList")
    val commercialRatesList: List<CommercialRates>
) {
    @JsonClass(generateAdapter = true)
    data class CommercialRates(
        @Json(name = "currency")
        val currency: String,
        @Json(name = "buy")
        val buy: Double,
        @Json(name = "sell")
        val sell: Double
    )
}