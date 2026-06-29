package com.projeto.nutrilog.domain.usecase

import com.projeto.nutrilog.domain.repository.UserRepository

class ClearUserUseCase(private val repository: UserRepository) {
    suspend operator fun invoke() {
        repository.clearUser()
    }
}
