package com.example.easyreadingapp.presentation.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.easyreadingapp.R
import kotlinx.coroutines.launch

@Composable
fun BookSearchScreen(innerPadding: PaddingValues, navController: NavController) {
    val scope = rememberCoroutineScope()
    var searchText by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val categories = listOf(
        Category("Fantasía", R.drawable.ic_fantasy),
        Category("Terror", R.drawable.ic_horror),
        Category("Acción", R.drawable.ic_action),
        Category("Drama", R.drawable.ic_drama),
        Category("Ciencia Ficción", R.drawable.ic_scifi),
        Category("Romance", R.drawable.ic_romance)
    )

    Column(
        modifier = Modifier
            .padding(innerPadding)
            .padding(16.dp)
            .fillMaxSize()
    ) {
        // Barra de búsqueda
        TextField(
            value = searchText,
            onValueChange = { searchText = it },
            placeholder = { Text("Buscar libros...") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
        )

        // Grid con categorías
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(categories) { category ->
                CategoryButton(
                    categoryName = category.name,
                    icon = category.icon,
                    onClick = {
                        scope.launch {
                            isLoading = true
                            try {
                                // Simulación de acción al seleccionar la categoría
                                errorMessage = null
                            } catch (e: Exception) {
                                errorMessage = "Error: ${e.message}"
                            } finally {
                                isLoading = false
                            }
                        }
                    }
                )
            }
        }

        // Mensaje de error
        errorMessage?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Composable
fun CategoryButton(categoryName: String, icon: Int, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f), // Asegura que el botón sea cuadrado
        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Ícono representativo
            Icon(
                imageVector = ImageVector.vectorResource(id = icon),
                contentDescription = categoryName,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = categoryName)
        }
    }
}


// Modelo para las categorías
data class Category(val name: String, val icon: Int)
