package com.stepgo.android.stepgo.presentation.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.stepgo.android.stepgo.R
import com.stepgo.android.stepgo.presentation.viewmodels.StatisticViewModel
import com.stepgo.android.stepgo.presentation.views.formatters.DateAxisValueFormatter
import com.stepgo.android.stepgo.presentation.views.formatters.StepFormatter
import org.koin.android.ext.android.inject

class StatisticFragment : Fragment() {

    private val statisticViewModel by inject<StatisticViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.statistic_main, null)
        val spinner = view.findViewById<Spinner>(R.id.timePeriods)
        spinner.onItemSelectedListener = statisticViewModel

        val chart = view.findViewById<BarChart>(R.id.bar_view)

        chart.setDrawBarShadow(false)
        chart.setDrawValueAboveBar(true)
        chart.description.isEnabled = false
        chart.setDrawGridBackground(false)


        xAxisSetup(chart)
        yAxisSetup(chart)

        statisticViewModel.barData.observe(this, Observer {
            chart.data = it
            chart.invalidate()
        })

        return view
    }

    private fun xAxisSetup(chart: BarChart) {
        val xAxisFormatter = DateAxisValueFormatter(chart)
        val xAxis = chart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        xAxis.granularity = 1f
        xAxis.labelCount = 7
        xAxis.valueFormatter = xAxisFormatter
    }

    private fun yAxisSetup(chart: BarChart) {
        val leftAxis = chart.axisLeft
        leftAxis.setLabelCount(8, false)
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
        leftAxis.spaceTop = 15f
        leftAxis.axisMinimum = 0f
        leftAxis.valueFormatter = StepFormatter()

        val rightAxis = chart.axisRight
        rightAxis.isEnabled = false
    }
}