package com.projeto.nutrilog.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import com.projeto.nutrilog.database.NutriLogDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(): Module = module {
    single<SqlDriver> {
        NativeSqliteDriver(
            schema = NutriLogDatabase.Schema,
            name = "nutrilog.db"
        )
    }
}
