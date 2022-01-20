package com.example.baadbank.repository

import com.example.baadbank.data.CoinGecko
import com.example.baadbank.network.CoinGeckoApi
import com.example.baadbank.util.Resource
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException
import javax.inject.Inject

class CryptoRepository @Inject constructor(private val coinApi: CoinGeckoApi) {

     fun getCoins(): Flow<Resource<List<CoinGecko>>>{
        return flow{
            try {
                emit(Resource.Loading())
                val response = coinApi.getCoinGecko()
                val body = response.body()
                if (response.isSuccessful && body != null){
                    emit(Resource.Success(body))
            }else{
                emit(Resource.Error("error from repository"))
            }


            }catch (e:IOException){
                emit(Resource.Error(e.message?: "error message"))
            }

        }.flowOn(IO)

    }





    fun getCoins02(): Flow<List<CoinGecko>>{
        return flow{
            val response = coinApi.getCoinGecko()
            val body = response.body()
            if (response.isSuccessful && body != null){
                emit(body)
            }
        }.flowOn(IO)

    }


}