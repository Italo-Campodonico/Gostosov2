package com.example.Gostoso

import com.example.Gostoso.viewmodel.ReviewViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ReviewViewModelTest {

    @Test
    fun addReview_agregaResenaParaUnPlato() {
        val viewModel = ReviewViewModel()

        viewModel.addReview(
            foodId = 1,
            userName = "Cliente anónimo",
            rating = 5,
            comment = "Muy rico, volvería a pedir"
        )

        val reviews = viewModel.reviews.value
        assertEquals(1, reviews.size)

        val review = reviews.first()
        assertEquals(1, review.foodId)
        assertEquals("Cliente anónimo", review.userName)
        assertEquals(5, review.rating)
        assertEquals("Muy rico, volvería a pedir", review.comment)
    }

    @Test
    fun addReview_asignaIdsIncrementales() {
        val viewModel = ReviewViewModel()

        viewModel.addReview(1, "Juan", 4, "Buena porción")
        viewModel.addReview(1, "María", 5, "Excelente sabor")

        val reviews = viewModel.reviews.value
        assertEquals(2, reviews.size)

        val primera = reviews[0]
        val segunda = reviews[1]

        assertTrue(segunda.id > primera.id)
    }
}
