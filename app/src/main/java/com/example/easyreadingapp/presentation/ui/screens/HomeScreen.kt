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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import coil.compose.AsyncImage
import com.example.easyreadingapp.R
import com.example.easyreadingapp.datasources.services.AuthService
import com.example.easyreadingapp.datasources.services.BookService
import com.example.easyreadingapp.domain.dtos.Book
import com.example.easyreadingapp.presentation.components.BookCard
import com.example.easyreadingapp.presentation.components.LoadingScreen
import com.example.easyreadingapp.domain.uses_cases.SharedPref
import com.example.easyreadingapp.presentation.ui.utils.Logout

@Composable
fun HomeScreen(innerPadding: PaddingValues, navController: NavController) {
    val scope = rememberCoroutineScope()
    var allBooks by remember { mutableStateOf(listOf<Book>()) }
    var isLoading by remember { mutableStateOf(true) }
    var userName by remember { mutableStateOf("Usuario") }
    var userImage by remember { mutableStateOf<String?>(null) }
    val sharedPref = SharedPref(context = navController.context)
    val userId = sharedPref.getUserIdSharedPref() // Obtener ID del usuario desde SharedPreferences

    // Para cerrar sesión
    fun logout() {
        sharedPref.removeUserSharedPref()
        navController.navigate("login") {
            popUpTo("home") { inclusive = true }
        }
    }

    // Cargar detalles del usuario y libros
    LaunchedEffect(Unit) {
        scope.launch {
            try {
                val BASE_URL = "http://143.244.179.13/"
                val authService = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(AuthService::class.java)

                // Obtener detalles del usuario por ID
                val user = authService.getUserById(userId)
                userName = user.name
                userImage = user.image

                // Obtener libros en secciones (paginados)
                val bookService = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(BookService::class.java)

                val booksPerSection = 5
                val totalSections = 4
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
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.White)
        ) {
            // Encabezado con imagen de usuario, saludo y botón de logout
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    AsyncImage(
                        model = userImage,
                        contentDescription = "Foto de perfil",
                        placeholder = painterResource(id = R.drawable.profile),
                        error = painterResource(id = R.drawable.god_of_war),
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .background(color = Color.Gray, shape = CircleShape)
                            .shadow(4.dp, CircleShape)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Hola, $userName",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
                IconButton(onClick = { logout() }) {
                    Icon(
                        imageVector = Logout,
                        contentDescription = "Cerrar sesión",
                        tint = Color.White
                    )
                }
            }

            // Contenido principal con libros
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Sección 1: Libros que te pueden gustar
                item {
                    TitleSection("Libros que te pueden gustar")
                }
                item {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.padding(vertical = 8.dp)
                    ) {
                        items(allBooks.take(5)) { book ->
                            BookCard(book = book, navController = navController)
                        }
                    }
                }

                // Más secciones de libros con divisores
                listOf(
                    "Más libros recomendados" to allBooks.drop(5).take(5),
                    "Descubre más libros" to allBooks.drop(10).take(5),
                    "Libros más populares" to allBooks.drop(15).take(5)
                ).forEach { (title, books) ->
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        TitleSection(title)
                    }
                    item {
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier.padding(vertical = 8.dp)
                        ) {
                            items(books) { book ->
                                BookCard(book = book, navController = navController)
                            }
                        }
                    }
                }
            }
        }
    }
}

// Componente para los títulos de las secciones
@Composable
fun TitleSection(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge.copy(
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold
        ),
        modifier = Modifier.padding(vertical = 8.dp)
    )
}

