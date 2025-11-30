package com.example.Gostoso.ui.navigation

// Sealed class para definir rutas tipo-safe en la navegación
sealed class Screen(val route: String) {
    // Rutas simples (sin argumentos): Usamos 'data object' es un singleton seguro de tipos

    data object Login : Screen("Login")
    data object Signup : Screen("Signup")
    data object Home : Screen("Home")
    data object Profile : Screen("Profile")

    object FoodDetail : Screen("food_detail/{foodId}") {
        fun createRoute(foodId: Int) = "food_detail/$foodId"
    }

    object Cart : Screen("cart")
}