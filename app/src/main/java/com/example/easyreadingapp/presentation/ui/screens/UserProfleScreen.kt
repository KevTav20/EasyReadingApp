package com.example.easyreadingapp.presentation.ui.screens

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.draw.clip
import com.example.easyreadingapp.R

@Composable
fun UserProfileScreen(innerPadding: PaddingValues, navController: NavController) {
    val activity = LocalContext.current as? Activity

    Column(
        modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(color = Color.Blue)
        ) {
            // Imagen circular de perfil
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.BottomCenter)
                    .offset(y = 60.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = "Foto de perfil",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(110.dp)
                        .clip(CircleShape) // Hace que la imagen sea redonda
                        .background(color = Color.Gray, shape = CircleShape)
                )
            }
        }

        Spacer(modifier = Modifier.height(80.dp))

        // Nombre y correo electrónico
        Text(
            text = "Alan Arana",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
        Text(
            text = "alanfx3@gmail.com",
            fontSize = 16.sp,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(30.dp))

        // Botones de opciones
        ButtonOption("Configuración", Color.Blue, R.drawable.ic_launcher_foreground, onClick = {})
        Spacer(modifier = Modifier.height(20.dp)) // Espaciado entre botones
        ButtonOption("Estadísticas", Color.Blue, R.drawable.ic_launcher_foreground, onClick = {})
        Spacer(modifier = Modifier.height(20.dp))
        ButtonOption(
            text = "Cerrar Sesión",
            color = Color.Blue,
            icon = R.drawable.ic_launcher_foreground,
            onClick = { activity?.finish() } // Llama a finish() para cerrar la aplicación
        )

        Spacer(modifier = Modifier.weight(1f))

        // Rectángulo azul inferior
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(color = Color.Blue)
        )
    }
}

@Composable
fun ButtonOption(text: String, color: Color, icon: Int, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .height(50.dp)
            .padding(vertical = 5.dp),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(containerColor = color)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = text,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Image(
                painter = painterResource(id = icon),
                contentDescription = "$text Icon",
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserProfileScreenPreview() {
    UserProfileScreen()
}