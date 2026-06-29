package com.projeto.nutrilog.domain.repository

import com.projeto.nutrilog.database.DailyProgressEntity

// ponytail: Using SQLDelight's generated DailyProgressEntity directly as our domain model to avoid redundant mapping.
interface ProgressRepository {
    suspend fun getProgress(date: String): DailyProgressEntity?
    suspend fun saveProgress(progress: DailyProgressEntity)
    suspend fun clearAllProgress()
}
