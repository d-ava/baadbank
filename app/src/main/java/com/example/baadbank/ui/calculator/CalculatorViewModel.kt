package com.example.baadbank.ui.calculator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baadbank.repository.CurrencyRepository
import com.example.baadbank.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject


@HiltViewModel
class CalculatorViewModel @Inject constructor(private val repository: CurrencyRepository) :
    ViewModel() {


    //private val _loadCalculatedValue: MutableSharedFlow<Resource<Double>> = MutableSharedFlow()
    //val loadCalculatedValue: SharedFlow<Resource<Double>> = _loadCalculatedValue


    /*fun calculateValue() {


        viewModelScope.launch {

            repository.getCurrencyConverter().collect {

                Log.d("---", "collect from VM - ${it.data}")
                _loadCalculatedValue.emit(it)

            }

        }
    } */



    val result = repository.getCurrencyConverter()



//    val loadCalculatedValue000: SharedFlow<Resource<Double>> =
//        repository.getCurrencyConverter().shareIn(viewModelScope, SharingStarted.WhileSubscribed())







}