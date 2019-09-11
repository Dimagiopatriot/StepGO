package com.stepgo.android.stepgo.presentation.views.formatters

import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.formatter.ValueFormatter
import java.lang.IndexOutOfBoundsException


class DateAxisValueFormatter(private val chart: BarChart) : ValueFormatter() {

    override fun getFormattedValue(value: Float): String {
        val dataSet = chart.data.dataSets[0]
        return try {
            dataSet.getEntryForIndex(value.toInt()).data.toString()
        } catch (e: IndexOutOfBoundsException) {
            "$value"
        }
    }
}