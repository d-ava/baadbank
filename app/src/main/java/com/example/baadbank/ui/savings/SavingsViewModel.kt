package com.example.baadbank.ui.savings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baadbank.model.User
import com.example.baadbank.repository.SavingsRepositoryImpl
import com.example.baadbank.util.Resource
import com.example.baadbank.util.Utils.savingsBalance
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavingsViewModel @Inject constructor(private val repository: SavingsRepositoryImpl) :
    ViewModel() {


    private val _loadUserInfo: MutableSharedFlow<User> = MutableSharedFlow()
    val loadUserInfo: SharedFlow<User> = _loadUserInfo

    fun loadUserInfo() {
        viewModelScope.launch {
            repository.loadUserInfo00(_loadUserInfo)
        }
    }


    private val _addTake: MutableSharedFlow<Resource<Double>> = MutableSharedFlow()
    val addTake: SharedFlow<Resource<Double>> = _addTake

//    fun addTake00(amount: Double){
//        val totalAmount = savingsBalance.toDouble() + amount
//
//        viewModelScope.launch {
//            if (amount.toString().isEmpty()){
//                _addTake.emit(Resource.Error("please enter amount"))
//            }
//            if (totalAmount<0){
//                _addTake.emit(Resource.Error("not enough amount"))
//            }else{
//                _addTake.emit(Resource.Loading())
//                repository.addTake00(totalAmount)
//                _addTake.emit(Resource.Success())
//            }
//
//        }
//    }

    fun addTake01(savingsAmount: String?, negative: Boolean) {


        viewModelScope.launch {
            val error =
                if (savingsAmount.isNullOrEmpty()) {

                    "please enter amount"
                } else {
                    null
                }

            error?.let {

                _addTake.emit(Resource.Error(error))
            }


            var newAmount: Double = savingsAmount!!.toDouble()
            Log.d("---", "new amount = $newAmount")
            if (negative) {

                newAmount *= -1
            }
            val totalAmount = savingsBalance.toDouble() + newAmount
            if (totalAmount < 0) {
                _addTake.emit(Resource.Error("not enough amount"))
            } else {
                _addTake.emit(Resource.Loading())
                repository.addTake00(totalAmount)
                _addTake.emit(Resource.Success())
            }


        }
    }


}


