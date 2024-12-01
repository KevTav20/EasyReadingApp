package com.example.easyreadingapp.datasources.services

import com.example.easyreadingapp.domain.dtos.AuthDto
import com.example.easyreadingapp.domain.dtos.AuthResponse
import com.example.easyreadingapp.domain.dtos.Book
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface BookService {
    @GET("books/list")
    suspend fun getBooks(
        @Query("skip") skip: Int = 0,
        @Query("limit") limit: Int = 10
    ): List<Book>

    @GET("books/{id}")
    suspend fun getBookById(@Path("id") bookId: Int): Book
}