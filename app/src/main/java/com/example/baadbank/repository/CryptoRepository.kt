package com.example.baadbank.repository

import android.content.Context
import com.example.baadbank.R
import com.example.baadbank.model.CoinGecko
import com.example.baadbank.network.CoinGeckoApi
import com.example.baadbank.util.Resource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException
import javax.inject.Inject


class CryptoRepository @Inject constructor(private val coinApi: CoinGeckoApi,
                                           @ApplicationContext val context: Context
) {

     fun getCoins(): Flow<Resource<List<CoinGecko>>>{
        return flow{
            try {
                emit(Resource.Loading())
                val response = coinApi.getCoinGecko()
                val body = response.body()
                if (response.isSuccessful && body != null){
                    emit(Resource.Success(body))
            }else{
                emit(Resource.Error(context.getString(R.string.server_loading_error)))
            }


            }catch (e:IOException){
                emit(Resource.Error(e.message?: context.getString(R.string.unknown_error)))
            }

        }.flowOn(IO)

    }








}