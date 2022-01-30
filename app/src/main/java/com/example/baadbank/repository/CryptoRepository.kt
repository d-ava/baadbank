package com.example.baadbank.repository

import com.example.baadbank.data.CoinGecko
import com.example.baadbank.util.Resource
import kotlinx.coroutines.flow.Flow

interface CryptoRepository {

    fun getCoins(): Flow<Resource<List<CoinGecko>>>
}