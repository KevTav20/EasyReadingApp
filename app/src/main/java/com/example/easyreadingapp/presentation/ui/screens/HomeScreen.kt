package com.example.easyreadingapp.presentation.ui.screens

import android.util.Log
import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.tooling.preview.Preview
import com.example.easyreadingapp.datasources.services.BookService
import com.example.easyreadingapp.domain.dtos.Book
import com.example.easyreadingapp.presentation.components.BookCard
import com.example.easyreadingapp.presentation.components.LoadingScreen

@Composable
fun HomeScreen(innerPadding: PaddingValues, navController: NavController){
    val scope = rememberCoroutineScope()
    var books by remember {
        mutableStateOf(listOf<Book>())
    }
    var isLoading by remember {
        mutableStateOf(true)
    }

    LaunchedEffect(Unit) {
        scope.launch {
            try {
                val BASE_URL = "http://192.168.100.10:8000/" // Cambia por la URL de tu API
                val bookService = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(BookService::class.java)

                // Obtener todos los libros
                books = bookService.getBooks()
                Log.i("BooksResponse", books.toString())
            } catch (e: Exception) {
                Log.e("API Error", e.toString())
                books = listOf()
            } finally {
                isLoading = false
            }
        }
    }

    if (isLoading) {
        LoadingScreen()
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
        ) {
            // Sección 1: Libros recomendados
            item {
                Text(
                    text = "Libros que te pueden gustar",
                    style = MaterialTheme.typography.displayLarge,
                    modifier = Modifier.padding(16.dp)
                )
            }
            item {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    items(books.take(5)) { book ->
                        BookCard(book = book) {
                            navController.navigate("detail/${book.id}")
                        }
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
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 16.dp)
                ) {
                    items(books.drop(5).take(5)) { book ->
                        BookCard(book = book) {
                            navController.navigate("detail/${book.id}")
                        }
                    }
                }
            }

            // Sección 3: Otros libros (puedes añadir más secciones si lo deseas)
            if (books.size > 10) {
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
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(horizontal = 16.dp)
                    ) {
                        items(books.drop(10)) { book ->
                            BookCard(book = book) {
                                navController.navigate("detail/${book.id}")
                            }
                        }
                    }
                }
            }
        }
    }
}


//@Composable
//fun BookImage(imageUrl:String){
//    Column {
//        AsyncImage(
//            model = ImageRequest.Builder(LocalContext.current)
//                .data(imageUrl) // URL hardcodeada
//                .build(),
//            contentDescription = "Libro",
//            modifier = Modifier
//                .width(145.dp)
//                .height(205.dp)
//                .padding(10.dp),
//            contentScale = ContentScale.Crop
//        )
//    }
//}
//
//@Composable
//fun bookList(titulo:String, items:List<String>){
//    Column {
//        Text(text = titulo)
//        Spacer(modifier = Modifier.height(10.dp))
//        LazyRow {
//            items(items){ item ->
//                BookImage(imageUrl = item)
//            }
//        }
//    }
//}
//
//@Composable
//fun bookColumn(bookData: List<Pair<String, List<String>>>){
//    LazyColumn {
//        items(bookData) { (titulo, items) ->
//            bookList(titulo = titulo, items = items)
//        }
//    }
//}
//
//
//@Composable
//@Preview
//fun booklistView(){
//    var palabras = listOf<String>("Hola", "Amigos", "Como", "estan", "todos", "ustedes")
//    var titulo = "Soy un titulo de prueba"
//    bookList(titulo = titulo, items = palabras)
//}