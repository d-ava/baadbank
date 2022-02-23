package com.example.baadbank.repository

import com.example.baadbank.data.User
import com.example.baadbank.util.Constants.FULL_NAME
import com.example.baadbank.util.Constants.PHONE
import com.example.baadbank.util.Constants.SAVINGS
import com.example.baadbank.util.Resource
import com.example.baadbank.util.Utils.auth
import com.example.baadbank.util.Utils.databaseReference
import com.example.baadbank.util.Utils.savingsBalance
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
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




    override fun addTake(newAmount: String, button: String): Flow<Resource<Double>> {

        val user = auth.currentUser
        val userReference = databaseReference.child(user?.uid!!)

        return flow {

            if (newAmount.isEmpty()) {
                emit(Resource.Error("please enter amount"))
            }else{

                try {

                    emit(Resource.Loading())

                    var amount = newAmount.toDouble()
                    if (button == "take") {
                        amount *= -1
                    }

                    val totalAmount = savingsBalance.toDouble() + amount

                    if (totalAmount < 0){
                        emit(Resource.Error("not enough amount"))
                    }else{



                        userReference.child("savings").setValue(totalAmount).await()



                        emit(Resource.Success())

                    }




                } catch (e: IOException) {
                    emit(Resource.Error(e.message ?: "unknown error"))
                }

            }



        }
    }




    override fun saveUserInfo(name: String, phone: String) {
        val user = auth.currentUser
        val userReference = databaseReference.child(user?.uid!!)

        CoroutineScope(IO).launch {
            userReference.child(PHONE).setValue(phone)
            userReference.child(FULL_NAME).setValue(name)

        }
    }


    override suspend fun loadUserInfo(userFlow: MutableSharedFlow<User>) {
        var userInfo: User

        val user = auth.currentUser
        val userReference = databaseReference.child(user?.uid!!)



        userReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userInfo = User(
                    fullName = snapshot.child("fullName").value.toString(),
                    savings = snapshot.child("savings").value.toString().toDouble(),
                    phone = snapshot.child("phone").value.toString(),
                    email = auth.currentUser?.email.toString()
                )
                savingsBalance = snapshot.child(SAVINGS).value.toString()



                CoroutineScope(IO).launch {

                    userFlow.emit(userInfo)

                }

            }


            override fun onCancelled(error: DatabaseError) {

            }
        })


    }


}