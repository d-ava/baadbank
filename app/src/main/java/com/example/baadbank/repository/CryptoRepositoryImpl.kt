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

class CryptoRepositoryImpl @Inject constructor(private val coinApi: CoinGeckoApi) : CryptoRepository{

     override fun getCoins(): Flow<Resource<List<CoinGecko>>>{
        return flow{
            try {
                emit(Resource.Loading())
                val response = coinApi.getCoinGecko()
                val body = response.body()
                if (response.isSuccessful && body != null){
                    emit(Resource.Success(body))
            }else{
                emit(Resource.Error("unknown error"))
            }


            }catch (e:IOException){
                emit(Resource.Error(e.message?: "unknown error"))
            }

        }.flowOn(IO)

    }








}