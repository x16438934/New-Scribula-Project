package com.getscriba.sampleapp.android.game.game;


import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import com.getscriba.sdk.android.scribasdk.ClickType;
import com.getscriba.sdk.android.scribasdk.ScribaStylusDevice;
import com.getscriba.sdk.android.scribasdk.ScribaStylusManagerCallbacks;

import java.util.List;

public class AppConstants implements ScribaStylusManagerCallbacks {

    static BitmapBank bitmapBank; // Bitmap object reference
    static GameEngine gameEngine; // GameEngine object reference
    static Leaderboard leaderboard; // GameEngine object reference

    static int SCREEN_WIDTH, SCREEN_HEIGHT;
    static int gravity;
    static int gapBetweenTopAndBottomTubes;
    static int numberOfTubes;
    static int tubeVelocity;
    static int minTubeOffsetY;
    static int maxTubeOffsetY;
    static int distanceBetweenTubes;
    static int gapBetweenTopAndBottomBlob;
    static int numberOfBlobs;
    static int blobVelocity;
    static int minBlobOffsetY;
    static int maxBlobOffsetY;
    static int distanceBetweenBlob;
    static int depression;
    static Context gameActivityContext;
    static Paint scorePaint;
    static Paint dataPaint;
    static int score;

    public static void initialization(Context context){
        setScreenSize(context);
        bitmapBank = new BitmapBank(context.getResources());
        setGameConstants();
        gameEngine = new GameEngine();
       Typeface myCustomFont = Typeface.createFromAsset(context.getAssets(), "fonts/PressStart2P-Regular.ttf");
        scorePaint = new Paint();
      scorePaint.setTypeface(myCustomFont);
        scorePaint.setColor(Color.WHITE);
        scorePaint.setTextSize(70);
        scorePaint.setTextAlign(Paint.Align.LEFT);

        dataPaint = new Paint();
        dataPaint.setColor(Color.WHITE);
        dataPaint.setTextSize(30);
        dataPaint.setTextAlign(Paint.Align.LEFT);

    }

    public static void setGameConstants(){
        AppConstants.gravity = 3;
   //     AppConstants.VELOCITY_WHEN_JUMPED = -40;
        gapBetweenTopAndBottomTubes = 0;
        AppConstants.numberOfTubes = 2;
        AppConstants.tubeVelocity = 6;
        AppConstants.depression = 0;
        AppConstants.minTubeOffsetY = 130;//(int)(AppConstants.gapBetweenTopAndBottomTubes / 2.0);
        AppConstants.maxTubeOffsetY = 800;//AppConstants.SCREEN_HEIGHT - AppConstants.minTubeOffsetY - AppConstants.gapBetweenTopAndBottomTubes;
        AppConstants.distanceBetweenTubes = AppConstants.SCREEN_WIDTH * 3/5;

        gapBetweenTopAndBottomBlob = 0;
        AppConstants.numberOfBlobs = 1;
        AppConstants.blobVelocity = 6;
        AppConstants.minBlobOffsetY = 33;
        AppConstants.maxBlobOffsetY = 800;
        AppConstants.distanceBetweenBlob = AppConstants.SCREEN_WIDTH;

    }

    // Return BitmapBank instance
    public static BitmapBank getBitmapBank(){
        return bitmapBank;
    }

    // Return GameEngine instance/
    public static GameEngine getGameEngine(){
        return gameEngine;
    }
    public static Leaderboard getLeaderboard(){
        return leaderboard;
    }



    private static void setScreenSize(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getRealMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        AppConstants.SCREEN_WIDTH = width;
        AppConstants.SCREEN_HEIGHT = height;
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
