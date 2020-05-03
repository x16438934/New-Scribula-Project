package com.getscriba.sampleapp.android.game.game;

import com.getscriba.sdk.android.scribasdk.ClickType;
import com.getscriba.sdk.android.scribasdk.ScribaStylusDevice;
import com.getscriba.sdk.android.scribasdk.ScribaStylusManagerCallbacks;

import java.util.List;

import static com.getscriba.sampleapp.android.game.game.AppConstants.SCREEN_HEIGHT;


public class Bird implements ScribaStylusManagerCallbacks {

    public static int birdX;
    public static int birdY;
    public int currentFrame;
    public int velocity;
    public static int maxFrame;

    public Bird(){
        birdX = AppConstants.SCREEN_WIDTH /9 - AppConstants.getBitmapBank().getBirdWidth()/9;
        birdY = SCREEN_HEIGHT ;
        currentFrame = 0;
        maxFrame = 3;
        velocity = 0;
    }

    // Getter method for velocity
    public int getVelocity(){
        return velocity;
    }

    // Setter method for velocity
    public void setVelocity(int velocity){
        this.velocity = velocity;
    }

    // Getter method for current frame
    public int getCurrentFrame(){
        return currentFrame;
    }

    // Setter method for current frame

    public void setCurrentFrame(int currentFrame) {
        this.currentFrame = currentFrame;
    }

    // Getter method for getting X-coordinate of the Bird
    public int getX(){
        return birdX;
    }

    // Getter method for getting the Y-coordinate of the Bird
    public int getY(){
        return birdY;
    }

    // Setter method for setting the X-coordinate
    public void setX(int birdX){
        this.birdX = birdX;
    }
    // Setter method for setting the Y-coordinate
    public void setY(int birdY){
        this.birdY = birdY;
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
