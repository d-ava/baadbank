package com.example.baadbank.ui

import androidx.navigation.findNavController
import com.example.baadbank.R
import com.example.baadbank.databinding.FragmentNavHomeBinding

class NavHomeFragment: BaseFragment<FragmentNavHomeBinding>(FragmentNavHomeBinding::inflate) {

    override fun start() {

        setListeners()

    }

    private fun setListeners(){

        binding.homeNavTab.setOnItemSelectedListener {
            when(it.itemId){
                R.id.savingsScreen -> {
                    binding.homeNavContainer.findNavController().navigate(R.id.toSavingsScreen)
                   true
                }
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