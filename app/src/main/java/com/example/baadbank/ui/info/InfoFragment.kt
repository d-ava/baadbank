package com.example.baadbank.ui.info

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.baadbank.model.User
import com.example.baadbank.databinding.FragmentInfoBinding

import com.example.baadbank.ui.BaseFragment
import com.example.baadbank.util.Utils.auth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class InfoFragment : BaseFragment<FragmentInfoBinding>(FragmentInfoBinding::inflate) {

//    var auth00: FirebaseAuth = FirebaseAuth.getInstance()

    private val viewModel: InfoViewModel by activityViewModels()

    override fun start() {


        loadUserInfo()
        setListeners()
        onBackPress()


    }

    private fun onBackPress() {
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()

            }
        })
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
                auth.signOut()
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