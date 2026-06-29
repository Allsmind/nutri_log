package com.projeto.nutrilog.data.repository

import com.projeto.nutrilog.database.DailyProgressEntity
import com.projeto.nutrilog.database.NutriLogDatabase
import com.projeto.nutrilog.domain.repository.ProgressRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class ProgressRepositoryImpl(
    private val database: NutriLogDatabase
) : ProgressRepository {

    private val queries = database.nutriLogDatabaseQueries

    override suspend fun getProgress(date: String): DailyProgressEntity? = withContext(Dispatchers.IO) {
        // ponytail: execute on Dispatchers.IO for background safety
        queries.getProgressForDate(date).executeAsOneOrNull()
    }

    override suspend fun saveProgress(progress: DailyProgressEntity): Unit = withContext(Dispatchers.IO) {
        // ponytail: execute on Dispatchers.IO for background safety
        queries.insertProgress(
            date = progress.date,
            caloriesConsumed = progress.caloriesConsumed,
            proteinConsumed = progress.proteinConsumed,
            carbConsumed = progress.carbConsumed,
            fatConsumed = progress.fatConsumed
        )
    }

    override suspend fun clearAllProgress(): Unit = withContext(Dispatchers.IO) {
        // ponytail: execute on Dispatchers.IO for background safety
        queries.clearProgress()
    }
}
