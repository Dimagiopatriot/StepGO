package com.stepgo.android.stepgo.presentation.views.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.stepgo.android.stepgo.R
import com.stepgo.android.stepgo.data.entities.Song

class SongListAdapter: RecyclerView.Adapter<SongViewHolder>() {

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
        val currentSong = songList[position]
        holder.songArtist.text = currentSong.artist
        holder.songTitle.text = currentSong.title
        Picasso.get()
                .load(currentSong.imageUri)
                .placeholder(R.drawable.ic_image_placeholder)
                .error(R.drawable.ic_image_placeholder)
                .into(holder.songPic)
    }
}

class SongViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    val songPic: ImageView = itemView.findViewById(R.id.song_image)
    val songArtist: TextView = itemView.findViewById(R.id.song_artist)
    val songTitle: TextView = itemView.findViewById(R.id.song_title)

    init {
        itemView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}