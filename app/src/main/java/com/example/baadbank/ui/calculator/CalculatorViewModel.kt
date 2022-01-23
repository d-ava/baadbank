package com.example.baadbank.ui.calculator

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baadbank.data.ConvertValue
import com.example.baadbank.data.CurrencyItem
import com.example.baadbank.data.User
import com.example.baadbank.repository.CurrencyRepository
import com.example.baadbank.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CalculatorViewModel @Inject constructor(private val repository: CurrencyRepository): ViewModel() {



    private val _loadCalculatedValue03: MutableSharedFlow<Resource<Double>> = MutableSharedFlow()
    val loadCalculatedValue03: SharedFlow<Resource<Double>> = _loadCalculatedValue03



    fun calculateValue03(){



        viewModelScope.launch {

                repository.getCurrencyConverter03().collect {

                    Log.d("---", "from VM ${it.data}")
                    _loadCalculatedValue03.emit(it)

                }



        }
    }


}