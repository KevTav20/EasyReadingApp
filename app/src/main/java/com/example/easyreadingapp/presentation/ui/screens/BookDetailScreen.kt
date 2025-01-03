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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.SnackbarDefaults
import androidx.compose.material3.Snackbar
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

@Composable
fun BookDetailScreen(innerPadding: PaddingValues, navController: NavController, bookId: Int, userId: Int) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    var book by remember { mutableStateOf<Book?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isBookAdded by remember { mutableStateOf<Boolean?>(null) }
    var isFavorite by remember { mutableStateOf<Boolean?>(null) }
    val sharedPref = SharedPref(context = LocalContext.current)
    val retrievedUserId = sharedPref.getUserIdSharedPref()

    LaunchedEffect(bookId) {
        scope.launch {
            try {
                val BASE_URL = "http://143.244.179.13/"
                val bookService = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(BookService::class.java)

                // Verificar si el libro está agregado y su estado de favorito
                val response = bookService.isBookLinkedToUser(retrievedUserId, bookId)
                if (response.isSuccessful) {
                    val result = response.body()
                    isBookAdded = result?.exists
                    isFavorite = result?.is_favorite
                } else {
                    snackbarHostState.showSnackbar("Failed to verify book: ${response.code()}")
                }

                // Cargar detalles del libro
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
        LoadingScreen()
    } else if (errorMessage != null) {
        Text(
            text = errorMessage ?: "An error occurred",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(16.dp)
        )
    } else if (book != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Mostrar información del libro
            AsyncImage(
                model = book!!.image,
                contentDescription = "Book Cover",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = book!!.title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "By ${book!!.author}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "Category: ${book!!.category}", style = MaterialTheme.typography.bodySmall)
            Text(text = "Year: ${book!!.year}", style = MaterialTheme.typography.bodySmall)
            Text(text = "Pages: ${book!!.num_pages}", style = MaterialTheme.typography.bodySmall)

            Spacer(modifier = Modifier.height(16.dp))

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

            // Mostrar botón "Add to My Books" si el libro no está añadido
            if (isBookAdded == false) {
                Button(
                    onClick = {
                        scope.launch {
                            try {
                                val BASE_URL = "http://143.244.179.13/"
                                val bookService = Retrofit.Builder()
                                    .baseUrl(BASE_URL)
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build()
                                    .create(BookService::class.java)

                                val response = bookService.addBookToUser(retrievedUserId, bookId)
                                if (response.isSuccessful) {
                                    isBookAdded = true
                                    snackbarHostState.showSnackbar("Book added to your list!")
                                } else {
                                    snackbarHostState.showSnackbar("Failed to add book: ${response.code()}")
                                }
                            } catch (e: Exception) {
                                snackbarHostState.showSnackbar("Error: ${e.message}")
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Add to My Books")
                }
            }

            // Mostrar botones de favoritos si el libro está añadido
            if (isBookAdded == true) {
                if (isFavorite == true) {
                    Button(
                        onClick = {
                            scope.launch {
                                try {
                                    val BASE_URL = "http://143.244.179.13/"
                                    val bookService = Retrofit.Builder()
                                        .baseUrl(BASE_URL)
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build()
                                        .create(BookService::class.java)

                                    val response = bookService.removeFavorite(retrievedUserId, bookId)
                                    if (response.isSuccessful) {
                                        isFavorite = false
                                        snackbarHostState.showSnackbar("Book unmarked as favorite!")
                                    } else {
                                        snackbarHostState.showSnackbar("Failed to unmark as favorite: ${response.code()}")
                                    }
                                } catch (e: Exception) {
                                    snackbarHostState.showSnackbar("Error: ${e.message}")
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Unmark as Favorite")
                    }
                } else if (isFavorite == false) {
                    Button(
                        onClick = {
                            scope.launch {
                                try {
                                    val BASE_URL = "http://143.244.179.13/"
                                    val bookService = Retrofit.Builder()
                                        .baseUrl(BASE_URL)
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .build()
                                        .create(BookService::class.java)

                                    val response = bookService.addFavorite(retrievedUserId, bookId)
                                    if (response.isSuccessful) {
                                        isFavorite = true
                                        snackbarHostState.showSnackbar("Book marked as favorite!")
                                    } else {
                                        snackbarHostState.showSnackbar("Failed to mark as favorite: ${response.code()}")
                                    }
                                } catch (e: Exception) {
                                    snackbarHostState.showSnackbar("Error: ${e.message}")
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Mark as Favorite")
                    }
                }
            }
        }
    } else {
        Text(
            text = "Book not found",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(16.dp)
        )
    }

    SnackbarHost(
        hostState = snackbarHostState,
        snackbar = { Snackbar(it) },
        modifier = Modifier.padding(16.dp)
    )
}




