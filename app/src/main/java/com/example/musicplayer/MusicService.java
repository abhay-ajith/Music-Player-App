package com.example.musicplayer;

import static com.example.musicplayer.PlayerActivity.listsongs;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MusicService extends Service implements MediaPlayer.OnCompletionListener{

    IBinder mBinder = new MyBinder();
    MediaPlayer mediaPlayer;
    ArrayList<MusicFiles> musicFiles = new ArrayList<>();
    Uri uri;
    int position = -1;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("Abhay","Method");
        return mBinder;
    }

    void OnCompleted(){
        mediaPlayer.setOnCompletionListener(this);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    public class MyBinder extends Binder {

        MusicService getService(){
            return MusicService.this;
        }

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        int myPosition = intent.getIntExtra("servicePosition", -1);
        if (myPosition != -1) {
            playMedia(myPosition); // Pass myPosition instead of position
        } else {
            // Handle the case where position is -1
        }
        return START_STICKY;
    }



    private void playMedia(int StartPosition) {
        musicFiles = listsongs;
        position = StartPosition;
        if(mediaPlayer!=null){
            mediaPlayer.stop();
            mediaPlayer.release();
            if ((musicFiles!=null)){
                createMediaPlayer(position);
                mediaPlayer.start();
            }
        }
        else {
            createMediaPlayer(position);
            mediaPlayer.start();
        }

    }

    void start(){
        mediaPlayer.start();
    }
    boolean isPlaying(){
        if (mediaPlayer != null)
            return mediaPlayer.isPlaying();
        return false;
    }
    void stop(){
        mediaPlayer.stop();
    }
    void release() {
        if (mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer.release();
        }
    }
    int getDuration(){
        if (mediaPlayer != null)
            return mediaPlayer.getDuration();
        return 0;
    }
    void seekTo(int position){
        if (mediaPlayer != null)
            mediaPlayer.seekTo(position);
    }

    int getCurrentPosition() {
        if (mediaPlayer != null)
            return mediaPlayer.getCurrentPosition();
        return 0;
    }
    void createMediaPlayer(int position){
        uri = Uri.parse(musicFiles.get(position).getPath());
        mediaPlayer = MediaPlayer.create(getBaseContext(), uri);
    }
    void pause(){
        mediaPlayer.pause();
    }
    void onCompleted() {
        if (mediaPlayer != null)
            mediaPlayer.setOnCompletionListener(this);
    }
}