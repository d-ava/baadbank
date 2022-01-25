package com.example.baadbank.ui.dialogs.passwordChange

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.baadbank.databinding.FragmentPasswordChangeDialogBinding
import com.example.baadbank.extensions.makeSnackbar
import com.example.baadbank.util.Resource
import com.example.baadbank.util.Utils.auth
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.EmailAuthProvider
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class PasswordChangeDialogFragment : BottomSheetDialogFragment() {


    private var _binding: FragmentPasswordChangeDialogBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PasswordChangeViewModel by activityViewModels()

//    lateinit var auth: FirebaseAuth


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


//        auth = FirebaseAuth.getInstance()

        setListeners()
        onBackPress()
    }

    private fun onBackPress() {
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()

            }
        })
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState)
            .apply { (this as BottomSheetDialog).behavior.expandedOffset = 350 }
    }

    private fun setListeners() {
        binding.btnSaveChanges.setOnClickListener {
            passwordChange00()


        }
    }

    private fun passwordChange() {
        val currentPassword = binding.etCurrentPassword.text.toString()
        val newPassword = binding.etNewPassword.text.toString()
        val repeatNewPassword = binding.etRepeatPassword.text.toString()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.passwordChange(currentPassword, newPassword, repeatNewPassword)
            }
        }


    }


    private fun passwordChange00() {

        val currentPassword = binding.etCurrentPassword.text.toString()
        val newPassword = binding.etNewPassword.text.toString()
        val repeatNewPassword = binding.etRepeatPassword.text.toString()
        viewModel.passwordChange(currentPassword, newPassword, repeatNewPassword)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.passwordChange.collect {
                    when (it) {
                        is Resource.Loading -> {
                            progressBar(true)
                        }
                        is Resource.Success -> {
                            progressBar(false)
                            view?.makeSnackbar("password changed")
                            findNavController().navigate(PasswordChangeDialogFragmentDirections.actionPasswordChangeDialogFragmentToLoginFragment())
                        }
                        is Resource.Error -> {
                            progressBar(false)
                            binding.btnSaveChanges.text = "${it.message}"

                        }


                    }
                }
            }
        }


    }

    private fun progressBar(visible: Boolean) {
        binding.progressbar.isVisible = visible
    }


    private fun passwordChangeOld() {
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