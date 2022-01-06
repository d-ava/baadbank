package com.example.baadbank.network

import com.example.baadbank.ui.calculator.amount
import com.example.baadbank.ui.calculator.fromCurrency
import com.example.baadbank.ui.calculator.toCurrency
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response


var clientConvert = OkHttpClient()

class ConvertInterceptor: Interceptor {


    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = Request.Builder()
            .url("https://test-api.tbcbank.ge/v1/exchange-rates/nbg/convert?amount=$amount&from=$fromCurrency&to=$toCurrency")
            .get()
            .addHeader("Accept", "application/json")
            .addHeader("apikey", "7tmMH0B4OEqJqfspTiPFL89JafPKrt6g")
            .build()

        return clientConvert.newCall(request).execute()

    }
}


