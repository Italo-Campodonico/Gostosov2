package com.example.Gostoso.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.Gostoso.data.model.Food
import com.example.Gostoso.data.repository.FoodRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class HomeUiState(
    val isLoading: Boolean = false,
    val foods: List<Food> = emptyList(),
    val error: String? = null
)

class HomeViewModel(
    private val foodRepository: FoodRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        loadFoods()
    }

    fun loadFoods() {
        viewModelScope.launch {
            try {
                _uiState.value = _uiState.value.copy(isLoading = true, error = null)
                val foods = foodRepository.getFoodsFromApi()
                _uiState.value = HomeUiState(
                    isLoading = false,
                    foods = foods,
                    error = null
                )
            } catch (e: Exception) {
                // Si la API falla, por ahora no rompe la app
                _uiState.value = HomeUiState(
                    isLoading = false,
                    foods = emptyList(),
                    error = e.message ?: "Error al cargar productos"
                )
            }
        }
    }
}
