package com.example.musicplayer;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.musicplayer.Music;

import java.util.List;

@Dao
public interface MusicDao {
    @Insert
    void insert(Music music);

    @Query("SELECT * FROM music")
    List<Music> getAllMusic();
}
