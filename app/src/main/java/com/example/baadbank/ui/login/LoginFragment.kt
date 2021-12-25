package com.example.baadbank.ui.login


import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.baadbank.databinding.FragmentLoginBinding
import com.example.baadbank.ui.BaseFragment


class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {
    override fun start() {

        setListeners()

    }


    private fun setListeners(){

        binding.apply {
            btnLogin.setOnClickListener {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToNavHomeFragment())
            }
        }

//        binding.btnLogin.setOnClickListener {
//            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToNavHomeFragment())
//        }

        binding.tvRegister.setOnClickListener{
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }

        binding.tvGuest.setOnClickListener{
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToNavHomeFragmentGuest())
        }

        binding.tvResetPassword.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToResetPasswordFragment())
        }

    }

}