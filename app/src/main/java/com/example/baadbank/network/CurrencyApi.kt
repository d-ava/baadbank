package com.example.baadbank.network

import com.example.baadbank.model.CurrencyItem
import retrofit2.Response
import retrofit2.http.GET

interface CurrencyApi {

    @GET("exchange-rates/nbg")
    suspend fun getCurrency():Response<List<CurrencyItem>>
}