package com.projeto.nutrilog.domain.usecase

import com.projeto.nutrilog.domain.repository.UserRepository
import com.projeto.nutrilog.domain.repository.ProgressRepository

class ClearUserUseCase(
    private val userRepository: UserRepository,
    private val progressRepository: ProgressRepository
) {
    suspend operator fun invoke() {
        userRepository.clearUser()
        progressRepository.clearAllProgress()
    }
}
