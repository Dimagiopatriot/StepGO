package com.stepgo.android.stepgo.presentation.views

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.orfium.rx.musicplayer.RxMusicPlayer
import com.orfium.rx.musicplayer.common.Action
import com.orfium.rx.musicplayer.common.PlaybackState
import com.orfium.rx.musicplayer.common.addQueue
import com.orfium.rx.musicplayer.media.Media
import com.stepgo.android.stepgo.R
import com.stepgo.android.stepgo.STORAGE_PERMISSION_REQUEST_CODE
import com.stepgo.android.stepgo.presentation.viewmodels.MusicPlayerViewModel
import com.stepgo.android.stepgo.presentation.views.adapters.SongListAdapter
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.ext.android.inject

class MusicPlayerFragment : Fragment() {

    private val viewModel by inject<MusicPlayerViewModel>()
    private lateinit var errorMessage: LinearLayout
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.music_layout, null)
        val songListAdapter = SongListAdapter()

        progressBar = view.findViewById(R.id.music_load)
        errorMessage = view.findViewById(R.id.error_message)

        val songListRecyclerView = view.findViewById<RecyclerView>(R.id.song_list)
        songListRecyclerView.layoutManager = LinearLayoutManager(activity)
        songListRecyclerView.adapter = songListAdapter

        ActivityCompat.requestPermissions(activity!!,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                STORAGE_PERMISSION_REQUEST_CODE)

        viewModel.mediaListLiveData.observe(this, Observer { songs ->
            songListAdapter.addItems(songs!!)
            progressBar.visibility = View.GONE

            if (songs.isEmpty()) {
                errorMessage.visibility = View.VISIBLE
            } else {
                errorMessage.visibility = View.GONE
            }
        })
        return view
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == STORAGE_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty()
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                viewModel.mediaListLiveData.value = viewModel.getSongs()
            } else {
                progressBar.visibility = View.GONE
                errorMessage.visibility = View.VISIBLE
            }
        }
    }
}