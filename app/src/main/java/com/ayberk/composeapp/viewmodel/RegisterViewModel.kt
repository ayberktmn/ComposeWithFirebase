package com.ayberk.composeapp.viewmodel

import androidx.lifecycle.ViewModel
import com.ayberk.composeapp.data.User
import com.ayberk.composeapp.util.Constans.USER_COLLECTION
import com.ayberk.composeapp.util.Resource
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
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
   fun currentUser():Boolean {
      firebaseauth.currentUser
      return false
   }
}