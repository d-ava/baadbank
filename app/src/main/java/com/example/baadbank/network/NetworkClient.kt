package com.example.baadbank.network

import androidx.viewbinding.BuildConfig
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

object NetworkClient {

    private const val BASE_URL = "https://test-api.tbcbank.ge/v1/"
    private const val BASE_URL_COIN = "https://api.coinpaprika.com/v1/"

    private const val BASE_URL_COIN_GECKO = "https://api.coingecko.com/api/v3/"


    private val client = OkHttpClient.Builder().apply {
        addInterceptor(CurrencyInterceptor())
    }.build()


    private val clientConvert = OkHttpClient.Builder().apply {
        addInterceptor(ConvertInterceptor())
    }.build()

    private fun loggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    private fun okHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(loggingInterceptor)
        return builder.build()
    }

    private fun moshi(): Moshi {
        return Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
    }

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
//            .client(okHttpClient(loggingInterceptor()))
            .addConverterFactory(MoshiConverterFactory.create(moshi()))
            .build()
    }

    val retrofitConvert: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(clientConvert)
//            .client(okHttpClient(loggingInterceptor()))
            .addConverterFactory(MoshiConverterFactory.create(moshi()))
            .build()
    }

    val retrofitCoin: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_COIN)
            .addConverterFactory(MoshiConverterFactory.create(moshi()))
            .build()

    }





    private val retrofitCoinGecko: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL_COIN_GECKO)
            .client(okHttpClient(loggingInterceptor()))
            .addConverterFactory(MoshiConverterFactory.create(moshi()))
            .build()
    }


    val api: CurrencyApi by lazy {
        retrofit.create(CurrencyApi::class.java)
    }

    val apiConvert: ConvertApi by lazy {
        retrofitConvert.create(ConvertApi::class.java)
    }

    val apiCoin: CoinsPaprikaApi by lazy {
        retrofitCoin.create(CoinsPaprikaApi::class.java)
    }


    val apiCoinGecko: CoinGeckoApi by lazy {
        retrofitCoinGecko.create(CoinGeckoApi::class.java)
    }

}