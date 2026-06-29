package com.projeto.nutrilog.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projeto.nutrilog.database.UserEntity
import com.projeto.nutrilog.domain.usecase.SaveUserUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val saveUserUseCase: SaveUserUseCase
) : ViewModel() {

    private val _registrationSuccess = MutableSharedFlow<Unit>()
    val registrationSuccess: SharedFlow<Unit> = _registrationSuccess

    fun registerUser(
        name: String,
        calories: Int,
        protein: Double,
        carbs: Double,
        fat: Double
    ) {
        viewModelScope.launch {
            try {
                // ponytail: Single-user app offline-first, ID is always 1L.
                val user = UserEntity(
                    id = 1L,
                    name = name,
                    calorieGoal = calories.toLong(),
                    proteinGoal = protein,
                    carbGoal = carbs,
                    fatGoal = fat
                )
                saveUserUseCase(user)
                _registrationSuccess.emit(Unit)
            } catch (e: Exception) {
                // ponytail: In a real app we'd propagate this error to the UI, keeping it simple for now.
            }
        }
    }
}
