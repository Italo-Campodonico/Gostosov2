package com.example.Gostoso.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.Gostoso.data.model.Food
import com.example.Gostoso.viewmodel.CartViewModel
import com.example.Gostoso.viewmodel.ReviewViewModel
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodDetailScreen(
    foodId: Int,
    navController: NavController,
    cartViewModel: CartViewModel,
    reviewViewModel: ReviewViewModel
) {
    // Por ahora, reconstruimos el Food a mano según el id
    val food: Food = when (foodId) {
        1 -> Food(
            id = 1,
            name = "Spätzle",
            price = 12.99,
            imageUrl = "https://i.imgur.com/qYyOs7y.png",
            type = "principal",
            category = "normal",
            size = "individual"
        )
        2 -> Food(
            id = 2,
            name = "big kali",
            price = 9.99,
            imageUrl = "https://i.imgur.com/YESV68t.png",
            type = "principal",
            category = "normal",
            size = "individual"
        )
        3 -> Food(
            id = 3,
            name = "Nigiri de salmon",
            price = 5.99,
            imageUrl = "https://i.imgur.com/I9nzvul.png",
            type = "principal",
            category = "normal",
            size = "individual"
        )
        4 -> Food(
            id = 4,
            name = "Escalopa kaiser",
            price = 8.99,
            imageUrl = "https://i.imgur.com/4K6gND8.png",
            type = "principal",
            category = "normal",
            size = "individual"
        )
        else -> Food(
            id = 0,
            name = "Plato desconocido",
            price = 0.0,
            imageUrl = "",
            type = "principal",
            category = "normal",
            size = "individual"
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(food.name) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { padding ->
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
                .verticalScroll(scrollState),   // 👈 aquí la magia
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(food.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = food.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = food.name,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "$${food.price}",
                fontSize = 18.sp,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(text = "Tipo: ${food.type}")
            Text(text = "Categoría: ${food.category}")
            Text(text = "Tamaño: ${food.size}")

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { cartViewModel.addFood(food) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Agregar al pedido")
            }

            Spacer(modifier = Modifier.height(24.dp))

            // ---------- RESEÑAS ----------
            Text(
                text = "Reseñas",
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Estados locales para nueva reseña
            var rating by remember { mutableStateOf(4f) }
            var comment by remember { mutableStateOf("") }

            Text(text = "Rating: ${rating.toInt()} ★")
            Slider(
                value = rating,
                onValueChange = { rating = it },
                valueRange = 1f..5f,
                steps = 3
            )

            OutlinedTextField(
                value = comment,
                onValueChange = { comment = it },
                label = { Text("Comentario") },
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 80.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    if (comment.isNotBlank()) {
                        reviewViewModel.addReview(
                            foodId = food.id,
                            userName = "Anónimo",
                            rating = rating.toInt(),
                            comment = comment
                        )
                        comment = ""
                        rating = 4f
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Enviar reseña")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ---------- LISTA DE RESEÑAS ----------
            val allReviews by reviewViewModel.reviews.collectAsState()
            val reviewsForFood = allReviews.filter { it.foodId == food.id }

            if (reviewsForFood.isEmpty()) {
                Text("Aún no hay reseñas")
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {
                    reviewsForFood.forEach { review ->
                        Column(modifier = Modifier.padding(vertical = 6.dp)) {
                            Text(
                                text = "${review.userName} - ${review.rating} ★",
                                fontWeight = FontWeight.Bold
                            )
                            Text(review.comment)
                        }
                        Divider()
                    }
                }
            }
        }
    }
}
