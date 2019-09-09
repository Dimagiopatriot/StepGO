package com.stepgo.android.stepgo.presentation.views

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.charts.BarChart
import com.stepgo.android.stepgo.R

class StatisticFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.statistic_main, null)

        val chart = view.findViewById<BarChart>(R.id.bar_view)

        return view
    }
}