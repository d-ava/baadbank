package com.example.baadbank.model

data class UserPreferences(
    val email: String,
    val password: String,
    val rememberCredentials: Boolean
)
