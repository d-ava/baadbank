package com.example.baadbank.ui.login


import android.util.Log
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.baadbank.data.CoinGecko
import com.example.baadbank.data.CurrencyItem
import com.example.baadbank.databinding.FragmentLoginBinding
import com.example.baadbank.extensions.makeSnackbar
import com.example.baadbank.network.NetworkClient
import com.example.baadbank.ui.BaseFragment
import com.example.baadbank.util.Resource
import com.example.baadbank.util.Utils.auth
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

var currencyBody: List<CurrencyItem> = listOf()
var cryptoBody: List<CoinGecko> = listOf()

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {


    private val viewModel: LoginViewModel by activityViewModels()


    override fun start() {



        auth.signOut() //droebit



        val userTest = auth.currentUser

//
        userTest?.let {
            binding.btnLogin.text = userTest.email
        }


        setListeners()
//        getCurrency()
//        getCoinsGecko()


    }

    private fun loginUser03() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        viewModel.logIn03(email, password)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.userLogInStatus03.collect() {
                    when (it) {
                        is Resource.Loading -> {
                            progressBar(true)
                        }
                        is Resource.Success -> {
                            progressBar(false)

                            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToNavHomeFragment())

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


        binding.apply {

            btnLogin.setOnClickListener {
                loginUser03()
            }
            tvRegister.setOnClickListener {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
            }
            tvGuest.setOnClickListener {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToNavHomeFragmentGuest())
            }

            tvResetPassword.setOnClickListener {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToResetPasswordFragment())
            }


        }


    }

}