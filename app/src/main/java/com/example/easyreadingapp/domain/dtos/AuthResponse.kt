package com.example.easyreadingapp.domain.dtos

data class AuthResponse(
    val userId : Int,
    val isLogged : Boolean,
    val message : String
)
