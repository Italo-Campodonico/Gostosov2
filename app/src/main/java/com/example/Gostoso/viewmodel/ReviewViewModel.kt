package com.example.Gostoso.viewmodel

import androidx.lifecycle.ViewModel
import com.example.Gostoso.data.model.Review
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ReviewViewModel : ViewModel() {

    private val _reviews = MutableStateFlow<List<Review>>(emptyList())
    val reviews: StateFlow<List<Review>> = _reviews

    fun addReview(foodId: Int, userName: String, rating: Int, comment: String) {
        val newId = (_reviews.value.maxOfOrNull { it.id } ?: 0) + 1

        val newReview = Review(
            id = newId,
            foodId = foodId,
            userName = userName,
            rating = rating,
            comment = comment
        )

        _reviews.value = _reviews.value + newReview
    }
}
