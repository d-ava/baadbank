package com.example.baadbank.di

import com.example.baadbank.BuildConfig
//import com.example.baadbank.network.CoinGeckoApi
import com.example.baadbank.network.CurrencyApi
import com.example.baadbank.network.CurrencyInterceptor
import com.example.baadbank.network.NetworkClient
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object CurrencyNetworkModule {


    private const val BASE_URL = "https://test-api.tbcbank.ge/v1/"


//    private val client = OkHttpClient.Builder().apply {
//        addInterceptor(CurrencyInterceptor00())
//    }.build()

    @Singleton
    @Provides
    fun client00():OkHttpClient{
        return OkHttpClient.Builder().apply {
            addInterceptor(CurrencyInterceptor00())
        }.build()
    }


    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(loggingInterceptor)
        return builder.build()
    }

    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder().addLast(KotlinJsonAdapterFactory()).build()
    }

    @Singleton
    @Provides
    fun provideCurrencyRetrofit(moshi: Moshi): Retrofit.Builder {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .client(client00())
            .addConverterFactory(
                MoshiConverterFactory.create(moshi)
            )
    }

    @Singleton
    @Provides
    fun apiCurrency(retrofit: Retrofit.Builder): CurrencyApi {
        return retrofit.build().create(CurrencyApi::class.java)


    }




}