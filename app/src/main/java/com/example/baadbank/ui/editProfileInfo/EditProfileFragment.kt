package com.example.baadbank.ui.editProfileInfo

import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.baadbank.databinding.FragmentEditProfileBinding
import com.example.baadbank.ui.BaseFragment
import com.example.baadbank.util.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class EditProfileFragment :
    BaseFragment<FragmentEditProfileBinding>(FragmentEditProfileBinding::inflate) {

    private val args: EditProfileFragmentArgs by navArgs()
    lateinit var auth: FirebaseAuth
    lateinit var databaseReference: DatabaseReference
    lateinit var database: FirebaseDatabase

    override fun start() {

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database.reference.child(Constants.PROFILE)

        setListeners()

        fillUserInformation()


    }

    private fun fillUserInformation() {
        binding.apply {
            tvEmail.text = args.userInformation.email
            etPhone.setText(args.userInformation.phone)
            etNameLastname.setText(args.userInformation.fullName)
        }
    }


    private fun saveUserInformation() {
        val user = auth.currentUser
        val userReference = databaseReference.child(user?.uid!!)

        userReference.child(Constants.FULL_NAME).setValue(binding.etNameLastname.text.toString())
        userReference.child(Constants.PHONE).setValue(binding.etPhone.text.toString())

    }


    private fun setListeners() {

        binding.apply {
            btnClose.setOnClickListener {
                findNavController().navigate(EditProfileFragmentDirections.actionEditProfileFragmentToInfoFragment())
            }

            btnSave.setOnClickListener {
                saveUserInformation()
//                view?.makeSnackbar("information changed")
                findNavController().navigate(EditProfileFragmentDirections.actionEditProfileFragmentToInfoFragment())
            }

            btnSignOut.setOnClickListener {
                findNavController().navigate(EditProfileFragmentDirections.actionEditProfileFragmentToLoginFragment())
            }



        }


    }

}