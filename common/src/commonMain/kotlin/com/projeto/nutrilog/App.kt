package com.projeto.nutrilog

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.koin.compose.viewmodel.koinViewModel
import com.projeto.nutrilog.presentation.main.MainViewModel
import com.projeto.nutrilog.presentation.main.MainUiState
import com.projeto.nutrilog.presentation.main.ProfileDashboardPlaceholderScreen
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
                // ponytail: placeholder dashboard for completed registration.
                ProfileDashboardPlaceholderScreen(
                    user = state.user,
                    onResetData = {
                        viewModel.resetUser()
                    }
                )
            }
        }
    }
}
