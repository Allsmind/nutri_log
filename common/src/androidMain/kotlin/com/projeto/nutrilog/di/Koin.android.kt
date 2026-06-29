package com.projeto.nutrilog.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.projeto.nutrilog.database.NutriLogDatabase
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.android.ext.koin.androidContext

actual fun platformModule(): Module = module {
    single<SqlDriver> {
        AndroidSqliteDriver(
            schema = NutriLogDatabase.Schema,
            context = androidContext(),
            name = "nutrilog.db"
        )
    }
}
