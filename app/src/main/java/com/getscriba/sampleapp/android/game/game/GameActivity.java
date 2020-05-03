package com.getscriba.sampleapp.android.game.game;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.getscriba.sdk.android.scribasdk.ClickType;
import com.getscriba.sdk.android.scribasdk.ScribaStylusDevice;
import com.getscriba.sdk.android.scribasdk.ScribaStylusManagerCallbacks;

import java.util.List;


public class GameActivity extends Activity implements ScribaStylusManagerCallbacks {

    GameVieww gameVieww;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        AppConstants.gameActivityContext = this;

        RelativeLayout layout = new RelativeLayout(this);
        layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));

        gameVieww = new GameVieww(this); // Instantiate gameView
        // Define ad view parameters
        RelativeLayout.LayoutParams adParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        adParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        adParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        layout.addView(gameVieww);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        hideSystemUI();
        setContentView(layout);


    }
    static void end(int score){
        Context context = AppConstants.gameActivityContext;
        Intent intent = new Intent(context, GameOver.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
        AppConstants.score = score;
        context.startActivity(intent);
        ((Activity) context).finish();


    }
    static void endd(int score) {
            Context context = AppConstants.gameActivityContext;
            GameEngine.gameState = 10;
            Intent intent = new Intent(context, Leaderboard.class);
            intent.putExtra("score", AppConstants.score);
            context.startActivity(intent);
            ((Activity) context).finish();



        }




    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        hideSystemUI();

    }
    private void hideSystemUI(){
        View decorView = getWindow().getDecorView();

        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN);
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
    public void changeDepressionForDevice(ScribaStylusDevice scribaStylusDevice, float depression) {

    }

    @Override
    public void changeSqueezeZoneForDevice(ScribaStylusDevice scribaStylusDevice, int i) {

    }

    @Override
    public void clickWithDevice(ScribaStylusDevice scribaStylusDevice, ClickType clickType) {

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
}
