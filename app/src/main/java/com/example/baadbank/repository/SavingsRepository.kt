package com.example.baadbank.repository

import com.example.baadbank.data.User
import com.example.baadbank.util.Resource
import kotlinx.coroutines.flow.Flow

import kotlinx.coroutines.flow.MutableSharedFlow

interface SavingsRepository {


    fun addTake(newAmount: String, button: String): Flow<Resource<Double>>

    fun saveUserInfo(name: String, phone: String)
    suspend fun loadUserInfo(userFlow: MutableSharedFlow<User>)


}