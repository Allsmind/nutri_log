package com.projeto.nutrilog.data.repository

import com.projeto.nutrilog.database.FoodEntity
import com.projeto.nutrilog.database.NutriLogDatabase
import com.projeto.nutrilog.domain.repository.FoodRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class FoodRepositoryImpl(
    private val database: NutriLogDatabase
) : FoodRepository {

    private val queries = database.nutriLogDatabaseQueries

    override suspend fun searchFoods(query: String): List<FoodEntity> = withContext(Dispatchers.IO) {
        // ponytail: SQL LIKE filter executes on Dispatchers.IO
        queries.searchFoods("%$query%").executeAsList()
    }

    override suspend fun saveFood(food: FoodEntity): Unit = withContext(Dispatchers.IO) {
        // ponytail: database write executes on Dispatchers.IO
        queries.insertFood(
            name = food.name,
            caloriesPer100g = food.caloriesPer100g,
            proteinPer100g = food.proteinPer100g,
            carbPer100g = food.carbPer100g,
            fatPer100g = food.fatPer100g
        )
    }
}
