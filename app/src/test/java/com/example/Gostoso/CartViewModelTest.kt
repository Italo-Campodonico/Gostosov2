package com.example.Gostoso

import com.example.Gostoso.data.model.Food
import com.example.Gostoso.viewmodel.CartViewModel
import org.junit.Assert.assertEquals
import org.junit.Test

class CartViewModelTest {

    private fun createFoodSpatzle(): Food {
        return Food(
            id = 1,
            name = "Spätzle",
            price = 12.99,
            imageUrl = "https://i.imgur.com/qYyOs7y.png",
            type = "principal",
            category = "normal",
            size = "individual"
        )
    }

    private fun createFoodNigiri(): Food {
        return Food(
            id = 3,
            name = "Nigiri de salmón",
            price = 5.99,
            imageUrl = "https://i.imgur.com/I9nzvul.png",
            type = "principal",
            category = "normal",
            size = "individual"
        )
    }

    @Test
    fun addFood_agregaPlatoAlCarritoCuandoEstaVacio() {
        val viewModel = CartViewModel()
        val spatzle = createFoodSpatzle()

        viewModel.addFood(spatzle)

        assertEquals(1, viewModel.items.size)
        assertEquals(1, viewModel.items[0].quantity)
        assertEquals("Spätzle", viewModel.items[0].food.name)
    }

    @Test
    fun addFood_incrementaCantidadCuandoEsElMismoPlato() {
        val viewModel = CartViewModel()
        val spatzle = createFoodSpatzle()

        viewModel.addFood(spatzle)
        viewModel.addFood(spatzle) // mismo plato otra vez

        assertEquals(1, viewModel.items.size)
        assertEquals(2, viewModel.items[0].quantity)
    }

    @Test
    fun clear_vaciaElCarrito() {
        val viewModel = CartViewModel()
        val spatzle = createFoodSpatzle()
        val nigiri = createFoodNigiri()

        viewModel.addFood(spatzle)
        viewModel.addFood(nigiri)

        assertEquals(2, viewModel.items.size)

        viewModel.clear()

        assertEquals(0, viewModel.items.size)
    }
}
