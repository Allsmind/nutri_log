package com.projeto.nutrilog.domain.usecase

import com.projeto.nutrilog.domain.repository.UserRepository
import com.projeto.nutrilog.domain.repository.MealLogRepository

class ClearUserUseCase(
    private val userRepository: UserRepository,
    private val mealLogRepository: MealLogRepository
) {
    suspend operator fun invoke() {
        userRepository.clearUser()
        mealLogRepository.clearAllMealLogs()
    }
}
