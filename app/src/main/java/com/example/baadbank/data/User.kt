package com.example.baadbank.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class User(
    val fullName: String,
    val email: String,
    val phone: String ,
    val savings:Double

):Parcelable
