package com.stepgo.android.stepgo.presentation.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.orfium.rx.musicplayer.media.Media
import com.stepgo.android.stepgo.domain.usecases.music.GetMusicUseCase

class MusicPlayerViewModel(private val getMusicUseCase: GetMusicUseCase) : ViewModel() {

    fun getSongs(): LiveData<List<Media>> {
        getMusicUseCase.mapToMedia(getMusicUseCase.songList)
        return getMusicUseCase.mediaList
    }
}