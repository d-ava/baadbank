package com.example.baadbank.ui.editProfileInfo

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.baadbank.databinding.FragmentEditProfileBinding
import com.example.baadbank.ui.BaseFragment

class EditProfileFragment : BaseFragment<FragmentEditProfileBinding>(FragmentEditProfileBinding::inflate) {

    override fun start() {

        setListeners()

    }


    private fun setListeners(){
        binding.btnSave.setOnClickListener {
            findNavController().navigate(EditProfileFragmentDirections.actionEditProfileFragmentToInfoFragment())
        }

        binding.btnSignOut.setOnClickListener {
            findNavController().navigate(EditProfileFragmentDirections.actionEditProfileFragmentToLoginFragment())
        }
    }

}