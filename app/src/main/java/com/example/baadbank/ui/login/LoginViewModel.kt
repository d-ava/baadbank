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
    private val repo: FireBaseRepository
) : ViewModel() {




    private val _userLogInStatusFlow: MutableSharedFlow<Resource<AuthResult>> = MutableSharedFlow()
    val userLogInStatusFlow: SharedFlow<Resource<AuthResult>> = _userLogInStatusFlow






//
//    fun logInFlow(email: String, password: String) {
//        viewModelScope.launch {
//            if (email.isEmpty() || password.isEmpty()) {
//                _userLogInStatusFlow.emit(Resource.Error("Empty fields"))
//            } else {
//                _userLogInStatusFlow.emit(Resource.Loading())
//                val loginResult = repo.loginUserFlow(email, password)
//                _userLogInStatusFlow.emit(loginResult.last())
//            }
//        }
//    }



    private val _userLogInStatus = MutableLiveData<Resource<AuthResult>>()
    val userLogInStatus: LiveData<Resource<AuthResult>> = _userLogInStatus

    fun logInLiveData(email:String, password:String){
        if (email.isEmpty() || password.isEmpty()){
            _userLogInStatus.postValue(Resource.Error("empty fields"))
        }else{
            _userLogInStatus.postValue(Resource.Loading())
            viewModelScope.launch(Dispatchers.Main){
                val loginResult = repo.loginUser(email, password)
                _userLogInStatus.postValue(loginResult)
            }
        }
    }


    private val _userLogInStatus03: MutableSharedFlow<Resource<AuthResult>> = MutableSharedFlow()
    val userLogInStatus03: SharedFlow<Resource<AuthResult>> = _userLogInStatus03

    //as philipp said
    private val _userLogInTest= MutableSharedFlow<Resource<AuthResult>>()
    val userLogInTest = _userLogInTest.asSharedFlow()


    fun logIn03(email: String, password: String){
       viewModelScope.launch {
           if (email.isEmpty() || password.isEmpty()){
               _userLogInStatus03.emit(Resource.Error("empty spaces"))
           }else{
               delay(500)
               _userLogInStatus03.emit(Resource.Loading())
               val loginResult = repo.loginUser(email, password)
               _userLogInStatus03.emit(loginResult)
           }
       }
    }




}