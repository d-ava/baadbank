package com.example.baadbank.di

//import com.example.baadbank.network.CoinGeckoApi
import com.example.baadbank.BuildConfig
import com.example.baadbank.network.CoinGeckoApi
import com.example.baadbank.network.CommercialApi
import com.example.baadbank.network.ConvertApi
import com.example.baadbank.network.CurrencyApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object CurrencyNetworkModule {


    private const val BASE_URL = "https://test-api.tbcbank.ge/v1/"
    private const val BASE_URL_COIN_GECKO = "https://api.coingecko.com/api/v3/"


    @Singleton
    @Provides
    @Named("OkHttpCurrency")
    fun provideClientCurrency(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            addInterceptor(CurrencyInterceptor())
        }.build()
    }


    //new
    @Singleton
    @Provides
    @Named("OkHttpConvert")
    fun provideClientConvert(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            addInterceptor(ConvertInterceptor())
        }.build()
    }

    @Singleton
    @Provides
    @Named("OkHttpCommercial")
    fun provideCommercialRates(): OkHttpClient {
        return OkHttpClient.Builder().apply {
            addInterceptor(CommercialRatesInterceptor())
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
    @Named("OkHttpCoin")
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
    @Named("Currency")
    fun provideCurrencyRetrofit(
        moshi: Moshi,
        @Named("OkHttpCurrency") client: OkHttpClient
    ): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(
                MoshiConverterFactory.create(moshi)
            )
    }

    @Singleton
    @Provides
    fun provideApiCurrency(@Named("Currency") retrofit: Retrofit.Builder): CurrencyApi {
        return retrofit.build().create(CurrencyApi::class.java)


    }


    @Singleton
    @Provides
    @Named("Coin")
    fun provideRetrofit(
        moshi: Moshi,
        @Named("OkHttpCoin") okHttpClient: OkHttpClient
    ): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(BASE_URL_COIN_GECKO)
            .client(okHttpClient)
            .addConverterFactory(
                MoshiConverterFactory.create(moshi)
            )
    }

    @Singleton
    @Provides
    fun provideApiCoinGecko(@Named("Coin") retrofit: Retrofit.Builder): CoinGeckoApi {
        return retrofit.build().create(CoinGeckoApi::class.java)


    }

    @Singleton
    @Provides
    @Named("Convert")
    fun provideConvertRetrofit(
        moshi: Moshi,
        @Named("OkHttpConvert") client: OkHttpClient
    ): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(
                MoshiConverterFactory.create(moshi)
            )
    }

    @Singleton
    @Provides
    fun provideApiConvert(@Named("Convert") retrofit: Retrofit.Builder): ConvertApi {
        return retrofit.build().create(ConvertApi::class.java)


    }



    @Singleton
    @Provides
    @Named("Commercial")
    fun provideCommercialRetrofit(
        moshi: Moshi,
        @Named("OkHttpCommercial") client: OkHttpClient
    ): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(
                MoshiConverterFactory.create(moshi)
            )
    }


    @Singleton
    @Provides
    fun provideApiCommercial(@Named("Commercial") retrofit: Retrofit.Builder): CommercialApi {
        return retrofit.build().create(CommercialApi::class.java)


    }


}