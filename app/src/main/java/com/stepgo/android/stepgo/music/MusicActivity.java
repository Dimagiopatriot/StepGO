package com.stepgo.android.stepgo.music;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.MediaController.MediaPlayerControl;

import com.stepgo.android.stepgo.MainActivity;
import com.stepgo.android.stepgo.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MusicActivity extends AppCompatActivity implements MediaPlayerControl {

    public static int position = -1;
    private boolean paused=false, playbackPaused=false;
    private ArrayList<Song> songList;
    private ListView songView;
    public static ListView staticSongView;
    public MusicService musicSrv;
    private Intent playIntent;
    private boolean musicBound=false;
    private MusicController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        songView = (ListView)findViewById(R.id.song_list);
        songList= new ArrayList<>();
        getSongList();
        Collections.sort(songList, new Comparator<Song>() {
            public int compare(Song a, Song b) {
                return a.getTitle().compareTo(b.getTitle());
            }
        });

        SongAdapter songAdt = new SongAdapter(this, songList);
        songView.setAdapter(songAdt);

        staticSongView=songView;

        setController();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.music_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //menu item selected
        switch (item.getItemId()) {
            case R.id.action_end:
                position = -1;
                stopService(playIntent);
                musicSrv=null;
                Intent intent = new Intent(this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                break;
            case R.id.action_back:
                Intent intent1 = new Intent(this,MainActivity.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //connect to the service
    private ServiceConnection musicConnection = new ServiceConnection(){

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.MusicBinder binder = (MusicService.MusicBinder)service;
            //get service
            musicSrv = binder.getService();
            //pass list
            musicSrv.setList(songList);
            musicBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicBound = false;
        }
    };

    public void getSongList() {
        //retrieve song info
        ContentResolver musicResolver = getContentResolver();
        Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);

        if(musicCursor!=null && musicCursor.moveToFirst()){
            //get columns
            int titleColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.TITLE);
            int idColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media._ID);
            int artistColumn = musicCursor.getColumnIndex
                    (android.provider.MediaStore.Audio.Media.ARTIST);
            //add songs to list
            do {
                long thisId = musicCursor.getLong(idColumn);
                String thisTitle = musicCursor.getString(titleColumn);
                String thisArtist = musicCursor.getString(artistColumn);
                songList.add(new Song(thisId, thisTitle, thisArtist));
            }
            while (musicCursor.moveToNext());
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if(playIntent==null){
            playIntent = new Intent(this, MusicService.class);
            bindService(playIntent, musicConnection, Context.BIND_AUTO_CREATE);
            startService(playIntent);
        }
    }

    //song picked on media player
    public void songPicked(View view){
        returnMusicView(0);
        returnMusicView(musicSrv.getSongPosn());
        musicSrv.setSong(Integer.parseInt(view.getTag().toString()));
        updateMusicView(musicSrv.getSongPosn());
        musicSrv.playSong();
        if(playbackPaused){
            controller.setVisibility(View.GONE);
            setController();
            playbackPaused=false;
        }
        controller.show(0);
    }

    @Override
    public void start() {
        musicSrv.go();
    }

    @Override
    public void pause() {
        playbackPaused=true;
        musicSrv.pausePlayer();
    }

    @Override
    public int getDuration() {
        if(musicSrv!=null && musicBound && musicSrv.isPng())
        return musicSrv.getDur();
        else return 0;
    }

    @Override
    public int getCurrentPosition() {
        if(musicSrv!=null && musicBound && musicSrv.isPng())
        return musicSrv.getPosn();
        else return 0;
    }

    @Override
    public void seekTo(int pos) {
        musicSrv.seek(pos);
    }

    @Override
    public boolean isPlaying() {
        if(musicSrv!=null && musicBound)
        return musicSrv.isPng();
        return false;
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getAudioSessionId() {
        return 0;
    }

    private void setController(){
        //set the controller up
        controller = new MusicController(this);
        controller.setPrevNextListeners(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playNext();
            }
        }, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playPrev();
            }
        });
        controller.setMediaPlayer(this);
        controller.setAnchorView(findViewById(R.id.song_list));
        controller.setEnabled(true);
    }

    //play next
    private void playNext(){
        returnMusicView(musicSrv.getSongPosn());
        musicSrv.playNext();

        updateMusicView(musicSrv.getSongPosn());
        if(playbackPaused) {
            controller.setVisibility(View.GONE);
            setController();
            playbackPaused=false;
        }
        controller.show(0);
    }

    //play previous
    private void playPrev(){
        returnMusicView(musicSrv.getSongPosn());
        musicSrv.playPrev();
        updateMusicView(musicSrv.getSongPosn());
        if(playbackPaused){
            controller.setVisibility(View.GONE);
            setController();
            playbackPaused=false;
        }
        controller.show(0);
    }

    @Override
    protected void onPause(){
        super.onPause();
        paused=true;
    }

    @Override
    protected void onResume(){
        if(MainActivity.isAppClosed){
            stopService(playIntent);
            musicSrv=null;
            finish();
        }
        super.onResume();
        if(paused){
            paused=false;
        }
    }

    @Override
    protected void onStop() {
        controller.hide();
        super.onStop();
    }

    public static void updateMusicView(int index){
        View songItem = staticSongView.getChildAt(index -
                staticSongView.getFirstVisiblePosition());
        position = index;
        if(songItem == null)
            return;
        songItem.setBackgroundColor(Color.parseColor("#e0eacc"));
    }

    public static void returnMusicView(int index){
        View songItem = staticSongView.getChildAt(index -
                staticSongView.getFirstVisiblePosition());

        if(songItem == null)
            return;
        songItem.setBackgroundColor(Color.parseColor("#FFFFFF"));
    }

    @Override
    public void onBackPressed(){ }
}
