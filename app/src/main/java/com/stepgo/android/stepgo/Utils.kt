package com.stepgo.android.stepgo

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*

const val NORMAL_STEPS = 10000
val SDF = SimpleDateFormat("yyyy-MM-dd", Locale.UK)

const val STORAGE_PERMISSION_REQUEST_CODE = 1

fun String.toBitmap(): Bitmap {
    val strByteArray = Base64.decode(this, Base64.DEFAULT)
    val res = BitmapFactory.decodeByteArray(strByteArray, 0, strByteArray.size)
    return res
}

fun ByteArray.toBitmap(): Bitmap {
    return BitmapFactory.decodeByteArray(this, 0, this.size)
}

fun Bitmap.decodeToString(): String {
    val baos = ByteArrayOutputStream()
    this.compress(Bitmap.CompressFormat.PNG, 100, baos)
    return Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT)
}