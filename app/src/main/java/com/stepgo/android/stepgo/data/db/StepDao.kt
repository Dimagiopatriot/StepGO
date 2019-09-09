package com.stepgo.android.stepgo.data.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import com.stepgo.android.stepgo.data.entities.Step

@Dao
interface StepDao {

    @Query("SELECT * from step WHERE DATE(timestamp) LIKE DATE(:timestamp) LIMIT 1")
    fun findStep(timestamp: String): Step?

    @Query("SELECT * FROM step WHERE DATE(timestamp) > DATE(:from) AND DATE(timestamp) <= DATE(:to)")
    fun findStepStatistic(from: String, to: String): List<Step>

    @Update
    fun updateStepStatistic(step: Step)

    @Insert
    fun insertStepStatistic(step: Step): Long
}