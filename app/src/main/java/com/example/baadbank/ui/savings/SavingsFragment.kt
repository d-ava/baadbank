package com.example.baadbank.ui.savings

import com.example.baadbank.databinding.FragmentSavingsBinding
import com.example.baadbank.extensions.makeSnackbar
import com.example.baadbank.ui.BaseFragment
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class SavingsFragment : BaseFragment<FragmentSavingsBinding>(FragmentSavingsBinding::inflate) {

    //    private lateinit var database: FirebaseDatabase
    private val database = Firebase.database
    private val mySavings = database.getReference("Savings Test")

    //val ballListener = object: ValueEventListener{
//    override fun onDataChange(snapshot: DataSnapshot) {
//        val value1 = snapshot.value
//        binding.tvBallance.text = value1.toString()
//    }
//
//    override fun onCancelled(error: DatabaseError) {
//        view?.makeSnackbar("failed to read from database")
//    }
//}
    override fun start() {


        setListeners()


    }


    private fun setListeners() {

        var ballance = 0
        binding.btnAdd.setOnClickListener {

            val amount = binding.etAdd.text.toString().toInt()
            ballance += amount
            mySavings.setValue(ballance)

//            it.makeSnackbar("saved")

            mySavings.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val value = snapshot.getValue<Int>()
                    binding.tvBallance.text = value.toString()
                }

                override fun onCancelled(error: DatabaseError) {
                    view?.makeSnackbar("failed to read from database")
                }
            }
            )
        }

        binding.btnTake.setOnClickListener {
            val amount = binding.etTake.text.toString().toInt()
            ballance -= amount

//            mySavings.addValueEventListener(object : ValueEventListener {
//                override fun onDataChange(snapshot: DataSnapshot) {
//                    val value = snapshot.getValue<Int>()
//                    binding.tvBallance.text = value.toString()
//                }
//
//                override fun onCancelled(error: DatabaseError) {
//                    view?.makeSnackbar("failed to read from database")
//                }
//            }
//            )

        }


    }
}