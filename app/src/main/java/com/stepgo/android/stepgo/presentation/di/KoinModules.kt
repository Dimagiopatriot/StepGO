package com.stepgo.android.stepgo.presentation.di

import com.stepgo.android.stepgo.data.db.StepGoDatabase
import com.stepgo.android.stepgo.data.repositories.SongRepositoryImpl
import com.stepgo.android.stepgo.domain.usecases.mainscreen.GetStatusStringIdUseCase
import com.stepgo.android.stepgo.domain.usecases.mainscreen.InitStepsUseCase
import com.stepgo.android.stepgo.domain.usecases.mainscreen.SaveStepsToDbUseCase
import com.stepgo.android.stepgo.domain.usecases.mainscreen.StepSensorChangedUseCase
import com.stepgo.android.stepgo.presentation.viewmodels.StepCountingViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        StepCountingViewModel(
                application = androidApplication(),
                initSteps = get(),
                sensorChanged = get(),
                saveSteps = get(),
                status = get())
    }
}

val repositoryModule = module {
    single { SongRepositoryImpl(appContext = androidApplication()) }
}

val useCasesModule = module {
    factory { SaveStepsToDbUseCase(stepDao = get()) }
    factory { GetStatusStringIdUseCase() }
    factory { InitStepsUseCase(stepDao = get()) }
    factory { StepSensorChangedUseCase() }
}

val dbModule = module {
    single { StepGoDatabase.getInstance(androidApplication()).stepDao() }
}