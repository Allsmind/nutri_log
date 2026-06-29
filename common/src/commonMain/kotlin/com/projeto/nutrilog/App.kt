package com.projeto.nutrilog

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.koin.compose.viewmodel.koinViewModel
import com.projeto.nutrilog.presentation.main.MainViewModel
import com.projeto.nutrilog.presentation.main.MainUiState
import com.projeto.nutrilog.presentation.dashboard.DashboardViewModel
import com.projeto.nutrilog.presentation.dashboard.DashboardUiState
import com.projeto.nutrilog.presentation.dashboard.DashboardScreen
import com.projeto.nutrilog.navigation.NavGraph
import com.projeto.nutrilog.theme.NutriLogTheme

@Composable
fun App() {
    NutriLogTheme {
        val viewModel: MainViewModel = koinViewModel()
        val uiState by viewModel.uiState.collectAsState()

        when (val state = uiState) {
            is MainUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is MainUiState.NotRegistered -> {
                // ponytail: onboarding/registration flow graph.
                NavGraph(
                    onRegistrationSuccess = {
                        viewModel.checkUser()
                    }
                )
            }
            is MainUiState.Registered -> {
                val dashboardViewModel: DashboardViewModel = koinViewModel()
                val dashboardState by dashboardViewModel.uiState.collectAsState()

                // ponytail: reload data on screen layout focus/enter to keep it fresh
                LaunchedEffect(state.user) {
                    dashboardViewModel.loadDashboardData()
                }

                when (val dashState = dashboardState) {
                    is DashboardUiState.Loading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                    is DashboardUiState.Error -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "Erro: ${dashState.message}")
                        }
                    }
                    is DashboardUiState.Success -> {
                        DashboardScreen(
                            user = dashState.user,
                            progress = dashState.progress,
                            onQuickAdd = { calories, protein, carbs, fat ->
                                dashboardViewModel.quickAdd(calories, protein, carbs, fat)
                            },
                            onResetData = {
                                viewModel.resetUser()
                            }
                        )
                    }
                }
            }
        }
    }
}
