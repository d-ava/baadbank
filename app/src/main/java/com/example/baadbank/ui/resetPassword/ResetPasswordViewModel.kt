package com.example.baadbank.ui.resetPassword

import android.provider.ContactsContract
import android.util.Log
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.baadbank.repository.FireBaseRepository
import com.example.baadbank.ui.dialogs.passwordChange.PasswordChangeViewModel
import com.example.baadbank.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(private val repository: FireBaseRepository) :ViewModel() {

    private val _passwordReset: MutableSharedFlow<Resource<String>> = MutableSharedFlow()
    val passwordReset: SharedFlow<Resource<String>> = _passwordReset




    fun passwordReset(email:String) {
        viewModelScope.launch {
           if(email.isNotEmpty())
           {
               if(android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
               {
                   try {
                       _passwordReset.emit(Resource.Loading())
                       repository.resetPassword(email)
                       _passwordReset.emit(Resource.Success())
                   } catch (e:Exception)
                   {
                      _passwordReset.emit(Resource.Error(e.message!!))
                   }

               }
               else{
                   _passwordReset.emit(Resource.Error("Enter a valid email"))
               }
           } else
           {
               _passwordReset.emit(Resource.Error("Please enter an email"))
           }
        }

    }
}