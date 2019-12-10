package com.stepgo.android.stepgo.presentation.viewmodels

import android.app.Application
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stepgo.android.stepgo.data.entities.Step
import com.stepgo.android.stepgo.domain.usecases.mainscreen.GetStatusStringIdUseCase
import com.stepgo.android.stepgo.domain.usecases.mainscreen.InitStepsUseCase
import com.stepgo.android.stepgo.domain.usecases.mainscreen.SaveStepsToDbUseCase
import com.stepgo.android.stepgo.domain.usecases.mainscreen.StepSensorChangedUseCase
import com.stepgo.android.stepgo.NORMAL_STEPS
import com.stepgo.android.stepgo.SDF

class StepCountingViewModel(private val application: Application,
                            private val initSteps: InitStepsUseCase,
                            private val sensorChanged: StepSensorChangedUseCase,
                            private val saveSteps: SaveStepsToDbUseCase,
                            private val status: GetStatusStringIdUseCase
) : ViewModel(), SensorEventListener {

    val steps: MutableLiveData<Step> by lazy {
        initSteps.init(SDF)
    }

    fun registerSensorListener() {
        val sensorManager = application.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)
        sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI)
    }

    fun saveStepsToDb() {
        saveSteps.saveStepsToDb(steps, SDF)
    }

    fun getProgress(): Int = (steps.value!!.stepCount * 100 / NORMAL_STEPS)

    fun getPercentProgressString(): String = "${getProgress()} %"

    fun getStatusResourceStringId(): Int = status.getStatusResourceStringId(getProgress())

    override fun onSensorChanged(event: SensorEvent?) {
        val eventValue = event!!.values[0].toInt()
        sensorChanged.onStepChanged(eventValue, steps, SDF)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}