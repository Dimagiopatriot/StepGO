package com.stepgo.android.stepgo.domain.usecases

import java.util.*

fun getDayDifference(startDate: Date, endDate: Date): Int {
    val difference = endDate.time - startDate.time
    val secondsDiff = difference / 1000
    val minuteDiff = secondsDiff / 60
    val hourDiff = minuteDiff / 60
    val dayDiff = hourDiff / 24
    return dayDiff.toInt()
}