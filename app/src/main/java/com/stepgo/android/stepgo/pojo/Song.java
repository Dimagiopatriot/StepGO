package com.stepgo.android.stepgo.pojo;

/**
 * Created by User on 04.04.2016.
 * One song represent
 */
public class Song {
    private long id;
    private String title;
    private String artist;

    public Song(long songID, String songTitle, String songArtist) {
        id = songID;
        title = songTitle;
        artist = songArtist;
    }

    /*
    getters
     */
    public long getID() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }
}
