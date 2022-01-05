package com.example.baadbank.ui.editProfileInfo

import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.baadbank.databinding.FragmentEditProfileBinding
import com.example.baadbank.ui.BaseFragment

class EditProfileFragment :
    BaseFragment<FragmentEditProfileBinding>(FragmentEditProfileBinding::inflate) {

    private val args: EditProfileFragmentArgs by navArgs()

    override fun start() {

        setListeners()

        fillUserInformation()


    }

    private fun fillUserInformation(){
        binding.apply {
            tvEmail.text = args.userInformation.email
            etPhone.setText(args.userInformation.phone)
            etNameLastname.setText(args.userInformation.fullName)
        }
    }


    private fun setListeners() {

        binding.apply {
            btnClose.setOnClickListener {
                findNavController().navigate(EditProfileFragmentDirections.actionEditProfileFragmentToInfoFragment())
            }

            btnSave.setOnClickListener {
                findNavController().navigate(EditProfileFragmentDirections.actionEditProfileFragmentToInfoFragment())
            }

            btnSignOut.setOnClickListener {
                findNavController().navigate(EditProfileFragmentDirections.actionEditProfileFragmentToLoginFragment())
            }

        }


    }

}