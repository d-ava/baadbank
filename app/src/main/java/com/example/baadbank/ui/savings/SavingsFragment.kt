package com.example.baadbank.ui.savings

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.baadbank.R
import com.example.baadbank.databinding.FragmentSavingsBinding
import com.example.baadbank.extensions.makeSnackbar
import com.example.baadbank.ui.BaseFragment
import com.example.baadbank.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.math.RoundingMode


@AndroidEntryPoint
class SavingsFragment : BaseFragment<FragmentSavingsBinding>(FragmentSavingsBinding::inflate) {

    private val viewModel: SavingsViewModel by activityViewModels()




    override fun start() {





        setListeners()
        loadUserInfo()

    }

    private fun setListeners() {
        binding.btnAdd.setOnClickListener {
            addTake("add")

            binding.etAmount.text?.clear()
        }

        binding.btnTake.setOnClickListener {

            addTake("take")
            binding.etAmount.text?.clear()
        }
    }


    private fun loadUserInfo() {
        viewModel.loadUserInfo()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loadUserInfo.collect {
                    binding.tvWelcome.text =
                        String.format(getString(R.string.welcome_string), it.fullName)
                    binding.tvBallance.text =
                        BigDecimal(it.savings).setScale(2, RoundingMode.HALF_EVEN).toPlainString()
                            .toString()

                }

            }
        }
    }


    private fun addTake(button: String) {


            viewModel.addTake(binding.etAmount.text.toString(), button)
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.addTake.collect {
                        when(it){
                            is Resource.Loading -> {
                                showLoading()
                            }
                            is Resource.Success -> {

                                hideLoading()

                            }
                            is Resource.Error -> {
                                hideLoading()
                                val message = it.message
                                view?.makeSnackbar(message!!)
                            }
                        }
                    }
                }
            }



    }





}