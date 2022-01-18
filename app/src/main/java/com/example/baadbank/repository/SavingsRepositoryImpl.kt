package com.example.baadbank.repository

import android.net.Uri
import com.example.baadbank.data.User
import com.example.baadbank.util.Utils.auth
import com.example.baadbank.util.Utils.databaseReference
import com.example.baadbank.util.Utils.savingsBalance
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SavingsRepositoryImpl @Inject constructor() : SavingsRepository {


    private val userReference = databaseReference.child(auth.currentUser?.uid!!)


    fun addTake(newAmount: Double) {
        userReference.child("savings").setValue(newAmount)

    }

    fun saveUserInfo(name: String, phone: String) {
        CoroutineScope(IO).launch {
            userReference.child("phone").setValue(phone)
            userReference.child("fullName").setValue(name)

        }




    }



    override suspend fun loadUserInfo(userFlow: MutableSharedFlow<User>) {
        userReference
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val userInfo = User(
                        fullName = snapshot.child("fullName").value.toString(),
                        savings = snapshot.child("savings").value.toString().toDouble(),
                        phone = snapshot.child("phone").value.toString(),
                        email = auth.currentUser?.email.toString(),


                    )
                    savingsBalance = snapshot.child("savings").value.toString()



                    CoroutineScope(IO).launch {

                        userFlow.emit(userInfo)

                    }

                }

                override fun onCancelled(error: DatabaseError) {
                    // nothing to do
                }
            })
    }


}