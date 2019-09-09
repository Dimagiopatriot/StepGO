package com.stepgo.android.stepgo.domain.usecases.mainscreen

import android.arch.lifecycle.MutableLiveData
import com.stepgo.android.stepgo.data.db.StepDao
import com.stepgo.android.stepgo.data.entities.Step
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.*

class InitStepsUseCase(private val stepDao: StepDao) {

    fun init(sdf: SimpleDateFormat): MutableLiveData<Step> {
        var stepsFromDB: Step? = null
        runBlocking(Dispatchers.Default) {
            launch {
                stepsFromDB = stepDao.findStep(sdf.format(Date()))
            }
        }
        return if (stepsFromDB != null) {
            val output = MutableLiveData<Step>()
            output.value = stepsFromDB
            output
        } else {
            MutableLiveData()
        }
    }
}