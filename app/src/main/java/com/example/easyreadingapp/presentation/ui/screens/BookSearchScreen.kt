package com.example.easyreadingapp.presentation.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.example.easyreadingapp.datasources.services.BookService
import com.example.easyreadingapp.domain.dtos.Book
import com.example.easyreadingapp.presentation.components.BookCard
import com.example.easyreadingapp.presentation.components.LoadingScreen
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun BookSearchScreen(innerPadding: PaddingValues, navController: NavController) {
    val scope = rememberCoroutineScope()
    var searchText by remember { mutableStateOf("") }
    var books by remember { mutableStateOf<List<Book>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var categories by remember { mutableStateOf<List<String>>(emptyList()) }
    var selectedCategory by remember { mutableStateOf<String?>(null) }

    // Configuración de Retrofit
    val retrofit = remember {
        Retrofit.Builder()
            .baseUrl("http://143.244.179.13/") // Cambia esto a tu base URL
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BookService::class.java)
    }

    // Obtener las categorías cuando se monta la pantalla
    LaunchedEffect(Unit) {
        scope.launch {
            isLoading = true
            try {
                categories = retrofit.getCategories()
                errorMessage = null
            } catch (e: Exception) {
                errorMessage = "Error al obtener las categorías: ${e.message}"
                categories = emptyList()
            } finally {
                isLoading = false
            }
        }
    }

    Column(
        modifier = Modifier
            .padding(innerPadding)
            .padding(16.dp)
            .fillMaxSize()
    ) {
        // Barra de búsqueda
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                value = searchText,
                onValueChange = { searchText = it },
                placeholder = { Text("Buscar libros...") },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            )
            Button(
                onClick = {
                    if (searchText.isNotBlank()) {
                        scope.launch {
                            isLoading = true
                            try {
                                books = retrofit.searchBooksByName(searchText)
                                errorMessage = null
                            } catch (e: Exception) {
                                errorMessage = "Error al buscar libros: ${e.message}"
                                books = emptyList()
                            } finally {
                                isLoading = false
                            }
                            searchText = ""
                        }
                    }
                },
                modifier = Modifier.size(48.dp)
            ) {
                Text("🔍")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Categorías en cuadrícula
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(categories) { category ->
                Button(
                    onClick = {
                        selectedCategory = category
                        scope.launch {
                            isLoading = true
                            try {
                                books = retrofit.getBooksByCategory(category)
                                errorMessage = null
                            } catch (e: Exception) {
                                errorMessage = "Error al buscar por categoría: ${e.message}"
                                books = emptyList()
                            } finally {
                                isLoading = false
                            }
                        }
                    },
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .height(80.dp)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        // Ícono de la categoría (puedes personalizar)
                        Text(
                            text = "🔖", // Cambiar por íconos relevantes
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                        // Nombre de la categoría
                        Text(text = category)
                    }
                }
            }
        }

        // Indicador de carga
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.Center
            ) {
                LoadingScreen()
            }
        }

        // Mostrar errores
        errorMessage?.let {
            Text(
                text = it,
                color = androidx.compose.ui.graphics.Color.Red,
                modifier = Modifier.padding(8.dp)
            )
        }

        // Lista de resultados
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(books) { book ->
                BookCard(book, navController)
            }
        }
    }
}
