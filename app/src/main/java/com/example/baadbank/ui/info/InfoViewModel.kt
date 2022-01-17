package com.example.baadbank.ui.info

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baadbank.data.User
import com.example.baadbank.repository.SavingsRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InfoViewModel @Inject constructor(private val repository: SavingsRepositoryImpl):ViewModel() {


    private val _loadUserInfo: MutableSharedFlow<User> = MutableSharedFlow()
    val loadUserInfo: SharedFlow<User> = _loadUserInfo

    fun loadUserInfo() {
        viewModelScope.launch {
            repository.loadUserInfo(_loadUserInfo)
        }
    }





}