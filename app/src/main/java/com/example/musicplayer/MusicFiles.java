package com.example.musicplayer;

public class MusicFiles {
    private String path;
    private String titile;
    private String artist;
    private String  album;
    private String duration;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTitile() {
        return titile;
    }

    public void setTitile(String titile) {
        this.titile = titile;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public MusicFiles(String path, String titile, String artist, String album, String duration) {
        this.path = path;
        this.titile = titile;
        this.artist = artist;
        this.album = album;
        this.duration = duration;
    }
}
