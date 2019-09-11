package com.stepgo.android.stepgo.presentation.views

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.stepgo.android.stepgo.R
import com.stepgo.android.stepgo.presentation.viewmodels.MusicPlayerViewModel
import com.stepgo.android.stepgo.presentation.views.adapters.SongListAdapter
import org.koin.android.ext.android.inject

class MusicPlayerFragment : Fragment() {

    private val viewModel by inject<MusicPlayerViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.music_layout, null)
        val songListAdapter = SongListAdapter()

        val songListRecyclerView = view.findViewById<RecyclerView>(R.id.song_list)
        songListRecyclerView.layoutManager = LinearLayoutManager(activity)
        songListRecyclerView.adapter = songListAdapter

        viewModel.getSongs().observe(this, Observer {
            songListAdapter.addItems(it!!)
        })

        return view
    }
}