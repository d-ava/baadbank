package com.example.baadbank

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    init {
        viewModelScope.launch {
            repository.getCurrency().collect {
                _getCurrency.emit(it)
            }

           _isLoading.value = false
        }
    }






}