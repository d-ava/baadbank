package com.example.baadbank.ui.register

import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.baadbank.databinding.FragmentRegisterBinding
import com.example.baadbank.extensions.makeSnackbar
import com.example.baadbank.extensions.safeNavigate
import com.example.baadbank.ui.BaseFragment
import com.example.baadbank.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {


    private val viewModel: RegisterViewModel by activityViewModels()


    override fun start() {


        onBackPress()

        setListeners()

    }


    private fun onBackPress() {
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()

            }
        })
    }

    private fun registerUser() {
        val fullName = binding.etFullName.text.toString()
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        val phoneNumber = binding.etPhoneNumber.text.toString()
        val repeatPassword = binding.etRepeatPassword.text.toString()

        viewModel.registerUser(
            email = email,
            fullName = fullName,
            password = password,
            phoneNumber = phoneNumber,
            repeatPassword = repeatPassword
        )

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.userRegisterStatus.collect() {
                    when (it) {
                        is Resource.Loading -> {
                            showLoading()
                        }
                        is Resource.Success -> {
                            hideLoading()
                            findNavController().safeNavigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
//                            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())

                        }
                        is Resource.Error -> {
                            hideLoading()
                            Log.d("---", "error log")
                            view?.makeSnackbar("${it.message}")

                        }
                    }

                }
            }
        }


    }


    private fun setListeners() {
        binding.btnRegister.setOnClickListener {
            registerUser()
        }

        binding.ivRegisterBackArrow.setOnClickListener {

            findNavController().popBackStack()
        }
    }

}