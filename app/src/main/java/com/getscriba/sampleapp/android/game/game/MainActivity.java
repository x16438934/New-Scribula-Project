package com.getscriba.sampleapp.android.game.game;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.getscriba.sampleapp.android.game.R;
import com.getscriba.sdk.android.scribasdk.ClickType;
import com.getscriba.sdk.android.scribasdk.ScribaStylusDevice;
import com.getscriba.sdk.android.scribasdk.ScribaStylusManager;
import com.getscriba.sdk.android.scribasdk.ScribaStylusManagerCallbacks;

import java.util.List;

import static com.getscriba.sampleapp.android.game.game.GameEngine.theTest;


public class MainActivity extends AppCompatActivity implements ScribaStylusManagerCallbacks {
    private static final String TAG = com.getscriba.sampleapp.android.game.game.MainActivity.class.getName();
    private ScribaStylusManager mManager;
    public  static String userGender;
    public  static int userAge;
    static float  newdepression;
    public Button level2button, level3btn, testBtn;
    private ImageButton btnfOut;
    BackgroundImage backgroundImage;

    private SharedPreferences settings;

// Links to where I got all my Images that i use in the game
//    https://pixabay.com/users/sweetmeatone-3429847/
//    https://pixabay.com/users/pencilparker-7519217/
//    https://pixabay.com/users/belozerovkids-10197347

TextView t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AppConstants.initialization(this.getApplicationContext());
        testBtn = findViewById(R.id.testBtn);
        level2button = findViewById(R.id.leveltwobtn);
        level3btn = findViewById(R.id.level3btn);


        testBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                theTest = 2;
            }
        });

        level2button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leveltwo(v);
            }
        });
        level3btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                levelthree(v);
            }
        });



        mManager = ScribaStylusManager.getInstance(this);
        mManager.addCallbackInterface(this);
        backgroundImage = new BackgroundImage();

        setContentView(R.layout.activity_main);
        View overLay = findViewById(R.id.myLayout);

        overLay.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
       }

        @Override //start the game
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        // Tap is detected
        if(action == MotionEvent.ACTION_DOWN){
            Intent intent = new Intent(this, GameActivity.class);
            startActivity(intent);
            finish();

            if(AppConstants.getGameEngine().gameState == 0){
                AppConstants.getGameEngine().gameState = 1;

            }

        }
        return true;
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
//Goes to test level
    public void testBtn(View view) {
        Intent intent = new Intent(this, GameActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TOP);
        AppConstants.gameEngine = new GameEngine();
        GameEngine.theTest = 2;
        GameEngine.theLevel = 3;

        finish();
        startActivity(intent);
        GameEngine.newLevel = 3;

        if(AppConstants.getGameEngine().gameState == 0){
            AppConstants.getGameEngine().gameState = 1;

        }
    }


    public void leveltwo(View view){
        Intent intent = new Intent(this, GameActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TOP);
        AppConstants.gameEngine = new GameEngine();
       // GameEngine.score = 10;
        GameEngine.theLevel = 2;

        finish();
        startActivity(intent);
        GameEngine.newLevel = 2;

        if(AppConstants.getGameEngine().gameState == 0){
            AppConstants.getGameEngine().gameState = 1;

        }
    }

    public void levelthree(View view){
        Intent intent = new Intent(this, GameActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TOP);
         AppConstants.gameEngine = new GameEngine();
      //  GameEngine.score = 30;
        GameEngine.theLevel = 3;

        finish();
        startActivity(intent);
        GameEngine.newLevel = 3;

        if(AppConstants.getGameEngine().gameState == 0){
            AppConstants.getGameEngine().gameState = 1;

        }
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

        Log.d(TAG, "changeDepressionForDevice: " + depression);
        newdepression = (float) (0.5 - depression) * 40;

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

