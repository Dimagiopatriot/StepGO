package com.stepgo.android.stepgo.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity
data class Step(
        @NotNull
        var timestamp: String,
        var stepCount: Int,
        @PrimaryKey(autoGenerate = true)
        var id: Int? = null)