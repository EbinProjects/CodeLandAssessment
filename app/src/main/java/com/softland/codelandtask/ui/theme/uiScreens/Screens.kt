package com.softland.codelandtask.ui.theme.uiScreens

sealed class Screens(val route:String) {
    object loginScreen : Screens("login")
    object homeScreen : Screens("HomeScreen")
    object splash : Screens("Splash")
}