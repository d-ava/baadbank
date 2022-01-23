package com.example.baadbank.repository


import com.example.baadbank.data.User
import com.example.baadbank.util.Resource
import com.example.baadbank.util.Utils.auth
import com.example.baadbank.util.Utils.databaseReference
import com.example.baadbank.util.safeCall
import com.google.firebase.FirebaseException
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.EmailAuthProvider
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject


class FireBaseRepository @Inject constructor() {


    private val user = auth.currentUser


    suspend fun resetPassword(email:String):Resource<String>
    {
        return withContext(IO)
        {
            auth.sendPasswordResetEmail(email).await()

            Resource.Success()
        }
    }


    suspend fun changePassword00(currentPassword: String, newPassword: String): Resource<String> {


        val credential = EmailAuthProvider.getCredential(user?.email!!, currentPassword)

        return withContext(IO) {


            user.reauthenticate(credential).await()
            user.updatePassword(newPassword).await()


            auth.signOut()

            Resource.Success()

        }


    }


    suspend fun loginUser(email: String, password: String): Resource<AuthResult> {
        return withContext(IO) {
            safeCall {

                val result =
                    auth.signInWithEmailAndPassword(email, password).await()
                Resource.Success(result)

            }
        }
    }

    suspend fun registerUser(
        fullName: String,
        userEmail: String,
        phoneNumber: String,
        userPassword: String
    ): Resource<AuthResult> {
        return withContext(IO) {
            safeCall {
                val registrationResult =
                    auth.createUserWithEmailAndPassword(userEmail, userPassword).await()


                val userId = registrationResult.user?.uid!!
                val newUser =
                    User(fullName = fullName, phone = phoneNumber, email = userEmail, savings = 0.0)
                databaseReference.child(userId).setValue(newUser)
                Resource.Success(registrationResult)
            }
        }
    }

    suspend fun registerUser01(
        fullName: String,
        userEmail: String,
        phoneNumber: String,
        userPassword: String
    ): Flow<Resource<AuthResult>> {
        return flow {
            try {
                emit(Resource.Loading())
                val registrationResult =
                    auth.createUserWithEmailAndPassword(userEmail, userPassword).await()


                val userId = registrationResult.user?.uid!!
                val newUser =
                    User(fullName = fullName, phone = phoneNumber, email = userEmail, savings = 0.0)
                databaseReference.child(userId).setValue(newUser)
                emit(Resource.Success(registrationResult))
            }catch (e:FirebaseException){
                emit(Resource.Error(e.message ?: "register message"))
            }
        }.flowOn(IO)
    }


}





