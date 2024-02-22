package com.example.musicplayer;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Music.class}, version = 1)
public abstract class MusicDatabase extends RoomDatabase {
    public abstract MusicDao musicDao();
}
