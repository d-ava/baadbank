package com.example.baadbank.ui.savings

import android.annotation.SuppressLint
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.baadbank.databinding.FragmentSavingsBinding
import com.example.baadbank.extensions.makeSnackbar

import com.example.baadbank.ui.BaseFragment
import com.example.baadbank.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch



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


//       addTakeAmount()
        setListeners()
        loadUserInfo()

    }

    private fun setListeners() {
        binding.btnAdd.setOnClickListener {
            addTake(false)
        }

        binding.btnTake.setOnClickListener {
            addTake(true)
        }
    }

    private fun loadUserInfo() {
        viewModel.loadUserInfo()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loadUserInfo.collect {
                    binding.tvWelcome.text = it.fullName
                    binding.tvBallance.text = it.savings.toString()

                }

            }
        }
    }







    private fun addTake(negative:Boolean) {
        if (negative){
            viewModel.addTake(binding.etAdd.text.toString().toDouble()*-1)
        }else{
            viewModel.addTake(binding.etAdd.text.toString().toDouble())
        }
    }

    private fun progressBar(visible: Boolean) {
        binding.progressbar.isVisible = visible
    }


}