package com.example.easyreadingapp.datasources.services

import com.example.easyreadingapp.domain.dtos.AddBook
import com.example.easyreadingapp.domain.dtos.AddBookResponse
import com.example.easyreadingapp.domain.dtos.AuthDto
import com.example.easyreadingapp.domain.dtos.AuthResponse
import com.example.easyreadingapp.domain.dtos.Book
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
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

    @POST("user/{user_id}/books/{book_id}/link")
    suspend fun addBookToUser(
        @Path("user_id") userId: Int,
        @Path("book_id") bookId: Int
    ): Response<AddBookResponse>

    @GET("user/{user_id}/books/")
    suspend fun getUserBooks(
        @Path("user_id") userId: Int,
        @Query("book_status") bookStatus: String
    ): List<Book>

    @PATCH("user/{user_id}/books/{book_id}/favorite")
    suspend fun addFavorite(
        @Path("user_id") userId: Int,
        @Path("book_id") bookId: Int
    ): Response<Unit>

    @GET("books/search/{book_name}")
    suspend fun searchBooksByName(@Path("book_name") bookName: String): List<Book>

    @GET("books/category/{book_category}")
    suspend fun getBooksByCategory(@Path("book_category") bookCategory: String): List<Book>

    @GET("books/categories")
    suspend fun getCategories(): List<String>
}

