package com.stepgo.android.stepgo.adapters;

/**
 * Created by User on 04.04.2016.
 */

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.stepgo.android.stepgo.R;
import com.stepgo.android.stepgo.views.MusicActivity;
import com.stepgo.android.stepgo.pojo.Song;

import java.util.ArrayList;

public class SongAdapter extends android.widget.BaseAdapter {

    private ArrayList<Song> songs;
    private LayoutInflater songInf;

    public SongAdapter(Context c, ArrayList<Song> theSongs) {
        songs = theSongs;
        songInf = LayoutInflater.from(c);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return songs.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //map to song layout
        LinearLayout songLay = (LinearLayout) songInf.inflate
                (R.layout.song_list, parent, false);

        //get title and artist views
        TextView songView = (TextView) songLay.findViewById(R.id.song_title);
        TextView artistView = (TextView) songLay.findViewById(R.id.song_artist);
        //get song using position
        Song currSong = songs.get(position);
        //get title and artist strings
        songView.setText(currSong.getTitle());
        artistView.setText(currSong.getArtist());
        //set background color on selected song item
        if (MusicActivity.position == position)
            songLay.setBackgroundColor(Color.parseColor("#e0eacc"));
        //set position as tag
        songLay.setTag(position);
        return songLay;
    }
}
