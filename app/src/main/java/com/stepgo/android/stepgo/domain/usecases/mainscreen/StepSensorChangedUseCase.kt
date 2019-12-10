package com.stepgo.android.stepgo.domain.usecases.mainscreen

import androidx.lifecycle.MutableLiveData
import com.stepgo.android.stepgo.data.entities.Step
import java.text.SimpleDateFormat
import java.util.*

class StepSensorChangedUseCase {

    fun onStepChanged(eventValue: Int, steps: MutableLiveData<Step>, sdf: SimpleDateFormat) {
        if (steps.value != null) {
            val stepValue = steps.value
            steps.value = Step(stepValue!!.timestamp, stepValue.stepCount + eventValue)
        } else {
            steps.value = Step(sdf.format(Date()), eventValue)
        }
    }
}