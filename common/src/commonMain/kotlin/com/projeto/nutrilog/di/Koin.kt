package com.projeto.nutrilog.di

import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import com.projeto.nutrilog.database.NutriLogDatabase
import app.cash.sqldelight.db.SqlDriver
import com.projeto.nutrilog.domain.repository.UserRepository
import com.projeto.nutrilog.data.repository.UserRepositoryImpl
import com.projeto.nutrilog.domain.usecase.GetUserUseCase
import com.projeto.nutrilog.domain.usecase.SaveUserUseCase
import com.projeto.nutrilog.domain.usecase.ClearUserUseCase
import com.projeto.nutrilog.presentation.register.RegisterViewModel
import com.projeto.nutrilog.presentation.main.MainViewModel
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
    single { GetUserUseCase(get()) }
    single { SaveUserUseCase(get()) }
    single { ClearUserUseCase(get()) }
    viewModel { RegisterViewModel(get()) }
    viewModel { MainViewModel(get(), get()) }
}
