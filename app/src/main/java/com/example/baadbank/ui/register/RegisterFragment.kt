package com.example.baadbank.ui.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.baadbank.R
import com.example.baadbank.databinding.FragmentRegisterBinding
import com.example.baadbank.extensions.makeSnackbar
import com.example.baadbank.ui.BaseFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    lateinit var auth: FirebaseAuth

    override fun start() {

        setListeners()
        auth = FirebaseAuth.getInstance()


    }




    private fun registerUser() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        if (email.isNotEmpty() && password.isNotEmpty()) {

            CoroutineScope(IO).launch {
                try {
                    auth.createUserWithEmailAndPassword(email, password).await()

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
        binding.btnRegister.setOnClickListener {
            registerUser()
        }

        binding.ivRegisterBackArrow.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}