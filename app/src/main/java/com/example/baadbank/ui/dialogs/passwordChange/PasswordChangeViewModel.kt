package com.example.baadbank.ui.dialogs.passwordChange

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baadbank.repository.FireBaseRepository
import com.example.baadbank.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PasswordChangeViewModel @Inject constructor(private val repository: FireBaseRepository) :
    ViewModel() {


    private val _passwordChange: MutableSharedFlow<Resource<String>> = MutableSharedFlow()
    val passwordChange: SharedFlow<Resource<String>> = _passwordChange


    fun passwordChange(currentPassword: String, newPassword: String, repeatNewPassword: String) {
        viewModelScope.launch {
            try
            {
                if (currentPassword.isNotEmpty() && newPassword.isNotEmpty() && repeatNewPassword.isNotEmpty()) {
                    if (newPassword == repeatNewPassword) {

                        _passwordChange.emit(Resource.Loading())
                        repository.changePassword00(currentPassword, newPassword)


                        _passwordChange.emit(Resource.Success())
                    }else{

                        _passwordChange.emit(Resource.Error("Password mismatching"))
                    }
                }else{

                    _passwordChange.emit(Resource.Error("Please enter all the fields"))
                }
            } catch (e:Exception)
            {
                _passwordChange.emit(Resource.Error(e.message!!))
            }
        }

    }


}

