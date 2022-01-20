package com.example.baadbank.ui.register

import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.baadbank.databinding.FragmentRegisterBinding
import com.example.baadbank.extensions.makeSnackbar
import com.example.baadbank.ui.BaseFragment
import com.example.baadbank.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {



    private val viewModel: RegisterViewModel by activityViewModels()


    override fun start() {





        setListeners()

    }




    private fun registerUser() {
        val fullName = binding.etFullName.text.toString()
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        val phoneNumber = binding.etPhoneNumber.text.toString()

        viewModel.registerUser(email = email, fullName = fullName, password = password, phoneNumber = phoneNumber)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.userRegisterStatus.collect() {
                    when (it) {
                        is Resource.Loading -> {
                            progressBar(true)
                        }
                        is Resource.Success -> {
                            progressBar(false)
                            view?.makeSnackbar("register hurrraaay")
                            findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())

                        }
                        is Resource.Error -> {
                            progressBar(false)
                            view?.makeSnackbar("${it.message}")

                        }
                    }

                }
            }
        }


    }

    private fun progressBar(visible: Boolean) {
        binding.progressbar.isVisible = visible
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