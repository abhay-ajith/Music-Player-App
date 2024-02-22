package com.example.musicplayer;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "music")
public class Music {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String title;
    private String artist;
    private long duration; // duration in milliseconds

    public Music(String title, String artist, long duration) {
        this.title = title;
        this.artist = artist;
        this.duration = duration;
    }

    // Getters and setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }
}
