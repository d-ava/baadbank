package com.example.baadbank.model

import com.squareup.moshi.Json

data class CurrencyItem(
    @Json(name = "currency")
    val currency: String,
    @Json(name = "value")
    val value: Double

)
