package com.example.Gostoso

import com.example.Gostoso.data.model.Review
import org.junit.Assert.assertEquals
import org.junit.Test

class ReviewFilterTest {

    @Test
    fun filterReviews_devuelveSoloResenasDeUnPlato() {
        val reviews = listOf(
            Review(id = 1, foodId = 1, userName = "Pedro", rating = 5, comment = "Spätzle espectacular"),
            Review(id = 2, foodId = 2, userName = "Ana", rating = 4, comment = "Big Kali bien, pero algo salado"),
            Review(id = 3, foodId = 1, userName = "Lucía", rating = 3, comment = "Rico pero pequeña la porción")
        )

        val resenasSpatzle = reviews.filter { it.foodId == 1 }

        assertEquals(2, resenasSpatzle.size)
        assertEquals(1, resenasSpatzle[0].foodId)
        assertEquals(1, resenasSpatzle[1].foodId)
    }
}
