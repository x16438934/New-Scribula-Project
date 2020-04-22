package com.getscriba.sampleapp.android.Game.Game;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.getscriba.sampleapp.android.Game.R;

public class AgeAndGender extends AppCompatActivity {
//    tutorial that I did to make a popup https://www.youtube.com/watch?v=fn5OlqQuOCk&ab_channel=FilipVujovic

    private String userGender;
    private int userAge;
    private SharedPreferences settings;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_age_and_gender);
        settings = getSharedPreferences("UserInfo", 0);
        userAge = 0;
        userGender = "Female";

//        used this tutorial to get a number picker https://www.zoftino.com/android-number-picker-tutorial.
        NumberPicker mNumberPicker = findViewById(R.id.numberPicker);
        mNumberPicker.setValue(userAge);
        mNumberPicker.setMinValue(0);
        mNumberPicker.setMaxValue(99);
        mNumberPicker.setOnValueChangedListener(onValueChangeListener);

        Button btnChangeAgeGender = findViewById(R.id.save_btn);
        btnChangeAgeGender.setOnClickListener(v -> saveGenderAge());
    }


    private NumberPicker.OnValueChangeListener onValueChangeListener =
            (numberPicker, i, i1) -> userAge = numberPicker.getValue();

    public void onGenderButton(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.male_btn:
                if (checked) {
                    userGender = "Male";
                }
                break;
            case R.id.female_btn:
                if (checked) {
                    userGender = "Female";
                }
                break;
        }
    }

    private void saveGenderAge() {
//      used code from this website to save the Age and Gender of the user
//      https://stackoverflow.com/questions/10209814/saving-user-information-in-app-settings
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("userAge", userAge);
        editor.putString("userGender", userGender);
        editor.apply();
        Toast.makeText(this, "The users Age was Saved as: "+userAge+" and Gender was Saved As: "+ userGender, Toast.LENGTH_SHORT).show();
        this.finish();
    }

}
