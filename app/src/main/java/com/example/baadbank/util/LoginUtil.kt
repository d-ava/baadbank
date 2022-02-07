package com.example.baadbank.util

object LoginUtil {

    private val registeredUsers = listOf<String>("baadbank@gmail.com", "david@gmail.com")

    /**
     * the input is not valid if...
     * ... email/password is empty
     * ... email is not registered
     *
     */

    fun validateLogInInput(
        email:String,
        password:String
    ):Boolean{
        if (email.isEmpty() || password.isEmpty()){
            return false
        }
        if (email !in registeredUsers){
            return false
        }
        return true
    }


}