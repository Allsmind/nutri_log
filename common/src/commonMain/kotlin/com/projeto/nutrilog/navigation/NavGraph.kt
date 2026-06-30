package com.projeto.nutrilog.navigation

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
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.projeto.nutrilog.presentation.welcome.WelcomeScreen
import com.projeto.nutrilog.presentation.register.RegisterScreen
import com.projeto.nutrilog.presentation.dashboard.DashboardScreen
import com.projeto.nutrilog.presentation.dashboard.DashboardViewModel
import com.projeto.nutrilog.presentation.dashboard.DashboardUiState
import com.projeto.nutrilog.presentation.food.FoodDatabaseScreen
import com.projeto.nutrilog.presentation.statistics.ScreenStatistics
import org.koin.compose.viewmodel.koinViewModel

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object Register : Screen("register")
    object Dashboard : Screen("dashboard")
    object FoodDatabase : Screen("food_database")
    object Statistics : Screen("statistics")
}

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String,
    onRegistrationSuccess: () -> Unit,
    onResetData: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Welcome.route) {
            WelcomeScreen(
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                }
            )
        }
        
        composable(Screen.Register.route) {
            RegisterScreen(
                onRegistrationSuccess = onRegistrationSuccess,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
        
        composable(Screen.Dashboard.route) {
            val dashboardViewModel: DashboardViewModel = koinViewModel()
            val dashboardState by dashboardViewModel.uiState.collectAsState()

            LaunchedEffect(Unit) {
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
                        mealLogs = dashState.mealLogs,
                        mealGroups = dashState.mealGroups,
                        onQuickAdd = { calories, protein, carbs, fat ->
                            dashboardViewModel.quickAdd(calories, protein, carbs, fat)
                        },
                        onNavigateToFoodDatabase = {
                            navController.navigate(Screen.FoodDatabase.route)
                        },
                        onNavigateToStatistics = {
                            navController.navigate(Screen.Statistics.route)
                        },
                        onDeleteMealLog = { id ->
                            dashboardViewModel.deleteLog(id)
                        },
                        onResetData = onResetData
                    )
                }
            }
        }
        
        composable(Screen.FoodDatabase.route) {
            FoodDatabaseScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.Statistics.route) {
            ScreenStatistics(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
