package com.example.baadbank.util

import android.util.Patterns

object RegisterUtil {

    fun registerValidation(
        fullName: String,
        email: String,
        phoneNumber: String,
        password: String
    ):Boolean{

        if (fullName.isEmpty() || email.isEmpty() || phoneNumber.isEmpty() || password.isEmpty()) {
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return false
        }

        return true



    }


}