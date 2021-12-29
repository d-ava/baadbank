package com.example.baadbank.ui.login


import android.view.View
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

    private fun loginUserNoCoroutines() {
        binding.progressBar.visibility= View.VISIBLE

        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        if (email.isNotEmpty() && password.isNotEmpty()) {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    binding.progressBar.visibility= View.INVISIBLE
                    findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToNavHomeFragment())
                }

            }.addOnFailureListener { exception ->
                view?.makeSnackbar(exception.localizedMessage)
                binding.progressBar.visibility= View.INVISIBLE
            }
        } else {
            view?.makeSnackbar("fill password and email fields")
            binding.progressBar.visibility= View.INVISIBLE

        }
    }

    private fun loginUserWithCoroutines() {
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
                        e.message?.let {
                            view?.makeSnackbar(it)

                        }
                    }
                }
            }
        } else {
            view?.makeSnackbar("fill password and email fields")
        }
    }

    private fun checkLoggedInstance() {
        if (auth.currentUser == null) {
            binding.tvLoogedInfoTest.text = "you r not logged in "
        } else {
            view?.makeSnackbar("u r logged in ")
            binding.tvLoogedInfoTest.text = "you r logged in "
        }


    }


    private fun setListeners() {

        binding.apply {
            btnLogin.setOnClickListener {
                loginUserNoCoroutines()

            }


            tvRegister.setOnClickListener {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
            }
            tvGuest.setOnClickListener {
                checkLoggedInstance()
                //findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToNavHomeFragmentGuest())
            }

            tvResetPassword.setOnClickListener {
                auth.signOut()
//            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToResetPasswordFragment())
            }


        }


    }

}