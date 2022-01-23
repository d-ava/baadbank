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

//
//    val loadConverter: SharedFlow<Resource<ConvertValue>> =
//        repository.getCurrencyConverter().shareIn(viewModelScope, SharingStarted.WhileSubscribed())
//
//
//    private val _loadCalculatedValue: MutableSharedFlow<Double> = MutableSharedFlow()
//    val loadCalculatedValue: SharedFlow<Double> = _loadCalculatedValue
//
//    fun loadCalculatedValue() {
//        viewModelScope.launch {
//            repository.getCurrencyConverter00().collect {
//                Log.d("---", "from viewmodel $it")
//                _loadCalculatedValue.emit(it)
//            }
//        }
//    }


//    private val _loadCalculatedValue0001111: MutableSharedFlow<Double> = MutableSharedFlow()
//    val loadCalculatedValue0001111: SharedFlow<Double> = _loadCalculatedValue0001111
//
//    fun calculateValue011111111111(){
//        viewModelScope.launch {
//           repository.getCurrencyConverter0222222222().collect {
//               _loadCalculatedValue0001111.emit(it)
//               Log.d("---", "vm ${it}")
//           }
//
//        }
//    }


    private val _loadCalculatedValue03: MutableSharedFlow<Resource<Double>> = MutableSharedFlow()
    val loadCalculatedValue03: SharedFlow<Resource<Double>> = _loadCalculatedValue03

    fun calculateValue03(){

        //todo check for value, must be int.

        viewModelScope.launch {
            repository.getCurrencyConverter03().collect {

                _loadCalculatedValue03.emit(it)

            }

        }
    }


}