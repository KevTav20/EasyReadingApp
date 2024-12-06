package com.example.easyreadingapp.presentation.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.easyreadingapp.R
import com.example.easyreadingapp.presentation.ui.utils.Lock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.*
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import kotlinx.coroutines.launch
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.easyreadingapp.datasources.services.AuthService
import com.example.easyreadingapp.domain.dtos.AuthDto
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import androidx.compose.ui.platform.LocalContext
import com.example.easyreadingapp.domain.uses_cases.SharedPref

@Composable
fun LoginScreen(
    innerPadding: PaddingValues = PaddingValues(0.dp),
    navController: NavController = rememberNavController()
) {
    val context = LocalContext.current // Obtén el contexto
    val sharedPref = SharedPref(context) // Crea la instancia de SharedPref
    val scope = rememberCoroutineScope() // Crea el scope para las corutinas

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color(0xFF2193b0), Color(0xFF6dd5ed))
                )
            )
            .padding(innerPadding)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Imagen PNG (agregada en la parte superior)
            Image(
                painter = painterResource(id = R.drawable.easyreadingicon), // Cambia "library_image" por el nombre de tu archivo PNG
                contentDescription = "Library Illustration",
                modifier = Modifier
                    .size(200.dp)
                    .padding(bottom = 16.dp)
            )

            // Título
            Text(
                text = "Easy Reading App",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Campo de correo
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                placeholder = { Text(text = "Correo Electrónico") },
                leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "email") }
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Campo de contraseña
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                placeholder = { Text(text = "Contraseña") },
                leadingIcon = { Icon(imageVector = Lock, contentDescription = "password") },
                visualTransformation = PasswordVisualTransformation()
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Error message
            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }

            // Botón de inicio de sesión
            Button(
                onClick = {
                    if (email.isNotEmpty() && password.isNotEmpty()) {
                        scope.launch {
                            try {
                                // Crear el servicio de autenticación con Retrofit
                                val authService = Retrofit.Builder()
                                    .baseUrl("http://143.244.179.13/") // URL base del servidor
                                    .addConverterFactory(GsonConverterFactory.create())
                                    .build()
                                    .create(AuthService::class.java)

                                // Crear el objeto AuthDto con las credenciales
                                val authDto = AuthDto(email = email, password = password)

                                // Llamada a la API para realizar login
                                val response = authService.login(authDto)

                                // Verificar si la respuesta fue exitosa
                                if (response.isSuccessful && response.code() == 200 && response.body()?.is_logged == true) {
                                    // Guardar en SharedPreferences que el usuario está logueado
                                    val userId = response.body()?.id ?: 0
                                    sharedPref.saveUserSharedPref(userId = userId, isLogged = true)

                                    // Navegar a la pantalla principal (home)
                                    withContext(Dispatchers.Main) {
                                        navController.navigate("home") // Navegar a Home
                                    }
                                } else {
                                    // Si la autenticación falla
                                    withContext(Dispatchers.Main) {
                                        errorMessage = "Credenciales incorrectas. Intenta nuevamente."
                                    }
                                }
                            } catch (e: Exception) {
                                // Si ocurre un error en la conexión o en la llamada API
                                withContext(Dispatchers.Main) {
                                    errorMessage = "Hubo un error de conexión. Por favor intenta más tarde."
                                }
                            }
                        }
                    } else {
                        // Si los campos de email o contraseña están vacíos
                        errorMessage = "Por favor ingresa un correo y una contraseña."
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2193b0)
                )
            ) {
                Text(text = "Iniciar sesión", color = Color.White, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Texto para crear cuenta
            Text(
                text = "¿No tienes una cuenta? Crea una aquí",
                color = Color.White,
                modifier = Modifier.clickable {
                    navController.navigate("register")
                }
            )
        }
    }
}
