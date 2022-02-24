package com.example.baadbank.ui

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.baadbank.R
import com.example.baadbank.databinding.FragmentNavHomeBinding
import com.example.baadbank.util.Utils.auth

class NavHomeFragment : BaseFragment<FragmentNavHomeBinding>(FragmentNavHomeBinding::inflate) {

    override fun start() {

        setListeners()
        onBackPress()
    }

    private fun onBackPress() {
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                setUpDialogBox()

            }
        })
    }



    private fun setUpDialogBox() {
        val dialogBoxBuilder = AlertDialog.Builder(requireContext())
        dialogBoxBuilder.setTitle(R.string.sign_out)
        dialogBoxBuilder.setMessage(getString(R.string.dialog_box_message))
        dialogBoxBuilder.setPositiveButton("Yes") { dialogInterface: DialogInterface, i: Int ->
//            auth.signOut()
            findNavController().popBackStack()
        }
        dialogBoxBuilder.setNegativeButton("No") { dialogInterface: DialogInterface, i: Int ->

        }
        dialogBoxBuilder.show()
    }

    private fun setListeners() {


        binding.ivUser.setOnClickListener {
            findNavController().navigate(NavHomeFragmentDirections.actionNavHomeFragmentToInfoFragment())
        }

        binding.homeNavTab.setOnItemSelectedListener {
            when (it.itemId) {
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