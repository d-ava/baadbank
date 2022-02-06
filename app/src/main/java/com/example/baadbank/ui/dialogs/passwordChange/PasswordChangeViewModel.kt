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
                        delay(500)
                        _passwordChange.emit(Resource.Loading())
                        repository.changePassword00(currentPassword, newPassword)
                        Log.d("---", "Password changed")
                        delay(500)
                        _passwordChange.emit(Resource.Success())
                    }else{
                        Log.d("---", "Password mismatching")
                        _passwordChange.emit(Resource.Error("Password mismatching"))
                    }
                }else{
                    Log.d("---", "Please enter all the fields")
                    _passwordChange.emit(Resource.Error("Please enter all the fields"))
                }
            } catch (e:Exception)
            {
                _passwordChange.emit(Resource.Error(e.message!!))
            }
        }

    }


}

//
//if (binding.etCurrentPassword.text!!.isNotEmpty() &&
//binding.etNewPassword.text!!.isNotEmpty() &&
//binding.etRepeatPassword.text!!.isNotEmpty()
//) {
//    if (binding.etNewPassword.text.toString() == binding.etRepeatPassword.text.toString()
//    ) {
//        val user = auth.currentUser
//        if (user != null && user.email != null) {
//            val credential = EmailAuthProvider.getCredential(
//                user.email!!,
//                binding.etCurrentPassword.text.toString()
//            )
//
//            user.reauthenticate(credential).addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    view?.makeSnackbar("Re-auth success")
//
//                    user.updatePassword(binding.etNewPassword.text.toString())
//                        .addOnCompleteListener { task ->
//                            if (task.isSuccessful) {
//                                view?.makeSnackbar("Your password has updated successfully")
//                                auth.signOut()
//                            }
//                        }
//                } else {
//                    binding.btnSaveChanges.text = "Re-auth failed"
////                            view?.makeSnackbar("Re-auth failed")
//                }
//            }
//        } else {
//            findNavController().navigate(PasswordChangeDialogFragmentDirections.actionPasswordChangeDialogFragmentToLoginFragment())
//
//        }
//    } else {
////                view?.makeSnackbar("Password mismatching")
//        binding.btnSaveChanges.text = "Password mismatching"
//    }
//
//} else {
////            view?.makeSnackbar("Please enter all the fields")
//    binding.btnSaveChanges.text = "Please enter all the fields"
//}