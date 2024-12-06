package com.example.easyreadingapp.presentation.ui.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ReadingStatisticsScreen(innerPadding: PaddingValues) {
    Surface(
        modifier = Modifier.fillMaxSize()
            .padding(innerPadding),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Título
            Text(
                text = "Estadísticas",
                style = MaterialTheme.typography.titleLarge,
                fontSize = 24.sp,
                color = Color(0xFF2196F3), // Color azul
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Bloque superior con estadísticas
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Libros Leídos
                Box(
                    modifier = Modifier
                        .size(150.dp, 116.dp)
                        .background(Color(0xFF2196F3)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "3",
                            style = MaterialTheme.typography.headlineLarge,
                            color = Color.White
                        )
                        Text(
                            text = "Libros Leídos",
                            style = MaterialTheme.typography.titleMedium,
                            fontSize = 18.sp,
                            color = Color.White
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    // Días de Lectura
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .background(Color(0xFF2196F3)),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "21",
                                style = MaterialTheme.typography.titleMedium,
                                fontSize = 18.sp,
                                color = Color.White
                            )
                            Text(
                                text = "Días de Lectura",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.White
                            )
                        }
                    }

                    // Minutos por día
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .background(Color(0xFF2196F3)),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "8.5",
                                style = MaterialTheme.typography.headlineMedium,
                                color = Color.White
                            )
                            Text(
                                text = "Minutos por día",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.White
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Nueva caja para "Horas diarias de Lectura"
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color(0xFF2196F3)), // Fondo azul
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Horas diarias de Lectura",
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = 18.sp,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    // Gráfico de barras
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        listOf(8, 6, 7, 5, 8, 9, 4).forEachIndexed { index, value ->
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = value.toString(),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.White
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Box(
                                    modifier = Modifier
                                        .width(20.dp)
                                        .height((value * 10).dp) // Altura proporcional
                                        .background(Color.White)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = listOf("L", "M", "M", "J", "V", "S", "D")[index],
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

