package com.example.baadbank.ui.resetPassword

import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.baadbank.databinding.FragmentResetPasswordBinding
import com.example.baadbank.extensions.makeSnackbar
import com.example.baadbank.ui.BaseFragment
import com.example.baadbank.util.Resource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class ResetPasswordFragment :  BaseFragment<FragmentResetPasswordBinding>(FragmentResetPasswordBinding::inflate) {


    private val viewModel: ResetPasswordViewModel by activityViewModels()


    override fun start() {

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

    private fun setListeners() {
        binding.apply {
            ivResetPasswordBackArrow.setOnClickListener {
                findNavController().popBackStack()
            }

            btnResetPassword.setOnClickListener {
                passwordReset()
            }

        }


    }

    private fun passwordReset() {
        val email = binding.etResetPassword.text.toString()
        viewModel.passwordReset(email)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.passwordReset.collect {
                    when (it) {
                        is Resource.Loading -> {
                            progressBar(true)
                        }
                        is Resource.Success -> {
                            progressBar(false)
                            view?.makeSnackbar("please check your email")
                            findNavController().popBackStack()
                        }
                        is Resource.Error -> {
                            progressBar(false)
                            view?.makeSnackbar(it.message!!)

                        }

                    }
                }
            }

        }
    }

    private fun progressBar(visible: Boolean) {
        binding.progressbar.isVisible = visible
    }
}
