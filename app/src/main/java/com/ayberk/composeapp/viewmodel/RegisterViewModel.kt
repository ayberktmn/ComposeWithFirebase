package com.ayberk.composeapp.viewmodel

import android.app.LoaderManager.LoaderCallbacks
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import com.ayberk.composeapp.data.User
import com.ayberk.composeapp.util.Constans.USER_COLLECTION
import com.ayberk.composeapp.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(

   private val firebaseauth : FirebaseAuth,
   private val db : FirebaseFirestore

): ViewModel() {

   private val _register = MutableStateFlow<Resource<User>>(Resource.Unspecified())
   val register: Flow<Resource<User>> = _register

     fun createEmailandPassword(user: User, password: String){

        firebaseauth.createUserWithEmailAndPassword(user.email,password)
           .addOnSuccessListener {
              it.user?.let {
                 saveUserInfo(it.uid,user)
              }
           }
           .addOnFailureListener {
              _register.value = Resource.Error(it.message.toString())
           }
     }

   fun saveUserInfo(userUid : String,user: User){

      db.collection(USER_COLLECTION)
         .document(userUid)
         .set(user)
         .addOnSuccessListener {
            _register.value = Resource.Success(user)
         }
         .addOnFailureListener {
            _register.value = Resource.Error(it.message.toString())
         }
   }

   fun changePassword(newPassword: String, confirmPassword: String, onResult: (Boolean, String) -> Unit) {
      currentUser()
      if (newPassword == confirmPassword) {
         val user = firebaseauth.currentUser
         user?.updatePassword(newPassword)
            ?.addOnCompleteListener { task ->
               if (task.isSuccessful) {
                  onResult(true, "Şifre değişikliği başarılı")
               } else {
                  onResult(false, "Şifre değişikliği başarısız")
               }
            }
      } else {
         onResult(false, "Şifreler uyuşmuyor")
      }
   }

   fun currentUser() {
      firebaseauth.currentUser
   }



}