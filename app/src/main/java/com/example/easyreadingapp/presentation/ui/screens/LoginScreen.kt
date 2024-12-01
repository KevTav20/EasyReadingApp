package com.example.easyreadingapp.presentation.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.easyreadingapp.R
import com.example.easyreadingapp.datasources.services.AuthService
import com.example.easyreadingapp.domain.dtos.AuthDto
import com.example.easyreadingapp.presentation.ui.theme.EasyReadingAppTheme
import com.example.easyreadingapp.presentation.ui.utils.Lock
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun LoginScreen(innerPadding: PaddingValues = PaddingValues(0.dp), navController: NavController = rememberNavController()) {
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "TaskApp")
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "login",
            modifier = Modifier.size(250.dp)
        )
        OutlinedTextField(
            value = email,
            onValueChange = {email = it},
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            placeholder = { Text(text = "Correo Electronico") },
            leadingIcon = { Icon(imageVector = Icons.Default.Email, contentDescription = "email") }
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            placeholder = { Text(text = "Contraseña") },
            leadingIcon = { Icon(imageVector = Lock, contentDescription = "email") },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            scope.launch(Dispatchers.IO) {
                if(email.isNotEmpty() && password.isNotEmpty()){
                    val authService = Retrofit.Builder()
                        .baseUrl("http://192.168.100.10:8000/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create((AuthService::class.java))
                    val authDto = AuthDto(email = email, password = password)
                    val response = authService.login(authDto)
                    Log.i("LoginScreenAPI", response.toString())
                    if(response.code() == 200){
                        if (response.isSuccessful && response.body()?.isLogged == true){
                            withContext(Dispatchers.Main){
                                navController.navigate("home")
                            }
                        }
                    }
                }
            }
        }, modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)) {
            Text(text = "Iniciar sesion")
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "No tienes una cuenta? Crea una",
            color = Color.Gray,
            modifier = Modifier.clickable {
                navController.navigate("register")
            })
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun LoginScreenPreview() {
    val navController = rememberNavController()
    EasyReadingAppTheme {
        LoginScreen()
    }
}