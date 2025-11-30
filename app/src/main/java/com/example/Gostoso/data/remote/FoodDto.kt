package com.example.Gostoso.data.remote

data class FoodDto(
    val id: Int,
    val name: String,
    val price: Double,
    val imageUrl: String,
    val type: String,
    val category: String,
    val size: String
)
