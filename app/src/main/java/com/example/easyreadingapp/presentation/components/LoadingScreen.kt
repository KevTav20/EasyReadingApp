package com.example.easyreadingapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder

@Composable
fun LoadingScreen() {
    // Configuración personalizada de ImageLoader para GIFs
    val imageLoader = ImageLoader.Builder(LocalContext.current)
        .components {
            add(GifDecoder.Factory())
        }
        .build()


    val painter = rememberAsyncImagePainter(
        model = "https://i.imgur.com/wf7KJ3z.gif",
        imageLoader = imageLoader
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF222222)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painter,
            contentDescription = "Loading Animation",
            modifier = Modifier.fillMaxSize(),

            )
    }
}