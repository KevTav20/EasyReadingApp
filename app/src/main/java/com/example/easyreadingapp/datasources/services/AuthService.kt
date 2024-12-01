package com.example.easyreadingapp.datasources.services

import com.example.easyreadingapp.domain.dtos.AuthDto
import com.example.easyreadingapp.domain.dtos.AuthResponse
import com.example.easyreadingapp.domain.dtos.User
import com.example.easyreadingapp.domain.dtos.UserResponse
import retrofit2.http.POST
import retrofit2.Response
import retrofit2.http.Body

interface AuthService {

    @POST("users/login_login_post")
    suspend fun login(@Body login: AuthDto) : Response<AuthResponse>

    @POST("users/create")
    suspend fun registerUser(@Body authDto: User) : Response<UserResponse>
}