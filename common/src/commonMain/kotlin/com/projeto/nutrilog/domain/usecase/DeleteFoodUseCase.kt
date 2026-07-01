package com.projeto.nutrilog.domain.usecase

import com.projeto.nutrilog.domain.repository.FoodRepository

class DeleteFoodUseCase(
    private val repository: FoodRepository
) {
    suspend operator fun invoke(id: Long) {
        repository.deleteFood(id)
    }
}
