package com.projeto.nutrilog.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projeto.nutrilog.database.UserEntity
import com.projeto.nutrilog.domain.usecase.GetUserUseCase
import com.projeto.nutrilog.domain.usecase.SaveUserUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val getUserUseCase: GetUserUseCase,
    private val saveUserUseCase: SaveUserUseCase
) : ViewModel() {

    private val _user = MutableStateFlow<UserEntity?>(null)
    val user: StateFlow<UserEntity?> = _user

    private val _saveSuccess = MutableSharedFlow<Unit>()
    val saveSuccess: SharedFlow<Unit> = _saveSuccess

    init {
        loadUser()
    }

    private fun loadUser() {
        viewModelScope.launch {
            try {
                _user.value = getUserUseCase()
            } catch (e: Exception) {
                // ponytail: In a real app we'd propagate this error to the UI, keeping it simple for now.
            }
        }
    }

    fun saveSettings(
        name: String,
        calorieGoal: Int,
        proteinGoal: Double,
        carbGoal: Double,
        fatGoal: Double
    ) {
        viewModelScope.launch {
            try {
                val updatedUser = UserEntity(
                    id = 1L,
                    name = name,
                    calorieGoal = calorieGoal.toLong(),
                    proteinGoal = proteinGoal,
                    carbGoal = carbGoal,
                    fatGoal = fatGoal
                )
                saveUserUseCase(updatedUser)
                _saveSuccess.emit(Unit)
            } catch (e: Exception) {
                // ponytail: In a real app we'd propagate this error to the UI, keeping it simple for now.
            }
        }
    }
}
