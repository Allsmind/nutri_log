package com.projeto.nutrilog.domain.usecase

import com.projeto.nutrilog.database.UserEntity
import com.projeto.nutrilog.domain.repository.UserRepository

class SaveUserUseCase(private val repository: UserRepository) {
    suspend operator fun invoke(user: UserEntity) {
        // ponytail: Business validation in UseCase as required by clean architecture
        require(user.name.isNotBlank()) { "Nome não pode ser vazio" }
        require(user.calorieGoal > 0) { "Meta calórica deve ser maior que zero" }
        repository.saveUser(user)
    }
}
