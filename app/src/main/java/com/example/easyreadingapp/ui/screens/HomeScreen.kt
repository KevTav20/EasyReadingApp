package com.example.easyreadingapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun HomeScreen(innerPadding: PaddingValues, navController: NavController){
    Column {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                //.placeholder(R.drawable.rick) // Asegúrate de tener este recurso en tu carpeta de drawables
                .data("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTmMQPyFbID_TEQ_nAlotYMPv-7Wm8JxQfAhQ&s") // URL hardcodeada
                .build(),
            contentDescription = "Rick Sanchez",
            modifier = Modifier
                .size(130.dp)
                .clip(CircleShape)
                .background(Color(0xFFD1D1D1))
                .padding(10.dp)
                .shadow(8.dp, CircleShape),
            contentScale = ContentScale.Crop
        )
    }
}