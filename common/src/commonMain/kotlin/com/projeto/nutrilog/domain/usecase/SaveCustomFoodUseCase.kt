package com.projeto.nutrilog.domain.usecase

import com.projeto.nutrilog.database.FoodEntity
import com.projeto.nutrilog.domain.repository.FoodRepository

class SaveCustomFoodUseCase(private val repository: FoodRepository) {
    suspend operator fun invoke(food: FoodEntity) {
        // ponytail: Business validations inside domain layer UseCase
        require(food.name.isNotBlank()) { "Nome do alimento não pode ser vazio" }
        require(food.caloriesPer100g >= 0) { "Calorias não podem ser negativas" }
        require(food.proteinPer100g >= 0) { "Proteínas não podem ser negativas" }
        require(food.carbPer100g >= 0) { "Carboidratos não podem ser negativos" }
        require(food.fatPer100g >= 0) { "Gorduras não podem ser negativas" }
        repository.saveFood(food)
    }
}
