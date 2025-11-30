package com.example.Gostoso.data.model

data class Food(
    val id: Int,
    val name: String,
    val price: Double,
    val imageUrl: String,
    val type: String,      // ej: pasta, burger, sushi…
    val category: String,  // vegano, carne, etc.
    val size: String       // individual, familiar, etc.
)

data class CartItem(
    val food: Food,
    val quantity: Int
)