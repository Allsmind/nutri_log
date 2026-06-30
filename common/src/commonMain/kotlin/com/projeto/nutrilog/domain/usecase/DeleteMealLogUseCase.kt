package com.projeto.nutrilog.domain.usecase

import com.projeto.nutrilog.domain.repository.MealLogRepository

class DeleteMealLogUseCase(private val repository: MealLogRepository) {
    suspend operator fun invoke(id: Long) {
        repository.deleteMealLog(id)
    }
}
