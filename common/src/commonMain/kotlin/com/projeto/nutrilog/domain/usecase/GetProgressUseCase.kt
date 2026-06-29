package com.projeto.nutrilog.domain.usecase

import com.projeto.nutrilog.database.DailyProgressEntity
import com.projeto.nutrilog.domain.repository.ProgressRepository

class GetProgressUseCase(private val repository: ProgressRepository) {
    suspend operator fun invoke(date: String): DailyProgressEntity? {
        return repository.getProgress(date)
    }
}
