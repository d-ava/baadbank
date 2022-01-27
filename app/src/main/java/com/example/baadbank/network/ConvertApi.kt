package com.example.baadbank.network

import com.example.baadbank.model.ConvertValue
import retrofit2.Response
import retrofit2.http.GET

interface ConvertApi {

    @GET("exchange-rates/nbg/convert")
    suspend fun convertCurrency():Response<ConvertValue>
}