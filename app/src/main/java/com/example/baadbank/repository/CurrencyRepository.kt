package com.example.baadbank.repository

import com.example.baadbank.data.CommercialRates
import com.example.baadbank.data.CurrencyItem
import com.example.baadbank.network.CommercialApi
import com.example.baadbank.network.ConvertApi
import com.example.baadbank.network.CurrencyApi
import com.example.baadbank.ui.calculator.amount
import com.example.baadbank.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException
import javax.inject.Inject

class CurrencyRepository @Inject constructor(
    private val currencyApi: CurrencyApi,
    private val convertApi: ConvertApi,
    private val commercialApi: CommercialApi
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
                    emit(Resource.Error("error message"))
                }


            } catch (e: IOException) {
                emit(Resource.Error(e.message ?: "error message"))
            }

        }.flowOn(Dispatchers.IO)

    }

    fun getCommercialRates(): Flow<Resource<CommercialRates>>{
        return flow {
            try {

                emit(Resource.Loading())
                val response = commercialApi.getCommercialRates()
                val body = response.body()
                if (response.isSuccessful && body !=null){

                    emit(Resource.Success(body))
                }else{

                    emit(Resource.Error("error message"))

                }
            }catch (e:IOException){
//                Log.d("---", "from repo ${e.message}")
                emit(Resource.Error(e.message ?: "error message"))
            }


        }.flowOn(IO)


    }







    fun getCurrencyConverter(): Flow<Resource<Double>> {
        return flow {
            try {

                if (amount.isEmpty()){
                    emit(Resource.Error("please enter amount"))
                }else{
                    emit(Resource.Loading())
                    val response = convertApi.convertCurrency()
                    val body = response.body()
                    if (response.isSuccessful && body != null) {

                        emit(Resource.Success(body.value))
                    } else {
                        emit(Resource.Error(response.message()))
                    }
                }
            } catch (e: IOException) {
                emit(Resource.Error(e.message ?: "error message"))
            }
        }.flowOn(IO)


    }




}