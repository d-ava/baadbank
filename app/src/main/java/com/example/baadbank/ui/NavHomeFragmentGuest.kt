package com.example.baadbank.ui

import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.baadbank.R

import com.example.baadbank.databinding.FragmentNavHomeGuestBinding

class NavHomeFragmentGuest : BaseFragment<FragmentNavHomeGuestBinding>(FragmentNavHomeGuestBinding::inflate) {

    override fun start() {

        setListeners()

    }

    private fun setListeners(){


        binding.tvGuest.setOnClickListener {
            findNavController().navigate(NavHomeFragmentGuestDirections.actionNavHomeFragmentGuestToLoginFragment())
        }

        binding.homeNavTab.setOnItemSelectedListener {
            when(it.itemId){

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