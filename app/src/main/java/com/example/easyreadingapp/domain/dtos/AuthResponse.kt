package com.example.easyreadingapp.domain.dtos

data class AuthResponse(
    val id : Int,
    val is_logged : Boolean,
    val message : String
)
