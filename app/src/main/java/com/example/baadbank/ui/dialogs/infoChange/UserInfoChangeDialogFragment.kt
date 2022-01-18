package com.example.baadbank.ui.dialogs.infoChange

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.baadbank.databinding.FragmentUserInfoChangeDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint

class UserInfoChangeDialogFragment : BottomSheetDialogFragment() {

    private val viewModel: InfoChangeViewModel by activityViewModels()

    private var _binding: FragmentUserInfoChangeDialogBinding? = null
    private val binding get() = _binding!!

    private val args: UserInfoChangeDialogFragmentArgs by navArgs()

    private lateinit var profilePictureImageUri: Uri

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUserInfoChangeDialogBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        setListeners()
        loadUserInfo()


    }


    //get image from gallery
    private val getImage = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) {
        binding.ivUser.setImageURI(it)
        profilePictureImageUri = it
    }

    private fun saveUserInfo(){
        val name = binding.etFullName.text.toString()
        val phone = binding.etPhoneNumber.text.toString()

        viewModel.saveUserInfo(name, phone,profilePictureImageUri)

    }

    private fun loadUserInfo() {
        binding.apply {
            etFullName.setText(args.userInformation.fullName)
            etPhoneNumber.setText(args.userInformation.phone)
        }
    }


    private fun setListeners() {
        binding.btnSave.setOnClickListener {


            saveUserInfo()
            findNavController().popBackStack()

        }

        binding.tvUploadUserImage.setOnClickListener {
            //all type pictures
            getImage.launch("image/*")
        }
    }

//    private fun saveUserInformation() {
//        val user = auth.currentUser
//        val userReference = databaseReference.child(user?.uid!!)
//
//        userReference.child("fullName").setValue(binding.etFullName.text.toString())
//        userReference.child("phoneNumber").setValue(binding.etPhoneNumber.text.toString())
//
//    }



    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }


}