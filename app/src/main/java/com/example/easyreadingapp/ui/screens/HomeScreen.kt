package com.example.easyreadingapp.ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
//@Composable
//fun ScaffolWrapper() {
//    Scaffold(
//        topBar = { /* Configuración del TopBar */ },
//        bottomBar = { /* Configuración del BottomBar */ }
//    ) { contentPadding ->
//        // Aplica el relleno al contenido principal
//        Box(modifier = Modifier.padding(contentPadding)) {
//            // Contenido principal
//            Text("Contenido principal")
//        }
//    }
//}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffolWrapper() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mi App") }
            )
        },
        bottomBar = {
            BottomAppBar(
            ) {
                Text(
                    text = "Barra inferior",
                    modifier = Modifier.padding(16.dp),
                    color = Color.White
                )
            }
        }
    ) { contentPadding ->
        // Aplica el relleno al contenido principal
        Box(modifier = Modifier.padding(contentPadding)) {
            // Contenido principal
            Text("Contenido principal", modifier = Modifier.padding(16.dp))
        }
    }
}

