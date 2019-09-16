package com.stepgo.android.stepgo.domain.usecases.statistic

import android.content.Context
import android.support.v4.content.ContextCompat
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.stepgo.android.stepgo.R
import com.stepgo.android.stepgo.data.entities.Step
import com.stepgo.android.stepgo.domain.usecases.getDayDifference
import com.stepgo.android.stepgo.SDF
import java.text.SimpleDateFormat
import java.util.*

class MapStepsUseCase(private val context: Context) {

    fun getMappedBarData(steps: List<Step>, startDate: Date): BarData {
        val barEntry = mutableListOf<BarEntry>()

        when (getDayDifference(startDate, Date())) {
            in 0..31 -> mapForWeek(steps, barEntry)
            in 32..365 -> mapForYear(steps, barEntry)
        }
        val barDataSet = BarDataSet(barEntry, "Steps")
        barDataSet.setColors(ContextCompat.getColor(context, R.color.colorPrimary))
        return BarData(barDataSet)
    }

    fun mapForWeek(steps: List<Step>, barEntry: MutableList<BarEntry>) {
        val showDay = SimpleDateFormat("EEE, MM/dd", Locale.getDefault())
        steps
                .map {
                    MappedStep(SDF.parse(it.timestamp), it.stepCount)
                }
                .forEachIndexed { index, step ->
                    barEntry.add(BarEntry(index.toFloat(), step.stepCount.toFloat(), showDay.format(step.timestamp)))
                }
    }

    fun mapForYear(steps: List<Step>, barEntry: MutableList<BarEntry>) {
        val showYear = SimpleDateFormat("LLLL", Locale.getDefault())
        steps
                .map { MappedStep(SDF.parse(it.timestamp), it.stepCount) }
                .groupBy { showYear.format(it.timestamp) }
                .mapValues { entry -> entry.value.sumBy { it.stepCount } }
                .toList()
                .forEachIndexed { index, pair ->
                    barEntry.add(BarEntry(index.toFloat(), pair.second.toFloat(), pair.first))
                }
    }
}

data class MappedStep(val timestamp: Date, val stepCount: Int)