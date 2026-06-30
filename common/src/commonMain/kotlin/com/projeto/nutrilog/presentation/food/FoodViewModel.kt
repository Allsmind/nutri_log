package com.projeto.nutrilog.presentation.food

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projeto.nutrilog.database.FoodEntity
import com.projeto.nutrilog.domain.usecase.AddMealLogUseCase
import com.projeto.nutrilog.domain.usecase.SaveCustomFoodUseCase
import com.projeto.nutrilog.domain.usecase.SearchFoodsUseCase
import com.projeto.nutrilog.utils.getTodayDateString
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FoodViewModel(
    private val searchFoodsUseCase: SearchFoodsUseCase,
    private val saveCustomFoodUseCase: SaveCustomFoodUseCase,
    private val addMealLogUseCase: AddMealLogUseCase
) : ViewModel() {

    private val _foods = MutableStateFlow<List<FoodEntity>>(emptyList())
    val foods: StateFlow<List<FoodEntity>> = _foods

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _consumptionSuccess = MutableSharedFlow<Unit>()
    val consumptionSuccess: SharedFlow<Unit> = _consumptionSuccess

    init {
        performSearch("")
    }

    fun search(query: String) {
        _searchQuery.value = query
        performSearch(query)
    }

    private fun performSearch(query: String) {
        viewModelScope.launch {
            try {
                val list = searchFoodsUseCase(query)
                _foods.value = list
            } catch (e: Exception) {
                // ponytail: In a real app we'd propagate this error to the UI, keeping it simple for now.
            }
        }
    }

    fun saveCustomFood(
        name: String,
        calories: Int,
        protein: Double,
        carbs: Double,
        fat: Double
    ) {
        viewModelScope.launch {
            try {
                val food = FoodEntity(
                    id = 0L,
                    name = name,
                    caloriesPer100g = calories.toLong(),
                    proteinPer100g = protein,
                    carbPer100g = carbs,
                    fatPer100g = fat
                )
                saveCustomFoodUseCase(food)
                performSearch(_searchQuery.value)
            } catch (e: Exception) {
                // ponytail: In a real app we'd propagate this error to the UI, keeping it simple for now.
            }
        }
    }

    fun consumeFood(food: FoodEntity, weightGrams: Int, mealName: String) {
        viewModelScope.launch {
            try {
                val factor = weightGrams.toDouble() / 100.0
                val calories = (food.caloriesPer100g.toDouble() * factor).toInt()
                val protein = food.proteinPer100g * factor
                val carbs = food.carbPer100g * factor
                val fat = food.fatPer100g * factor
                
                val date = getTodayDateString()
                addMealLogUseCase(
                    date = date,
                    mealName = mealName,
                    foodName = food.name,
                    calories = calories,
                    protein = protein,
                    carbs = carbs,
                    fat = fat,
                    weightGrams = weightGrams
                )
                _consumptionSuccess.emit(Unit)
            } catch (e: Exception) {
                // ponytail: In a real app we'd propagate this error to the UI, keeping it simple for now.
            }
        }
    }
}
