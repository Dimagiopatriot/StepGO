package com.stepgo.android.stepgo.presentation.views

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import com.github.mikephil.charting.charts.BarChart
import com.stepgo.android.stepgo.R
import com.stepgo.android.stepgo.presentation.viewmodels.StatisticViewModel
import org.koin.android.ext.android.inject

class StatisticFragment : Fragment() {

    private val statisticViewModel by inject<StatisticViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.statistic_main, null)
        val spinner = view.findViewById<Spinner>(R.id.timePeriods)
        spinner.onItemSelectedListener = statisticViewModel

        val chart = view.findViewById<BarChart>(R.id.bar_view)

        statisticViewModel.barData.observe(this, Observer {
            chart.data = it
        })

        return view
    }
}