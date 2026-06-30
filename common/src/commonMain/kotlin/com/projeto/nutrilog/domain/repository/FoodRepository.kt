package com.projeto.nutrilog.domain.repository

import com.projeto.nutrilog.database.FoodEntity

// ponytail: Using SQLDelight's generated FoodEntity directly as our domain model to avoid redundant mapping.
interface FoodRepository {
    suspend fun searchFoods(query: String): List<FoodEntity>
    suspend fun saveFood(food: FoodEntity)
}
