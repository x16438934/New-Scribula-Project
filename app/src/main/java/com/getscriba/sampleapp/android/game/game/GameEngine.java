package com.getscriba.sampleapp.android.game.game;


import android.content.pm.ActivityInfo;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.getscriba.sdk.android.scribasdk.Buzz;
import com.getscriba.sdk.android.scribasdk.ClickType;
import com.getscriba.sdk.android.scribasdk.ScribaStylusDevice;
import com.getscriba.sdk.android.scribasdk.ScribaStylusManager;
import com.getscriba.sdk.android.scribasdk.ScribaStylusManagerCallbacks;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;


import static com.getscriba.sampleapp.android.game.game.MainActivity.userAge;
import static com.getscriba.sampleapp.android.game.game.MainActivity.userGender;


public class GameEngine extends AppCompatActivity implements ScribaStylusManagerCallbacks {

    private ScribaStylusManager mManager;
    BackgroundImage backgroundImage;
    Bird bird;
    private int level;
    static int gameState;
    ArrayList<Tube> tubes;
    ArrayList<Blob> blobs;
    Random random;
    private int numberOfSavedScoresForGraphs;
  static int lives;
    static int score; // Stores the score
    int scoringTube; // Keeps track of scoring tube
    Paint livesPaint;
    static int theLevel;
    static int newLevel;
    int pictureLevel;
    boolean livesScore;
    boolean fIncreased;
    boolean hi, mi;
    private int motorPlanning;
    private int engagementInn;
    private boolean engagementInnBoolean;
    private int engagementTotal;
    private static final String FILE_NAME = "data.csv";
    private String detailsSavedToCSV;
    static int theTest;
    private int graphMotorPlanning;
    private int graphEngagement;
    private String currentDateTimeString;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    public GameEngine() {

        theLevel = 1;
        theTest = 1;
        mManager = ScribaStylusManager.getInstance(this);
        mManager.addCallbackInterface(this);
        backgroundImage = new BackgroundImage();
        bird = new Bird();
        GameThread.pause = false;
        fIncreased = false;
        hi = false;
        mi = false;
        // 0 = Not started
        //1 = Playing
        //2 = GameOver
        //3 = Win


        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        gameState = 0;
        tubes = new ArrayList<>();
        blobs = new ArrayList<>();
        random = new Random();
        livesScore = true;


        for (int i = 0; i < AppConstants.numberOfTubes; i++) {
            int tubeX = AppConstants.SCREEN_WIDTH + i * AppConstants.distanceBetweenTubes;
            // Get topTubeOffsetY
            int topTubeOffsetY = AppConstants.minTubeOffsetY +
                    random.nextInt(AppConstants.maxTubeOffsetY - AppConstants.minTubeOffsetY + 1);
            // Now create Tube objects

            Tube tube = new Tube(tubeX, topTubeOffsetY);
            tubes.add(tube);
        }

        // For new blob images
        for (int i = 0; i < AppConstants.numberOfBlobs; i++) {
            int blobX = AppConstants.SCREEN_WIDTH + i * AppConstants.distanceBetweenBlob;
            // Get topTubeOffsetY
            int blobOffsetY = AppConstants.minBlobOffsetY +
                    random.nextInt(AppConstants.maxBlobOffsetY - AppConstants.minBlobOffsetY + 1);
            // Now create blob objects

            Blob blob = new Blob(blobX, blobOffsetY);
            blobs.add(blob);
        }

        lives = 3;
        score = 0;
        scoringTube = 0;

    }

