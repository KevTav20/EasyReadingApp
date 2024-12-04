import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.easyreadingapp.R
import com.example.easyreadingapp.presentation.ui.utils.Settings
import com.example.easyreadingapp.presentation.ui.utils.Exit_to_app
import com.example.easyreadingapp.presentation.ui.utils.GraphUpArrow

@Composable
fun UserProfileScreen(innerPadding: PaddingValues = PaddingValues(0.dp), navController: NavController = rememberNavController()) {
    val activity = LocalContext.current as? Activity
    val customColor = Color(0xFF4175E1) // Color personalizado

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
                .background(color = customColor) // Aplicar color personalizado
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
        ButtonOption("Configuración", customColor, icon = Settings, onClick = {})
        Spacer(modifier = Modifier.height(20.dp)) // Espaciado entre botones
        ButtonOption("Estadísticas", customColor, icon = GraphUpArrow , onClick = {})
        Spacer(modifier = Modifier.height(20.dp))
        ButtonOption(
            text = "Cerrar Aplicación",
            color = customColor,
            icon = Exit_to_app,
            onClick = { activity?.finish() } // Llama a finish() para cerrar la aplicación
        )

        Spacer(modifier = Modifier.weight(1f))
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