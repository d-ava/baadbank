package com.example.baadbank.repository


import android.util.Patterns
import com.example.baadbank.data.User
import com.example.baadbank.util.Resource
import com.example.baadbank.util.Utils.auth
import com.example.baadbank.util.Utils.databaseReference
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.EmailAuthProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject


class FireBaseRepository @Inject constructor() {





    suspend fun resetPassword(email: String): Resource<String> {
        return withContext(IO)
        {
            auth.sendPasswordResetEmail(email).await()

            Resource.Success()
        }
    }


    suspend fun changePassword(currentPassword: String, newPassword: String): Resource<String> {
        val user = auth.currentUser

        val ioDispatcher = Dispatchers.IO
        val credential = EmailAuthProvider.getCredential(user?.email!!, currentPassword)

        return withContext(ioDispatcher) {


            user.reauthenticate(credential).await()
            user.updatePassword(newPassword).await()


            auth.signOut()

            Resource.Success()

        }


    }

    suspend fun loginUser(email: String, password: String): Flow<Resource<AuthResult>> {
        return flow {
            try {
                emit(Resource.Loading())
                val result =
                    auth.signInWithEmailAndPassword(email, password).await()
                emit(Resource.Success(result))
            } catch (e: FirebaseNetworkException) {
                emit(Resource.Error("network connection error"))
            } catch (e: Exception) {
                emit(Resource.Error(e.message ?: "unknown error"))
            }
        }.flowOn(IO)

    }


    suspend fun registerUser(
        fullName: String,
        userEmail: String,
        phoneNumber: String,
        userPassword: String,
        repeatPassword: String
    ): Flow<Resource<AuthResult>> {
        return flow {

            if (fullName.isNotEmpty() && userEmail.isNotEmpty() && phoneNumber.isNotEmpty() && userPassword.isNotEmpty() && repeatPassword.isNotEmpty()) {
                if (Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
                    if (userPassword == repeatPassword) {
                        try {
                            emit(Resource.Loading())
                            val registrationResult =
                                auth.createUserWithEmailAndPassword(userEmail, userPassword).await()


                            val userId = registrationResult.user?.uid!!
                            val newUser =
                                User(
                                    fullName = fullName,
                                    phone = phoneNumber,
                                    email = userEmail,
                                    savings = 0.0
                                )
                            databaseReference.child(userId).setValue(newUser)
                            emit(Resource.Success(registrationResult))
                        } catch (e: FirebaseNetworkException) {
                            emit(Resource.Error(e.message ?: "network connection error"))
                        } catch (e: Exception) {
                            emit(Resource.Error(e.message ?: "unknown error"))
                        }

                    } else {
                        emit(Resource.Error("password mismatching"))
                    }
                } else {
                    emit(Resource.Error("enter valid email"))
                }
            } else {
                emit(Resource.Error("empty fields"))
            }


        }.flowOn(IO)
    }


}





