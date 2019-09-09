package com.stepgo.android.stepgo.presentation.viewmodels

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.stepgo.android.stepgo.data.entities.Step
import com.stepgo.android.stepgo.domain.usecases.mainscreen.GetStatusStringIdUseCase
import com.stepgo.android.stepgo.domain.usecases.mainscreen.InitStepsUseCase
import com.stepgo.android.stepgo.domain.usecases.mainscreen.SaveStepsToDbUseCase
import com.stepgo.android.stepgo.domain.usecases.mainscreen.StepSensorChangedUseCase
import java.text.SimpleDateFormat
import java.util.*

class StepCountingViewModel(private val application: Application,
                            private val initSteps: InitStepsUseCase,
                            private val sensorChanged: StepSensorChangedUseCase,
                            private val saveSteps: SaveStepsToDbUseCase,
                            private val status: GetStatusStringIdUseCase) : ViewModel(), SensorEventListener {
    private val normalSteps = 10000
    private val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    val steps: MutableLiveData<Step> by lazy {
        initSteps.init(sdf)
    }

    fun registerSensorListener() {
        val sensorManager = application.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)
        sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI)
    }

    fun saveStepsToDb() {
        saveSteps.saveStepsToDb(steps, sdf)
    }

    fun getProgress(): Int = (steps.value!!.stepCount * 100 / normalSteps)

    fun getPercentProgressString(): String = "${getProgress()} %"

    fun getStatusResourceStringId(): Int = status.getStatusResourceStringId(getProgress())

    override fun onSensorChanged(event: SensorEvent?) {
        val eventValue = event!!.values[0].toInt()
        sensorChanged.onStepChanged(eventValue, steps, sdf)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}