package com.stepgo.android.stepgo.presentation.viewmodels

import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.mikephil.charting.data.BarData
import com.stepgo.android.stepgo.domain.usecases.statistic.GetStepsFromDbUseCase
import com.stepgo.android.stepgo.domain.usecases.statistic.MapPositionToDateUseCase
import com.stepgo.android.stepgo.domain.usecases.statistic.MapStepsUseCase
import java.util.*

class StatisticViewModel(
        private val positionMapper: MapPositionToDateUseCase,
        private val dbUseCase: GetStepsFromDbUseCase,
        private val stepsMapper: MapStepsUseCase
) : ViewModel(), AdapterView.OnItemSelectedListener {

    val barData: MutableLiveData<BarData> = MutableLiveData()

    fun onTimeRangeChange(listPosition: Int = 0) {
        val startDate = positionMapper.mapPositionToDate(listPosition)
        val stepsFromDb = dbUseCase.getStepStatistic(startDate, Date())
        barData.value = stepsMapper.getMappedBarData(stepsFromDb, startDate)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        onTimeRangeChange(position)
    }
}