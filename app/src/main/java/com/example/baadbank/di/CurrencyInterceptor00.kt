package com.example.baadbank.di

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
class CurrencyInterceptor00: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = Request.Builder()
            .url("https://test-api.tbcbank.ge/v1/exchange-rates/nbg")
            .get()
            .addHeader("Accept", "application/json")
            .addHeader("apikey", "7tmMH0B4OEqJqfspTiPFL89JafPKrt6g")
            .build()


        return OkHttpClient().newCall(request).execute()

    }
}