package com.getscriba.sampleapp.android.game.game;


import android.content.Context;
import android.graphics.Canvas;
import android.os.SystemClock;
import android.util.Log;
import android.view.SurfaceHolder;

import com.getscriba.sdk.android.scribasdk.ClickType;
import com.getscriba.sdk.android.scribasdk.ScribaStylusDevice;
import com.getscriba.sdk.android.scribasdk.ScribaStylusManager;
import com.getscriba.sdk.android.scribasdk.ScribaStylusManagerCallbacks;

import java.util.List;

public class GameThread extends Thread implements ScribaStylusManagerCallbacks {
    private ScribaStylusManager mManager;
    static boolean  pause;
    SurfaceHolder surfaceHolder; //Surfaceholder object reference
    boolean isRunning; // Flag to detect whether the thread is running or not
    long startTime, loopTime; // Loop start time and loop duration
    long DELAY = 33; // Delay in milliseconds between screen refreshes

    public GameThread(SurfaceHolder surfaceHolder, Context context){
        this.surfaceHolder = surfaceHolder;
        isRunning = true;
        mManager = ScribaStylusManager.getInstance(context);
        mManager.addCallbackInterface(this);
    }

    @Override
    public void run() {
        // Looping until the boolean is false
        while(isRunning){
            startTime = SystemClock.uptimeMillis();
            //locking the canvas
            Canvas canvas = surfaceHolder.lockCanvas(null);
            if(canvas != null){
                synchronized (surfaceHolder){
                    AppConstants.getGameEngine().updateAndDrawBackgroundImage(canvas);
                    AppConstants.getGameEngine().updateAndDrawTubes(canvas);
                    AppConstants.getGameEngine().updateAndDrawBird(canvas);
                    //unlocking the canvas
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
            //loop time
            loopTime = SystemClock.uptimeMillis() - startTime;
            // Pausing here to make sure we update the right amount per second
            if(loopTime < DELAY){
                try{
                    Thread.sleep(DELAY - loopTime);
                }catch(InterruptedException e){
                    Log.e("Interrupted","Interrupted while sleeping");
                }
            }
        }
    }

    // Return whether the thread is running
    public boolean isRunning(){
        return isRunning;
    }

    // Sets the thread state, false = stopped, true = running
    public void setIsRunning(boolean state){
        isRunning = state;
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
