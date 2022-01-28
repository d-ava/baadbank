package com.example.baadbank.ui.login


import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.baadbank.data.CoinGecko
import com.example.baadbank.data.CurrencyItem
import com.example.baadbank.databinding.FragmentLoginBinding
import com.example.baadbank.extensions.makeSnackbar
import com.example.baadbank.ui.BaseFragment
import com.example.baadbank.util.Resource
import com.example.baadbank.util.Utils.auth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

var currencyBody: List<CurrencyItem> = listOf()
var cryptoBody: List<CoinGecko> = listOf()

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private var rememberCredential: Boolean = false
    lateinit var password: String
    lateinit var email: String

    private val viewModel: LoginViewModel by activityViewModels()


    override fun start() {

        auth.signOut() //droebit
        val userLogged = auth.currentUser
        userLogged?.let {
            binding.btnLogin.text = userLogged.email
        }


        setListeners()
        observer()
        setLoginCredentials()

    }



    private fun loginUser01() {
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        viewModel.logIn04(email, password)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.userLogInStatus03.collect() {
                    when (it) {
                        is Resource.Loading -> {
                            showLoading()
                        }
                        is Resource.Success -> {
                            hideLoading()
                            saveToUserDatastore()
                            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToNavHomeFragment())

                        }
                        is Resource.Error -> {
                            hideLoading()
                            view?.makeSnackbar("${it.message}")

                        }
                    }

                }
            }
        }
    }





    private fun setListeners() {


        binding.apply {

            btnLogin.setOnClickListener {
                loginUser01()

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

    private fun observer() {

        viewModel.userPreferences.observe(viewLifecycleOwner, {
            rememberCredential = it.rememberCredentials
            email = it.password
            password = it.password
        })
    }

    private fun saveToUserDatastore() {

        if (binding.cbRememberMe.isChecked) {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            viewModel.saveUserPreferences(email, password, true)
        }

    }

    private fun setLoginCredentials(){

        viewModel.userPreferences.observe(viewLifecycleOwner, { userPreferences ->
            val email = userPreferences.email
            val password = userPreferences.password
            if (email.isNotEmpty() && password.isNotEmpty()) {
                binding.etEmail.setText(email)
                binding.etPassword.setText(password)
            }
        })


    }

}





