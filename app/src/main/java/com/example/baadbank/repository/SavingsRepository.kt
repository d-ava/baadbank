package com.example.baadbank.repository

import com.example.baadbank.data.User1
import kotlinx.coroutines.flow.MutableSharedFlow

interface SavingsRepository {

   suspend fun loadUserInfo(flow: MutableSharedFlow<User1>)



}