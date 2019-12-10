package com.stepgo.android.stepgo.presentation.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.orfium.rx.musicplayer.RxMusicPlayer
import com.stepgo.android.stepgo.R
import com.stepgo.android.stepgo.presentation.viewmodels.MusicPlayerViewModel
import com.stepgo.android.stepgo.presentation.views.adapters.SongListAdapter
import org.koin.android.ext.android.inject

class MusicPlayerFragment : Fragment() {

    private val viewModel by inject<MusicPlayerViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.music_layout, null)
        val songListAdapter = SongListAdapter()
        RxMusicPlayer.start(activity!!.applicationContext)

        val songListRecyclerView = view.findViewById<RecyclerView>(R.id.song_list)
        songListRecyclerView.layoutManager = LinearLayoutManager(activity)
        songListRecyclerView.adapter = songListAdapter


        viewModel.getSongs().observe(this, Observer {
            songListAdapter.addItems(it!!)
        })

        return view
    }
}