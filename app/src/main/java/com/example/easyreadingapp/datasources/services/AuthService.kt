package com.example.easyreadingapp.datasources.services

import com.example.easyreadingapp.domain.dtos.AuthDto
import com.example.easyreadingapp.domain.dtos.AuthResponse
import com.example.easyreadingapp.domain.dtos.Book
import com.example.easyreadingapp.domain.dtos.User
import com.example.easyreadingapp.domain.dtos.UserResponse
import retrofit2.http.POST
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Path

interface AuthService {

    @POST("users/login")
    suspend fun login(@Body login: AuthDto) : Response<AuthResponse>

    @POST("users")
    suspend fun registerUser(@Body authDto: User) : Response<UserResponse>

    @GET("users/{user_id}")
    suspend fun getUserById(@Path("user_id") userId: Int): User
}