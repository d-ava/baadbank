package com.example.baadbank.ui.info

import androidx.navigation.fragment.findNavController
import com.example.baadbank.databinding.FragmentInfoBinding
import com.example.baadbank.ui.BaseFragment

class InfoFragment : BaseFragment<FragmentInfoBinding>(FragmentInfoBinding::inflate) {

    override fun start() {
        setListeners()
    }

    private fun setListeners(){
        binding.btnSignout.setOnClickListener {
            findNavController().navigate(InfoFragmentDirections.actionInfoFragmentToLoginFragment())
        }
    }
}