package com.example.baadbank.repository

import android.net.Uri
import android.util.Log
import com.example.baadbank.data.User
import com.example.baadbank.extensions.makeSnackbar
import com.example.baadbank.util.Resource
import com.example.baadbank.util.Utils.auth
import com.example.baadbank.util.Utils.databaseReference
import com.example.baadbank.util.Utils.savingsBalance
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import java.io.IOException
import javax.inject.Inject

class SavingsRepositoryImpl @Inject constructor() : SavingsRepository {


    private val userReference = databaseReference.child(auth.currentUser?.uid!!)

    fun addTakeTest(newAmount: String, button: String): Flow<Resource<Double>> {
        return flow {

            try {
                if (newAmount.isNullOrEmpty()) {
                    emit(Resource.Error("please enter amount"))
                } else {
                    emit(Resource.Loading())

                    var amount = newAmount.toDouble()
                    if (button == "take") {
                        amount *= -1
                    }

                    val totalAmount = savingsBalance.toDouble() + amount

                    if (totalAmount < 0){
                        emit(Resource.Error("not enough amount"))
                    }else{

                        userReference.child("savings").setValue(totalAmount).await() //// ??
                        emit(Resource.Success())

                    }


                }

            } catch (e: IOException) {
                emit(Resource.Error(e.message ?: "error message"))
            }

        }
    }


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


    suspend fun loadUserInfo00(userFlow: MutableSharedFlow<User>) {
        var userInfo = User()
        val user = auth.currentUser
        val userReference = databaseReference.child(user?.uid!!)
        Log.d("---", "userInfo $userInfo")


        userReference.addValueEventListener(object : ValueEventListener {
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