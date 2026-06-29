package com.projeto.nutrilog.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projeto.nutrilog.database.UserEntity
import com.projeto.nutrilog.domain.usecase.GetUserUseCase
import com.projeto.nutrilog.domain.usecase.ClearUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val getUserUseCase: GetUserUseCase,
    private val clearUserUseCase: ClearUserUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<MainUiState>(MainUiState.Loading)
    val uiState: StateFlow<MainUiState> = _uiState

    init {
        checkUser()
    }

    fun checkUser() {
        viewModelScope.launch {
            try {
                val user = getUserUseCase()
                if (user != null) {
                    _uiState.value = MainUiState.Registered(user)
                } else {
                    _uiState.value = MainUiState.NotRegistered
                }
            } catch (e: Exception) {
                // ponytail: Default to NotRegistered on error to let user retry/configure
                _uiState.value = MainUiState.NotRegistered
            }
        }
    }

    fun resetUser() {
        viewModelScope.launch {
            try {
                clearUserUseCase()
                _uiState.value = MainUiState.NotRegistered
            } catch (e: Exception) {
                // ponytail: In a real app we'd propagate this error to the UI, keeping it simple for now.
            }
        }
    }
}

sealed interface MainUiState {
    object Loading : MainUiState
    object NotRegistered : MainUiState
    data class Registered(val user: UserEntity) : MainUiState
}
