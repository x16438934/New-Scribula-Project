package com.getscriba.sampleapp.android.Game.Game;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.getscriba.sampleapp.android.Game.R;

import java.util.List;

public class Credit extends AppCompatActivity implements ScribaStylusManagerCallbacks {
    Button buttonback;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.credit);
         setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        View overLay = findViewById(R.id.credit);
        overLay.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN);

        buttonback = (Button) findViewById(R.id.buttonback);

    }
public void buttonback(View view){
    Intent intent = new Intent(Credit.this, PairActivity.class);
    startActivity(intent);
}

    @Override
    public void ensureBleEnabled() {

    }

    @Override
    public void whenDeviceNotSupported() {

    }

    @Override
    public void foundDevices(List<ScribaStylusDevice> devices) {

    }

    @Override
    public void connectedDevice(ScribaStylusDevice device) {

    }

    @Override
    public void disconnectedDevice(ScribaStylusDevice device) {

    }

    @Override
    public void updateBatteryStateOfDevice(ScribaStylusDevice device, int batteryState) {

    }

    @Override
    public void changeDepressionForDevice(ScribaStylusDevice device, float depression) {

    }

    @Override
    public void changeSqueezeZoneForDevice(ScribaStylusDevice device, int squeezeZone) {

    }

    @Override
    public void SingleClickWithDevice(ScribaStylusDevice device) {

    }

    @Override
    public void doubleClickWithDevice(ScribaStylusDevice device) {

    }

    @Override
    public void tripleClickWithDevice(ScribaStylusDevice device) {

    }
}
