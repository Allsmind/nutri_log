package com.projeto.nutrilog.domain.repository

import com.projeto.nutrilog.database.MealLogEntity

data class DailySummary(
    val date: String,
    val totalCalories: Long,
    val totalProtein: Double,
    val totalCarbs: Double,
    val totalFat: Double
)

// ponytail: Using SQLDelight's generated MealLogEntity directly as our domain model to avoid redundant mapping.
interface MealLogRepository {
    suspend fun getMealLogs(date: String): List<MealLogEntity>
    suspend fun addMealLog(
        date: String,
        mealName: String,
        foodName: String,
        calories: Int,
        protein: Double,
        carbs: Double,
        fat: Double,
        weightGrams: Int
    )
    suspend fun deleteMealLog(id: Long)
    suspend fun clearAllMealLogs()
    suspend fun getDailySummaries(limit: Long): List<DailySummary>
}
