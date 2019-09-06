package com.stepgo.android.stepgo.domain.viewmodels

import android.app.Application
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.stepgo.android.stepgo.R
import com.stepgo.android.stepgo.data.db.StepDao
import com.stepgo.android.stepgo.data.entities.Step
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.*

class StepCountingViewModel(private val application: Application,
                            private val stepDao: StepDao) : ViewModel(), SensorEventListener {
    private val normalSteps = 10000
    private val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    val steps: MutableLiveData<Step> by lazy {
        var stepsFromDB: Step? = null
        runBlocking(Dispatchers.Default) {
            launch {
                stepsFromDB = stepDao.findStepStatistic(sdf.format(Date()))
            }
        }
        if (stepsFromDB != null) {
            val output = MutableLiveData<Step>()
            output.value = stepsFromDB
            output
        } else MutableLiveData()
    }

    fun registerSensorListener() {
        val sensorManager = application.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)
        sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI)
    }

    fun saveStepsToDb() {
        runBlocking(Dispatchers.Default) {
            launch {
                val stepsFromDB = stepDao.findStepStatistic(sdf.format(Date()))
                stepsFromDB?.let {
                    stepDao.updateStepStatistic(steps.value!!)
                } ?: stepDao.insertStepStatistic(steps.value?.let { it }
                        ?: Step(sdf.format(Date()), 0))
            }
        }
    }

    fun getProgress(): Int = (steps.value!!.stepCount * 100 / normalSteps)

    fun getPercentProgressString(): String = "${getProgress()} %"

    fun getStatusResourceStringId(): Int =
            when (getProgress()) {
                in 0..25 -> R.string.work_harder
                in 26..50 -> R.string.well_done
                in 51..75 -> R.string.such_sportsman
                in 76..100 -> R.string.full_of_health
                else -> R.string.monster
            }

    override fun onSensorChanged(event: SensorEvent?) {
        val eventValue = event!!.values[0].toInt()
        if (steps.value != null) {
            val stepValue = steps.value
            steps.value = Step(stepValue!!.timestamp, stepValue.stepCount + eventValue)
        } else {
            steps.value = Step(sdf.format(Date()), eventValue)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}