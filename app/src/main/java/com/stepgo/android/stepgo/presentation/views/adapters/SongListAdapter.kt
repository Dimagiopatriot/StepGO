package com.stepgo.android.stepgo.presentation.views.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.orfium.rx.musicplayer.common.isPlaying
import com.orfium.rx.musicplayer.common.playStop
import com.orfium.rx.musicplayer.media.Media
import com.stepgo.android.stepgo.R
import com.stepgo.android.stepgo.data.entities.Song
import com.stepgo.android.stepgo.toBitmap

class SongListAdapter : RecyclerView.Adapter<SongViewHolder>() {

    private val songList: MutableList<Song> = arrayListOf()

    fun addItems(items: List<Song>) {
        songList.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): SongViewHolder {
        val row = LayoutInflater.from(parent.context).inflate(R.layout.song_list_item, parent, false)
        return SongViewHolder(row)
    }

    override fun getItemCount(): Int = songList.size

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        holder.bind(songList[position])
    }
}

class SongViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val songPic: ImageView = itemView.findViewById(R.id.song_image)
    private val songArtist: TextView = itemView.findViewById(R.id.song_artist)
    private val songTitle: TextView = itemView.findViewById(R.id.song_title)
    private val songLayout: LinearLayout = itemView.findViewById(R.id.song_layout)
    private val songState: ImageView = itemView.findViewById(R.id.song_state)

    fun bind(song: Song) {
        songArtist.text = song.artist
        songTitle.text = song.title
        song.image?.let {
            songPic.setImageBitmap(it)
        } ?: songPic.setImageResource(R.drawable.ic_image_placeholder)
        updateItem(song)
        songLayout.setOnClickListener { song.playStop() }
    }

    fun updateItem(song: Song) {
        if (song.isPlaying){
            songState.setImageResource(android.R.drawable.ic_media_pause)
            songLayout.setBackgroundColor(Color.parseColor("#CCFF90"))
        }  else {
            songState.setImageResource(android.R.drawable.ic_media_play)
            songLayout.setBackgroundColor(Color.WHITE)
        }
    }
}