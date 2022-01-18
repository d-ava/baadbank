package com.example.baadbank.network

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response


var client = OkHttpClient()

class CurrencyInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = Request.Builder()
            .url("https://test-api.tbcbank.ge/v1/exchange-rates/nbg")
            .get()
            .addHeader("Accept", "application/json")
            .addHeader("apikey", "7tmMH0B4OEqJqfspTiPFL89JafPKrt6g")
            .build()

//        return client.newCall(request).execute()
        return OkHttpClient().newCall(request).execute()

    }
}