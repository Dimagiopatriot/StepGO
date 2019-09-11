package com.stepgo.android.stepgo.domain.usecases.music

import android.arch.lifecycle.MutableLiveData
import com.stepgo.android.stepgo.data.entities.Song
import com.stepgo.android.stepgo.data.repositories.SongRepositoryImpl
import com.stepgo.android.stepgo.domain.repositories.SongRepository

class GetMusicUseCase(private val songRepository: SongRepository) {

    val songList by lazy {
        val liveDataSongList = MutableLiveData<List<Song>>()
        liveDataSongList.value = songRepository.getSongs()
        liveDataSongList
    }
}