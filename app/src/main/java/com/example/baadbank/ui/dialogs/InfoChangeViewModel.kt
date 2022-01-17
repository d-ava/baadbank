package com.example.baadbank.ui.dialogs

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baadbank.repository.SavingsRepositoryImpl
import com.example.baadbank.util.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InfoChangeViewModel @Inject constructor(private val repository: SavingsRepositoryImpl):ViewModel() {



    fun saveUserInfo(name:String, phone:String,profilePic:Uri){
        viewModelScope.launch {
            repository.saveUserInfo(name, phone,profilePic)
        }

    }




}