package com.example.baadbank.ui.register

import androidx.navigation.fragment.findNavController
import com.example.baadbank.databinding.FragmentRegisterBinding
import com.example.baadbank.extensions.makeSnackbar
import com.example.baadbank.ui.BaseFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegisterFragment : BaseFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {

    lateinit var auth: FirebaseAuth
    lateinit var databaseReference: DatabaseReference
    lateinit var database: FirebaseDatabase


    override fun start() {

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database.reference.child("profile")


        registerUser()
        setListeners()

    }

    private fun registerUser() {
        binding.btnRegister.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()

            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val currentUser = auth.currentUser
                    val currentUserDb = databaseReference.child(currentUser?.uid!!)
                    currentUserDb.child("fullName").setValue(binding.etFullName.text.toString())
                    currentUserDb.child("phoneNumber")
                        .setValue(binding.etPhoneNumber.text.toString())
                    currentUserDb.child("savings").setValue(0.0)

                    view?.makeSnackbar("registration successful")
                    auth.signOut()
                  findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
                } else {
                    view?.makeSnackbar("registration fail")
                }

            }
        }
    }

    private fun setListeners(){
        binding.ivRegisterBackArrow.setOnClickListener {
            findNavController().popBackStack()
        }
    }

}