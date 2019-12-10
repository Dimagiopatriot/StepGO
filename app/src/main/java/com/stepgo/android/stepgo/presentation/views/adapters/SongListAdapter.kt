package com.stepgo.android.stepgo.presentation.views.adapters

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
import com.stepgo.android.stepgo.toBitmap

class SongListAdapter : RecyclerView.Adapter<SongViewHolder>() {

    private val songList: MutableList<Media> = arrayListOf()

    fun addItems(items: List<Media>) {
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

    fun bind(song: Media) {
        songArtist.text = song.artist
        songTitle.text = song.title
        song.image?.let {
            songPic.setImageBitmap(it.toBitmap())
        } ?: songPic.setImageResource(R.drawable.ic_image_placeholder)
        songState.setImageResource(if (song.isPlaying()) android.R.drawable.ic_media_pause else android.R.drawable.ic_media_play)
        songLayout.setOnClickListener { song.playStop() }
    }
}