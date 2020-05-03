package com.getscriba.sampleapp.android.game.game;


import java.util.Random;

public class Tube {
    // tubeX = Tube X-coordinate, topTubeOffsetY = top tube bottom edge coordinate
    private int tubeX, topTubeOffsetY;
    private Random random;
    private int tubeColor;
    private int number;
    private int bottomNumber;

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }



    public int getBottomNumber() {
        return bottomNumber;
    }

    public void setBottomNumber(int bottomNumber) {
        this.bottomNumber = bottomNumber;
    }



    public Tube(int tubeX, int topTubeOffsetY){
        this.tubeX = tubeX;
        this.topTubeOffsetY = topTubeOffsetY;
        random = new Random();
        number = (int) (Math.random()*12);
        bottomNumber = (int) (Math.random()*12);
    }


    public void setTubeColor(){
        tubeColor = random.nextInt(2);
    }

   // public int getTubeColor(){
    //    return tubeColor;
   // }

    public int getTopTubeOffsetY(){
        return topTubeOffsetY;
    }

    public int getTubeX(){
        return tubeX;
    }

    public int getTopTubeY(){
        return topTubeOffsetY - AppConstants.getBitmapBank().getTubeHeight();
    }

//    public int getBottomTubeY(){
//        return topTubeOffsetY + AppConstants.gapBetweenTopAndBottomTubes;
//    }

    public void setTubeX(int tubeX){
        this.tubeX = tubeX;
    }

    public void setTopTubeOffsetY(int topTubeOffsetY){
        this.topTubeOffsetY = topTubeOffsetY;
    }
}
