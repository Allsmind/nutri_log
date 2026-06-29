package com.projeto.nutrilog.domain.usecase

import com.projeto.nutrilog.database.UserEntity
import com.projeto.nutrilog.domain.repository.UserRepository

class GetUserUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(): UserEntity? {
        return repository.getUser()
    }
}
