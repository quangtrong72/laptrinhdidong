package com.example.dangnhapdangky

sealed class Screen(val rout: String) {
    object Signin : Screen("signin")
    object Signup : Screen("signup")
    object Home : Screen("home")
}