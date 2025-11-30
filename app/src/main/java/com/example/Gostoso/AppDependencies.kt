package com.example.Gostoso

import android.content.Context
import com.example.Gostoso.repository.AvatarRepository
import com.example.Gostoso.data.remote.ApiService
import com.example.Gostoso.data.repository.FoodRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Esta clase actúa como un contenedor de dependencias.
class AppDependencies(context: Context) {

    // -----------------------------
    // Avatar Repository (ya existente)
    // -----------------------------
    val avatarRepository: AvatarRepository by lazy {
        AvatarRepository(context)
    }

    // -----------------------------
    // Retrofit para productos (API Spring Boot)
    // -----------------------------
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/") // <-- Cambia cuando esté tu backend
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    // -----------------------------
    // Repositorio de productos
    // -----------------------------
    val foodRepository: FoodRepository by lazy {
        FoodRepository(apiService)
    }

    // -----------------------------
    // Singleton
    // -----------------------------
    companion object {
        @Volatile
        private var INSTANCE: AppDependencies? = null

        fun getInstance(context: Context): AppDependencies {
            return INSTANCE ?: synchronized(this) {
                val instance = AppDependencies(context.applicationContext)
                INSTANCE = instance
                instance
            }
        }
    }
}
