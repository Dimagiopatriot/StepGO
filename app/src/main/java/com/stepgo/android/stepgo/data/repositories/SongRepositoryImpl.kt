package com.stepgo.android.stepgo.data.repositories

import android.content.Context
import android.provider.MediaStore
import com.stepgo.android.stepgo.data.entities.Song
import com.stepgo.android.stepgo.domain.repositories.SongRepository

class SongRepositoryImpl(private val appContext: Context) : SongRepository {

    override fun getSongs(): List<Song> {
        val files = mutableListOf<Song>()

        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0"

        val cursor = appContext.contentResolver.query(
                uri,
                null,
                selection,
                null,
                null
        )

        if (cursor != null && cursor.moveToFirst()) {
            val id = cursor.getColumnIndex(MediaStore.Audio.Media._ID)
            val title = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
            val artist = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)
            val imageUri = cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART)

            do {
                files.add(
                        Song(
                                id = cursor.getLong(id),
                                title = cursor.getString(title),
                                artist = cursor.getString(artist),
                                imageUri = if (imageUri != -1) {
                                    cursor.getString(artist)
                                } else ""
                        )
                )
            } while (cursor.moveToNext())
        }
        cursor?.close()
        return files
    }
}