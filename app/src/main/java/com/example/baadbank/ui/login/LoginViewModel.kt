package com.example.baadbank.ui.login

import androidx.lifecycle.*
import com.example.baadbank.repository.FireBaseRepository
import com.example.baadbank.repository.UserDataStoreRepository
import com.example.baadbank.util.Resource
import com.google.firebase.auth.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: FireBaseRepository, private val userPreferencesRepository: UserDataStoreRepository
) : ViewModel() {


    private val _userLogInStatus: MutableSharedFlow<Resource<AuthResult>> = MutableSharedFlow()
    val userLogInStatus: SharedFlow<Resource<AuthResult>> = _userLogInStatus




    fun logIn(email: String, password: String) {
        viewModelScope.launch {
            repository.loginUser(email, password).collect {
                _userLogInStatus.emit(it)
            }
        }
    }


    val userPreferences = userPreferencesRepository.readFromDataStore.asLiveData()

    fun saveUserPreferences(userEmail: String,userPassword:String, remember: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            userPreferencesRepository.saveToDataStore(userEmail,userPassword,remember)
        }
    }

    fun clearUserPreferences() {
        viewModelScope.launch(Dispatchers.IO) {
            userPreferencesRepository.clearDataStore()
        }
    }








}