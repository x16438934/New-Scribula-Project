package com.getscriba.sampleapp.android.Game.Game;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.getscriba.sampleapp.android.Game.R;

public class Leaderboard extends AppCompatActivity {

    BackgroundImage backgroundImage;
    TextView tvScore, tvPersonalBest, tvSecondBest, tvThirdBest, nameOutput, nameOutput2, nameOutput3;
    Button backBtn, submitBtn;
    static int score;
    static boolean m;
    EditText nameInput;
    String name1,name2,name3;
   static  int gameState;
   static int newScoreSP;
    static int newScoreSB;
    static int newScoreTB;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboard);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        View overLay = findViewById(R.id.leader);
        overLay.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN);

        m = true;
        int scoreName = 0;
        gameState = 0;
        nameInput = (EditText) findViewById(R.id.nameInput);
        submitBtn = (Button) findViewById(R.id.submitBtn);
        backBtn = (Button) findViewById(R.id.backBtn);
        nameOutput = (TextView) findViewById(R.id.nameOutput);
        nameOutput2 = (TextView) findViewById(R.id.nameOutput2);
        nameOutput3 = (TextView) findViewById(R.id.nameOutput3);

//        submitBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                nameOutput = (TextView) findViewById(R.id.nameOutput);
//                nameInput = (EditText) findViewById(R.id.nameInput);
//                nameOutput.setText(nameInput.getText());
//            }
//        });


        backgroundImage = new BackgroundImage();


        score = getIntent().getExtras().getInt("score");
        SharedPreferences pref = getSharedPreferences("MyPref",0);
        int scoreSP = pref.getInt("scoreSP",0);
        SharedPreferences.Editor editor = pref.edit();

        int scoreSB = pref.getInt("scoreSB",0);
        SharedPreferences.Editor editorr = pref.edit();

        int scoreTB = pref.getInt("scoreTB",0);
        SharedPreferences.Editor editorrr = pref.edit();


        String name1;
        SharedPreferences pref2 = getSharedPreferences("MyPreff",0);
        SharedPreferences.Editor editor4 = pref2.edit();

        String name2;
        SharedPreferences pref3 = getSharedPreferences("MyPrefff",0);
        SharedPreferences.Editor editor5 = pref3.edit();

        String name3;
        SharedPreferences pref4 = getSharedPreferences("MyPreffff",0);
        SharedPreferences.Editor editor6 = pref4.edit();


        name1=pref2.getString("name1", "----");
        name2=pref3.getString("name2", "----");
        name3=pref4.getString("name3", "----");

        newScoreSP = scoreSP;
        newScoreSB = scoreSB;
        newScoreTB = scoreTB;

        //score//////////////////////////////////////////////

        if(score > scoreSP){
            scoreTB = scoreSB;
            scoreSB = scoreSP;
            editorr.putInt("scoreSB", scoreSB);
            editorrr.putInt("scoreTB", scoreTB);
            scoreSP = score;
            editor.putInt("scoreSP", scoreSP);
            editor.commit();
            editorr.commit();
            editorrr.commit();
            scoreName = 1;
            m = true;
            gameState = 4;

            name3= name2;
            name2 =name1;
            name1 = "----";
            editor4.putString("name1",name1);
            editor5.putString("name2", name2);
            editor6.putString("name3", name3);
            editor4.commit();
            editor5.commit();
            editor6.commit();

        }else
        if(score > scoreSB && score <= scoreSP){
            scoreTB = scoreSB;
            editorrr.putInt("scoreTB", scoreTB);
            scoreSB = score;
            editorr.putInt("scoreSB", scoreSB);
            editorr.commit();
            editorrr.commit();
            scoreName = 2;
            m = true;
            gameState = 4;

            name3= name2;
            name2 = "----";
            editor5.putString("name2", name2);
            editor6.putString("name3", name3);
            editor5.commit();
            editor6.commit();
        }else
        if(score <= scoreSB && score > scoreTB){
            scoreTB = score;
            editorrr.putInt("scoreTB", scoreTB);
            editorrr.commit();
            scoreName = 3;
            m = true;
            gameState = 4;
            name3 = "----";
            editor6.putString("name3", name3);
        }

        //name//////////////////////////////////////////////

        if(scoreName == 0) {
            submitBtn.setVisibility(View.INVISIBLE);
            nameInput.setVisibility(View.INVISIBLE);
        }

        if (scoreName == 1){
            submitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    submitBtn.setVisibility(View.INVISIBLE);
                    nameInput.setVisibility(View.INVISIBLE);
                    nameOutput.setText(nameInput.getText());

                    editor4.putString("name1", nameInput.getText().toString());
                    editor4.commit();

                }
            });
        }


        if (scoreName ==2){
            submitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    submitBtn.setVisibility(View.INVISIBLE);
                    nameInput.setVisibility(View.INVISIBLE);
                    nameOutput2.setText(nameInput.getText());
                    editor5.putString("name2", nameInput.getText().toString());
                    editor5.commit();

                }
            });
        }


        if ( scoreName == 3){
            submitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    submitBtn.setVisibility(View.INVISIBLE);
                    nameInput.setVisibility(View.INVISIBLE);
                    nameOutput3.setText(nameInput.getText());

                    editor6.putString("name3", nameInput.getText().toString());
                    editor6.commit();

                }
            });
        }

        tvScore = findViewById(R.id.tvScore);
        tvPersonalBest = findViewById(R.id.tvPersonalBest);
        tvSecondBest = findViewById(R.id.secondBest);
        tvThirdBest = findViewById(R.id.thirdBest);
       // tvScore.setText(""+ AppConstants.score);
        tvPersonalBest.setText(""+scoreSP);
        tvSecondBest.setText(""+scoreSB);
        tvThirdBest.setText(""+scoreTB);
        nameOutput.setText(""+name1);
        nameOutput2.setText(""+name2);
        nameOutput3.setText(""+name3);
    }

    public void back(View view){
        Intent intent = new Intent(Leaderboard.this, GameOver.class);
        intent.putExtra("score", score);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
    public void reset (View view){

    }

}
