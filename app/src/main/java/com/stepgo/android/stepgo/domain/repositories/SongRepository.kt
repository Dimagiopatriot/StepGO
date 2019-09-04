package com.stepgo.android.stepgo.domain.repositories

import com.stepgo.android.stepgo.data.entities.Song

interface SongRepository {
    fun getSongs(): List<Song>
}