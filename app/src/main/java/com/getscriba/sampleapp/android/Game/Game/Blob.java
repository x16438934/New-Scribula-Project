package com.getscriba.sampleapp.android.Game.Game;

import java.util.Random;

public class Blob {




    // blobX = Blob X-coordinate, blobOffsetY = blob bottom edge coordinate
    private int blobX, blobOffsetY;
    private Random random;

    boolean newScore;
    boolean livesScore;



    public Blob(int blobX, int blobOffsetY) {
        this.blobX = blobX;
        this.blobOffsetY = blobOffsetY;
        random = new Random();
        newScore = true;

    }
    public int getBlobOffsetY(){
        return blobOffsetY;
    }

    public int getBlobX(){
        return blobX;
    }
    public void setBlobX(int blobX) {
        this.blobX = blobX;
    }

    public int getBlobY(){
        return blobOffsetY - AppConstants.getBitmapBank().getBlobHeight();
    }


    public void setBlobOffsetY(int blobOffsetY){
        this.blobOffsetY = blobOffsetY;
    }
}
