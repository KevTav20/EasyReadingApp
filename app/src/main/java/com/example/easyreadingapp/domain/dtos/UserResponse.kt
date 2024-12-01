package com.example.easyreadingapp.domain.dtos

data class UserResponse(
    val email: String,
    val id: Int,
    val is_logged: Boolean,
    val name: String,
    val password: String
)