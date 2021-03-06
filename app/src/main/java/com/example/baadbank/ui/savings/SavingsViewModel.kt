package com.example.baadbank.ui.savings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baadbank.data.User
import com.example.baadbank.repository.SavingsRepositoryImpl
import com.example.baadbank.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavingsViewModel @Inject constructor(private val repository: SavingsRepositoryImpl) :
    ViewModel() {


    private val _loadUserInfo: MutableSharedFlow<User> = MutableSharedFlow()
    val loadUserInfo: SharedFlow<User> = _loadUserInfo

    fun loadUserInfo() {
        viewModelScope.launch {
            repository.loadUserInfo(_loadUserInfo)
        }
    }


    private val _addTake: MutableSharedFlow<Resource<Double>> = MutableSharedFlow()
    val addTake: SharedFlow<Resource<Double>> = _addTake

fun addTake(amount:String, button:String){

    viewModelScope.launch {

        repository.addTake(amount, button).collect {
            _addTake.emit(it)
        }

    }



}







}


