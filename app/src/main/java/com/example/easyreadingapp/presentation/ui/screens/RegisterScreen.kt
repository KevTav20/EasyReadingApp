package com.example.easyreadingapp.presentation.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.easyreadingapp.R
import com.example.easyreadingapp.datasources.services.AuthService
import com.example.easyreadingapp.domain.dtos.AuthDto
import com.example.easyreadingapp.domain.dtos.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun RegisterScreen(innerPadding: PaddingValues = PaddingValues(0.dp), navController: NavController = rememberNavController()) {
    var name by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var confirmPassword by remember {
        mutableStateOf("")
    }
    val  isButtonEnabled = email.isNotEmpty()
            && password.isNotEmpty()
            && confirmPassword.isNotEmpty()
            && name.isNotEmpty()
            && password == confirmPassword
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "register",
            modifier = Modifier.size(250.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = name,
            onValueChange = {name = it},
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(text = "Nombre") },
            shape = RoundedCornerShape(24.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = email,
            onValueChange = {email = it},
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(text = "Correo Electronico") },
            shape = RoundedCornerShape(24.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = password,
            onValueChange = {password = it},
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(text = "Contraseña") },
            shape = RoundedCornerShape(24.dp),
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = {confirmPassword = it},
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(text = "Confirmar Contraseña") },
            shape = RoundedCornerShape(24.dp),
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(10.dp))
        Button(onClick = {
            scope.launch(Dispatchers.IO) {
                try {
                    val authService = Retrofit.Builder()
                        .baseUrl("http://192.168.100.10:8000/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create((AuthService::class.java))
                    val authDto = User(name = name, email = email, password = password)
                    val response = authService.registerUser(authDto)
                    Log.i("RegisterScreenAPI", response.toString())
                    if (response.isSuccessful && response.body()?.is_logged == true) {
                        withContext(Dispatchers.Main) {
                            navController.navigate("home")
                        }
                    } else {
                        Log.e("RegisterScreenAPI", "Error: ${response.code()} - ${response.message()}")
                    }
                } catch (e: Exception) {
                    Log.e("RegisterScreenAPI", "Exception: ${e.message}")
                }
            }
        },
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            enabled = isButtonEnabled
        ) {
            Text(text = "Registrarse")
        }
        if (password != confirmPassword){
            Text(
                text = "Las contraseñas no coiciden",
                color = Color.Red,
                modifier = Modifier. padding(top = 10.dp)
            )
        }
    }
}