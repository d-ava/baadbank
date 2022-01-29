package com.example.baadbank.ui.savings

import android.util.Log
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

    lateinit var auth: FirebaseAuth
    lateinit var databaseReference: DatabaseReference
    lateinit var database: FirebaseDatabase


    override fun start() {

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database.reference.child("profile")



        setListeners()
        loadUserInfo()

    }

    private fun setListeners() {
        binding.btnAdd.setOnClickListener {
            addTakeTest("add")
//            addTake00(false)
//            binding.etAdd.text?.clear()
        }

        binding.btnTake.setOnClickListener {
//            addTake00(true)
            addTakeTest("take")
//            binding.etTake.text?.clear()
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


    private fun addTakeTest(button: String) {


            viewModel.addTakeTest(binding.etAdd.text.toString(), button)
            viewLifecycleOwner.lifecycleScope.launch {
                repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.addTakeTest.collect {
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