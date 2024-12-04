package com.example.easyreadingapp.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.easyreadingapp.R
import com.example.easyreadingapp.domain.dtos.Book
import com.example.easyreadingapp.presentation.ui.utils.Screens


@Composable
fun BookCard(book: Book, navController: NavController) {
    Card(
        modifier = Modifier
            .width(145.dp)
            .height(205.dp)
            .clickable {
                // Navegación al detalle del libro con el userId
                navController.navigate(
                    Screens.BookDetail.route
                        .replace("{bookId}", book.id.toString())
                        //.replace("{userId}", userId.toString())
                )
            },
//        elevation = CardDefaults.cardElevation(
//            defaultElevation = 10.dp
//        )
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .placeholder(R.drawable.god_of_war)
                .data(book.image)
                .build(),
            contentDescription = book.title,
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}


