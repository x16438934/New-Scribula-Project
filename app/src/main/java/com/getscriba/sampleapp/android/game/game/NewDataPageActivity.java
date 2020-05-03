package com.getscriba.sampleapp.android.game.game;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.getscriba.sampleapp.android.game.game.data.GoogleFormUploader;
import com.getscriba.sampleapp.android.game.R;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;


public class NewDataPageActivity extends AppCompatActivity {
    private static final String TAG = NewDataPageActivity.class.getName();
    private static final String FILE_NAME = "data.csv";
    public static boolean testing;
    //    Button testBtn;
    private String dataAsString;
    private TextView txt_age_gender_status;
    private SharedPreferences settings;
//    private boolean isThereInternet;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_data_page);
//        this.setTitle(R.string.scriba_data_page);
        settings = this.getSharedPreferences("UserInfo", Context.MODE_PRIVATE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Button sendBtn = findViewById(R.id.sendBtn);

        Button resetBtn = findViewById(R.id.resetBtn);
        txt_age_gender_status = findViewById(R.id.txt_age_gender_status);
//        testBtn = findViewById(R.id.testBtn);
        Switch simpleSwitch1 = findViewById(R.id.simpleSwitch1);

        sendBtn.setOnClickListener(v -> GoogleForm());
        resetBtn.setOnClickListener(v -> resetCSV());
        simpleSwitch1.setOnClickListener(v -> testMode());

        Button btnChangeAgeGender = findViewById(R.id.gender_age_btn);
        btnChangeAgeGender.setOnClickListener(v -> startActivity(new Intent(NewDataPageActivity.this, AgeAndGender.class)));

        checkAgeGender();
    }

    private void testMode() {
        SharedPreferences.Editor editor = settings.edit();
        testing = settings.getBoolean("testing", false);

        if (testing) {
            testing = false;
            Toast.makeText(NewDataPageActivity.this, "Test Data is off!", Toast.LENGTH_LONG).show();
            editor.putBoolean("testing", testing);
            editor.apply();
        } else {
            testing = true;
            Toast.makeText(NewDataPageActivity.this, "Test Data is on!", Toast.LENGTH_LONG).show();
            editor.putBoolean("testing", testing);
            editor.apply();
        }


    }

    private void readScoreLog() {
//        This code reads the CSV file and adds it to a string
//      Incorporate code from this website https://www.mkyong.com/java/how-to-read-file-from-java-bufferedreader-example/
        BufferedReader br = null;
        try {
            String sCurrentLine;
            br = new BufferedReader(new FileReader(getFilesDir() + "/files/" + FILE_NAME));
            Log.v("DataPageActivity: ", "br: " + br);
            StringBuilder sb = null;
            while ((sCurrentLine = br.readLine()) != null) {
                System.out.println(sCurrentLine);
                sb = new StringBuilder();
                dataAsString = sb.append(sCurrentLine).toString();
            }
            Log.v("DataPageActivity: ", "sb: " + sb);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        SpreadSheet();
    }

    @Override
    public void onResume() {
        super.onResume();
        checkAgeGender();
    }

    private void checkAgeGender() {
        int userAge = settings.getInt("userAge", 0);
        String userGender = settings.getString("userGender", "Na");
        if (userGender.equals("Na")){
            txt_age_gender_status.setText("Age and Gender");
        }
        else{
            String textAgeAndGender = "User's Age: " + userAge +" & User's Gender: "+ userGender;
            txt_age_gender_status.setText(textAgeAndGender);
        }

    }

    private void SpreadSheet() {
//        Adds the Scriba Data as a String to a specific cell in the Google Sheet.
//        startActivity(new Intent(DataPageActivity.this, AddItem.class));
//        Incorporate code from this website https://github.com/FoamyGuy/GoogleFormUploader used to upload data from android to google

        if (dataAsString != null) {
            boolean internet = isNetworkAvailable();

            if (internet) {
                GoogleForm();
            }
            else {
                Toast.makeText(NewDataPageActivity.this, "Please connect to the Internet", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(NewDataPageActivity.this, "The Scriba data file is empty, play the game if you want to send data.", Toast.LENGTH_LONG).show();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = null;
        if (connectivityManager != null) {
            activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        }
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void GoogleForm() {
//        Send to a google spread sheet.
//        GoogleFormUploader uploader = new GoogleFormUploader("1FAIpQLSdbu5lz54nMChDYzmKVD2kT-Fj0fo40ujeOjoQjfj9BR2vQog");// My own Google Sheet
        GoogleFormUploader uploader = new GoogleFormUploader("1FAIpQLSfXojnKiZMVJJkfVet8iD2xEeKkwyH9vPz_jOnuogrOui3syA");//The Official Google Sheet
        uploader.addEntry("410215655", dataAsString);//The Official Google Sheet
        uploader.addEntry("135689214", dataAsString);
        uploader.addEntry("974849132", String.valueOf(0));

        uploader.upload();
        Log.d(TAG,"Data was successfully sent ---------------------------");

        Toast.makeText(NewDataPageActivity.this, "The Data was sent", Toast.LENGTH_LONG).show();
        resetCSV();
    }

    // Delete all the saved Data on the Phone
    private void resetCSV() {
//        This code is For resetting the Data on the CSV file, it simply over wrights it with a blank and sets the dataAsString to Null.
        dataAsString = null;
        try {
            File folder = new File(getFilesDir(), "files");
//            folder.mkdirs();
            File file = new File(folder, FILE_NAME);
            FileOutputStream out = new FileOutputStream(file);
            out.write("".getBytes());
            out.flush();
            out.close();

            new Handler().postDelayed(() -> Toast.makeText(NewDataPageActivity.this, "The Data File Has Been Reset", Toast.LENGTH_LONG).show(), 1000);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
