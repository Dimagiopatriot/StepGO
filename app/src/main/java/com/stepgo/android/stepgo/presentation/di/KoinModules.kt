package com.stepgo.android.stepgo.presentation.di

import com.stepgo.android.stepgo.data.db.StepGoDatabase
import com.stepgo.android.stepgo.data.repositories.SongRepositoryImpl
import com.stepgo.android.stepgo.domain.usecases.mainscreen.GetStatusStringIdUseCase
import com.stepgo.android.stepgo.domain.usecases.mainscreen.InitStepsUseCase
import com.stepgo.android.stepgo.domain.usecases.mainscreen.SaveStepsToDbUseCase
import com.stepgo.android.stepgo.domain.usecases.mainscreen.StepSensorChangedUseCase
import com.stepgo.android.stepgo.domain.usecases.statistic.GetStepsFromDbUseCase
import com.stepgo.android.stepgo.domain.usecases.statistic.MapPositionToDateUseCase
import com.stepgo.android.stepgo.domain.usecases.statistic.MapStepsUseCase
import com.stepgo.android.stepgo.presentation.viewmodels.StatisticViewModel
import com.stepgo.android.stepgo.presentation.viewmodels.StepCountingViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel {
        StepCountingViewModel(
                application = androidApplication(),
                initSteps = get(),
                sensorChanged = get(),
                saveSteps = get(),
                status = get())
    }
    viewModel {
        StatisticViewModel(
                positionMapper = get(),
                dbUseCase = get(),
                stepsMapper = get()
        )
    }
}

val repositoriesModule = module {
    single { SongRepositoryImpl(appContext = androidApplication()) }
}

val useCasesModule = module {
    factory { SaveStepsToDbUseCase(stepDao = get()) }
    factory { GetStatusStringIdUseCase() }
    factory { InitStepsUseCase(stepDao = get()) }
    factory { StepSensorChangedUseCase() }
    factory { GetStepsFromDbUseCase(stepDao = get()) }
    factory { MapPositionToDateUseCase() }
    factory { MapStepsUseCase() }
}

val dbModule = module {
    single { StepGoDatabase.getInstance(androidApplication()).stepDao() }
}