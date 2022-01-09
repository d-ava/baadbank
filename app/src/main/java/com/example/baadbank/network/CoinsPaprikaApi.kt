package com.example.baadbank.network

import com.example.baadbank.data.CoinPaprika
import retrofit2.Response
import retrofit2.http.GET

interface CoinsPaprikaApi {

    @GET("coins")
    suspend fun getCoins():Response<List<CoinPaprika>>
}