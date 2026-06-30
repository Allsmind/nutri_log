package com.projeto.nutrilog.domain.usecase

import com.projeto.nutrilog.database.MealLogEntity
import com.projeto.nutrilog.domain.repository.MealLogRepository

class GetMealLogsUseCase(private val repository: MealLogRepository) {
    suspend operator fun invoke(date: String): List<MealLogEntity> {
        return repository.getMealLogs(date)
    }
}
