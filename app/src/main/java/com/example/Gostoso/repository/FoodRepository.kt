package com.example.Gostoso.data.repository

import com.example.Gostoso.data.model.Food
import com.example.Gostoso.data.remote.ApiService
import com.example.Gostoso.data.remote.FoodDto

class FoodRepository(
    private val apiService: ApiService
) {

    // Versión que llama a la API REAL
    suspend fun getFoodsFromApi(): List<Food> {
        val dtos = apiService.getFoods()
        return dtos.map { it.toFood() }
    }

    // Por si quieren detalle
    suspend fun getFoodDetailFromApi(id: Int): Food {
        return apiService.getFoodDetail(id).toFood()
    }

    // Función de extensión para mapear DTO -> modelo
    private fun FoodDto.toFood(): Food =
        Food(
            id = id,
            name = name,
            price = price,
            imageUrl = imageUrl,
            type = type,
            category = category,
            size = size
        )
}
