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

        binding.btnLogin.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToNavHomeFragment())
        }
    }

}