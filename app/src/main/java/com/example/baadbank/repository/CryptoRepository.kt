package com.example.baadbank.repository

import com.example.baadbank.data.CoinGecko
import com.example.baadbank.network.CoinGeckoApi
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CryptoRepository @Inject constructor(private val coinApi: CoinGeckoApi) {

    suspend fun getCoins(): Flow<List<CoinGecko>>{
        return flow{
            val response = coinApi.getCoinGecko()
            val body = response.body()
            if (response.isSuccessful && body != null){
                emit(body)
            }
        }.flowOn(IO)

    }


}