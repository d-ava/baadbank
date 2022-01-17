package com.example.baadbank.ui.info

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.baadbank.data.User
import com.example.baadbank.databinding.FragmentInfoBinding
import com.example.baadbank.ui.BaseFragment
import com.example.baadbank.util.Utils.auth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class InfoFragment : BaseFragment<FragmentInfoBinding>(FragmentInfoBinding::inflate) {

//    lateinit var auth: FirebaseAuth
//
//    lateinit var databaseReference: DatabaseReference
//    lateinit var database: FirebaseDatabase

    private val viewModel: InfoViewModel by activityViewModels()

    override fun start() {
//        auth = FirebaseAuth.getInstance()
//        database = FirebaseDatabase.getInstance()
//        databaseReference = database.reference.child("profile")


        loadUserInfo()
        setListeners()

    }


//    private fun loadUserInfo() {
//        val user = auth.currentUser
//        val userReference = databaseReference.child(user?.uid!!)
//
//        userReference.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val fullName = snapshot.child("fullName").value.toString()
//                val phoneNumber = snapshot.child("phoneNumber").value.toString()
//                val email = user.email.toString()
//
//
//
//
//                binding.tvEmail.text = email
//                binding.tvNameLastname.text = fullName
//                binding.tvPhone.text = phoneNumber
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//        })
//
//
//    }

    private fun loadUserInfo() {
        viewModel.loadUserInfo()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loadUserInfo.collect {
                    binding.apply {
                        tvEmail.text = it.email
                        tvNameLastname.text = it.fullName
                        tvPhone.text = it.phone

                    }

                }

            }
        }
    }


    private fun setListeners() {

        binding.apply {
            tvChangePassword.setOnClickListener {
                findNavController().navigate(InfoFragmentDirections.actionInfoFragmentToPasswordChangeDialogFragment())
            }

            btnClose.setOnClickListener {
                findNavController().navigate(InfoFragmentDirections.actionInfoFragmentToNavHomeFragment())
            }


            btnSignOut.setOnClickListener {
                auth.signOut()
                findNavController().navigate(InfoFragmentDirections.actionInfoFragmentToLoginFragment())
            }

            btnEdit.setOnClickListener {

                val fullName = binding.tvNameLastname.text.toString()
                val phone = binding.tvPhone.text.toString()

                findNavController().navigate(
                    InfoFragmentDirections.actionInfoFragmentToUserInfoChangeDialogFragment(
                        User(fullName=fullName,phone= phone)
                    )
                )


//            findNavController().navigate(
//                InfoFragmentDirections.actionInfoFragmentToEditProfileFragment(
//                    User(fullName, email, phone)
//                )
//            )
            }


        }


    }
}