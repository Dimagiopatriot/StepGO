package com.stepgo.android.stepgo.domain.usecases.mainscreen

import com.stepgo.android.stepgo.R

class GetStatusStringIdUseCase {

    fun getStatusResourceStringId(progress: Int) : Int =
            when (progress) {
                in 0..25 -> R.string.work_harder
                in 26..50 -> R.string.well_done
                in 51..75 -> R.string.such_sportsman
                in 76..100 -> R.string.full_of_health
                else -> R.string.monster
            }
}