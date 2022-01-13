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
import com.example.baadbank.ui.login.LoginFragmentDirections
import com.example.baadbank.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

//    lateinit var auth: FirebaseAuth
//    lateinit var databaseReference: DatabaseReference
//    lateinit var database: FirebaseDatabase

    private val viewModel: RegisterViewModel by activityViewModels()


    override fun start() {

//        auth = FirebaseAuth.getInstance()
//        database = FirebaseDatabase.getInstance()
//        databaseReference = database.reference.child("profile")



        setListeners()

    }

//    private fun registerUser() {
//        binding.btnRegister.setOnClickListener {
//            val email = binding.etEmail.text.toString()
//            val password = binding.etPassword.text.toString()
//
//            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
//                if (task.isSuccessful) {
//                    val currentUser = auth.currentUser
//                    val currentUserDb = databaseReference.child(currentUser?.uid!!)
//                    currentUserDb.child("fullName").setValue(binding.etFullName.text.toString())
//                    currentUserDb.child("phoneNumber")
//                        .setValue(binding.etPhoneNumber.text.toString())
//                    currentUserDb.child("savings").setValue(0.0)
//
//                    view?.makeSnackbar("registration successful")
//                    auth.signOut()
//                  findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
//                } else {
//                    view?.makeSnackbar("registration fail")
//                }
//
//            }
//        }
//    }


    private fun registerUser() {
        val fullname = binding.etFullName.text.toString()
        val email = binding.etEmail.text.toString()
        val password = binding.etPassword.text.toString()
        val phoneNumber = binding.etPhoneNumber.text.toString()

        viewModel.registerUser(email = email, fullName = fullname, password = password, phoneNumber = phoneNumber)

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