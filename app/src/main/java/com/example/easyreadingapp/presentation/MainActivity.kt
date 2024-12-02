package com.example.easyreadingapp.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.easyreadingapp.domain.uses_cases.SharedPref
import com.example.easyreadingapp.presentation.ui.screens.BookDetailScreen
import com.example.easyreadingapp.presentation.ui.screens.HomeScreen
import com.example.easyreadingapp.presentation.ui.screens.LoginScreen
import com.example.easyreadingapp.presentation.ui.screens.RegisterScreen
import com.example.easyreadingapp.presentation.ui.theme.EasyReadingAppTheme
import com.example.easyreadingapp.presentation.ui.utils.Screens

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EasyReadingAppTheme {
                val navController = rememberNavController()
                val sharedPref = SharedPref(context = applicationContext) // Usa una única instancia

                // Determinar el destino inicial
                val startDestination = if (sharedPref.getIsLoggedSharedPref()) {
                    Screens.Home.route
                } else {
                    Screens.Login.route
                }

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = startDestination // Usar destino inicial estático
                    ) {
                        composable(route = Screens.Login.route) {
                            LoginScreen(innerPadding = innerPadding, navController = navController)
                        }
                        composable(route = Screens.Register.route) {
                            RegisterScreen(innerPadding = innerPadding, navController = navController)
                        }
                        composable(route = Screens.Home.route) {
                            HomeScreen(innerPadding = innerPadding, navController = navController)
                        }
                        composable(
                            route = Screens.BookDetail.route,
                            arguments = listOf(navArgument("bookId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val bookId = backStackEntry.arguments?.getInt("bookId") ?: 0
                            val userId = backStackEntry.arguments?.getInt("userId") ?: 0
                            BookDetailScreen(innerPadding, navController, bookId, userId)
                            Log.d("NavHost", "Received bookId: $bookId")
                        }
                    }
                }
            }
        }
    }
}

