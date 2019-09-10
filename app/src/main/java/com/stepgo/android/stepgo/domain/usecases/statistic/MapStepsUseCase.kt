package com.stepgo.android.stepgo.domain.usecases.statistic

import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.stepgo.android.stepgo.R
import com.stepgo.android.stepgo.data.entities.Step
import com.stepgo.android.stepgo.domain.usecases.getDayDifference
import com.stepgo.android.stepgo.presentation.viewmodels.SDF
import java.text.SimpleDateFormat
import java.util.*

class MapStepsUseCase {

    fun getMappedBarData(steps: List<Step>, startDate: Date): BarData {
        val barEntry = mutableListOf<BarEntry>()
        val barEntryLabels = mutableListOf<String>()

        when (getDayDifference(startDate, Date())) {
            in 0..31 -> mapForWeek(steps, barEntry, barEntryLabels)
            in 32..365 -> mapForYear(steps, barEntry, barEntryLabels)
        }
        val barDataSet = BarDataSet(barEntry, "Steps")
        barDataSet.setColors(R.color.colorPrimary)
        val barData = BarData(barDataSet)
        barData.setValueFormatter(IndexAxisValueFormatter(barEntryLabels))
        return barData
    }

    fun mapForWeek(steps: List<Step>, barEntry: MutableList<BarEntry>, barEntryLabels: MutableList<String>) {
        val showDay = SimpleDateFormat("EEE, MM/dd", Locale.getDefault())
        steps
                .map {
                    MappedStep(SDF.parse(it.timestamp), it.stepCount)
                }
                .onEach {
                    barEntryLabels.add(showDay.format(it.timestamp))
                }
                .forEachIndexed { index, step ->
                    barEntry.add(BarEntry(index.toFloat(), step.stepCount.toFloat()))
                }
    }

    fun mapForYear(steps: List<Step>, barEntry: MutableList<BarEntry>, barEntryLabels: MutableList<String>) {
        val showYear = SimpleDateFormat("LLLL", Locale.getDefault())
        steps
                .map { MappedStep(SDF.parse(it.timestamp), it.stepCount) }
                .groupBy { showYear.format(it.timestamp) }
                .mapValues { entry -> entry.value.sumBy { it.stepCount } }
                .toList()
                .onEach {
                    barEntryLabels.add(it.first)
                }
                .forEachIndexed { index, pair ->
                    barEntry.add(BarEntry(index.toFloat(), pair.second.toFloat()))
                }
    }
}

data class MappedStep(val timestamp: Date, val stepCount: Int)