    public void updateAndDrawBird (Canvas canvas){
        if(GameThread.pause == false) {
            if (gameState == 1) {
                if (bird.getY() < (AppConstants.SCREEN_HEIGHT - AppConstants.getBitmapBank().getBirdHeight()) || bird.getVelocity() < 0) {
                    bird.setVelocity(bird.getVelocity());
                    bird.setY(bird.getY() + bird.getVelocity());
                }
            }
            if (Bird.birdY + AppConstants.getBitmapBank().getBirdHeight() /3.4 + MainActivity.newdepression > 0 && bird.getY() + MainActivity.newdepression < AppConstants.SCREEN_HEIGHT - AppConstants.getBitmapBank().getBirdHeight()*2/3) {
                // if (Bird.birdY + MainActivity.newdepression > 0 && bird.getY() + MainActivity.newdepression < AppConstants.SCREEN_HEIGHT - AppConstants.getBitmapBank().getBirdHeight()) {

                Bird.birdY += MainActivity.newdepression;

            }
            else{
                if (Bird.birdY + AppConstants.getBitmapBank().getBirdHeight()/3.4 + MainActivity.newdepression < 0){
                    Bird.birdY = (int) (-AppConstants.getBitmapBank().getBirdHeight()/3.4);
                }else
                    Bird.birdY = AppConstants.SCREEN_HEIGHT - AppConstants.getBitmapBank().getBirdHeight()*2/3;

            }
            int currentFrame = bird.getCurrentFrame();
            canvas.drawBitmap(AppConstants.getBitmapBank().getBird(currentFrame), bird.getX(), bird.getY(), null);
            currentFrame++;
            // If it exceeds maxframe re-initialize to 0
            if (currentFrame > bird.maxFrame) {
                currentFrame = 0;
            }
            bird.setCurrentFrame(currentFrame);
        }

    }

