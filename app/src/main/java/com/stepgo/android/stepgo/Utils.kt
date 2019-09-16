package com.stepgo.android.stepgo

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*

const val NORMAL_STEPS = 10000
val SDF = SimpleDateFormat("yyyy-MM-dd", Locale.UK)

fun String.toBitmap(): Bitmap {
    val strByteArray = this.toByteArray()
    return BitmapFactory.decodeByteArray(this.toByteArray(), 0, strByteArray.size)
}

fun ByteArray.toBitmap(): Bitmap {
    return BitmapFactory.decodeByteArray(this, 0, this.size)
}

fun Bitmap.decodeToString(): String {
    val baos = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.PNG, 100, baos)
    return baos.toByteArray().toString(Charsets.UTF_8)
}