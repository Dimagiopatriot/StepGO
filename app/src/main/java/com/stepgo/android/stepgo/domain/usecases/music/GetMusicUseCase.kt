package com.stepgo.android.stepgo.domain.usecases.music

import com.orfium.rx.musicplayer.media.Media
import com.stepgo.android.stepgo.data.entities.Song
import com.stepgo.android.stepgo.decodeToString
import com.stepgo.android.stepgo.domain.repositories.SongRepository

class GetMusicUseCase(private val songRepository: SongRepository) {

    fun mapToMedia(songs: List<Song>): List<Media> {
        val mediaList = mutableListOf<Media>()
        val mappedSongs = songs.map {
            Media(
                    id = it.id.toInt(),
                    image = it.image?.decodeToString(),
                    title = it.title,
                    artist = it.artist
            )
        }
        mediaList.addAll(mappedSongs)
        return mediaList
    }

    fun getStorageSongs() = songRepository.getSongs()
}