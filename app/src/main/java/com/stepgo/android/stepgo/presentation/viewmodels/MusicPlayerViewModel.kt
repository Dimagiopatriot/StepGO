package com.stepgo.android.stepgo.presentation.viewmodels

import android.arch.lifecycle.ViewModel
import com.stepgo.android.stepgo.domain.usecases.music.GetMusicUseCase

class MusicPlayerViewModel(private val getMusicUseCase: GetMusicUseCase): ViewModel() {

    fun getSongs() = getMusicUseCase.songList
}