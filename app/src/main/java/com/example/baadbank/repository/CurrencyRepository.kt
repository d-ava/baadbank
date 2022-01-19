package com.example.baadbank.repository

import com.example.baadbank.data.CoinGecko
import com.example.baadbank.data.CurrencyItem
import com.example.baadbank.network.CurrencyApi
import com.example.baadbank.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException
import javax.inject.Inject

class CurrencyRepository @Inject constructor(private val currencyApi: CurrencyApi){

    fun getCurrency(): Flow<Resource<List<CurrencyItem>>> {
        return flow{
            try {
                emit(Resource.Loading())
                val response = currencyApi.getCurrency()
                val body = response.body()
                if (response.isSuccessful && body != null){
                    emit(Resource.Success(body))
                }else{
                    emit(Resource.Error("error from repository"))
                }


            }catch (e: IOException){
                emit(Resource.Error(e.message?: "error message"))
            }

        }.flowOn(Dispatchers.IO)

    }

}