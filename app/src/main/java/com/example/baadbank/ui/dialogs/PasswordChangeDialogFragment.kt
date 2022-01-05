package com.example.baadbank.ui.dialogs

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.baadbank.R
import com.example.baadbank.databinding.FragmentPasswordChangeDialogBinding
import com.example.baadbank.extensions.makeSnackbar
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth


class PasswordChangeDialogFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentPasswordChangeDialogBinding? = null
    private val binding get() = _binding!!

    lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPasswordChangeDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        auth = FirebaseAuth.getInstance()

        setListeners()

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
            .apply { (this as BottomSheetDialog).behavior.expandedOffset = 350 }
    }

    private fun setListeners() {
        binding.btnSaveChanges.setOnClickListener {
            passwordChange()
        }
    }


    private fun passwordChange() {
        if (binding.etCurrentPassword.text!!.isNotEmpty() &&
            binding.etNewPassword.text!!.isNotEmpty() &&
            binding.etRepeatPassword.text!!.isNotEmpty()
        ) {
            if (binding.etNewPassword.text.toString() == binding.etRepeatPassword.text.toString()
            ) {
                val user = auth.currentUser
                if (user != null && user.email != null) {
                    val credential = EmailAuthProvider.getCredential(
                        user.email!!,
                        binding.etCurrentPassword.text.toString()
                    )

                    user.reauthenticate(credential).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            view?.makeSnackbar("Re-auth success")

                            user.updatePassword(binding.etNewPassword.text.toString())
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        view?.makeSnackbar("Your password has updated successfully")
                                        auth.signOut()
                                    }
                                }
                        } else {
                            binding.btnSaveChanges.text = "Re-auth failed"
//                            view?.makeSnackbar("Re-auth failed")
                        }
                    }
                } else {
                    findNavController().navigate(PasswordChangeDialogFragmentDirections.actionPasswordChangeDialogFragmentToLoginFragment())

                }
            } else {
//                view?.makeSnackbar("Password mismatching")
                binding.btnSaveChanges.text = "Password mismatching"
            }

        } else {
//            view?.makeSnackbar("Please enter all the fields")
            binding.btnSaveChanges.text = "Please enter all the fields"
        }


    }


    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }

}