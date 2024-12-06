import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.easyreadingapp.R
import com.example.easyreadingapp.datasources.services.AuthService
import com.example.easyreadingapp.datasources.services.BookService
import com.example.easyreadingapp.domain.dtos.User
import com.example.easyreadingapp.domain.uses_cases.SharedPref
import com.example.easyreadingapp.presentation.components.LoadingScreen
import com.example.easyreadingapp.presentation.ui.utils.Settings
import com.example.easyreadingapp.presentation.ui.utils.Exit_to_app
import com.example.easyreadingapp.presentation.ui.utils.GraphUpArrow
import com.example.easyreadingapp.presentation.ui.utils.Screens
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun UserProfileScreen(innerPadding: PaddingValues = PaddingValues(0.dp), navController: NavController = rememberNavController()) {
    val scope = rememberCoroutineScope()
    val sharedPref = SharedPref(context = LocalContext.current)
    val activity = LocalContext.current as? Activity
    val gradientColors = listOf(Color(0xFF4FC3F7), Color(0xFF0288D1))
    val retrievedUserId = sharedPref.getUserIdSharedPref()
    val userState = remember { mutableStateOf<User?>(null) }
    val errorMessage = remember { mutableStateOf<String?>(null) }
    val isLoading = remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        scope.launch {
            try {
                val BASE_URL = "http://143.244.179.13/"
                val authService = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(AuthService::class.java)

                val user = authService.getUserById(retrievedUserId)
                userState.value = user
            } catch (e: Exception) {
                errorMessage.value = "Error al cargar el perfil del usuario: ${e.message}"
            } finally {
                isLoading.value = false
            }
        }
    }

    if (isLoading.value) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Cargando datos...")
        }
    } else {
        userState.value?.let { user ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .background(brush = androidx.compose.ui.graphics.Brush.verticalGradient(gradientColors))
                ) {
                    Box(
                        modifier = Modifier
                            .size(175.dp)
                            .align(Alignment.BottomCenter)
                            .offset(y = 50.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = user.image,
                            contentDescription = "Foto de perfil",
                            placeholder = painterResource(id = R.drawable.profile),
                            error = painterResource(id = R.drawable.god_of_war),
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(175.dp)
                                .clip(CircleShape)
                                .background(color = Color.Gray, shape = CircleShape)
                                .shadow(10.dp, CircleShape)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(80.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Face,
                        contentDescription = "Nombre",
                        modifier = Modifier.size(24.dp),
                        tint = Color(0xFF0288D1)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = user.name,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 26.sp
                        ),
                        color = Color(0xFF0288D1)
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "Correo",
                        modifier = Modifier.size(24.dp),
                        tint = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = user.email,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontSize = 18.sp
                        ),
                        color = Color.Gray
                    )
                }


                Spacer(modifier = Modifier.height(40.dp))

                ButtonOption("Configuración", Color(0xFF4CAF50), icon = Settings, onClick = {})
                Spacer(modifier = Modifier.height(20.dp))
                ButtonOption("Estadísticas", Color(0xFF4CAF50), icon = GraphUpArrow, onClick = {
                    navController.navigate("stadistics")
                })
                Spacer(modifier = Modifier.height(20.dp))
                ButtonOption(
                    text = "Cerrar Aplicación",
                    color = Color(0xFFD32F2F),
                    icon = Exit_to_app,
                    onClick = { activity?.finish() }
                )
            }
        } ?: run {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(errorMessage.value ?: "Error desconocido")
            }
        }
    }
}



@Composable
fun ButtonOption(text: String, color: Color, icon: ImageVector, onClick: () -> Unit) {
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
            Icon(
                imageVector = icon,
                contentDescription = "$text Icon",
                modifier = Modifier.size(24.dp),
                tint = Color.White
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UserProfileScreenPreview() {
    UserProfileScreen()
}