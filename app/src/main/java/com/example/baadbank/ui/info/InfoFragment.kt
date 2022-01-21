package com.example.baadbank.ui.info

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.baadbank.data.User
import com.example.baadbank.databinding.FragmentInfoBinding

import com.example.baadbank.extensions.glideExtension
import com.example.baadbank.ui.BaseFragment
import com.example.baadbank.util.Utils.auth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class InfoFragment : BaseFragment<FragmentInfoBinding>(FragmentInfoBinding::inflate) {

    var auth00: FirebaseAuth = FirebaseAuth.getInstance()

    private val viewModel: InfoViewModel by activityViewModels()

    override fun start() {



        loadUserInfo()
        setListeners()

//        showImage2()

    }




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
                auth00.signOut()
                findNavController().navigate(InfoFragmentDirections.actionInfoFragmentToLoginFragment())
            }

            btnEdit.setOnClickListener {

                val fullName = binding.tvNameLastname.text.toString()
                val phone = binding.tvPhone.text.toString()

                findNavController().navigate(
                    InfoFragmentDirections.actionInfoFragmentToUserInfoChangeDialogFragment(
                        User(fullName = fullName, phone = phone)
                    )
                )


            }


        }


    }
}