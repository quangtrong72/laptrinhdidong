package com.example.dangnhapdangky

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun Mynavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Signin.rout
    ) {
        composable(Screen.Signin.rout) {
            SignIn(navController = navController)
        }
        composable(Screen.Home.rout) {
            HomeScreen(navController = navController)
        }
        composable(Screen.Signup.rout) {
            SignUp(navController = navController)
        }
    }
}