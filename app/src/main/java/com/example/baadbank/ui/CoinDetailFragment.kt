package com.example.baadbank.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import com.example.baadbank.R
import com.example.baadbank.databinding.FragmentCoinDetailBinding

class CoinDetailFragment : BaseFragment<FragmentCoinDetailBinding>(FragmentCoinDetailBinding::inflate) {

    override fun start() {
onBackPress()

        binding.coinText.setOnClickListener {
            findNavController().popBackStack()
        }
    }


    private fun onBackPress() {
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()

            }
        })
    }


}