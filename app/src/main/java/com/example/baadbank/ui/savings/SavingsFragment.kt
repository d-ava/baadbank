package com.example.baadbank.ui.savings

import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.baadbank.databinding.FragmentSavingsBinding
import com.example.baadbank.extensions.makeSnackbar
import com.example.baadbank.ui.BaseFragment
import com.example.baadbank.util.Resource
import com.example.baadbank.util.Utils.savingsBalance
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
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
            addTake00(false)
//            binding.etAdd.text?.clear()
        }

        binding.btnTake.setOnClickListener {
            addTake00(true)
//            binding.etTake.text?.clear()
        }
    }

    private fun loadUserInfoFFF(){
        val user = auth.currentUser
        val userReference = databaseReference.child(user?.uid!!)

        userReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val fullName =  snapshot.child("fullName").value.toString()
                savingsBalance = snapshot.child("savings").value.toString()
                val savings = snapshot.child("savings").value.toString()

                binding.tvWelcome.text = "welcome $fullName"
                binding.tvBallance.text = "$savingsBalance ₾" //need to set digits limit
            }

            override fun onCancelled(error: DatabaseError) {
                view?.makeSnackbar("oncancelled")
            }
        })


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
                    binding.tvWelcome.text = it.fullName
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


//    private fun addTake(negative: Boolean) {
//        if (negative) {
//            viewModel.addTake(binding.etAdd.text.toString().toDouble() * -1)
//        } else {
//            viewModel.addTake(binding.etAdd.text.toString().toDouble())
//        }
//    }

//    private fun progressBar(visible: Boolean) {
//        binding.progressbar.isVisible = visible
//    }


}