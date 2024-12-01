package com.example.easyreadingapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screens.Home.route
                    ){
                        composable(route = Screens.Login.route) {
                            LoginScreen(innerPadding = innerPadding, navController = navController)
                        }
                        composable(route = Screens.Register.route) {
                            RegisterScreen(innerPadding = innerPadding, navController = navController)
                        }
                        composable(route = Screens.Home.route ){
                            HomeScreen(innerPadding = innerPadding, navController = navController)
                        }
                    }
                }
            }
        }
    }
}
