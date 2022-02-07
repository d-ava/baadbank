package com.example.baadbank.ui

import android.content.DialogInterface
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.baadbank.R

import com.example.baadbank.databinding.FragmentNavHomeGuestBinding
import com.example.baadbank.extensions.makeSnackbar
import com.example.baadbank.util.Utils
import nl.joery.animatedbottombar.AnimatedBottomBar

class NavHomeFragmentGuest :
    BaseFragment<FragmentNavHomeGuestBinding>(FragmentNavHomeGuestBinding::inflate) {

    override fun start() {


        setAnimatedBottomBar()

        setListeners()
        onBackPress()
    }

    private fun setAnimatedBottomBar() {

        binding.homeNavTab.setOnTabSelectListener(object : AnimatedBottomBar.OnTabSelectListener {
            override fun onTabSelected(
                lastIndex: Int,
                lastTab: AnimatedBottomBar.Tab?,
                newIndex: Int,
                newTab: AnimatedBottomBar.Tab
            ) {
                when (newIndex) {
                    0 -> {
                        //currency
                        binding.homeNavContainer.findNavController().navigate(R.id.toCurrencyScreen)
                    }
                    1 -> {
                        //calculator
                        binding.homeNavContainer.findNavController()
                            .navigate(R.id.toCalculatorScreen)
                    }
                    2 -> {
                        //crypto
                        binding.homeNavContainer.findNavController().navigate(R.id.toCryptoScreen)
                    }
                }
            }
        })


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


    }

}