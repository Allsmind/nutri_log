package com.projeto.nutrilog.domain.usecase

import com.projeto.nutrilog.database.DailyProgressEntity
import com.projeto.nutrilog.domain.repository.ProgressRepository

class AddConsumptionUseCase(private val repository: ProgressRepository) {
    suspend operator fun invoke(
        date: String,
        calories: Int,
        protein: Double,
        carbs: Double,
        fat: Double
    ) {
        val currentProgress = repository.getProgress(date) ?: DailyProgressEntity(
            date = date,
            caloriesConsumed = 0L,
            proteinConsumed = 0.0,
            carbConsumed = 0.0,
            fatConsumed = 0.0
        )

        // ponytail: perform the addition of calories and macros consumed here inside the use case
        val updatedProgress = DailyProgressEntity(
            date = date,
            caloriesConsumed = currentProgress.caloriesConsumed + calories,
            proteinConsumed = currentProgress.proteinConsumed + protein,
            carbConsumed = currentProgress.carbConsumed + carbs,
            fatConsumed = currentProgress.fatConsumed + fat
        )

        repository.saveProgress(updatedProgress)
    }
}
