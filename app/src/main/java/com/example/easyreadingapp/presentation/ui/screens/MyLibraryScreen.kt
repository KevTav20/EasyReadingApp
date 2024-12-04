package com.example.easyreadingapp.presentation.ui.screens

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.easyreadingapp.datasources.services.BookService
import com.example.easyreadingapp.domain.dtos.Book
import com.example.easyreadingapp.domain.uses_cases.SharedPref
import com.example.easyreadingapp.presentation.components.BookCard
import com.example.easyreadingapp.presentation.components.LoadingScreen
import com.example.easyreadingapp.presentation.components.ReadBook
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.Response

@Composable
fun MyLibraryScreen(innerPadding: PaddingValues, navController: NavController) {
    val scope = rememberCoroutineScope()
    val sharedPref = SharedPref(context = LocalContext.current)
    val userId = sharedPref.getUserIdSharedPref() // Obtener el ID del usuario desde SharedPreferences

    var favoriteBooks by remember { mutableStateOf<List<Book>>(emptyList()) }
    var nonFavoriteBooks by remember { mutableStateOf<List<Book>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }

    // Retrofit setup
    val BASE_URL = "http://192.168.100.12:8000/"
    val bookService = remember {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BookService::class.java)
    }

    // Fetch books
    LaunchedEffect(Unit) {
        scope.launch {
            try {
                val fetchedFavoriteBooks = bookService.getUserBooks(userId, "favorite")
                favoriteBooks = fetchedFavoriteBooks
                val fetchedNonFavoriteBooks = bookService.getUserBooks(userId, "no favorite")
                nonFavoriteBooks = fetchedNonFavoriteBooks
            } catch (e: Exception) {
                Log.e("MyLibraryScreen", "Error fetching books: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }

    Surface(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
    ) {
        if (isLoading) {
            LoadingScreen()
        } else {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Columna de libros favoritos
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(end = 8.dp)
                ) {
                    Text(
                        text = "Libros Favoritos",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(favoriteBooks) { book ->
                            ReadBook(book)
                        }
                    }
                }

                // Columna de libros no favoritos
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(start = 8.dp)
                ) {
                    Text(
                        text = "Otros Libros",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(nonFavoriteBooks) { book ->
                            ReadBook(book)
                        }
                    }
                }
            }
        }
    }
}

