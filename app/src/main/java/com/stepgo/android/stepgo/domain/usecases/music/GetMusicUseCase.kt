package com.stepgo.android.stepgo.domain.usecases.music

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.orfium.rx.musicplayer.media.Media
import com.stepgo.android.stepgo.data.entities.Song
import com.stepgo.android.stepgo.decodeToString
import com.stepgo.android.stepgo.domain.repositories.SongRepository

class GetMusicUseCase(private val songRepository: SongRepository) {

    val songList by lazy {
        val liveDataSongList = MutableLiveData<List<Song>>()
        liveDataSongList.value = songRepository.getSongs()
        liveDataSongList
    }

    val mediaList = MutableLiveData<List<Media>>()

    fun mapToMedia(songs: LiveData<List<Song>>) {
        mediaList.value = songs.value?.map {
            Media(
                    id = it.id.toInt(),
                    image = it.image?.decodeToString(),
                    title = it.title,
                    artist = it.artist
            )
        }
    }
}