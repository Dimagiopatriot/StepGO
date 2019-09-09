package com.stepgo.android.stepgo.domain.usecases.mainscreen

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import com.stepgo.android.stepgo.data.db.StepDao
import com.stepgo.android.stepgo.data.entities.Step
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.*

class SaveStepsToDbUseCase(private val stepDao: StepDao) {

    private val logTag = "SaveSteps"

    fun saveStepsToDb(steps: MutableLiveData<Step>, sdf: SimpleDateFormat) {
        runBlocking(Dispatchers.Default) {
            launch {
                val stepsFromDB = stepDao.findStepStatistic(sdf.format(Date()))
                if (stepsFromDB != null) {
                    stepDao.updateStepStatistic(stepsFromDB.apply { stepCount = steps.value!!.stepCount })
                    Log.d(logTag, "Steps successfully updated!")
                } else {
                    val uid = stepDao.insertStepStatistic(steps.value?.let { it }
                            ?: Step(sdf.format(Date()), 0))
                    Log.d(logTag, "Step successfully inserted! Step id in db - $uid")
                }
            }
        }
    }
}