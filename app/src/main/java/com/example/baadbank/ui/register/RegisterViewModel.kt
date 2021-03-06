package com.example.baadbank.ui.register

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baadbank.repository.FireBaseRepository
import com.example.baadbank.util.Resource
import com.google.firebase.auth.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val repository: FireBaseRepository) :
    ViewModel() {


    private val _userRegisterStatus: MutableSharedFlow<Resource<AuthResult>> = MutableSharedFlow()
    val userRegisterStatus: SharedFlow<Resource<AuthResult>> = _userRegisterStatus


    fun registerUser(
        fullName: String,
        email: String,
        phoneNumber: String,
        password: String,
        repeatPassword: String

    ) {

        viewModelScope.launch {

            _userRegisterStatus.emit(Resource.Loading())
            repository.registerUser(
                fullName = fullName,
                userEmail = email,
                phoneNumber = phoneNumber,
                userPassword = password,
                repeatPassword = repeatPassword
            ).collect {
                _userRegisterStatus.emit(it)
            }


        }

    }




}