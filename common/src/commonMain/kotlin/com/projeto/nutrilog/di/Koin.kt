package com.projeto.nutrilog.di

import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import com.projeto.nutrilog.database.NutriLogDatabase
import app.cash.sqldelight.db.SqlDriver
import com.projeto.nutrilog.domain.repository.UserRepository
import com.projeto.nutrilog.data.repository.UserRepositoryImpl
import com.projeto.nutrilog.domain.repository.MealLogRepository
import com.projeto.nutrilog.data.repository.MealLogRepositoryImpl
import com.projeto.nutrilog.domain.usecase.GetUserUseCase
import com.projeto.nutrilog.domain.usecase.SaveUserUseCase
import com.projeto.nutrilog.domain.usecase.ClearUserUseCase
import com.projeto.nutrilog.domain.usecase.GetMealLogsUseCase
import com.projeto.nutrilog.domain.usecase.AddMealLogUseCase
import com.projeto.nutrilog.domain.usecase.DeleteMealLogUseCase
import com.projeto.nutrilog.domain.usecase.GetDailySummariesUseCase
import com.projeto.nutrilog.presentation.register.RegisterViewModel
import com.projeto.nutrilog.presentation.main.MainViewModel
import com.projeto.nutrilog.presentation.dashboard.DashboardViewModel
import com.projeto.nutrilog.presentation.statistics.StatisticsViewModel
import com.projeto.nutrilog.presentation.recipe.CreateRecipeViewModel
import org.koin.core.module.dsl.viewModel

expect fun platformModule(): Module

fun initKoin(appDeclaration: KoinAppDeclaration = {}) = startKoin {
    appDeclaration()
    modules(commonModule(), platformModule())
}

// For iOS/Swift entry point
fun initKoin() = initKoin {}

fun commonModule() = module {
    single<NutriLogDatabase> {
        val driver = get<SqlDriver>()
        NutriLogDatabase(driver)
    }
    single<UserRepository> { UserRepositoryImpl(get()) }
    single<MealLogRepository> { MealLogRepositoryImpl(get()) }
    single { GetUserUseCase(get()) }
    single { SaveUserUseCase(get()) }
    single { ClearUserUseCase(get(), get()) }
    single { GetMealLogsUseCase(get()) }
    single { AddMealLogUseCase(get()) }
    single { DeleteMealLogUseCase(get()) }
    single { GetDailySummariesUseCase(get()) }
    viewModel { RegisterViewModel(get()) }
    viewModel { MainViewModel(get(), get()) }
    viewModel { DashboardViewModel(get(), get(), get(), get()) }
    viewModel { StatisticsViewModel(get(), get()) }
    viewModel { CreateRecipeViewModel(get(), get()) }
}
