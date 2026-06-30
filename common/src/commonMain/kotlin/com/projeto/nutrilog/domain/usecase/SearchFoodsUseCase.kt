package com.projeto.nutrilog.domain.usecase

import com.projeto.nutrilog.database.FoodEntity
import com.projeto.nutrilog.domain.repository.FoodRepository

class SearchFoodsUseCase(private val repository: FoodRepository) {
    suspend operator fun invoke(query: String): List<FoodEntity> {
        return repository.searchFoods(query)
    }
}
