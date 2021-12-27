package com.example.baadbank.ui.login


import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.baadbank.databinding.FragmentLoginBinding
import com.example.baadbank.extensions.makeSnackbar

import com.example.baadbank.ui.BaseFragment
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext


class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    lateinit var auth: FirebaseAuth

    override fun start() {
        auth = FirebaseAuth.getInstance()
        setListeners()

    }

    private fun loginUser() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        if (email.isNotEmpty() && password.isNotEmpty()) {

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    auth.signInWithEmailAndPassword(email, password).await()
                    withContext(Dispatchers.Main) {
                        checkLoggedInstance()
                    }

                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        binding.tvLoogedInfoTest.text = e.message
                        e.message?.let { view?.makeSnackbar(it)

                        }
                    }
                }
            }
        }else{
            view?.makeSnackbar("fill password and email fields")
        }
    }

    private fun checkLoggedInstance() {
        if (auth.currentUser == null) {
            binding.tvLoogedInfoTest.text = "you r not logged in "
        } else {
            binding.tvLoogedInfoTest.text = "you r logged in "
        }
    }


    private fun setListeners() {

        binding.apply {
            btnLogin.setOnClickListener {
                loginUser()
                //findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToNavHomeFragment())
            }

        }

//        binding.btnLogin.setOnClickListener {
//            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToNavHomeFragment())
//        }

        binding.tvRegister.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }

        binding.tvGuest.setOnClickListener {
            checkLoggedInstance()
            //findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToNavHomeFragmentGuest())
        }

        binding.tvResetPassword.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToResetPasswordFragment())
        }

    }

}