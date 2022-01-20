package com.example.baadbank.util

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

object Utils {

    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    var database = FirebaseDatabase.getInstance()
    var databaseReference = database.reference.child("profile")


    var savingsBalance: String = ""
    var currencyList: MutableList<String> = mutableListOf()

}