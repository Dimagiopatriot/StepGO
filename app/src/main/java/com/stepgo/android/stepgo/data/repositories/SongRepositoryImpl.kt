package com.stepgo.android.stepgo.data.repositories

import android.content.Context
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
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
        val mmr = MediaMetadataRetriever()

        if (cursor != null && cursor.moveToFirst()) {
            val id = cursor.getColumnIndex(MediaStore.Audio.Media._ID)
            val title = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
            val artist = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)
            val fileUri = cursor.getColumnIndex(MediaStore.Audio.Media.DATA)

            do {
                mmr.setDataSource(cursor.getString(fileUri))
                val picBytes= mmr.embeddedPicture
                files.add(
                        Song(
                                id = cursor.getLong(id),
                                title = cursor.getString(title),
                                artist = cursor.getString(artist),
                                image = picBytes?.let {
                                    BitmapFactory.decodeByteArray(it, 0, it.size)
                                }
                        )
                )
            } while (cursor.moveToNext())
        }
        cursor?.close()
        return files
    }
}