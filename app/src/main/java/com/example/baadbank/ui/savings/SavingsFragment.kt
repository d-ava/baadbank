package com.example.baadbank.ui.savings

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import com.example.baadbank.util.Resource
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.baadbank.databinding.FragmentSavingsBinding
import com.example.baadbank.extensions.makeSnackbar
import com.example.baadbank.ui.BaseFragment
import com.example.baadbank.util.Utils
import com.example.baadbank.util.Utils.savingsBalance
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode
import com.example.baadbank.R
import com.example.baadbank.ui.NavHomeFragmentDirections
import com.example.baadbank.util.Utils.auth
import kotlin.text.Typography.dagger


@AndroidEntryPoint
class SavingsFragment : BaseFragment<FragmentSavingsBinding>(FragmentSavingsBinding::inflate) {

    private val viewModel: SavingsViewModel by activityViewModels()



    override fun start() {

        //auth = FirebaseAuth.getInstance()
        //database = FirebaseDatabase.getInstance()
        //databaseReference = database.reference.child("profile")
        loadUserInfo()


    }

    private fun setListeners() {
        binding.btnAdd.setOnClickListener {
            addTake00(false)
//            binding.etAdd.text?.clear()
        }

        binding.btnTake.setOnClickListener {
            addTake00(true)
//            binding.etTake.text?.clear()
        }
    }

//    private fun loadUserInfo00(){
//        viewModel.loadUserInfo00()
//        viewLifecycleOwner.lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.STARTED){
//                viewModel.loadUserInfo00.collect {
//                    when (it){
//                        is Resource.Loading -> {}
//                        is Resource.Success -> {
//                            binding.tvWelcome.text = it.data?.fullName
//                            binding.tvBallance.text =
//                                BigDecimal(it.data!!.savings).setScale(2, RoundingMode.HALF_EVEN).toPlainString()
//                                    .toString()
//
//                        }
//                        is Resource.Error -> {}
//
//                    }                    }
//                }
//            }
//
//
//    }

    private fun loadUserInfo() {
        viewModel.loadUserInfo()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loadUserInfo.collect {
                    binding.tvWelcome.text = String.format(getString(R.string.welcome_to_user),it.fullName)
                    binding.tvBallance.text =
                        BigDecimal(it.savings).setScale(2, RoundingMode.HALF_EVEN).toPlainString()
                            .toString()

                }

            }
        }
    }

    private fun addTake00(negative: Boolean) {
        if (negative) {

            viewModel.addTake00(binding.etAdd.text.toString().toDouble() * -1)
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.addTake.collect {
                        when (it) {
                            is Resource.Loading -> {
                                showLoading()
                            }
                            is Resource.Success -> {
                                hideLoading()
                            }
                            is Resource.Error -> {
                                hideLoading()
                                view?.makeSnackbar("${it.message}")
                            }

                        }
                    }
                }
            }


        } else {
            viewModel.addTake00(binding.etAdd.text.toString().toDouble())
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.addTake.collect {
                        when (it) {
                            is Resource.Loading -> {
                                showLoading()
                            }
                            is Resource.Success -> {
                                hideLoading()
                            }
                            is Resource.Error -> {
                                hideLoading()
                                view?.makeSnackbar("${it.message}")
                            }

                        }
                    }
                }
            }

        }
    }



}