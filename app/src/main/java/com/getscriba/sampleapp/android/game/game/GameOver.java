package com.getscriba.sampleapp.android.game.game;


import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.getscriba.sampleapp.android.game.R;
import com.getscriba.sdk.android.scribasdk.ClickType;
import com.getscriba.sdk.android.scribasdk.ScribaStylusDevice;
import com.getscriba.sdk.android.scribasdk.ScribaStylusManagerCallbacks;

import java.util.List;

public class GameOver extends AppCompatActivity implements ScribaStylusManagerCallbacks {
    private static final String TAG = GameOver.class.getName();
    BackgroundImage backgroundImage;
    TextView tvScore, tvPersonalBest;
    TextView t;
    private String dataAsString;
    private static final String FILE_NAME = "data.csv";
    private Button button, level2btn, level3btn,  mainmenubtn, dataPageBtn;
    int score;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        button = findViewById(R.id.leaderboard);
        level2btn = findViewById(R.id.level2btn);
        level3btn = findViewById(R.id.level3btn);
        mainmenubtn =  findViewById(R.id.mainmenubtn);
        dataPageBtn = findViewById(R.id.datapageBtn);
        dataPageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToDataPage();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLeaderboard();
            }
        });

        View overLay = findViewById(R.id.over);

        overLay.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN);

        backgroundImage = new BackgroundImage();

        SharedPreferences pref = getSharedPreferences("MyPref",0);

        tvScore = findViewById(R.id.tvScore);

        tvScore.setText(""+AppConstants.score);


    }

//    private void sendScribaData() {
//        Intent intent = new Intent(GameOver.this, DataPageActivity.class);
//        finish();
//        startActivity(intent);
//    }

    private void goToDataPage() {
        Intent intent = new Intent(this, NewDataPageActivity.class);
        startActivity(intent);
    }


    public void restart(View view){
        Intent intent = new Intent(GameOver.this, GameActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TOP);
        AppConstants.gameEngine = new GameEngine();
        finish();
        startActivity(intent);

        if(AppConstants.getGameEngine().gameState == 0){
            AppConstants.getGameEngine().gameState = 1;

        }
    }

    public void level1(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TOP);
        AppConstants.gameEngine = new GameEngine();
        GameEngine.score = 0;
        GameEngine.theLevel = 1;

        finish();
        startActivity(intent);

        if(AppConstants.getGameEngine().gameState == 0){
            AppConstants.getGameEngine().gameState = 1;

        }
    }
    public void level2(View view) {
        Intent intent = new Intent(GameOver.this, GameActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TOP);
        AppConstants.gameEngine = new GameEngine();
        GameEngine.theLevel = 2;
        GameEngine.score = 10;
        finish();
        startActivity(intent);
        GameEngine.newLevel = 2;

        if(AppConstants.getGameEngine().gameState == 0){
            AppConstants.getGameEngine().gameState = 1;

        }
    }

public void openLeaderboard (){
        GameActivity.endd(score);
}

    public void level3(View view){
        Intent intent = new Intent(GameOver.this, GameActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TOP);
        AppConstants.gameEngine = new GameEngine();
        GameEngine.score = 30;
        GameEngine.theLevel = 3;

        finish();
        startActivity(intent);
        GameEngine.newLevel = 3;

        if(AppConstants.getGameEngine().gameState == 0){
            AppConstants.getGameEngine().gameState = 1;

        }
    }

    public void mainmenu(View view) {
        Intent intent = new Intent(GameOver.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        AppConstants.gameEngine = new GameEngine();
        finish();
        startActivity(intent);
    }

    @Override
    public void foundDevices(List<ScribaStylusDevice> list) {

    }

    @Override
    public void connectedDevice(ScribaStylusDevice scribaStylusDevice) {

    }

    @Override
    public void disconnectedDevice(ScribaStylusDevice scribaStylusDevice) {

    }

    @Override
    public void updateBatteryStateOfDevice(ScribaStylusDevice scribaStylusDevice, int i) {

    }

    @Override
    public void changeDepressionForDevice(ScribaStylusDevice scribaStylusDevice, float v) {

    }

    @Override
    public void changeSqueezeZoneForDevice(ScribaStylusDevice scribaStylusDevice, int i) {

    }

    @Override
    public void clickWithDevice(ScribaStylusDevice scribaStylusDevice, ClickType clickType) {
        if    (clickType == ClickType.SINGLE);
        Intent intent = new Intent(GameOver.this, MainActivity.class);
        startActivity(intent);
        finish(); {

        }
    }


    @Override
    public void enabledLockConditionChange(boolean b) {

    }

    @Override
    public void lockStateChange(boolean b) {

    }

    @Override
    public void brushSizeChange(float v) {

    }

//    public void exit(View view){
//        if (mInterstitialAd.isLoaded()) {
//            mInterstitialAd.show();
//        } else {
//            Log.d("TAG", "The interstitial wasn't loaded yet.");
//        }
//        finish();
//    }
}

