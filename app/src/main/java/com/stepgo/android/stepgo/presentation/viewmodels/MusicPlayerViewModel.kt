package com.stepgo.android.stepgo.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.orfium.rx.musicplayer.media.Media
import com.stepgo.android.stepgo.domain.usecases.music.GetMusicUseCase

class MusicPlayerViewModel(private val getMusicUseCase: GetMusicUseCase) : ViewModel() {

    val mediaListLiveData = MutableLiveData<List<Media>>()

    fun getSongs(): List<Media> = getMusicUseCase.mapToMedia(getMusicUseCase.getStorageSongs())
}