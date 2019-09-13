package com.stepgo.android.stepgo.data.entities

import android.graphics.Bitmap

data class Song(val id: Long,
                val title: String,
                val artist: String,
                val image: Bitmap?
)