package com.example.Gostoso.viewmodel

import androidx.lifecycle.ViewModel
import com.example.Gostoso.data.model.CartItem
import com.example.Gostoso.data.model.Food

class CartViewModel : ViewModel() {

    private val _items = mutableListOf<CartItem>()
    val items: List<CartItem> get() = _items

    fun addFood(food: Food) {
        val index = _items.indexOfFirst { it.food.id == food.id }
        if (index >= 0) {
            val existing = _items[index]
            _items[index] = existing.copy(quantity = existing.quantity + 1)
        } else {
            _items.add(CartItem(food, 1))
        }
    }

    fun clear() {
        _items.clear()
    }
}
