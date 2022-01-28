package com.example.baadbank.network

import com.example.baadbank.data.CoinGecko
import com.example.baadbank.data.Ping
import retrofit2.Response
import retrofit2.http.GET

interface CoinGeckoApi {

    @GET("coins/markets?vs_currency=usd&order=market_cap_desc&per_page=100&page=1&sparkline=false")
    suspend fun getCoinGecko(

    ):Response<List<CoinGecko>>

    @GET("ping")
    suspend fun coinGeckoPing():Response<Ping>
}