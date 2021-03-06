package com.example.baadbank.util

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import androidx.core.graphics.drawable.toDrawable
import com.example.baadbank.R
import com.example.baadbank.data.CommercialRates
import com.example.baadbank.data.Converted
import com.example.baadbank.data.CurrencyItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase


object Utils {

//    var auth: FirebaseAuth = FirebaseAuth.getInstance()
//    var database = FirebaseDatabase.getInstance()
//    var databaseReference = database.reference.child("profile")

    var auth: FirebaseAuth = FirebaseAuth.getInstance()
    var database = FirebaseDatabase.getInstance()
    var databaseReference = database.reference.child("profile")


    var savingsBalance: String = ""
    var currencyListForAdapter: List<CurrencyItem> = listOf()
    var currencyList: MutableList<String> = mutableListOf()
    var convertedList: MutableList<Converted> = mutableListOf()

    var commercialRatesList:List<CommercialRates.CommercialRates> = listOf()


    fun showLoadingDialog(context: Context): Dialog {
        val progressDialog = Dialog(context)

        progressDialog.let {
            it.show()
            it.window?.setBackgroundDrawable(Color.TRANSPARENT.toDrawable())
            it.setContentView(R.layout.progress_dialog)
            it.setCancelable(false)
            it.setCanceledOnTouchOutside(true)

            return it
        }
    }

}