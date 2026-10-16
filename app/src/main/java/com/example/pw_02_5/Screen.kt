package com.example.pw_02_5

sealed class Screen(val route: String) {
    object MainMenu : Screen("main_menu")
    object Login : Screen("login")
    object Register : Screen("register")
    object Profile : Screen("profile")
}