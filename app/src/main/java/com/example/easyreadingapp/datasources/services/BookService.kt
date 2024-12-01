package com.example.easyreadingapp.datasources.services

import com.example.easyreadingapp.domain.dtos.AuthDto
import com.example.easyreadingapp.domain.dtos.AuthResponse
import com.example.easyreadingapp.domain.dtos.Book
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface BookService {
    @GET("books/")
    suspend fun getBooks(): List<Book>
}