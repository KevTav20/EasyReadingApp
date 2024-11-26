package com.example.easyreadingapp.ui.utils

sealed class Screens(val route : String) {
    data object Home : Screens("home")
    data object BookSearch : Screens("bookSearch")
    data object Contact : Screens("contact")
    data object BookDetail : Screens("bookDetail")
    data object Login : Screens("login")
    data object Library : Screens("library")
    data object Notifications : Screens("notifications")
    data object Stadistics : Screens("stadistics")
    data object Reviews : Screens("reviews")
    data object Results : Screens("results")
    data object Settings : Screens("settings")
    data object Profile : Screens("profile")
}