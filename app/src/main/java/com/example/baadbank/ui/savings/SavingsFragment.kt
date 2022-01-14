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

    var savingsTest: String = ""

    override fun start() {

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database.reference.child("profile")


//       addTakeAmount()
        setListeners()
        loadUserInfo01()

    }

    private fun setListeners() {
        binding.btnAdd.setOnClickListener {
            addTake(false)
        }

        binding.btnTake.setOnClickListener {
            addTake(true)
        }
    }

    private fun loadUserInfo01() {
        viewModel.loadUserInfo()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loadUserInfo.collect {
                    binding.tvWelcome.text = it.fullName
                    binding.tvBallance.text = it.savings
                }

            }
        }
    }


    private fun progressBar(visible: Boolean) {
        binding.progressbar.isVisible = visible
    }


//    private fun loadUserInfo() {
//        val user = auth.currentUser
//        val userReference = databaseReference.child(user?.uid!!)
//
//        userReference.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val fullName = snapshot.child("fullName").value.toString()
//                savingsTest = snapshot.child("savings").value.toString()
//                val savings = snapshot.child("savings").value.toString()
//
//                binding.tvWelcome.text = "welcome $fullName"
//                binding.tvBallance.text = "$savingsTest ₾" //need to set digits limit
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                view?.makeSnackbar("oncancelled")
//            }
//        })
//
//
//    }

    private fun addTake(negative:Boolean) {
        if (negative){
            viewModel.addTake(binding.etAdd.text.toString().toDouble()*-1)
        }else{
            viewModel.addTake(binding.etAdd.text.toString().toDouble())
        }
    }


    private fun addTakeAmount() {

        val currentUser = auth.currentUser
        val currentUserDb = databaseReference.child(currentUser?.uid!!)



        binding.btnAdd.setOnClickListener {
            val amount = binding.etAdd.text.toString().toDouble()
            var newAmount = savingsTest!!.toDouble()
            newAmount += amount
            currentUserDb.child("savings").setValue(newAmount)

        }

        binding.btnTake.setOnClickListener {
            val requiredAmount = binding.etTake.text.toString().toDouble()
            var newAmount = savingsTest!!.toDouble()

            if (requiredAmount <= newAmount) {
                newAmount -= requiredAmount
                currentUserDb.child("savings").setValue(newAmount)
            } else {
                view?.makeSnackbar("not enough money")
            }


        }


    }


}