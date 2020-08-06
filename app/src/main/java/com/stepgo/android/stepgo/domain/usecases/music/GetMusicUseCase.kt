package com.stepgo.android.stepgo.domain.usecases.music

import com.stepgo.android.stepgo.data.entities.Song
import com.stepgo.android.stepgo.domain.repositories.SongRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class GetMusicUseCase(private val songRepository: SongRepository) {

    fun getStorageSongs(): List<Song> {
        val result = mutableListOf<Song>()
        runBlocking(Dispatchers.IO) {
            result.addAll(songRepository.getSongs())
        }
        return result
    }
}