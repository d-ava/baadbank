package com.example.baadbank.network

import com.example.baadbank.data.CommercialRates
import com.example.baadbank.data.CurrencyItem
import retrofit2.Response
import retrofit2.http.GET

interface CommercialApi {

    @GET("exchange-rates/commercial")
    suspend fun getCommercialRates():Response<CommercialRates>
}