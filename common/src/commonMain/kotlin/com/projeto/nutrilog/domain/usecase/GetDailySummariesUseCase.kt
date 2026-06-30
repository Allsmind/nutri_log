package com.projeto.nutrilog.domain.usecase

import com.projeto.nutrilog.domain.repository.DailySummary
import com.projeto.nutrilog.domain.repository.MealLogRepository

class GetDailySummariesUseCase(private val repository: MealLogRepository) {
    suspend operator fun invoke(limit: Long = 7L): List<DailySummary> {
        return repository.getDailySummaries(limit)
    }
}
