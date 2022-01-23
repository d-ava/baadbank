package com.example.baadbank.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baadbank.repository.FireBaseRepository
import com.example.baadbank.util.Resource
import com.google.firebase.auth.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: FireBaseRepository
) : ViewModel() {






    private val _userLogInStatus03: MutableSharedFlow<Resource<AuthResult>> = MutableSharedFlow()
    val userLogInStatus03: SharedFlow<Resource<AuthResult>> = _userLogInStatus03

    //as philipp said
//    private val _userLogInTest= MutableSharedFlow<Resource<AuthResult>>()
//    val userLogInTest = _userLogInTest.asSharedFlow()


    fun logIn03(email: String, password: String){
       viewModelScope.launch {
           if (email.isEmpty() || password.isEmpty()){
               _userLogInStatus03.emit(Resource.Error("empty spaces"))
           }else{

               _userLogInStatus03.emit(Resource.Loading())
               val loginResult = repository.loginUser(email, password)
               _userLogInStatus03.emit(loginResult)
           }
       }
    }


    fun logIn04(email: String, password: String){
        viewModelScope.launch {
            if (email.isEmpty() || password.isEmpty()){
                _userLogInStatus03.emit(Resource.Error("empty spaces"))
            }else{

                _userLogInStatus03.emit(Resource.Loading())
                repository.loginUser01(email, password).collect {
                    _userLogInStatus03.emit(it)
                }


            }
        }
    }




}