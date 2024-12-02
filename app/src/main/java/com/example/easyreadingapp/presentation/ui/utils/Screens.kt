package com.example.easyreadingapp.presentation.ui.utils

sealed class Screens(val route: String) {
    data object Home : Screens("home")
    data object BookSearch : Screens("bookSearch")
    data object BookDetail : Screens("bookDetail/{bookId}") // Usa un placeholder dinámico
    data object Login : Screens("login")
    data object Library : Screens("library")
    data object Stadistics : Screens("stadistics")
    data object Results : Screens("results")
    data object Profile : Screens("profile")
    data object Register : Screens("register")
}
