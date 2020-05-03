package com.getscriba.sampleapp.android.game.game;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.getscriba.sampleapp.android.game.R;

public class BitmapBank {

    Bitmap background;
    static Bitmap[] bird;
    static  Bitmap[] images;
    Bitmap tubeTop, tubeBottom;
    Bitmap redTubeTop, redTubeBottom;
    Bitmap planet1;
    Bitmap planet2;
    Bitmap[] blob;
    Bitmap level1;
    Bitmap level2;
    Bitmap level3;
    Bitmap heart1;




    public BitmapBank(Resources res) {
        background = BitmapFactory.decodeResource(res, R.drawable.jpegbackground);
        background = scaleImage(background);
        blob = new Bitmap[1];
        blob[0] = BitmapFactory.decodeResource(res, R.drawable.starr);
      //  blob[1] = BitmapFactory.decodeResource(res, R.drawable.starr);


        heart1 = BitmapFactory.decodeResource(res, R.drawable.newheart);


        bird = new Bitmap[8];
        bird[0] = BitmapFactory.decodeResource(res, R.drawable.s1);
        bird[1] = BitmapFactory.decodeResource(res, R.drawable.s2);
        bird[2] = BitmapFactory.decodeResource(res, R.drawable.s3);
        bird[3] = BitmapFactory.decodeResource(res, R.drawable.s4);
        bird[4] = BitmapFactory.decodeResource(res, R.drawable.s5);
        bird[5] = BitmapFactory.decodeResource(res, R.drawable.s6);
        bird[6] = BitmapFactory.decodeResource(res, R.drawable.s7);
        bird[7] = BitmapFactory.decodeResource(res, R.drawable.s8);


        level1 = BitmapFactory.decodeResource(res, R.drawable.btnlevel);
        level2 = BitmapFactory.decodeResource(res, R.drawable.btnleveltwo);
        level3 = BitmapFactory.decodeResource(res, R.drawable.btnlevelthree);
        tubeTop = BitmapFactory.decodeResource(res, R.drawable.astronaught);
        tubeBottom = BitmapFactory.decodeResource(res, R.drawable.satelite);


        images = new Bitmap[12];
        images[0] = BitmapFactory.decodeResource(res, R.drawable.astronaught);
        images[1] = BitmapFactory.decodeResource(res, R.drawable.satelite);
        images[2] = BitmapFactory.decodeResource(res, R.drawable.spaceship);
        images[3] = BitmapFactory.decodeResource(res, R.drawable.astronaughtt);
        images[4] = BitmapFactory.decodeResource(res, R.drawable.planet);
        images[5] = BitmapFactory.decodeResource(res, R.drawable.planettwo);
        images[6] = BitmapFactory.decodeResource(res, R.drawable.astronaughttt);
        images[7] = BitmapFactory.decodeResource(res, R.drawable.satelite);
        images[8] = BitmapFactory.decodeResource(res, R.drawable.planetthree);
        images[9] = BitmapFactory.decodeResource(res, R.drawable.planetfour);
        images[10] = BitmapFactory.decodeResource(res, R.drawable.planet);
        images[11] = BitmapFactory.decodeResource(res, R.drawable.planetfour);
    }


    public Bitmap getPlanet1() {
        return planet1;
    }

    public Bitmap getPlanet2() {
        return planet2;
    }

    // Return Red Tube-Top Bitmap
    public Bitmap getRedTubeTop(){
        return redTubeTop;
    }

    // Return Red Tube-Bottom Bitmap
    public Bitmap getRedTubeBottom(){
        return redTubeBottom;
    }

    // Return Tube-Top Bitmap
    public Bitmap getTubeTop(){
        return tubeTop;
    }

    // Return Tube-Bottom Bitmap
    public Bitmap getTubeBottom(){
        return tubeBottom;
    }

    //Return Tube-width
    public int getTubeWidth(){
        return tubeTop.getWidth();
    }

    //Return Tube-height
    public int getTubeHeight(){
        return tubeTop.getHeight();
    }

    public Bitmap getBlob(){
        return blob[0];
    }
       public int getBlobWidth() {
           return blob[0].getWidth();
       }

        public int getBlobHeight(){
        return blob[0].getHeight();
    }


    // Return bird bitmap of frame
    public Bitmap getBird(int frame){
        return bird[frame];
    }

    public int getBirdWidth(){
        return bird[0].getWidth();
    }

    public int getBirdHeight(){
        return bird[0].getHeight();
    }

    //Return background bitmap
    public Bitmap getBackground(){
        return background;
    }

    //Return background width
    public int getBackgroundWidth(){
        return background.getWidth();
    }

    //Return background height
    public int getBackgroundHeight(){
        return background.getHeight();
    }

    public Bitmap scaleImage(Bitmap bitmap){

        /*
        We'll multiply widthHeightRatio with screenHeight to get scaled width of the bitmap.
        Then call createScaledBitmap() to create a new bitmap, scaled from an existing bitmap, when possible.
         */

        return Bitmap.createScaledBitmap(bitmap, getBackgroundWidth()/2, AppConstants.SCREEN_HEIGHT, false);
    }
}
