package com.projeto.nutrilog.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.projeto.nutrilog.presentation.welcome.WelcomeScreen
import com.projeto.nutrilog.presentation.register.RegisterScreen

sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object Register : Screen("register")
}

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    onRegistrationSuccess: () -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Welcome.route
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
    }
}
