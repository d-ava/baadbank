package com.example.baadbank.ui.login


import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.baadbank.R
import com.example.baadbank.data.CoinGecko
import com.example.baadbank.data.CurrencyItem
import com.example.baadbank.databinding.FragmentLoginBinding

import com.example.baadbank.extensions.makeSnackbar
import com.example.baadbank.network.NetworkClient
import com.example.baadbank.ui.BaseFragment
import com.example.baadbank.ui.currency.currencyList
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

var currencyBody : List<CurrencyItem> = listOf()
var cryptoBody: List<CoinGecko> = listOf()

class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private lateinit var auth: FirebaseAuth


    override fun start() {

        auth = FirebaseAuth.getInstance()
        auth.signOut() //droebit
        loginUser()

        val userTest = Firebase.auth.currentUser

        userTest?.let {
            binding.btnLogin.text = userTest.email
        }






        setListeners()
//        getCurrency()
        getCoinsGecko()

    }

    private fun getCoinsGecko(){

        lifecycleScope.launchWhenStarted {
            withContext(Dispatchers.IO) {
                val response = NetworkClient.apiCoinGecko.getCoinGecko()
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    Log.d("---", "$body")
                    cryptoBody=body

                } else {
                    Log.d("---", "${response.code()}")

                }
            }
        }

    }

    private fun getCurrency(){
        lifecycleScope.launchWhenStarted {
            withContext(Dispatchers.IO) {
                val response = NetworkClient.api.getCurrency()
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    Log.d("---", "$body")
                    currencyBody = body
                } else {
                    Log.d("---", "${response.code()}")

                }
            }
        }

    }



    private fun loginUser() {

        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {

                    findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToNavHomeFragment())
                } else {
                    view?.makeSnackbar("failed to login")
                }
            }

        }


    }


    private fun setListeners() {


//        binding.btnLogin.setOnClickListener {
//            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToNavHomeFragment())
//        }

        binding.tvRegister.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }

        binding.tvGuest.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToNavHomeFragmentGuest())
        }

        binding.tvResetPassword.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToResetPasswordFragment())
        }

    }

}