package com.stepgo.android.stepgo.presentation.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.stepgo.android.stepgo.data.entities.Song
import com.stepgo.android.stepgo.domain.usecases.music.GetMusicUseCase

class MusicPlayerViewModel(private val getMusicUseCase: GetMusicUseCase) : ViewModel() {

    val mediaListLiveData = MutableLiveData<List<Song>>()

    fun getSongs(): List<Song> = getMusicUseCase.getStorageSongs()
}