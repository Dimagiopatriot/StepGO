package com.stepgo.android.stepgo.data.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Step(
        val timestamp: String,
        val stepCount: Int,
        @PrimaryKey(autoGenerate = true) val id: Int = 0)