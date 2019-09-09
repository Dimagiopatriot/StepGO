package com.stepgo.android.stepgo.data.entities

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity
data class Step(
        @NotNull
        var timestamp: String,
        var stepCount: Int,
        @PrimaryKey(autoGenerate = true)
        var id: Int? = null)