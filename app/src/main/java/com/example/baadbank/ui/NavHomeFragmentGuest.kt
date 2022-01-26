package com.example.baadbank.ui

import android.content.DialogInterface
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.baadbank.R

import com.example.baadbank.databinding.FragmentNavHomeGuestBinding
import com.example.baadbank.util.Utils

class NavHomeFragmentGuest :
    BaseFragment<FragmentNavHomeGuestBinding>(FragmentNavHomeGuestBinding::inflate) {

    override fun start() {

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


    private fun setListeners() {


        binding.tvGuest.setOnClickListener {
            findNavController().navigate(NavHomeFragmentGuestDirections.actionNavHomeFragmentGuestToLoginFragment())
        }

        binding.homeNavTab.setOnItemSelectedListener {
            when (it.itemId) {

                R.id.calculatorScreen -> {
                    binding.homeNavContainer.findNavController().navigate(R.id.toCalculatorScreen)
                    true
                }
                R.id.cryptoScreen -> {
                    binding.homeNavContainer.findNavController().navigate(R.id.toCryptoScreen)
                    true
                }
                R.id.currencyScreen -> {
                    binding.homeNavContainer.findNavController().navigate(R.id.toCurrencyScreen)
                    true
                }

                else -> false


            }
        }
    }


}