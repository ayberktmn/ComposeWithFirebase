package com.ayberk.composeapp.Navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.ui.NavigationUiSaveStateControl
import com.ayberk.composeapp.Loading
import com.ayberk.composeapp.anasayfa
import com.ayberk.composeapp.countrydetails
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
        composable("country/{countryCode}",
            arguments = listOf(
                navArgument("countryCode") {
                    type = NavType.StringType
                }
            )
        ) {
            val countryCode = remember {
                it.arguments?.getString("countryCode")
            }
            countrydetails(
                countryCode = countryCode ?: "",
                navHostController = navHostController
            )
        }
    }
}