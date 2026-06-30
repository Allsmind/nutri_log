package com.projeto.nutrilog.presentation.recipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projeto.nutrilog.database.FoodEntity
import com.projeto.nutrilog.domain.usecase.SaveCustomFoodUseCase
import com.projeto.nutrilog.domain.usecase.SearchFoodsUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class RecipeIngredient(
    val food: FoodEntity,
    val weightGrams: Int
)

class CreateRecipeViewModel(
    private val searchFoodsUseCase: SearchFoodsUseCase,
    private val saveCustomFoodUseCase: SaveCustomFoodUseCase
) : ViewModel() {

    private val _recipeName = MutableStateFlow("")
    val recipeName: StateFlow<String> = _recipeName

    private val _ingredients = MutableStateFlow<List<RecipeIngredient>>(emptyList())
    val ingredients: StateFlow<List<RecipeIngredient>> = _ingredients

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _searchResults = MutableStateFlow<List<FoodEntity>>(emptyList())
    val searchResults: StateFlow<List<FoodEntity>> = _searchResults

    private val _saveSuccess = MutableSharedFlow<Unit>()
    val saveSuccess: SharedFlow<Unit> = _saveSuccess

    fun setRecipeName(name: String) {
        _recipeName.value = name
    }

    fun searchIngredients(query: String) {
        _searchQuery.value = query
        viewModelScope.launch {
            try {
                _searchResults.value = searchFoodsUseCase(query)
            } catch (e: Exception) {
                // ponytail: In a real app we'd propagate this error to the UI, keeping it simple for now.
            }
        }
    }

    fun addIngredient(food: FoodEntity, weightGrams: Int) {
        val currentList = _ingredients.value.toMutableList()
        val index = currentList.indexOfFirst { it.food.id == food.id }
        if (index >= 0) {
            currentList[index] = RecipeIngredient(food, weightGrams)
        } else {
            currentList.add(RecipeIngredient(food, weightGrams))
        }
        _ingredients.value = currentList
    }

    fun removeIngredient(foodId: Long) {
        _ingredients.value = _ingredients.value.filter { it.food.id != foodId }
    }

    fun saveRecipe() {
        val name = _recipeName.value
        val list = _ingredients.value
        if (name.isBlank() || list.isEmpty()) return

        viewModelScope.launch {
            try {
                val totalWeight = list.sumOf { it.weightGrams }.toDouble()
                if (totalWeight <= 0) return@launch

                val totalCalories = list.sumOf { (it.food.caloriesPer100g.toDouble() * it.weightGrams.toDouble()) / 100.0 }
                val totalProtein = list.sumOf { (it.food.proteinPer100g * it.weightGrams.toDouble()) / 100.0 }
                val totalCarbs = list.sumOf { (it.food.carbPer100g * it.weightGrams.toDouble()) / 100.0 }
                val totalFat = list.sumOf { (it.food.fatPer100g * it.weightGrams.toDouble()) / 100.0 }

                // Scale to per 100g
                val factor = 100.0 / totalWeight
                val caloriesPer100g = (totalCalories * factor).toLong()
                val proteinPer100g = totalProtein * factor
                val carbPer100g = totalCarbs * factor
                val fatPer100g = totalFat * factor

                // ponytail: save recipe directly as a standard custom FoodEntity
                val foodRecipe = FoodEntity(
                    id = 0L,
                    name = "$name (Receita)",
                    caloriesPer100g = caloriesPer100g,
                    proteinPer100g = proteinPer100g,
                    carbPer100g = carbPer100g,
                    fatPer100g = fatPer100g
                )

                saveCustomFoodUseCase(foodRecipe)
                _saveSuccess.emit(Unit)
            } catch (e: Exception) {
                // ponytail: In a real app we'd propagate this error to the UI, keeping it simple for now.
            }
        }
    }
}
