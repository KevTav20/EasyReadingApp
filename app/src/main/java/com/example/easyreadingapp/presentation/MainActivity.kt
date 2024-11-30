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
import com.example.easyreadingapp.presentation.ui.screens.BookImage
import com.example.easyreadingapp.presentation.ui.screens.bookColumn
import com.example.easyreadingapp.presentation.ui.screens.bookList
import com.example.easyreadingapp.presentation.ui.theme.EasyReadingAppTheme
import com.example.easyreadingapp.utils.Screens

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
                        startDestination = "home",
                        modifier = Modifier.padding(innerPadding)
                    ){
                        composable(route = Screens.Home.route){
//                            HomeScreen(innerPadding = innerPadding, navController = navController)
//                            var imagenes = listOf<String>("https://m.media-amazon.com/images/I/81ySgYEwArL._AC_UF1000,1000_QL80_.jpg","https://m.media-amazon.com/images/I/81ySgYEwArL._AC_UF1000,1000_QL80_.jpg","https://m.media-amazon.com/images/I/81ySgYEwArL._AC_UF1000,1000_QL80_.jpg","https://m.media-amazon.com/images/I/81ySgYEwArL._AC_UF1000,1000_QL80_.jpg","https://m.media-amazon.com/images/I/81ySgYEwArL._AC_UF1000,1000_QL80_.jpg")
//                            bookList(titulo = "Hola", items = imagenes)

                            val bookData = listOf(
                                "Libros de Fantasía" to listOf(
                                    "https://m.media-amazon.com/images/I/81ySgYEwArL._AC_UF1000,1000_QL80_.jpg",
                                    "https://m.media-amazon.com/images/I/81ySgYEwArL._AC_UF1000,1000_QL80_.jpg",
                                    "https://m.media-amazon.com/images/I/81ySgYEwArL._AC_UF1000,1000_QL80_.jpg",
                                    "https://m.media-amazon.com/images/I/81ySgYEwArL._AC_UF1000,1000_QL80_.jpg",
                                    ),
                                "Libros de Ciencia Ficción" to listOf(
                                    "https://m.media-amazon.com/images/I/81ySgYEwArL._AC_UF1000,1000_QL80_.jpg",
                                    "https://m.media-amazon.com/images/I/81ySgYEwArL._AC_UF1000,1000_QL80_.jpg",
                                    "https://m.media-amazon.com/images/I/81ySgYEwArL._AC_UF1000,1000_QL80_.jpg",
                                    "https://m.media-amazon.com/images/I/81ySgYEwArL._AC_UF1000,1000_QL80_.jpg",
                                    ),
                                "Libros de Ciencia Ficción" to listOf(
                                    "https://m.media-amazon.com/images/I/81ySgYEwArL._AC_UF1000,1000_QL80_.jpg",
                                    "https://m.media-amazon.com/images/I/81ySgYEwArL._AC_UF1000,1000_QL80_.jpg",
                                    "https://m.media-amazon.com/images/I/81ySgYEwArL._AC_UF1000,1000_QL80_.jpg",
                                    "https://m.media-amazon.com/images/I/81ySgYEwArL._AC_UF1000,1000_QL80_.jpg",
                                ),
                                "Libros de Ciencia Ficción" to listOf(
                                    "https://m.media-amazon.com/images/I/81ySgYEwArL._AC_UF1000,1000_QL80_.jpg",
                                    "https://m.media-amazon.com/images/I/81ySgYEwArL._AC_UF1000,1000_QL80_.jpg",
                                    "https://m.media-amazon.com/images/I/81ySgYEwArL._AC_UF1000,1000_QL80_.jpg",
                                    "https://m.media-amazon.com/images/I/81ySgYEwArL._AC_UF1000,1000_QL80_.jpg",
                                ),
                                "Libros de Ciencia Ficción" to listOf(
                                    "https://m.media-amazon.com/images/I/81ySgYEwArL._AC_UF1000,1000_QL80_.jpg",
                                    "https://m.media-amazon.com/images/I/81ySgYEwArL._AC_UF1000,1000_QL80_.jpg",
                                    "https://m.media-amazon.com/images/I/81ySgYEwArL._AC_UF1000,1000_QL80_.jpg",
                                    "https://m.media-amazon.com/images/I/81ySgYEwArL._AC_UF1000,1000_QL80_.jpg",
                                ),
                                "Libros de Ciencia Ficción" to listOf(
                                    "https://m.media-amazon.com/images/I/81ySgYEwArL._AC_UF1000,1000_QL80_.jpg",
                                    "https://m.media-amazon.com/images/I/81ySgYEwArL._AC_UF1000,1000_QL80_.jpg",
                                    "https://m.media-amazon.com/images/I/81ySgYEwArL._AC_UF1000,1000_QL80_.jpg",
                                    "https://m.media-amazon.com/images/I/81ySgYEwArL._AC_UF1000,1000_QL80_.jpg",
                                )

                            )

                            bookColumn(bookData = bookData)
                        }
                    }

//                    composable(route = Screens.Home.route) {
//                        HomeScreen(innerPadding = innerPadding, navController = navController, context = this@MainActivity)
//                    }
                }
            }
        }
    }
}
