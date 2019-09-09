package com.stepgo.android.stepgo.presentation.viewmodels

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.github.mikephil.charting.data.BarData
import com.stepgo.android.stepgo.domain.usecases.statistic.GetStepsFromDbUseCase
import com.stepgo.android.stepgo.domain.usecases.statistic.MapPositionToDateUseCase
import com.stepgo.android.stepgo.domain.usecases.statistic.MapStepsUseCase
import java.util.*

class StatisticViewModel(
        private val positionMapper: MapPositionToDateUseCase,
        private val dbUseCase: GetStepsFromDbUseCase,
        private val stepsMapper: MapStepsUseCase
) : ViewModel() {

    lateinit var barData: MutableLiveData<BarData>

    fun onTimeRangeChange(listPosition: Int = 0) {
        val startDate = positionMapper.mapPositionToDate(listPosition)
        val stepsFromDb = dbUseCase.getStepStatistic(startDate, Date())
        barData.value = stepsMapper.getMappedBarData(stepsFromDb)
    }
}