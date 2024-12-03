package com.example.easyreadingapp.presentation.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.easyreadingapp.datasources.services.BookService
import com.example.easyreadingapp.domain.dtos.Book
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun BookSearchScreen(innerPadding: PaddingValues, navController: NavController) {
    val scope = rememberCoroutineScope()
    var searchText by remember { mutableStateOf(TextFieldValue("")) }
    var books by remember { mutableStateOf<List<Book>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val categories = listOf("Fiction", "Non-Fiction", "Science", "History") // Categorías predefinidas

    val retrofit = remember {
        Retrofit.Builder()
            .baseUrl("http://192.168.100.12:8000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(BookService::class.java)
    }

    Column(
        modifier = Modifier
            .padding(innerPadding)
            .padding(16.dp)
            .fillMaxSize()
    ) {
        // Barra de búsqueda
        BasicTextField(
            value = searchText,
            onValueChange = { searchText = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        Button(
            onClick = {
                scope.launch {
                    isLoading = true
                    try {
                        books = retrofit.searchBooksByName(searchText.text)
                        errorMessage = null
                    } catch (e: Exception) {
                        errorMessage = "Error al buscar libros: ${e.message}"
                        books = emptyList()
                    } finally {
                        isLoading = false
                    }
                }
            },
            modifier = Modifier.padding(vertical = 8.dp)
        ) {
            Text(text = "Buscar por Nombre")
        }

        // Botones de categorías
        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(categories) { category ->
                Button(
                    onClick = {
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
                    modifier = Modifier.padding(vertical = 4.dp)
                ) {
                    Text(text = category)
                }
            }
        }

        // Indicador de carga
        if (isLoading) {
            Text(text = "Cargando...", modifier = Modifier.padding(16.dp))
        }

        // Mostrar errores
        errorMessage?.let {
            Text(text = it, color = androidx.compose.ui.graphics.Color.Red, modifier = Modifier.padding(8.dp))
        }

        // Lista de resultados
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(books) { book ->
                Text(text = "${book.title} - ${book.author} (${book.category})")
            }
        }
    }
}


