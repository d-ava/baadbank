package com.example.baadbank.data

data class UserPreferences(
    val email: String,
    val password: String,
    val rememberCredentials: Boolean
)
