package com.example.baadbank.util

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase

object Utils {

    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    var database = FirebaseDatabase.getInstance()
    var databaseReference = database.reference.child("profile")

    var savingsBalance: String = ""


}