package com.projeto.nutrilog.domain.repository

import com.projeto.nutrilog.database.UserEntity

// ponytail: Using SQLDelight's generated UserEntity directly as our domain model to avoid redundant mapping boilerplate.
interface UserRepository {
    suspend fun getUser(): UserEntity?
    suspend fun saveUser(user: UserEntity)
    suspend fun clearUser()
}
