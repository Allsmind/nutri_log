package com.projeto.nutrilog.data.repository

import com.projeto.nutrilog.database.NutriLogDatabase
import com.projeto.nutrilog.database.UserEntity
import com.projeto.nutrilog.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class UserRepositoryImpl(
    private val database: NutriLogDatabase
) : UserRepository {
    
    private val queries = database.nutriLogDatabaseQueries

    override suspend fun getUser(): UserEntity? = withContext(Dispatchers.IO) {
        // ponytail: Execute database queries on Dispatchers.IO for safety
        queries.getUser().executeAsOneOrNull()
    }

    override suspend fun saveUser(user: UserEntity): Unit = withContext(Dispatchers.IO) {
        // ponytail: Execute database queries on Dispatchers.IO for safety
        queries.insertUser(
            id = user.id,
            name = user.name,
            calorieGoal = user.calorieGoal,
            proteinGoal = user.proteinGoal,
            carbGoal = user.carbGoal,
            fatGoal = user.fatGoal
        )
    }

    override suspend fun clearUser(): Unit = withContext(Dispatchers.IO) {
        // ponytail: Execute database queries on Dispatchers.IO for safety
        queries.clearUser()
    }
}
