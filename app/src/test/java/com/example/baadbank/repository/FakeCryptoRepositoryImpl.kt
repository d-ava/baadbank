package com.example.baadbank.repository

import com.example.baadbank.data.CoinGecko
import com.example.baadbank.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeCryptoRepositoryImpl : CryptoRepository {

    private var shouldReturnNetworkError = false

    fun setShouldReturnNetworkError(value: Boolean) {
        shouldReturnNetworkError = value
    }

    override fun getCoins(): Flow<Resource<List<CoinGecko>>> {
        return flow {
            if (shouldReturnNetworkError) {
                emit(Resource.Error("Error"))
            } else {
               emit( Resource.Success(listOf(CoinGecko("", "", "", 0.0, ""))))
            }
        }


    }
}