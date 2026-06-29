package com.projeto.nutrilog.android

import android.app.Application
import com.projeto.nutrilog.di.initKoin
import org.koin.android.ext.koin.androidContext

class NutriLogApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin {
            androidContext(this@NutriLogApplication)
        }
    }
}
