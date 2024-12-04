package com.example.easyreadingapp.domain.dtos

import androidx.annotation.Nullable

data class User(
    val email: String,
    val name: String,
    val password: String,
    @Nullable val image: String? = null
)