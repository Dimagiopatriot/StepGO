package com.stepgo.android.stepgo.data.repositories

import android.content.Context
import android.media.MediaMetadataRetriever
import android.provider.MediaStore
import android.util.Log
import com.stepgo.android.stepgo.data.entities.Song
import com.stepgo.android.stepgo.domain.repositories.SongRepository
import com.stepgo.android.stepgo.toBitmap

class SongRepositoryImpl(private val appContext: Context) : SongRepository {

    override fun getSongs(): List<Song> {
        val files = mutableListOf<Song>()

        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val selection = MediaStore.Audio.Media.IS_MUSIC + "!= 0"

        return try {
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
                val duration = cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)

                do {
                    mmr.setDataSource(cursor.getString(fileUri))
                    val picBytes= mmr.embeddedPicture
                    files.add(
                            Song(
                                    id = cursor.getLong(id),
                                    title = cursor.getString(title),
                                    artist = cursor.getString(artist),
                                    uri = cursor.getString(fileUri),
                                    duration = cursor.getInt(duration),
                                    image = picBytes?.toBitmap()
                            )
                    )
                } while (cursor.moveToNext())
            }
            cursor?.close()
            files
        } catch (e: SecurityException) {
            Log.d("SongRepositoryImpl", "External storage permission denied!")
            files
        }
    }
}