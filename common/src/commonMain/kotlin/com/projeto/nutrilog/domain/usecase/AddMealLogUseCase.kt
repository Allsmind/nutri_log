package com.projeto.nutrilog.domain.usecase

import com.projeto.nutrilog.domain.repository.MealLogRepository

class AddMealLogUseCase(private val repository: MealLogRepository) {
    suspend operator fun invoke(
        date: String,
        mealName: String,
        foodName: String,
        calories: Int,
        protein: Double,
        carbs: Double,
        fat: Double,
        weightGrams: Int
    ) {
        // ponytail: Business validation inside domain UseCase
        require(foodName.isNotBlank()) { "Nome do alimento não pode ser vazio" }
        require(weightGrams > 0) { "Quantidade deve ser maior que zero" }
        repository.addMealLog(
            date = date,
            mealName = mealName,
            foodName = foodName,
            calories = calories,
            protein = protein,
            carbs = carbs,
            fat = fat,
            weightGrams = weightGrams
        )
    }
}
