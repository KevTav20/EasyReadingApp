package com.example.easyreadingapp.domain.dtos

import android.graphics.RectF

data class SearchResults(
    val page: Int,
    val results: List<RectF>
)