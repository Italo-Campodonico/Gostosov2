package com.example.Gostoso

import com.example.Gostoso.data.model.Food
import org.junit.Assert.assertEquals
import org.junit.Test

class FoodModelTest {

    @Test
    fun foodModel_guardaCorrectamenteLosDatosDeUnPlato() {
        val food = Food(
            id = 2,
            name = "Big Kali",
            price = 9.99,
            imageUrl = "https://i.imgur.com/YESV68t.png",
            type = "principal",
            category = "normal",
            size = "individual"
        )

        assertEquals(2, food.id)
        assertEquals("Big Kali", food.name)
        assertEquals(9.99, food.price, 0.001)
        assertEquals("https://i.imgur.com/YESV68t.png", food.imageUrl)
        assertEquals("principal", food.type)
        assertEquals("normal", food.category)
        assertEquals("individual", food.size)
    }
}
