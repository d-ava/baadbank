package com.example.baadbank.repository

import com.example.baadbank.data.User1
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

class SavingsRepositoryImpl @Inject constructor() : SavingsRepository {

    var savingsTest: String = ""

    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseDatabase.getInstance()
    private val databaseReference = database.reference.child("profile")


    private val user = auth.currentUser

    private val userReference = databaseReference.child(user?.uid!!)


    fun addTake(newAmount: Double){
        userReference.child("savings").setValue(newAmount)

    }


    override suspend fun loadUserInfo(userFlow: MutableSharedFlow<User1>) {
        userReference
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val userInfo = User1(
                        snapshot.child("fullName").value.toString(),
                        snapshot.child("savings").value.toString()
                    )
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