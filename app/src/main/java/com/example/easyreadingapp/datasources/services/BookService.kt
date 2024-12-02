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
    suspend fun addBookToUser2(@Body bookToUser: AddBook) : Response<AddBookResponse>

    @POST("user/{user_id}/books/{book_id}/link")
    suspend fun addBookToUser(
        @Query("user_id") userId: Int,
        @Query("book_id") bookId: Int
    )

    @GET("user/{user_id}/books/")
    suspend fun getUserBooks(

    )

    @PATCH("user/{user_id}/books/{book_id}/favorite")
    suspend fun addFavorite(
        @Path("user_id") userId: Int,
        @Path("book_id") bookId: Int
    )

}
