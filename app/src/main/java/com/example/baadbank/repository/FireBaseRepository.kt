package com.example.baadbank.repository


import android.util.Log
import com.example.baadbank.data.User
import com.example.baadbank.util.Resource
import com.example.baadbank.util.Utils.auth
import com.example.baadbank.util.Utils.databaseReference
import com.example.baadbank.util.safeCall
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton


class FireBaseRepository @Inject constructor() {



    private val user = auth.currentUser



   suspend fun changePassword(password:String, newPassword:String){
        val credential = EmailAuthProvider.getCredential(user?.email!!, password)
//       val credentialTest = EmailAuthProvider.getCredential("neo@gmail.com", "test123")

       CoroutineScope(IO).launch {

           user.reauthenticate(credential).await()

           user.updatePassword(newPassword).await()

           auth.signOut()

       }
//        withContext(IO){
//            user.reauthenticate(credential).await()
//            user.updatePassword(newPassword).await()
//            auth.signOut()
//        }

//        user.reauthenticate(credential).addOnCompleteListener { task ->
//            user.updatePassword(newPassword).addOnCompleteListener {
//                auth.signOut()
//            }
//
//        }
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
        fullName:String,
        userEmail:String,
        phoneNumber:String,
        userPassword:String
    ) : Resource<AuthResult>{
        return withContext(IO) {
            safeCall {
                val registrationResult = auth.createUserWithEmailAndPassword(userEmail, userPassword).await()


                val userId = registrationResult.user?.uid!!
                val newUser = User(fullName=fullName,phone=phoneNumber, email=userEmail, savings = 0.0)
                databaseReference.child(userId).setValue(newUser)
                Resource.Success(registrationResult)
            }
        }
    }


}





