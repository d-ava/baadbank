package com.example.baadbank.ui.crypto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baadbank.data.CoinGecko
import com.example.baadbank.repository.CryptoRepository
import com.example.baadbank.repository.CryptoRepositoryImpl
//import com.example.baadbank.repository.CryptoRepository
import com.example.baadbank.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoViewModel @Inject constructor(private val repository: CryptoRepository) : ViewModel() {



    val loadCoins03: SharedFlow<Resource<List<CoinGecko>>> =
        repository.getCoins().shareIn(viewModelScope, SharingStarted.WhileSubscribed())



    private val _loadCoins004:MutableSharedFlow<Resource<List<CoinGecko>>> = MutableSharedFlow()
    var loadCoins004: SharedFlow<Resource<List<CoinGecko>>> = _loadCoins004


    fun loadCoins004(){

        viewModelScope.launch {
            repository.getCoins().collect {
                _loadCoins004.emit(it)
            }
        }
    }




    }




