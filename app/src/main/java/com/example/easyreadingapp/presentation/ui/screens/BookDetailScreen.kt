package com.example.easyreadingapp.presentation.ui.screens

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.easyreadingapp.datasources.services.AuthService
import com.example.easyreadingapp.datasources.services.BookService
import com.example.easyreadingapp.domain.dtos.AuthDto
import com.example.easyreadingapp.domain.dtos.Book
import com.example.easyreadingapp.domain.uses_cases.SharedPref
import com.example.easyreadingapp.presentation.components.LoadingScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun BookDetailScreen(innerPadding: PaddingValues, navController: NavController, bookId: Int, userId: Int) {
    val scope = rememberCoroutineScope()
    var book by remember { mutableStateOf<Book?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val sharedPref = SharedPref(context = LocalContext.current)
    val retrievedUserId = sharedPref.getUserIdSharedPref() // Recuperar el ID del usuario desde SharedPreferences

    // Log para verificar bookId y userId
    Log.i("BookDetailScreen", "User ID: $retrievedUserId, Book ID: $bookId")

    // Realizar la solicitud a la API
    LaunchedEffect(bookId) {
        scope.launch {
            try {
                val BASE_URL = "http://192.168.100.10:8000/"
                val bookService = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(BookService::class.java)

                // Obtener detalles del libro
                book = bookService.getBookById(bookId)
                errorMessage = null
            } catch (e: Exception) {
                errorMessage = "Failed to load book details: ${e.message}"
                book = null
            } finally {
                isLoading = false
            }
        }
    }

    if (isLoading) {
        // Pantalla de carga
        LoadingScreen()
    } else if (errorMessage != null) {
        // Pantalla de error
        Text(
            text = errorMessage ?: "An error occurred",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(16.dp)
        )
    } else if (book != null) {
        // Mostrar detalles del libro
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Imagen del libro
            AsyncImage(
                model = book!!.image,
                contentDescription = "Book Cover",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Título
            Text(
                text = book!!.title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Autor
            Text(
                text = "By ${book!!.author}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Categoría, Año y Número de Páginas
            Text(
                text = "Category: ${book!!.category}",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "Year: ${book!!.year}",
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = "Pages: ${book!!.num_pages}",
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Sinopsis
            Text(
                text = "Synopsis:",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = book!!.synopsis,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Botones
            Button(
                onClick = {
                    scope.launch {
                        try {
                            val BASE_URL = "http://192.168.100.10:8000/"
                            val bookService = Retrofit.Builder()
                                .baseUrl(BASE_URL)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build()
                                .create(BookService::class.java)

                            // Agregar libro al usuario
                            bookService.addBookToUser(retrievedUserId, bookId)
                            Log.d("BookDetailScreen", "Book successfully linked to user")
                        } catch (e: Exception) {
                            Log.e("BookDetailScreen", "Failed to link book to user: ${e.message}")
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add to My Books")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    scope.launch {
                        try {
                            val BASE_URL = "http://192.168.100.10:8000/"
                            val bookService = Retrofit.Builder()
                                .baseUrl(BASE_URL)
                                .addConverterFactory(GsonConverterFactory.create())
                                .build()
                                .create(BookService::class.java)

                            // Marcar como favorito
                            bookService.addFavorite(retrievedUserId, bookId)
                            Log.d("BookDetailScreen", "Book marked as favorite")
                        } catch (e: Exception) {
                            Log.e("BookDetailScreen", "Failed to mark book as favorite: ${e.message}")
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Mark as Favorite")
            }
        }
    } else {
        // Si no hay detalles del libro
        Text(
            text = "Book not found",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(16.dp)
        )
    }
}



// COMMMIT YEF


