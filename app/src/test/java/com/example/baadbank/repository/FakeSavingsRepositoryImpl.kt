package com.example.baadbank.repository

import com.example.baadbank.data.User
import com.example.baadbank.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow

class FakeSavingsRepositoryImpl:SavingsRepository {

    private var shouldReturnNetworkError = false


    override fun addTake(newAmount: String, button: String): Flow<Resource<Double>> {
        TODO("Not yet implemented")
    }

    override fun saveUserInfo(name: String, phone: String) {
        TODO("Not yet implemented")
    }

    override suspend fun loadUserInfo(userFlow: MutableSharedFlow<User>) {
        TODO("Not yet implemented")
    }
}