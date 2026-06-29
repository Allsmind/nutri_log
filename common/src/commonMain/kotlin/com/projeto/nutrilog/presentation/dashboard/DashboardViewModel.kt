package com.projeto.nutrilog.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projeto.nutrilog.database.DailyProgressEntity
import com.projeto.nutrilog.database.UserEntity
import com.projeto.nutrilog.domain.usecase.AddConsumptionUseCase
import com.projeto.nutrilog.domain.usecase.GetProgressUseCase
import com.projeto.nutrilog.domain.usecase.GetUserUseCase
import com.projeto.nutrilog.utils.getTodayDateString
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val getUserUseCase: GetUserUseCase,
    private val getProgressUseCase: GetProgressUseCase,
    private val addConsumptionUseCase: AddConsumptionUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<DashboardUiState>(DashboardUiState.Loading)
    val uiState: StateFlow<DashboardUiState> = _uiState

    init {
        loadDashboardData()
    }

    fun loadDashboardData() {
        viewModelScope.launch {
            try {
                val user = getUserUseCase()
                if (user == null) {
                    _uiState.value = DashboardUiState.Error("Usuário não cadastrado")
                    return@launch
                }
                
                val date = getTodayDateString()
                val progress = getProgressUseCase(date) ?: DailyProgressEntity(
                    date = date,
                    caloriesConsumed = 0L,
                    proteinConsumed = 0.0,
                    carbConsumed = 0.0,
                    fatConsumed = 0.0
                )

                _uiState.value = DashboardUiState.Success(user, progress)
            } catch (e: Exception) {
                _uiState.value = DashboardUiState.Error(e.message ?: "Erro desconhecido")
            }
        }
    }

    fun quickAdd(calories: Int, protein: Double, carbs: Double, fat: Double) {
        viewModelScope.launch {
            try {
                val date = getTodayDateString()
                addConsumptionUseCase(date, calories, protein, carbs, fat)
                loadDashboardData()
            } catch (e: Exception) {
                // ponytail: In a real app we'd propagate this error to the UI, keeping it simple for now.
            }
        }
    }
}

sealed interface DashboardUiState {
    object Loading : DashboardUiState
    data class Success(val user: UserEntity, val progress: DailyProgressEntity) : DashboardUiState
    data class Error(val message: String) : DashboardUiState
}
