package com.example.baadbank.repository

import android.util.Log
import com.example.baadbank.data.CoinGecko
import com.example.baadbank.data.ConvertValue
import com.example.baadbank.data.CurrencyItem
import com.example.baadbank.network.ConvertApi
import com.example.baadbank.network.CurrencyApi
import com.example.baadbank.ui.calculator.result
import com.example.baadbank.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

class CurrencyRepository @Inject constructor(
    private val currencyApi: CurrencyApi,
    private val convertApi: ConvertApi
) {

    fun getCurrency(): Flow<Resource<List<CurrencyItem>>> {
        return flow {
            try {
                emit(Resource.Loading())
                val response = currencyApi.getCurrency()
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    emit(Resource.Success(body))
                } else {
                    emit(Resource.Error("error from repository"))
                }


            } catch (e: IOException) {
                emit(Resource.Error(e.message ?: "error message"))
            }

        }.flowOn(Dispatchers.IO)

    }

    suspend fun getCurrencyConverter0222222222(): Flow<Double> {

        return flow {
            val response = convertApi.convertCurrency()
            val body = response.body()
            if (response.isSuccessful && body != null) {
                Log.d("---", "repo ${body.value}")
                emit(body.value)
            }

        }.flowOn(IO)

    }


    suspend fun getCurrencyConverter03(): Flow<Resource<Double>>{
        return flow {
            try {
               emit(Resource.Loading())
                val response = convertApi.convertCurrency()
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    Log.d("---", "repo ${body.value}")
                    emit(Resource.Success(body.value))
                }else{
                    emit(Resource.Error(response.message()))
                }

            }catch (e:IOException){
                emit(Resource.Error(e.message ?:"error message"))
            }
        }.flowOn(IO)



    }


//    suspend fun getCurrencyConverter01(): Resource<Double> {
//        var res001 = 0.0
//        withContext(IO) {
//            val response = convertApi.convertCurrency()
//            val body = response.body()
//            if (response.isSuccessful && body != null) {
//                Log.d("---", "${body.value}")
//                res001 = body.value
//
//            }
//        }
//
//    }

//    suspend fun getCurrencyConverter00(): Flow<Double> {
//        return flow {
////            var resultValue = 0.0
//            withContext(IO) {
//
//                val response = convertApi.convertCurrency()
//                val body = response.body()
//                if (response.isSuccessful && body != null) {
////                    resultValue = body.value
//                    emit(body.value)
//                }
//            }
////            emit(resultValue)
//
//        }
//    }

//
//    fun getCurrencyConverter(): Flow<Resource<ConvertValue>> {
//        return flow {
//            try {
//                emit(Resource.Loading())
//                val response = convertApi.convertCurrency()
//                val body = response.body()
//                if (response.isSuccessful && body != null) {
//                    emit(Resource.Success(body))
//                } else {
//                    emit(Resource.Error("error"))
//                }
//            } catch (e: IOException) {
//                emit(Resource.Error(e.message ?: "error message"))
//            }
//        }.flowOn(Dispatchers.IO)
//
//    }


}