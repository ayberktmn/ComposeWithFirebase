package com.ayberk.composeapp.Navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.ui.NavigationUiSaveStateControl
import com.ayberk.composeapp.anasayfa
import com.ayberk.composeapp.login
import com.ayberk.composeapp.register

@SuppressLint("SuspiciousIndentation")
@Composable
fun navigation(){

  val navHostController = rememberNavController()

    NavHost(navHostController, startDestination = "login") {
        composable("login"){
            login(navHostController)
        }
        composable("register") {
            register(navHostController)
        }
        composable("anasayfa") {
            anasayfa(navHostController)
        }
    }
}