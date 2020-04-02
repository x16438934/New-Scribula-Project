package com.getscriba.sampleapp.android.Game.Game;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.getscriba.sampleapp.android.Game.R;

public class Leaderboard extends AppCompatActivity {

    public static int newScoreSP;
    BackgroundImage backgroundImage;
    TextView tvScore, tvPersonalBest, tvSecondBest, tvThirdBest, nameOutput1, nameOutput2, nameOutput3;
    Button backBtn, submitBtn;
    static int score;
    EditText nameInput;
    String name1,name2,name3;
    private int scoreFirstBest,scoreSecondBest,scoreThirdBest;
    private SharedPreferences.Editor editor;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaderboard);
        View overLay = findViewById(R.id.leader);
        overLay.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN);

        nameInput = findViewById(R.id.nameInput);
        submitBtn = findViewById(R.id.submitBtn);
        submitBtn.setVisibility(View.VISIBLE);
        nameInput.setVisibility(View.VISIBLE);

        backBtn = findViewById(R.id.backBtn);
        nameOutput1 = findViewById(R.id.nameOutput);
        nameOutput2 = findViewById(R.id.nameOutput2);
        nameOutput3 = findViewById(R.id.nameOutput3);

        tvScore = findViewById(R.id.tvScore);
        tvPersonalBest = findViewById(R.id.tvPersonalBest);
        tvSecondBest = findViewById(R.id.secondBest);
        tvThirdBest = findViewById(R.id.thirdBest);

        backgroundImage = new BackgroundImage();


        score = getIntent().getExtras().getInt("score");
        newScoreSP = score;
        SharedPreferences pref = getSharedPreferences("MyPref", Context.MODE_PRIVATE);
        editor = pref.edit();

        scoreFirstBest = pref.getInt("scoreFirstBest",0);
        scoreSecondBest = pref.getInt("scoreSecondBest",0);
        scoreThirdBest = pref.getInt("scoreThirdBest",0);

        name1 = pref.getString("name1", "----");
        name2 = pref.getString("name2", "----");
        name3 = pref.getString("name3", "----");

        //score//////////////////////////////////////////////

        if(score > scoreFirstBest){

            scoreThirdBest = scoreSecondBest;
            scoreSecondBest = scoreFirstBest;
            scoreFirstBest = score;

            name3 = name2;
            name2 = name1;
            name1 = "----";

            editor.putInt("scoreSecondBest", scoreSecondBest);
            editor.putInt("scoreThirdBest", scoreThirdBest);
            editor.putInt("scoreFirstBest", scoreFirstBest);

            editor.putString("name1",name1);
            editor.putString("name2", name2);
            editor.putString("name3", name3);
            editor.apply();
            setScoreText();
            setNameOutput1();

        }else if(score > scoreSecondBest){

            scoreThirdBest = scoreSecondBest;
            scoreSecondBest = score;

            name3=name2;
            name2= "----";

            editor.putInt("scoreThirdBest", scoreThirdBest);
            editor.putInt("scoreSecondBest", scoreSecondBest);

            editor.putString("name2", name2);
            editor.putString("name3", name3);
            editor.apply();
            setScoreText();
            setNameOutput2();

        }else if(score > scoreThirdBest){

            scoreThirdBest = score;
            name3 = "----";

            editor.putInt("scoreThirdBest", scoreThirdBest);
            editor.putString("name3", name3);
            editor.apply();
            setScoreText();
            setNameOutput3();
        }
        else{
            setScoreText();
        }

        //name//////////////////////////////////////////////
    }

    private void setNameOutput1() {
        submitBtn.setOnClickListener(v -> {
            submitBtn.setVisibility(View.INVISIBLE);
            nameInput.setVisibility(View.INVISIBLE);
            name1 = String.valueOf(nameInput.getText());

            editor.putString("name1", name1);
            editor.apply();
            setScoreText();
        });
    }

    private void setNameOutput2() {
        submitBtn.setOnClickListener(v -> {
            submitBtn.setVisibility(View.INVISIBLE);
            nameInput.setVisibility(View.INVISIBLE);
            name2 = String.valueOf(nameInput.getText());

            editor.putString("name2", name2);
            editor.apply();
            setScoreText();
        });
    }

    private void setNameOutput3() {
        submitBtn.setOnClickListener(v -> {
            submitBtn.setVisibility(View.INVISIBLE);
            nameInput.setVisibility(View.INVISIBLE);
            name3 = String.valueOf(nameInput.getText());

            editor.putString("name3", name3);
            editor.apply();
            setScoreText();
        });
    }





    private void setScoreText() {
        tvPersonalBest.setText(String.valueOf(scoreFirstBest));
        tvSecondBest.setText(String.valueOf(scoreSecondBest));
        tvThirdBest.setText(String.valueOf(scoreThirdBest));

        nameOutput1.setText(String.valueOf(name1));
        nameOutput2.setText(String.valueOf(name2));
        nameOutput3.setText(String.valueOf(name3));
    }

    public void back(View view){
        Intent intent = new Intent(Leaderboard.this, GameOver.class);
        intent.putExtra("score", score);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
    public void resetScores(View view){
        scoreFirstBest=0;
        scoreSecondBest=0;
        scoreThirdBest=0;
        name1="----";
        name2="----";
        name3="----";

        editor.putInt("scoreFirstBest", scoreFirstBest);
        editor.putInt("scoreSecondBest", scoreSecondBest);
        editor.putInt("scoreThirdBest", scoreThirdBest);

        editor.putString("name1",name1);
        editor.putString("name2", name2);
        editor.putString("name3", name3);
        editor.apply();
        setScoreText();
    }

}
