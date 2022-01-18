package com.example.baadbank.ui.crypto

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baadbank.data.CoinGecko
import com.example.baadbank.repository.CryptoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoViewModel @Inject constructor(private val repository: CryptoRepository) : ViewModel() {

    private val _loadCoins: MutableSharedFlow<List<CoinGecko>> = MutableSharedFlow()
    val loadCoins:SharedFlow<List<CoinGecko>> = _loadCoins


    fun loadCoins() {

        viewModelScope.launch {
           repository.getCoins().catch {

           }
            }
        }

    }


