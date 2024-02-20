package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class PlayerActivity extends AppCompatActivity {

    TextView song_name, artist_name, duration_Played, duration_Total;
    ImageView cover_art, nextBtn, prevBtn;
    FloatingActionButton playPauseBtn;
    SeekBar seekBar;
    static ArrayList<MusicFiles> listsongs = new ArrayList<>();
    int position = -1;
    static Uri uri;
    static MediaPlayer mediaPlayer;
    private Handler handler;
    private Thread playThread, prevThread, nextThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        // Initialize the handler
        handler = new Handler();
        initViews();
        getIntentMethod();

        // Set up the seek bar change listener
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mediaPlayer != null && fromUser) {
                    mediaPlayer.seekTo(progress * 1000);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        // Update seek bar and duration text every second
        updateSeekBar();
    }

    // Method to update seek bar and duration text
    private void updateSeekBar() {
        PlayerActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
                    seekBar.setProgress(mCurrentPosition);
                    duration_Played.setText(formattedTime(mCurrentPosition));
                }
                // Schedule the runnable to run again after 1 second
                handler.postDelayed(this, 1000);
            }
        });
    }

    private void playPauseBtnClicked() {
        if (mediaPlayer.isPlaying()) {
            playPauseBtn.setImageResource(R.drawable.play);
            mediaPlayer.pause();
        } else {
            playPauseBtn.setImageResource(R.drawable.pause);
            mediaPlayer.start();
        }
    }

    // Method to format time in minutes and seconds
    private String formattedTime(int mCurrentPosition) {
        String totalOut = "";
        String totalNew = "";
        String seconds = String.valueOf(mCurrentPosition % 60);
        String minutes = String.valueOf(mCurrentPosition / 60);
        totalOut = minutes + ":" + seconds;
        totalNew = minutes + ":" + "0" + seconds;
        if (seconds.length() == 1) {
            return totalNew;
        } else {
            return totalOut;
        }
    }

    // Method to retrieve intent data
    private void getIntentMethod() {
        position = getIntent().getIntExtra("position", -1);
        listsongs = MainActivity.musicFiles;
        if (listsongs != null && position != -1) {
            playPauseBtn.setImageResource(R.drawable.pause);
            uri = Uri.parse(listsongs.get(position).getPath());
        }
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            mediaPlayer.start();
        } else {
            mediaPlayer = MediaPlayer.create(getApplicationContext(), uri);
            mediaPlayer.start();
        }
        seekBar.setMax(mediaPlayer.getDuration() / 1000);
        metaData(uri);
    }

    // Method to initialize views
    private void initViews() {
        song_name = findViewById(R.id.song_name);
        artist_name = findViewById(R.id.song_artist);
        duration_Played = findViewById(R.id.durationPlayed);
        duration_Total = findViewById(R.id.durationTotal);
        cover_art = findViewById(R.id.cover_art);
        nextBtn = findViewById(R.id.id_next);
        prevBtn = findViewById(R.id.id_prev);
        playPauseBtn = findViewById(R.id.play_pause);
        seekBar = findViewById(R.id.seekbar);

        playPauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playPauseBtnClicked();
            }
        });
    }

    private  void metaData( Uri uri){
        MediaMetadataRetriever retriever =  new MediaMetadataRetriever();
        retriever.setDataSource(uri.toString());
        int durationTotal = Integer.parseInt(listsongs.get(position).getDuration()) /1000;
        duration_Total.setText(formattedTime(durationTotal));
        byte[] art = retriever.getEmbeddedPicture();
        if(art!=null ){
            Glide.with(this)
                    .asBitmap()
                    .load(art)
                    .into(cover_art);
        }
        else{
            Glide.with(this)
                    .asBitmap()
                    .load(R.drawable.card)
                    .into(cover_art);
        }
    }
}
