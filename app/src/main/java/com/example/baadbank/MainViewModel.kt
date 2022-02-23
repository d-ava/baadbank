package com.example.baadbank

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baadbank.data.CommercialRates
import com.example.baadbank.data.CurrencyItem
import com.example.baadbank.repository.CurrencyRepository
import com.example.baadbank.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: CurrencyRepository): ViewModel() {

    private val _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private val _getCurrency: MutableSharedFlow<Resource<List<CurrencyItem>>> = MutableSharedFlow()
    val getCurrency: SharedFlow<Resource<List<CurrencyItem>>> = _getCurrency


    private val _getCommercialRates: MutableSharedFlow<Resource<CommercialRates>> = MutableSharedFlow()
    val getCommercialRates: SharedFlow<Resource<CommercialRates>> = _getCommercialRates

    init {
        viewModelScope.launch {
            Log.d("---", "1 get currency")
            repository.getCurrency().collect {
                _getCurrency.emit(it)
            }
            Log.d("---", "2 get commercial")
            viewModelScope.launch {
                repository.getCommercialRates().collect {
                    _getCommercialRates.emit(it)
                }
            }
            Log.d("---", "3 splash screen false")
           _isLoading.value = false
        }
    }




//    val loadCommercialRates: SharedFlow<Resource<CommercialRates>> =
//        repository.getCommercialRates().shareIn(viewModelScope, SharingStarted.WhileSubscribed())





}