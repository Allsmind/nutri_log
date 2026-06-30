package com.projeto.nutrilog.presentation.statistics

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projeto.nutrilog.database.UserEntity
import com.projeto.nutrilog.domain.repository.DailySummary
import com.projeto.nutrilog.domain.usecase.GetDailySummariesUseCase
import com.projeto.nutrilog.domain.usecase.GetUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class StatisticsViewModel(
    private val getUserUseCase: GetUserUseCase,
    private val getDailySummariesUseCase: GetDailySummariesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<StatisticsUiState>(StatisticsUiState.Loading)
    val uiState: StateFlow<StatisticsUiState> = _uiState

    init {
        loadStatistics()
    }

    fun loadStatistics() {
        viewModelScope.launch {
            try {
                val user = getUserUseCase()
                if (user == null) {
                    _uiState.value = StatisticsUiState.Error("Usuário não cadastrado")
                    return@launch
                }
                
                val summaries = getDailySummariesUseCase(7L)
                _uiState.value = StatisticsUiState.Success(user, summaries)
            } catch (e: Exception) {
                _uiState.value = StatisticsUiState.Error(e.message ?: "Erro desconhecido")
            }
        }
    }
}

sealed interface StatisticsUiState {
    object Loading : StatisticsUiState
    data class Success(val user: UserEntity, val summaries: List<DailySummary>) : StatisticsUiState
    data class Error(val message: String) : StatisticsUiState
}
