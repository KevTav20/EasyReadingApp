package com.example.easyreadingapp.domain.dtos

data class Book(
    val id: Int,
    val author: String,
    val title: String,
    val year: String,
    val num_pages: Int,
    val category: String,
    val image: String,
    val synopsis: String
)