    public void updateAndDrawTubes(Canvas canvas) {
        if (gameState == 1) {
            currentDateTimeString = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            detailsSavedToCSV = "";
            startTheCSV();
            //Hit Detection
            int topTubeY = tubes.get(scoringTube).getTopTubeOffsetY();

            if (theLevel >= 2) {                            //stops the game from lagging by putting the code here

                if (collision()) {
                    if (livesScore) {
                        lives--;
                        mManager.hapticsBuzz(Buzz.DOUBLE_BUZZ);
                        livesScore = false;
                    }
                }

                if (lives == 0 ) {
                    gameState = 2;
                    GameActivity.end(score);

                }
                if (lives == 0 && score > Leaderboard.newScoreSP) {
                    GameActivity.endd(score);

                }
            }
        }

        for (int i = 0; i < AppConstants.numberOfTubes; i++) {
            if (tubes.get(i).getTubeX() < -AppConstants.getBitmapBank().getTubeWidth()) {
                tubes.get(i).setTubeX(tubes.get(i).getTubeX() +
                        AppConstants.numberOfTubes * AppConstants.distanceBetweenTubes);
                int topTubeOffsetY = AppConstants.minTubeOffsetY +
                        random.nextInt(AppConstants.maxTubeOffsetY - AppConstants.minTubeOffsetY + 1);
                tubes.get(i).setTopTubeOffsetY(topTubeOffsetY);
                tubes.get(i).setTubeColor();
                tubes.get(i).setNumber((int) (Math.random() * 12));
                tubes.get(i).setBottomNumber((int) (Math.random() * 12));

                tubes.get(i).setNumber((int) (Math.random() * 12));
                tubes.get(i).setBottomNumber((int) (Math.random() * 12));

                livesScore = true;
            }
            tubes.get(i).setTubeX(tubes.get(i).getTubeX() - AppConstants.tubeVelocity);
            if(theLevel >= 2){

                //    AppConstants.gameEngine = new GameEngine();
                canvas.drawBitmap(AppConstants.getBitmapBank().images[tubes.get(i).getNumber()], tubes.get(i).getTubeX(), tubes.get(i).getTopTubeY(), null);
            }
        }

            for (int j = 0; j < AppConstants.numberOfBlobs; j++) {
                if (blobs.get(j).getBlobX() < -AppConstants.getBitmapBank().getBlobWidth()) {
                    blobs.get(j).setBlobX(blobs.get(j).getBlobX() +
                            AppConstants.numberOfBlobs * AppConstants.distanceBetweenBlob);
                    int blobOffSetY = AppConstants.minBlobOffsetY +
                            random.nextInt(AppConstants.maxBlobOffsetY - AppConstants.minBlobOffsetY + 1);
                   blobs.get(j).setBlobOffsetY(blobOffSetY);

                   blobs.get(j).newScore = true;
                }
                blobs.get(j).setBlobX(blobs.get(j).getBlobX() - AppConstants.blobVelocity);
            }

            //Changing speed as user progresses through game///////////////////////////////////////////

            if (score >= 10) {
                AppConstants.tubeVelocity = 7;
            }
            if (score >= 15) {
                AppConstants.tubeVelocity = 9;
            }
            if (score >= 20){
                AppConstants.tubeVelocity = 11;
            } if (score >= 30){
                AppConstants.tubeVelocity = 12;
            }if (score >= 40){
                AppConstants.tubeVelocity = 13;
            }if (score >= 45){
                AppConstants.tubeVelocity = 14;
            }if (score >= 50){
                AppConstants.tubeVelocity = 15;
            }if (score >= 60){
                AppConstants.tubeVelocity = 20;
            }

            if(score >= 0){
                AppConstants.blobVelocity = 8;
            }
            if (score >= 5){
                AppConstants.blobVelocity = 10;
            }
            if (score >= 30){
                AppConstants.blobVelocity = 13;
            }if (score >= 40){
                AppConstants.blobVelocity = 14;
            }if (score >= 50){
                AppConstants.blobVelocity = 15;
            }if (score >= 60){
                AppConstants.blobVelocity = 22;
            }

            //changes the frequency of blobs//////////////////////////////////////////////

    //      drawableLevel();
            level();

           if (theLevel == 1 || theLevel == 3) {
               startTheCSV();
               int indexCollision = BlobCollision();
                if (indexCollision != 100) {
                    if (blobs.get(indexCollision).newScore) {
                        score++;
                        blobs.get(indexCollision).newScore = false;
                        mManager.hapticsBuzz(Buzz.SINGLE_BUZZ);
                    }
                }

                for (int j = 0; j < blobs.size(); j++) {
                    if (blobs.get(j).newScore)
                        canvas.drawBitmap(AppConstants.getBitmapBank().blob[0], blobs.get(j).getBlobX(), blobs.get(j).getBlobY(), null);
                }
            }
//            if(theLevel == 3){
//                newLevel = 3;
//
//            }

                if (theLevel == 2) {
                    startTheCSV();
                    newLevel = 2;
                    if (tubes.get(scoringTube).getTubeX() < bird.getX() - AppConstants.getBitmapBank().getTubeWidth()) {
                        score++;
                        scoringTube++;
                        if (scoringTube > AppConstants.numberOfTubes - 1) {
                            scoringTube = 0;
                        }
                    }
                }


                    canvas.drawText("" + score, 20, 110, AppConstants.scorePaint);


            switch (lives) {
                case 3:
                canvas.drawBitmap(AppConstants.getBitmapBank().heart1, 600, 50, livesPaint);
                case 2:
                canvas.drawBitmap(AppConstants.getBitmapBank().heart1, 550, 50, livesPaint);
                case 1:
                canvas.drawBitmap(AppConstants.getBitmapBank().heart1, 500, 50, livesPaint);
            }

        if (score >= 5 && !fIncreased){
            AppConstants.distanceBetweenBlob = AppConstants.SCREEN_WIDTH * 3 /5;
            int blobX = blobs.get(AppConstants.numberOfBlobs - 1).getBlobX() + AppConstants.distanceBetweenBlob;
            // Get topTubeOffsetY
            int blobOffsetY = AppConstants.minBlobOffsetY +
                    random.nextInt(AppConstants.maxBlobOffsetY - AppConstants.minBlobOffsetY + 1);
            // Now create blob objects

            Blob blob = new Blob(blobX, blobOffsetY);
            blobs.add(blob);
            fIncreased = true;
            AppConstants.numberOfBlobs = 2;
        }

        if(theTest == 2){
            canvas.drawText("Motor Planning: " + motorPlanning, 20, 150, AppConstants.dataPaint);
            canvas.drawText("Engagement: " + engagementInnBoolean, 20, 190, AppConstants.dataPaint);
            canvas.drawText("Engagement Score: " + engagementInn, 20, 230, AppConstants.dataPaint);
        }
        if(lives == 0){
            saveFile();
        }
    }

        public void updateAndDrawBackgroundImage (Canvas canvas){
            if(GameThread.pause == false) {
                backgroundImage.setX(backgroundImage.getX() - backgroundImage.getVelocity());
                if (backgroundImage.getX() < -AppConstants.getBitmapBank().getBackgroundWidth()) {
                    backgroundImage.setX(0);
                }
                canvas.drawBitmap(AppConstants.getBitmapBank().getBackground(), backgroundImage.getX(), backgroundImage.getY(), null);
                if (backgroundImage.getX() < -(AppConstants.getBitmapBank().getBackgroundWidth() - AppConstants.SCREEN_WIDTH)) {
                    canvas.drawBitmap(AppConstants.getBitmapBank().getBackground(), backgroundImage.getX() +
                            AppConstants.getBitmapBank().getBackgroundWidth(), backgroundImage.getY(), null);
                }
            }
        }

