package com.example.easyreadingapp.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.easyreadingapp.R
import com.example.easyreadingapp.datasources.services.BookService
import com.example.easyreadingapp.domain.dtos.Book
import com.example.easyreadingapp.presentation.components.BookCard
import com.example.easyreadingapp.presentation.components.LoadingScreen
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.foundation.layout.padding

@Composable
fun BookSearchScreen(innerPadding: PaddingValues, navController: NavController) {
    val scope = rememberCoroutineScope()
    var searchText by remember { mutableStateOf("") }
    var books by remember { mutableStateOf<List<Book>>(emptyList()) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var categories by remember { mutableStateOf<List<String>>(emptyList()) }
    var selectedCategory by remember { mutableStateOf<String?>(null) }
    var isCategoryDialogVisible by remember { mutableStateOf(false) }
    var areCategoriesVisible by remember { mutableStateOf(true) }

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
                categories = listOf("Horror", "Acción", "Drama", "Fantasía", "Romance", "Ficción")
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
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF2193b0), Color(0xFF6dd5ed))
                )
            )
            .padding(16.dp)
    ) {
        // Barra de búsqueda
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                value = searchText,
                onValueChange = { searchText = it },
                placeholder = { Text("Buscar libros...", color = Color.Gray) },
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White) // Aplica el fondo blanco directamente
                    .border(1.dp, Color.Gray, RoundedCornerShape(16.dp)), // Borde gris
                textStyle = LocalTextStyle.current.copy(color = Color.Black), // Estilo de texto
                singleLine = true // Evitar que el texto se desborde
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
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(12.dp))
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = "Buscar"
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para buscar por categorías
        if (areCategoriesVisible) {
            Button(
                onClick = { isCategoryDialogVisible = !isCategoryDialogVisible },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(bottom = 16.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFF6dd5ed))
            ) {
                Text(text = "Buscar por categorías", fontSize = 16.sp, color = Color.White)
            }

            // Mostrar categorías si está activo
            if (isCategoryDialogVisible) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(8.dp)
                ) {
                    items(categories) { category ->
                        Button(
                            onClick = {
                                selectedCategory = category
                                areCategoriesVisible = false // Ocultar las categorías al seleccionar
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
                                .height(120.dp)
                                .clip(RoundedCornerShape(8.dp))
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier.fillMaxSize()
                            ) {
                                // Ícono de la categoría
                                Image(
                                    painter = painterResource(id = getCategoryIcon(category)),
                                    contentDescription = category,
                                    modifier = Modifier.size(40.dp)
                                )
                                // Nombre de la categoría
                                Text(text = category, fontSize = 20.sp, color = Color.Black)
                            }
                        }
                    }
                }
            }
        } else {
            // Botón para reactivar la vista de categorías
            Button(
                onClick = { areCategoriesVisible = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(bottom = 16.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFF6dd5ed))
            ) {
                Text(text = "Mostrar categorías", fontSize = 16.sp, color = Color.White)
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
                color = Color.Red,
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

// Función para obtener el ícono de cada categoría
fun getCategoryIcon(category: String): Int {
    return when (category) {
        "Horror" -> R.drawable.ic_horror
        "Acción" -> R.drawable.ic_action
        "Drama" -> R.drawable.ic_drama
        "Fantasía" -> R.drawable.ic_fantasy
        "Romance" -> R.drawable.ic_romance
        "Ficción" -> R.drawable.ic_ficcion
        else -> R.drawable.ic_category // Ícono por defecto
    }
}
