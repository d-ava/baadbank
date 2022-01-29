package com.example.baadbank.ui.dialogs.infoChange

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
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
        onBackPress()

    }

    private fun onBackPress() {
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()

            }
        })
    }


    private fun saveUserInfo() {
        val name = binding.etFullName.text.toString()
        val phone = binding.etPhoneNumber.text.toString()

        viewModel.saveUserInfo(name, phone)

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


    }




    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }


}