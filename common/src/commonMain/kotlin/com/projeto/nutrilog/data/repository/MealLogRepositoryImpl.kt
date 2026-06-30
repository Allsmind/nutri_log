package com.projeto.nutrilog.data.repository

import com.projeto.nutrilog.database.MealLogEntity
import com.projeto.nutrilog.database.NutriLogDatabase
import com.projeto.nutrilog.domain.repository.DailySummary
import com.projeto.nutrilog.domain.repository.MealLogRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class MealLogRepositoryImpl(
    private val database: NutriLogDatabase
) : MealLogRepository {

    private val queries = database.nutriLogDatabaseQueries

    override suspend fun getMealLogs(date: String): List<MealLogEntity> = withContext(Dispatchers.IO) {
        // ponytail: execute database fetch on Dispatchers.IO
        queries.getMealLogsForDate(date).executeAsList()
    }

    override suspend fun addMealLog(
        date: String,
        mealName: String,
        foodName: String,
        calories: Int,
        protein: Double,
        carbs: Double,
        fat: Double,
        weightGrams: Int
    ): Unit = withContext(Dispatchers.IO) {
        // ponytail: execute database insert on Dispatchers.IO
        queries.insertMealLog(
            date = date,
            mealName = mealName,
            foodName = foodName,
            calories = calories.toLong(),
            protein = protein,
            carbs = carbs,
            fat = fat,
            weightGrams = weightGrams.toLong()
        )
    }

    override suspend fun deleteMealLog(id: Long): Unit = withContext(Dispatchers.IO) {
        // ponytail: execute database delete on Dispatchers.IO
        queries.deleteMealLog(id)
    }

    override suspend fun clearAllMealLogs(): Unit = withContext(Dispatchers.IO) {
        // ponytail: execute database delete on Dispatchers.IO
        queries.clearAllMealLogs()
    }

    override suspend fun getDailySummaries(limit: Long): List<DailySummary> = withContext(Dispatchers.IO) {
        // ponytail: execute group sum aggregation on Dispatchers.IO and map result rows
        queries.getDailySummaries(limit).executeAsList().map { row ->
            DailySummary(
                date = row.date,
                totalCalories = row.totalCalories ?: 0L,
                totalProtein = row.totalProtein ?: 0.0,
                totalCarbs = row.totalCarbs ?: 0.0,
                totalFat = row.totalFat ?: 0.0
            )
        }
    }
}
