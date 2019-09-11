package com.stepgo.android.stepgo.presentation.views.formatters

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.formatter.ValueFormatter

class StepFormatter: ValueFormatter() {

    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
        return if (axis is YAxis) {
             "${value.toInt()} steps"
        } else "${value.toInt()}"
    }
}