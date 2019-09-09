package com.stepgo.android.stepgo.domain.usecases.statistic

import com.stepgo.android.stepgo.data.db.StepDao
import com.stepgo.android.stepgo.data.entities.Step
import com.stepgo.android.stepgo.presentation.viewmodels.SDF
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import java.util.*

class GetStepsFromDbUseCase(
        private val stepDao: StepDao
) {

    fun getStepStatistic(from: Date, to: Date): List<Step> =
            runBlocking(Dispatchers.Default) {
                stepDao.findStepStatistic(SDF.format(from), SDF.format(to))
            }
}