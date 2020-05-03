package com.getscriba.sampleapp.android.game.game;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.getscriba.sampleapp.android.game.R;

public class Video extends AppCompatActivity implements MediaPlayer.OnCompletionListener {

    VideoView videoView;
    MediaController mc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int currentOrientation = getResources().getConfiguration().orientation;
        //if
//        (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        //}
//        else {
//            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
//        }

        setContentView(R.layout.video);
        videoView = findViewById(R.id.video);
        String path = "android.resource://" + getPackageName() + "/" + R.raw.vid;
        videoView.setMediaController(mc);
        videoView.setVideoURI(Uri.parse(path));
        videoView.start();
        videoView.setOnCompletionListener(this);
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            hideSystemUI();
        }
    }

    private void hideSystemUI() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(!videoView.isPlaying()){
            Intent i = new Intent(Video.this, PairActivity.class);
            startActivityForResult(i,2);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        Intent i = new Intent(Video.this, PairActivity.class);
        startActivityForResult(i,2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        finish();
    }

}
