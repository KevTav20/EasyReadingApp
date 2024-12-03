package com.example.easyreadingapp.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.bottombar.AnimatedBottomBar
import com.example.bottombar.components.BottomBarItem
import com.example.bottombar.model.IndicatorStyle
import com.example.bottombar.model.ItemStyle
import com.example.easyreadingapp.domain.dtos.NavigationItem
import com.example.easyreadingapp.domain.uses_cases.SharedPref
import com.example.easyreadingapp.presentation.ui.screens.BookDetailScreen
import com.example.easyreadingapp.presentation.ui.screens.BookSearchScreen
import com.example.easyreadingapp.presentation.ui.screens.HomeScreen
import com.example.easyreadingapp.presentation.ui.screens.LoginScreen
import com.example.easyreadingapp.presentation.ui.screens.RegisterScreen
import com.example.easyreadingapp.presentation.ui.screens.UserProfileScreen
import com.example.easyreadingapp.presentation.ui.theme.EasyReadingAppTheme
import com.example.easyreadingapp.presentation.ui.utils.Screens

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EasyReadingAppTheme {
                val navController = rememberNavController()
                var selectedItem by remember { mutableIntStateOf(0) }

                val sharedPref = SharedPref(context = applicationContext) // Usa una única instancia

                // Determinar el destino inicial
                val startDestination = if (sharedPref.getIsLoggedSharedPref()) {
                    Screens.Home.route
                } else {
                    Screens.Login.route
                }
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                // Define los ítems de la barra de navegación
                val navigationItems = listOf(
                    NavigationItem("Home", Icons.Default.Home, "home"),
                    NavigationItem("Search", Icons.Default.Search, "bookSearch"),
                    NavigationItem("Profile", Icons.Default.Person, "profile")
                )
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        if (currentRoute != Screens.Login.route && currentRoute != Screens.Register.route) {
                            AnimatedBottomBar(
                                selectedItem = selectedItem,
                                itemSize = navigationItems.size,
                                containerColor = MaterialTheme.colorScheme.errorContainer,
                                indicatorStyle = IndicatorStyle.DOT,
                            ) {
                                navigationItems.forEachIndexed { index, navigationItem ->
                                    val selected = index == selectedItem
                                    BottomBarItem(
                                        selected = selected,
                                        onClick = {
                                            selectedItem = index
                                        },
                                        imageVector = navigationItem.icon,
                                        label = navigationItem.title,
                                        iconColor = if (selected) Color.Black else Color.Black.copy(0.8f),
                                        textColor = if (selected) Color.Black else Color.Black.copy(0.8f),
                                        itemStyle = ItemStyle.STYLE5,
                                        activeIndicatorColor = Color.White,
                                    )
                                }
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = startDestination
                    ) {
                        composable(route = Screens.Login.route) {
                            LoginScreen(innerPadding = innerPadding, navController = navController)
                        }
                        composable(route = Screens.Register.route) {
                            RegisterScreen(
                                innerPadding = innerPadding,
                                navController = navController
                            )
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
                        composable(route = Screens.BookSearch.route) {
                            BookSearchScreen(innerPadding, navController)
                        }
                        composable(route = Screens.Profile.route) {
                            UserProfileScreen(innerPadding, navController)
                        }
                    }
                }
            }
        }
    }
}
