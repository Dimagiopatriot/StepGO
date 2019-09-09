package com.stepgo.android.stepgo.domain.usecases.statistic

import com.github.mikephil.charting.data.BarData
import com.stepgo.android.stepgo.data.entities.Step

class MapStepsUseCase {

    fun getMappedBarData(steps: List<Step>): BarData {
        //todo implement map logic for week, month, year
        return BarData()
    }
}