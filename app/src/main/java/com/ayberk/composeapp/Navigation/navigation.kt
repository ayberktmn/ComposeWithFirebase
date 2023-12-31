package com.ayberk.composeapp.Navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ayberk.composeapp.anasayfa
import com.ayberk.composeapp.countrydetails
import com.ayberk.composeapp.login
import com.ayberk.composeapp.register
import com.ayberk.composeapp.timesScreen
import com.ayberk.composeapp.townScreen

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
        composable("town/{townCode}",
            arguments = listOf(
                navArgument("townCode") {
                    type = NavType.StringType
                }
            )
        ) {
            val townCode = remember {
                it.arguments?.getString("townCode")
            }
            townScreen(
                townCode = townCode ?: "",
                navHostController = navHostController
            )
        }
        composable("times/{timesCode}",
            arguments = listOf(
                navArgument("timesCode") {
                    type = NavType.StringType
                }
            )
        ) {
            val timesCode = remember {
                it.arguments?.getString("timesCode")
            }
            timesScreen(
                timesCode = timesCode ?: "",
                navHostController = navHostController
            )
        }
    }
}