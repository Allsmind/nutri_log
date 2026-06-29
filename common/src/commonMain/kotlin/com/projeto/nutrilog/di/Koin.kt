package com.projeto.nutrilog.di

import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import com.projeto.nutrilog.database.NutriLogDatabase
import app.cash.sqldelight.db.SqlDriver

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
}
