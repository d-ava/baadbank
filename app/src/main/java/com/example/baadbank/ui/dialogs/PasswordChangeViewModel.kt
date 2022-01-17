package com.example.baadbank.ui.dialogs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baadbank.repository.FireBaseRepository
import com.example.baadbank.repository.SavingsRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PasswordChangeViewModel @Inject constructor(private val repository:FireBaseRepository):ViewModel() {



    fun passwordChange(password:String, newPassword:String){
        viewModelScope.launch {
            repository.changePassword(password, newPassword)
        }

    }


}