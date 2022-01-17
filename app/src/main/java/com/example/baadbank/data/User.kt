package com.example.baadbank.data

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class User(
    val fullName: String = "",
    val email: String = "",
    val phone: String = "",
    val savings: Double = 0.0

) : Parcelable
