package com.example.baadbank.ui.savings

import com.example.baadbank.databinding.FragmentSavingsBinding
import com.example.baadbank.ui.BaseFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class SavingsFragment : BaseFragment<FragmentSavingsBinding>(FragmentSavingsBinding::inflate) {

    lateinit var auth: FirebaseAuth
    lateinit var databaseReference: DatabaseReference
    lateinit var database: FirebaseDatabase

    var savingsTest:String? = null

    override fun start() {

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        databaseReference = database.reference.child("profile")

        loadUserInfo()

        addAmount()

    }


    private fun loadUserInfo(){
       val user = auth.currentUser
       val userReference = databaseReference.child(user?.uid!!)

        userReference.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val fullName =  snapshot.child("fullName").value.toString()
                savingsTest = snapshot.child("savings").value.toString()
//                val savings = snapshot.child("savings").value.toString()

                binding.tvWelcome.text = "welcome $fullName"
                binding.tvBallance.text = savingsTest
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


    }


    private fun addAmount(){

        val currentUser = auth.currentUser
        val currentUserDb = databaseReference.child(currentUser?.uid!!)

        binding.btnAdd.setOnClickListener {
            val amount = binding.etAdd.text.toString().toDouble()
            var newAmount = savingsTest!!.toDouble()
            newAmount += amount
            currentUserDb.child("savings").setValue(newAmount)

        }


    }

}