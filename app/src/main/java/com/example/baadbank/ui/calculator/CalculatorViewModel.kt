package com.example.baadbank.ui.calculator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baadbank.data.ConvertValue
import com.example.baadbank.data.CurrencyItem
import com.example.baadbank.repository.CurrencyRepository
import com.example.baadbank.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject


@HiltViewModel
class CalculatorViewModel @Inject constructor(private val repository: CurrencyRepository): ViewModel() {


    val loadConverter: SharedFlow<Resource<ConvertValue>> =
        repository.getCurrencyConverter().shareIn(viewModelScope, SharingStarted.WhileSubscribed())



}