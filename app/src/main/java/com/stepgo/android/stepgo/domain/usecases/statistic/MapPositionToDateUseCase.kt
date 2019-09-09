package com.stepgo.android.stepgo.domain.usecases.statistic

import java.util.*

class MapPositionToDateUseCase {

    fun mapPositionToDate(position: Int): Date {
        val calendar = Calendar.getInstance()
        when (position) {
            //for a week
            0 -> calendar.add(Calendar.DAY_OF_YEAR, -7)
            //for a month
            1 -> calendar.add(Calendar.DAY_OF_YEAR, -30)
            //for a year
            2 -> calendar.add(Calendar.DAY_OF_YEAR, -365)
        }
        return calendar.time
    }
}