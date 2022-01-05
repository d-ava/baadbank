package com.example.baadbank.ui.savings

import com.example.baadbank.databinding.FragmentSavingsBinding
import com.example.baadbank.extensions.makeSnackbar
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

        addTakeAmount()


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
                binding.tvBallance.text = "$savingsTest ₾" //need to set digits limit
            }

            override fun onCancelled(error: DatabaseError) {
               view?.makeSnackbar("oncancelled")
            }
        })


    }


    private fun addTakeAmount(){

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

            if (requiredAmount<=newAmount){
                newAmount-=requiredAmount
                currentUserDb.child("savings").setValue(newAmount)
            }else{
                view?.makeSnackbar("not enough money")
            }


        }


    }



}