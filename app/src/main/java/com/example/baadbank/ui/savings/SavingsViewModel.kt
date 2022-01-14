package com.example.baadbank.ui.savings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baadbank.data.User1
import com.example.baadbank.repository.SavingsRepositoryImpl
import com.example.baadbank.util.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavingsViewModel @Inject constructor(private val repository: SavingsRepositoryImpl) :
    ViewModel() {


    private val _loadUserInfo: MutableSharedFlow<User1> = MutableSharedFlow()
    val loadUserInfo: SharedFlow<User1> = _loadUserInfo

    fun loadUserInfo() {
        viewModelScope.launch {
            repository.loadUserInfo(_loadUserInfo)
            }
        }

//    private val _addTake: MutableSharedFlow<Double> = MutableSharedFlow()
//    val addTake: SharedFlow<Double> = _addTake


    fun addTake(amount:Double){
        val totalAmount =  Utils.savingsBalance.toDouble() +amount
        viewModelScope.launch {

            repository.addTake(totalAmount)

        }






    }




    }


