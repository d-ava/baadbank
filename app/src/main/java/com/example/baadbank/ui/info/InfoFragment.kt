package com.example.baadbank.ui.info

import androidx.navigation.fragment.findNavController
import com.example.baadbank.data.User
import com.example.baadbank.databinding.FragmentInfoBinding
import com.example.baadbank.ui.BaseFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class InfoFragment : BaseFragment<FragmentInfoBinding>(FragmentInfoBinding::inflate) {

    lateinit var auth: FirebaseAuth

    lateinit var databaseReference: DatabaseReference
    lateinit var database: FirebaseDatabase

    override fun start() {
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database.reference.child("profile")


        loadUserInfo()
        setListeners()

    }


    private fun loadUserInfo() {
        val user = auth.currentUser
        val userReference = databaseReference.child(user?.uid!!)

        userReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val fullName = snapshot.child("fullName").value.toString()
                val phoneNumber = snapshot.child("phoneNumber").value.toString()
                val email = user.email.toString()




                binding.tvEmail.text = email
                binding.tvNameLastname.text = fullName
                binding.tvPhone.text = phoneNumber
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


    }

    private fun setListeners() {

        binding.apply {
            tvChangePassword.setOnClickListener {
                findNavController().navigate(InfoFragmentDirections.actionInfoFragmentToPasswordChangeDialogFragment())
            }
        }

        binding.btnClose.setOnClickListener {
            findNavController().navigate(InfoFragmentDirections.actionInfoFragmentToNavHomeFragment())
        }


        binding.btnSignOut.setOnClickListener {
//            loadUserInfo()
            auth.signOut()
            findNavController().navigate(InfoFragmentDirections.actionInfoFragmentToLoginFragment())
        }

        binding.btnEdit.setOnClickListener {

            findNavController().navigate(InfoFragmentDirections.actionInfoFragmentToUserInfoChangeDialogFragment())
            val fullName = binding.tvNameLastname.text.toString()
            val phone = binding.tvPhone.text.toString()
            val email = binding.tvEmail.text.toString()

//            findNavController().navigate(
//                InfoFragmentDirections.actionInfoFragmentToEditProfileFragment(
//                    User(fullName, email, phone)
//                )
//            )
        }
    }
}