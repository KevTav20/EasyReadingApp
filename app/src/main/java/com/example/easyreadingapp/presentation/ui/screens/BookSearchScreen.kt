package com.example.easyreadingapp.presentation.ui.screens

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerSha

//@Composable
//fun BookSearchScreen(innerPadding: PaddingValues, navController: NavController){
//    Text(text = "Hola estas en el Search")
//}




// Dialog para seleccionar una categoria
if (showCategoryDialog) {
    Dialog(onDismissRequest = { showCategoryDialog = false }) {
        Surface(modifier = Modifier.fillMaxWidth()) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2), // Define dos columnas
                contentPadding = PaddingValues(16.dp)
            ) {
                items(categories) { category ->
                    Button(
                        onClick = {
                            selectedCategory = category
                            showCategoryDialog = false
                            scope.launch {
                                isLoading = true
                                try {
                                    books = retrofit.getBooksByCategory(category)
                                    errorMessage = null
                                } catch (e: Exception) {
                                    errorMessage = "Error al buscar por categoria: ${e.message}
                                    books = emptyList()
                                } finally {
                                    isLoading = false
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth().padding(8.dp)
                    ) {
                        Text(text = category)
                    }
                }
            }
        }
    }
}




@Composable
fun GenreGrid() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            GenreBox(name = "Fantasía", iconRes = R.drawable.fantasy)
            GenreBox(name = "Terror", iconRes = R.drawable.horror)
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            GenreBox(name = "Acción", iconRes = R.drawable.action)
            GenreBox(name = "Drama", iconRes = R.drawable.drama)
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            GenreBox(name = "Ciencia Ficción", iconRes = R.drawable.scifi)
            GenreBox(name = "Romance", iconRes = R.drawable.romance)
        }
    }
}

@Composable
fun GenreBox(name: String, iconRes: Int) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF4285F4)),
        modifier = Modifier
            .size(150.dp)
            .clickable { }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = iconRes),
                contentDescription = name,
                modifier = Modifier.size(60.dp),
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = name,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }
}
