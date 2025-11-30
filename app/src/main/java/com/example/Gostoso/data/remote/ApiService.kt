package com.example.Gostoso.data.remote

import com.example.Gostoso.data.remote.dto.AuthResponse
import com.example.Gostoso.data.remote.dto.LoginRequest
import com.example.Gostoso.data.remote.dto.SignupRequest
import com.example.Gostoso.data.remote.dto.UserDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Define los endpoints de la API
 */
interface ApiService {

    /**
     * LOGIN - Autenticar usuario
     * POST /auth/login
     *
     * Ejemplo de uso:
     * val response = apiService.login(LoginRequest(email, password))
     * sessionManager.saveSession(response.authToken, response.userId)
     */
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): AuthResponse

    /**
     * REGISTRO (SIGNUP) - Crear cuenta y obtener token
     * POST /auth/signup
     *
     * Ejemplo de uso:
     * val response = apiService.signup(SignupRequest(name, email, password))
     * sessionManager.saveSession(response.authToken, response.userId)
     */
    @POST("auth/signup")
    suspend fun signup(@Body request: SignupRequest): AuthResponse

    /**
     * OBTENER USUARIO ACTUAL (requiere autenticación)
     * GET /auth/me
     *
     * IMPORTANTE: Este endpoint REQUIERE el token de autenticación
     * El AuthInterceptor lo añade automáticamente en el header:
     * Authorization: Bearer {token}
     *
     * Ejemplo de uso:
     * val currentUser = apiService.getCurrentUser()
     */
    @GET("auth/me")
    suspend fun getCurrentUser(): UserDto

    @GET("foods")
    suspend fun getFoods(): List<FoodDto>

    @GET("foods/{id}")
    suspend fun getFoodDetail(
        @Path("id") id: Int
    ): FoodDto

}

/*
    flujo de login

    1. Usuario ingresa username/password en LoginScreen
       ↓
    2. LoginViewModel llama a apiService.login(LoginRequest(...))
       ↓
    3. Servidor valida credenciales y devuelve LoginResponse con accessToken
       ↓
    4. Guardamos el token: sessionManager.saveAuthToken(response.accessToken)
       ↓
    5. AuthInterceptor automáticamente añade el token a futuras peticiones
       ↓
    6. Podemos llamar a getCurrentUser() sin pasar el token manualmente
 */