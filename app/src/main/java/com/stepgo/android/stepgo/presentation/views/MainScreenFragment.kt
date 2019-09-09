package com.stepgo.android.stepgo.presentation.views

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import com.stepgo.android.stepgo.R
import com.stepgo.android.stepgo.presentation.viewmodels.StepCountingViewModel
import org.koin.android.ext.android.inject

class MainScreenFragment : Fragment() {

    private val viewModel by inject<StepCountingViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.content_main, null)

        val stepText = view.findViewById<TextView>(R.id.stepCounter)
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)
        val percentProgress = view.findViewById<TextView>(R.id.percentProgress)
        val status = view.findViewById<TextView>(R.id.status)

        //viewModel = ViewModelProviders.of(this)[StepCountingViewModel::class.java]
        viewModel.registerSensorListener()
        viewModel.steps.observe(this, Observer {
            stepText.text = "${it?.stepCount} steps"
            percentProgress.text = viewModel.getPercentProgressString()
            progressBar.progress = viewModel.getProgress()
            status.text = activity!!.resources.getString(viewModel.getStatusResourceStringId())
        })
        return view
    }

    override fun onStop() {
        viewModel.saveStepsToDb()
        super.onStop()
    }
}