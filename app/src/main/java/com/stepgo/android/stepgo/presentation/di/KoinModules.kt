package com.stepgo.android.stepgo.presentation.di

import com.stepgo.android.stepgo.data.db.StepGoDatabase
import com.stepgo.android.stepgo.data.repositories.SongRepositoryImpl
import com.stepgo.android.stepgo.domain.viewmodels.StepCountingViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { StepCountingViewModel(application = androidApplication(), stepDao = get()) }
}

val repositoryModule = module {
    single { SongRepositoryImpl(appContext = androidApplication()) }
}

val dbModule = module {
    single { StepGoDatabase.getInstance(androidApplication()).stepDao() }
}