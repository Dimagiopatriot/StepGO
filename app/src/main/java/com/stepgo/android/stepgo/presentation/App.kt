package com.stepgo.android.stepgo.presentation

import android.app.Application
import com.stepgo.android.stepgo.presentation.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(viewModelModule, repositoryModule, useCasesModule, dbModule))
        }
    }
}