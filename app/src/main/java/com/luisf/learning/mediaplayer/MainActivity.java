package com.luisf.learning.mediaplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private SeekBar seekVolume;
    private AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.music_rock);
        this.InitializeSeekVolume();

        seekVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        this.PlaySound();
    }

    @Override
    protected void onStop() {
        super.onStop();

        this.PauseSound();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (this.IsPlayingSound()) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public void PlaySound_Interface(View view) {
        this.PlaySound();
    }

    public void PauseSound_Interface(View view) {
        this.PauseSound();
    }

    public void StopSound_Interface(View view) {
        if (this.IsPlayingSound()) {
            mediaPlayer.stop();

            mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.music_rock);
        }
    }

    public void PlaySound() {
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    public void PauseSound() {
        if (this.IsPlayingSound())
            mediaPlayer.pause();
    }

    private void InitializeSeekVolume() {
        seekVolume = findViewById(R.id.seekVolume);

        // Configuration object class to access of the audio system manager
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        // get max volume sound of music...
        int max_volume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);

        // get min volume sound of music
        int current_volume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);

        // setting max volume of seekbar
        seekVolume.setMax(max_volume);

        // setting current volume of seekbar
        seekVolume.setProgress(current_volume);
    }

    public boolean IsPlayingSound() {
        boolean lReturn = false;

        if ((mediaPlayer != null) && (mediaPlayer.isPlaying()))
            lReturn = true;

        return lReturn;
    }
}