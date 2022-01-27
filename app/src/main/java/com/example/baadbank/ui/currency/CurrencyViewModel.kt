package com.example.baadbank.ui.currency

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baadbank.model.CommercialRates
import com.example.baadbank.model.CurrencyItem
import com.example.baadbank.repository.CurrencyRepository
import com.example.baadbank.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject


@HiltViewModel
class CurrencyViewModel @Inject constructor(private val repository:CurrencyRepository): ViewModel() {


    val loadCurrency: SharedFlow<Resource<List<CurrencyItem>>> =
        repository.getCurrency().shareIn(viewModelScope, SharingStarted.WhileSubscribed())


    val loadCommercialRates: SharedFlow<Resource<CommercialRates>> =
        repository.getCommercialRates().shareIn(viewModelScope, SharingStarted.WhileSubscribed())


}