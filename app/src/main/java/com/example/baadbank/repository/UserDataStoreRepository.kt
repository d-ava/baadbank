package com.example.baadbank.repository

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.baadbank.data.UserPreferences
import com.example.baadbank.repository.UserDataStoreRepository.PreferencesKeys.EMAIL
import com.example.baadbank.repository.UserDataStoreRepository.PreferencesKeys.PASSWORD
import com.example.baadbank.repository.UserDataStoreRepository.PreferencesKeys.REMEMBER
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore by preferencesDataStore("user_login")

@Singleton
class UserDataStoreRepository @Inject constructor(@ApplicationContext context:Context) {

    private val dataStore = context.dataStore

    private object PreferencesKeys {
        val EMAIL = stringPreferencesKey("email")
        val PASSWORD = stringPreferencesKey("password")
        val REMEMBER = booleanPreferencesKey("remember")
    }

    suspend fun saveToDataStore(email:String,password:String,remember:Boolean)
    {
        dataStore.edit { preference->
            preference[EMAIL] = email
            preference[PASSWORD] = password
            preference[REMEMBER] = remember
        }
    }

    val readFromDataStore : Flow<UserPreferences> = dataStore.data.catch { exception->
        if(exception is IOException)
        {
            emit(emptyPreferences())
        } else{
            throw exception
        }
    }.map {preference->
        val userEmail = preference[EMAIL] ?: ""
        val userPassword = preference[PASSWORD]?:""
        val remember = preference[REMEMBER] ?: false
        UserPreferences(userEmail,userPassword,remember)
    }

    suspend fun clearDataStore() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    suspend fun removeUsername() {
        dataStore.edit { preference ->
            preference.remove(EMAIL)
        }
    }

}