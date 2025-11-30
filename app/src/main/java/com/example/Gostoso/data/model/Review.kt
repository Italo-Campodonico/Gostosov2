package com.example.Gostoso.data.model

data class Review(
    val id: Int,
    val foodId: Int,
    val userName: String,
    val rating: Int,
    val comment: String
)
