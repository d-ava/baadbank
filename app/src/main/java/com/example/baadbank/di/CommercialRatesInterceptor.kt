package com.example.baadbank.di

import com.example.baadbank.BuildConfig.API_KEY
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response




@Module
@InstallIn(SingletonComponent::class)
class CommercialRatesInterceptor: Interceptor {




    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = Request.Builder()
            .url("https://test-api.tbcbank.ge/v1/exchange-rates/commercial")
            .get()
            .addHeader("Accept", "application/json")
            .addHeader("apikey", API_KEY)
            .build()


        return OkHttpClient().newCall(request).execute()

    }
}