     public boolean collision(){
        for(int i = 0; i< tubes.size();i++){
            if(Math.sqrt(Math.pow(tubes.get(i).getTubeX()
                    - Bird.birdX - AppConstants.getBitmapBank().getBirdWidth()/2,2)
                    + Math.pow(tubes.get(i).getTopTubeY() + AppConstants.getBitmapBank().getTubeHeight()/2
                    -Bird.birdY - AppConstants.getBitmapBank().getBirdHeight()/2,2))
                    < AppConstants.getBitmapBank().getBirdHeight()/2 + AppConstants.getBitmapBank().getTubeHeight()/2)

                return true;
        }
        return false;
     }
     public int BlobCollision(){
        for(int j = 0; j< blobs.size();j++){
            if(Math.sqrt(Math.pow(blobs.get(j).getBlobX()
                    - Bird.birdX - AppConstants.getBitmapBank().getBirdWidth()/2,2)
                    + Math.pow(blobs.get(j).getBlobY() + AppConstants.getBitmapBank().getBlobHeight()/2
                    -Bird.birdY - AppConstants.getBitmapBank().getBirdHeight()/2,2))
                    < AppConstants.getBitmapBank().getBirdHeight()/2 + AppConstants.getBitmapBank().getBlobHeight()/2)

                return j;
        }
         return 100;
     }

//private void drawableLevel() {
//        if (theLevel == 1) {
//        pictureLevel = R.drawable.levelone;
//    } else if (theLevel == 2) {
//        pictureLevel = R.drawable.two;
//    } else if (theLevel == 3) {
//        pictureLevel = R.drawable.three;
//    }
//
//}

    private void level() {
        if(theLevel == 1 && score == 10) {
            theLevel++;
            addToCSV();

        }

        if (theLevel == 2){
                theLevel =2;
                if(score == 30)
                    theLevel++;
            addToCSV();

        } else if (theLevel > 2 && theLevel < 4){
                 theLevel = 3;
                 if (score == 999999999)
                     theLevel++;
                 addToCSV();

        }
    }

    private void addToCSV() {
//        for calculating all the data from the Scriba and adding it to a string.
        float engagement = (engagementInn * 100.0f) / engagementTotal;

        detailsSavedToCSV=

                        motorPlanning + "," +
                        engagement + "," +
                                score + ",";

        saveToGraph(motorPlanning, engagement);

//        engagementInn = 0;
//        motorPlanning = 0;

        saveFile();
    }

    private void saveToGraph(int motorPlanning, float engagement) {
        numberOfSavedScoresForGraphs++;

        graphMotorPlanning += motorPlanning;
        graphEngagement += engagement;

//        updateDetailsForGraph(graphMotorPlanning,graphEngagement);
    }


    private void startTheCSV() {
//        save user and game details at start of game
        detailsSavedToCSV =
                "#" + currentDateTimeString + "," +
                        userGender + "," +
                        userAge + ",";
        saveFile();
    }

    private void saveFile() {
//        Code for saving data to a CSV on the phone using code from https://www.javatpoint.com/java-fileoutputstream-class
//        and from the Youtube Video https://www.youtube.com/watch?v=ZtVcT38_7Gs.
        try {
            File folder = new File(getApplicationContext().getFilesDir(), "files");
            boolean mkdirs = folder.mkdirs();
            if (!mkdirs) {
                System.out.println("was not successful.");
            }
            File file = new File(folder, FILE_NAME);
            FileOutputStream out = new FileOutputStream(file, true);
            out.write(detailsSavedToCSV.getBytes());
            out.flush();
            out.close();
            detailsSavedToCSV = "";
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    public void onPause() {
        super.onPause();

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
        if (v > 0 && v < 1) {
            engagementInn++;
            engagementInnBoolean = true;
        } else {
            engagementInnBoolean = false;
        }
    }

    @Override
    public void changeSqueezeZoneForDevice(ScribaStylusDevice scribaStylusDevice, int i) {

    }

    @Override
    public void clickWithDevice(ScribaStylusDevice scribaStylusDevice, ClickType clickType) {
        if (clickType == ClickType.SINGLE || clickType == ClickType.DOUBLE || clickType == ClickType.TRIPLE) {
            motorPlanning++;
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
}
