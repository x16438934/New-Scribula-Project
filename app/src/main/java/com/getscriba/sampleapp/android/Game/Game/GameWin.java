package com.getscriba.sampleapp.android.Game.Game;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.getscriba.sampleapp.android.Game.R;

public class GameWin extends AppCompatActivity {
    BackgroundImage backgroundImage;
    TextView t;
    TextView tvScore, tvPersonalBest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_win);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        View overLay = findViewById(R.id.win);

        overLay.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN);


        t = (TextView) findViewById(R.id.congratulations);
        Typeface myCustomFont = Typeface.DEFAULT.createFromAsset(getAssets(), "fonts/PressStart2p_Regular.ttf");
        t.setTypeface(myCustomFont);

        t = (TextView) findViewById(R.id.youWin);
        Typeface myCustomFont2 = Typeface.DEFAULT.createFromAsset(getAssets(), "fonts/PressStart2p_Regular.ttf");
        t.setTypeface(myCustomFont2);

        backgroundImage = new BackgroundImage();
    }
    public void restart(View view) {
        Intent intent = new Intent(GameWin.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
    }
