package com.example.baadbank.ui.info

import androidx.navigation.fragment.findNavController
import com.example.baadbank.databinding.FragmentInfoBinding
import com.example.baadbank.ui.BaseFragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class InfoFragment : BaseFragment<FragmentInfoBinding>(FragmentInfoBinding::inflate) {

    override fun start() {
        setListeners()
        val user = Firebase.auth.currentUser
        binding.tvEmail.text = user?.email


    }




    private fun setListeners(){
        binding.ivClose.setOnClickListener{
            findNavController().popBackStack()
        }

        binding.btnSignOut.setOnClickListener {
            findNavController().navigate(InfoFragmentDirections.actionInfoFragmentToLoginFragment())
        }

        binding.btnEdit.setOnClickListener {
            findNavController().navigate(InfoFragmentDirections.actionInfoFragmentToEditProfileFragment())
        }
    }
}