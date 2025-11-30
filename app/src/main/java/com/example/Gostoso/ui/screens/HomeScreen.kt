package com.example.Gostoso.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
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
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.Gostoso.AppDependencies
import com.example.Gostoso.data.model.Food
import com.example.Gostoso.ui.navigation.Screen
import com.example.Gostoso.viewmodel.CartViewModel
import com.example.Gostoso.viewmodel.HomeViewModel

data class Product(
    val id: String,
    val name: String,
    val price: String,
    val imageUrl: String,
    val type: String,
    val category: String,
    val size: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    cartViewModel: CartViewModel
) {
    val items = listOf(Screen.Home, Screen.Profile)
    var selectedItem by remember { mutableIntStateOf(0) }

    val context = LocalContext.current

    // ViewModel que carga productos desde el repositorio / API
    val homeViewModel: HomeViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val deps = AppDependencies.getInstance(context)
                @Suppress("UNCHECKED_CAST")
                return HomeViewModel(deps.foodRepository) as T
            }
        }
    )

    val uiState by homeViewModel.uiState.collectAsState()

    // Productos ya como Product (para la UI)
    val products = if (uiState.foods.isNotEmpty()) {
        uiState.foods.map {
            Product(
                id = it.id.toString(),
                name = it.name,
                price = "$${it.price}",
                imageUrl = it.imageUrl,
                type = it.type,
                category = it.category,
                size = it.size
            )
        }
    } else {
        // Fallback mientras no haya API real
        listOf(
            Product(
                id = "1",
                name = "Spätzle",
                price = "$12.99",
                imageUrl = "https://i.imgur.com/qYyOs7y.png",
                type = "pasta",
                category = "normal",
                size = "individual"
            ),
            Product(
                id = "2",
                name = "big kali",
                price = "$9.99",
                imageUrl = "https://i.imgur.com/YESV68t.png",
                type = "burger",
                category = "carne",
                size = "individual"
            ),
            Product(
                id = "3",
                name = "Nigiri de salmon",
                price = "$5.99",
                imageUrl = "https://i.imgur.com/I9nzvul.png",
                type = "sushi",
                category = "pescado",
                size = "individual"
            ),
            Product(
                id = "4",
                name = "Escalopa kaiser",
                price = "$8.99",
                imageUrl = "https://i.imgur.com/4K6gND8.png",
                type = "carne",
                category = "carne",
                size = "individual"
            ),
        )
    }

    // -------- FILTROS --------
    var selectedType by remember { mutableStateOf("Todos") }

    val availableTypes = remember(products) {
        listOf("Todos") + products.map { it.type }.distinct()
    }

    val filteredProducts = products.filter { product ->
        selectedType == "Todos" || product.type == selectedType
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Gostoso") },
                actions = {
                    IconButton(onClick = {
                        navController.navigate(Screen.Cart.route)
                    }) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Carrito de compras"
                        )
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, screen ->
                    NavigationBarItem(
                        selected = selectedItem == index,
                        onClick = {
                            selectedItem = index
                            if (navController.currentDestination?.route != screen.route) {
                                navController.navigate(screen.route)
                            }
                        },
                        label = { Text(screen.route) },
                        icon = {
                            Icon(
                                imageVector = if (screen == Screen.Home) Icons.Default.Home else Icons.Default.Person,
                                contentDescription = screen.route
                            )
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {

            // ----- Barra de filtros -----
            var expanded by remember { mutableStateOf(false) }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Filtrar por tipo:")

                Box {
                    OutlinedButton(onClick = { expanded = true }) {
                        Text(selectedType)
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        availableTypes.forEach { type ->
                            DropdownMenuItem(
                                text = { Text(type) },
                                onClick = {
                                    selectedType = type
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }

            // ----- Grid de productos filtrados -----
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                content = {
                    items(filteredProducts, key = { it.id }) { product ->

                        val food = Food(
                            id = product.id.toInt(),
                            name = product.name,
                            price = product.price.replace("$", "").toDouble(),
                            imageUrl = product.imageUrl,
                            type = product.type,
                            category = product.category,
                            size = product.size
                        )

                        ProductCard(
                            product = product,
                            onAdd = { cartViewModel.addFood(food) },
                            onClick = {
                                navController.navigate(
                                    Screen.FoodDetail.createRoute(food.id)
                                )
                            }
                        )
                    }
                }
            )
        }
    }
}

@Composable
private fun ProductCard(
    product: Product,
    onAdd: () -> Unit,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(product.imageUrl)
                    .crossfade(true)
                    .build(),
                contentDescription = "Imagen de ${product.name}",
                modifier = Modifier
                    .height(120.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = product.name,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = product.price,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onAdd,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Agregar")
            }
        }
    }
}
