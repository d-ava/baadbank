package com.example.baadbank.repository

import android.util.Log
import com.example.baadbank.model.User
import com.example.baadbank.util.Resource
import com.example.baadbank.util.Utils.auth
import com.example.baadbank.util.Utils.databaseReference
import com.example.baadbank.util.Utils.savingsBalance
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SavingsRepositoryImpl @Inject constructor() : SavingsRepository {




    private val userReference = databaseReference.child(auth.currentUser?.uid!!)




    fun addTake00(newAmount: Double): Resource<Double> {
        userReference.child("savings").setValue(newAmount)
        return Resource.Success()
    }

    fun saveUserInfo(name: String, phone: String) {
        CoroutineScope(IO).launch {
            userReference.child("phone").setValue(phone)
            userReference.child("fullName").setValue(name)

        }
    }


    suspend fun loadUserInfo00(userFlow: MutableSharedFlow<User>){
        var userInfo = User()
        val user = auth.currentUser
        val userReference = databaseReference.child(user?.uid!!)
        Log.d("---", "userInfo $userInfo")


        userReference.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                 userInfo = User(
                    fullName = snapshot.child("fullName").value.toString(),
                    savings = snapshot.child("savings").value.toString().toDouble(),
                    phone = snapshot.child("phone").value.toString(),
                    email = auth.currentUser?.email.toString()
                    )
                savingsBalance = snapshot.child("savings").value.toString()
                Log.d("---", " savings ballance $savingsBalance")
                Log.d("---", "userInfo 2 $userInfo")

                CoroutineScope(IO).launch {

                    userFlow.emit(userInfo)

                }

            }



            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })





    }




}