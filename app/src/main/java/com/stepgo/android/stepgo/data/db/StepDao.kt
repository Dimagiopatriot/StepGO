package com.stepgo.android.stepgo.data.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import com.stepgo.android.stepgo.data.entities.Step

@Dao
interface StepDao {

    @Query("SELECT * from step WHERE timestamp LIKE :timestamp LIMIT 1")
    fun findStepStatistic(timestamp: String): Step?

    @Update
    fun updateStepStatistic(step: Step)

    @Insert
    fun insertStepStatistic(step: Step): Long
}