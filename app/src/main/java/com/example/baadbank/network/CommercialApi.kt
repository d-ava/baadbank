package com.example.baadbank.network

import com.example.baadbank.model.CommercialRates
import retrofit2.Response
import retrofit2.http.GET

interface CommercialApi {

    @GET("exchange-rates/commercial")
    suspend fun getCommercialRates():Response<CommercialRates>
}