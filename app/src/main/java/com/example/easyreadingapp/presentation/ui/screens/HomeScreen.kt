package com.example.easyreadingapp.presentation.ui.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import com.example.easyreadingapp.datasources.services.BookService
import com.example.easyreadingapp.domain.dtos.Book
import com.example.easyreadingapp.presentation.components.BookCard
import com.example.easyreadingapp.presentation.components.LoadingScreen
import com.example.easyreadingapp.domain.uses_cases.SharedPref

@Composable
fun HomeScreen(innerPadding: PaddingValues, navController: NavController) {
    val scope = rememberCoroutineScope()
    var allBooks by remember { mutableStateOf(listOf<Book>()) }
    var isLoading by remember { mutableStateOf(true) }
    val sharedPref = SharedPref(context = navController.context)

    // Para cerrar sesión
    fun logout() {
        sharedPref.removeUserSharedPref()
        navController.navigate("login") {
            popUpTo("home") { inclusive = true }
        }
    }

    LaunchedEffect(Unit) {
        scope.launch {
            try {
                val BASE_URL = "http://10.166.125.88:8000/"
//                val BASE_URL = "http://192.168.100.12:8000/" // URL de la API
                val bookService = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(BookService::class.java)

                // Obtener libros en secciones (paginados)
                val booksPerSection = 5
                val totalSections = 4 // Ahora serán 4 secciones

                val loadedBooks = mutableListOf<Book>()
                for (i in 0 until totalSections) {
                    val skip = i * booksPerSection
                    val books = bookService.getBooks(skip = skip, limit = booksPerSection)
                    loadedBooks.addAll(books)
                }
                allBooks = loadedBooks
            } catch (e: Exception) {
                Log.e("API Error", e.toString())
            } finally {
                isLoading = false
            }
        }
    }

    if (isLoading) {
        LoadingScreen()
    } else {
        Column (modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
        ){
            // Encabezado con el botón de cerrar sesión
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White),
                horizontalArrangement = Arrangement.End // Alineamos el botón a la derecha
            ) {
                Button(
                    onClick = { logout() },
                    modifier = Modifier.padding(16.dp) // Añadimos un padding alrededor del botón
                ) {
                    Text("Cerrar sesión", color = Color.White)
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {
                // Sección 1: Libros que te pueden gustar
                item {
                    Text(
                        text = "Libros que te pueden gustar",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                item {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.padding(8.dp)
                    ){
                        items(allBooks.take(5)) { book -> // Primer conjunto de 5 libros
                            BookCard(book = book, navController = navController)
                        }
                    }
                }

                // Sección 2: Más libros recomendados
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Más libros recomendados",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                item {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.padding(8.dp)
                    ) {
                        items(allBooks.drop(5).take(5)) { book -> // Segundo conjunto de 5 libros
                            BookCard(book = book, navController = navController)
                        }
                    }
                }

                // Sección 3: Descubre más libros
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Descubre más libros",
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                item {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.padding(8.dp)
                    ) {
                        items(allBooks.drop(10).take(5)) { book -> // Tercer conjunto de 5 libros
                            BookCard(book = book, navController = navController)
                        }
                    }
                }

                // Sección 4: Libros más populares
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Libros más populares",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                item {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.padding(8.dp)
                    ) {
                        items(allBooks.drop(15).take(5)) { book -> // Cuarto conjunto de 5 libros
                            BookCard(book = book, navController = navController)
                        }
                    }
                }
            }
        }
    }
}