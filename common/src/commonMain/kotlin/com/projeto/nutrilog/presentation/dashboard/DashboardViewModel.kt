package com.projeto.nutrilog.presentation.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projeto.nutrilog.database.MealLogEntity
import com.projeto.nutrilog.database.UserEntity
import com.projeto.nutrilog.domain.usecase.GetUserUseCase
import com.projeto.nutrilog.domain.usecase.GetMealLogsUseCase
import com.projeto.nutrilog.domain.usecase.DeleteMealLogUseCase
import com.projeto.nutrilog.domain.usecase.AddMealLogUseCase
import com.projeto.nutrilog.utils.getTodayDateString
import com.projeto.nutrilog.utils.getOffsetDateString
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class DailyProgress(
    val date: String,
    val caloriesConsumed: Long,
    val proteinConsumed: Double,
    val carbConsumed: Double,
    val fatConsumed: Double
)

class DashboardViewModel(
    private val getUserUseCase: GetUserUseCase,
    private val getMealLogsUseCase: GetMealLogsUseCase,
    private val deleteMealLogUseCase: DeleteMealLogUseCase,
    private val addMealLogUseCase: AddMealLogUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<DashboardUiState>(DashboardUiState.Loading)
    val uiState: StateFlow<DashboardUiState> = _uiState

    // ponytail: Maintain selected date state to enable history tracking in dashboard
    private val _selectedDate = MutableStateFlow(getTodayDateString())
    val selectedDate: StateFlow<String> = _selectedDate

    private var lastSystemToday = getTodayDateString()

    init {
        loadDashboardData()
    }

    fun loadDashboardData() {
        viewModelScope.launch {
            try {
                // ponytail: check if the calendar day has turned since last check
                val currentToday = getTodayDateString()
                if (currentToday != lastSystemToday) {
                    if (_selectedDate.value == lastSystemToday) {
                        _selectedDate.value = currentToday
                    }
                    lastSystemToday = currentToday
                }

                val user = getUserUseCase()
                if (user == null) {
                    _uiState.value = DashboardUiState.Error("Usuário não cadastrado")
                    return@launch
                }
                
                val date = _selectedDate.value
                val logs = getMealLogsUseCase(date)

                // ponytail: dynamically calculate totals from the logs list
                val totalCalories = logs.sumOf { it.calories }
                val totalProtein = logs.sumOf { it.protein }
                val totalCarbs = logs.sumOf { it.carbs }
                val totalFat = logs.sumOf { it.fat }

                val progress = DailyProgress(
                    date = date,
                    caloriesConsumed = totalCalories,
                    proteinConsumed = totalProtein,
                    carbConsumed = totalCarbs,
                    fatConsumed = totalFat
                )

                // Group logs by meal name (ensure all standard meals show up)
                val mealGroups = listOf("Café da Manhã", "Almoço", "Café da Tarde", "Jantar", "Consumo Rápido")
                val groupedLogs = logs.groupBy { it.mealName }

                _uiState.value = DashboardUiState.Success(user, progress, groupedLogs, mealGroups)
            } catch (e: Exception) {
                _uiState.value = DashboardUiState.Error(e.message ?: "Erro desconhecido")
            }
        }
    }

    fun nextDay() {
        _selectedDate.value = getOffsetDateString(_selectedDate.value, 1)
        loadDashboardData()
    }

    fun previousDay() {
        _selectedDate.value = getOffsetDateString(_selectedDate.value, -1)
        loadDashboardData()
    }

    fun quickAdd(calories: Int, protein: Double, carbs: Double, fat: Double) {
        viewModelScope.launch {
            try {
                val date = _selectedDate.value
                addMealLogUseCase(
                    date = date,
                    mealName = "Consumo Rápido",
                    foodName = "Adição Rápida",
                    calories = calories,
                    protein = protein,
                    carbs = carbs,
                    fat = fat,
                    weightGrams = 100
                )
                loadDashboardData()
            } catch (e: Exception) {
                // ponytail: In a real app we'd propagate this error to the UI, keeping it simple for now.
            }
        }
    }

    fun deleteLog(id: Long) {
        viewModelScope.launch {
            try {
                deleteMealLogUseCase(id)
                loadDashboardData()
            } catch (e: Exception) {
                // ponytail: In a real app we'd propagate this error to the UI, keeping it simple for now.
            }
        }
    }
}

sealed interface DashboardUiState {
    object Loading : DashboardUiState
    data class Success(
        val user: UserEntity,
        val progress: DailyProgress,
        val mealLogs: Map<String, List<MealLogEntity>>,
        val mealGroups: List<String>
    ) : DashboardUiState
    data class Error(val message: String) : DashboardUiState
}
