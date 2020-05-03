package com.getscriba.sampleapp.android.game.game;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.getscriba.sampleapp.android.game.R;
import com.getscriba.sdk.android.scribasdk.ClickType;
import com.getscriba.sdk.android.scribasdk.ScribaStylusDevice;
import com.getscriba.sdk.android.scribasdk.ScribaStylusManager;
import com.getscriba.sdk.android.scribasdk.ScribaStylusManagerCallbacks;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by alannaogrady on 07/03/2018.
 */

public class PairActivity extends AppCompatActivity implements ScribaStylusManagerCallbacks, AdapterView.OnItemClickListener {
    private static final String TAG = PairActivity.class.getName();
    private static final int REQUEST_ENABLE_BT = 2;
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 456;

    private ScribaStylusManager mManager = ScribaStylusManager.getInstance(this);
    private ListView mListView;
    private ScribaDeviceAdapter mScribaAdapter;
    private boolean mConnected = false;
    private TextView mPermissionRationale;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    Button creditBtn;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ensureBleEnabled();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

//        View overLay = findViewById(R.id.pair);
//        overLay.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//                | View.SYSTEM_UI_FLAG_FULLSCREEN);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
        }

creditBtn = (Button) findViewById(R.id.creditBtn);

        setContentView(R.layout.activity_pair);
        this.setTitle(R.string.scriba_demo);
        mPermissionRationale = findViewById(R.id.permission_rationale);
        mListView = findViewById(R.id.listView);
        if (savedInstanceState != null) {
            mScribaAdapter = savedInstanceState.getParcelable("scriba");
            mListView.setAdapter(mScribaAdapter);
            mListView.setOnItemClickListener(PairActivity.this);
            mScribaAdapter.notifyDataSetChanged();
            mConnected = savedInstanceState.getBoolean("connected");
        }
        else{
            mScribaAdapter = new ScribaDeviceAdapter(this);
            mListView.setAdapter(mScribaAdapter);
            mListView.setOnItemClickListener(PairActivity.this);
        }


        mManager.addCallbackInterface(this);
        mManager.setUpGattCallBacks();
        tryStartScan();
    }

    private void tryStartScan() {
        if (isBLEEnabled()) {
            mManager.startScan();
            Log.d(TAG, "BLE enabled, scan started");
        } else {
            Log.d(TAG, "BLE not enabled");
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    PairActivity.this.tryStartScan();
                    Log.d(TAG, "retry ");
                }
            }, 200);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_COARSE_LOCATION: {
                if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted, yay! Start the Bluetooth device scan.
                    mManager.startScan();
                } else {
                    // Alert the user that this application requires the location permission to perform the scan.
                    mPermissionRationale.setVisibility(View.VISIBLE);
                    Toast.makeText(this, R.string.no_required_permission, Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mManager.stopScan();    //just incase scan not stopped
        mManager.removeCallbackInterface(this);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //want to reset scan variables as want to be able to rediscover device
        //when leaving the app as you would from this activity, should disconnect device (close connection and unregister receivers)
        mManager.disconnectDevice();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mConnected && mManager.mConnectedDevice != null) {
            mListView.setOnItemClickListener(PairActivity.this);
            mScribaAdapter.clearDevices();
            List<ScribaStylusDevice> connectedScriba = new ArrayList<ScribaStylusDevice>();
            connectedScriba.add(mManager.mConnectedDevice);
            mScribaAdapter.update(connectedScriba);
            mManager.enableHapticsBuzz(false);
            mManager.enableSqueezeZoneChangeBuzz(false);
            mManager.enableSmartLock(false);
        }
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mScribaAdapter = savedInstanceState.getParcelable("scriba");
        mListView.setAdapter(mScribaAdapter);
        mListView.setOnItemClickListener(PairActivity.this);
        mScribaAdapter.notifyDataSetChanged();
        mConnected = savedInstanceState.getBoolean("connected");
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("scriba", mScribaAdapter);
        outState.putBoolean("connected", mConnected);
    }


    public void ensureBleEnabled() {
        if (!isBLEEnabled()) {
            showBLEDialog();
        }
    }

    @Override
    public void foundDevices(List<ScribaStylusDevice> devices) {
        if (devices != null) {
            mScribaAdapter.update(devices);
            mScribaAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void connectedDevice(final ScribaStylusDevice device) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (device != null) {
                    Toast.makeText(PairActivity.this, "Connected with " + device.name(), Toast.LENGTH_SHORT).show();
                    mScribaAdapter.notifyDataSetChanged();
                    mConnected = true;
                    //startActivity(new Intent(PairActivity.this, InitialDemoActivity.class));
                    startActivity(new Intent(PairActivity.this, MainActivity.class));
                    mListView.setOnItemClickListener(PairActivity.this);
                }
                else
                    Toast.makeText(PairActivity.this, "connected device is null!!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void disconnectedDevice(final ScribaStylusDevice device) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (device != null) {
                    Toast.makeText(PairActivity.this, "Disconnected from " + device.name(), Toast.LENGTH_SHORT).show();
                    //in the case that device does not connect properly the first time, will be able to try again
                    mListView.setOnItemClickListener(PairActivity.this);
                }
                else
                    Toast.makeText(PairActivity.this, "disconnected device is null!!", Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void creditBtn(View view){
        Intent intent = new Intent(this, Credit.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
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
    public void clickWithDevice(ScribaStylusDevice device, ClickType clickType) {

    }

    @Override
    public void enabledLockConditionChange(boolean enabled) {

    }

    @Override
    public void lockStateChange(boolean lockOn) {

    }

    @Override
    public void brushSizeChange(float brushSize) {

    }

    private boolean isBLEEnabled() {
        final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        final BluetoothAdapter adapter = bluetoothManager.getAdapter();
        return adapter != null && adapter.isEnabled();
    }

    private void showBLEDialog() {
        final Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
    }

    @Override
    public void onItemClick (AdapterView<?> parent, View view, int position, long id){
        if (!mScribaAdapter.isEnabled(position)){
            return;
        }
        mListView.setOnItemClickListener(null);     //so that only one click gets registered rather than many which would cause multiple intents to be created
        ScribaStylusDevice device = (ScribaStylusDevice) mScribaAdapter.getItem(position);
        String scribaName = device.name();
        String scribaAddress = device.getBluetoothDevice().getAddress();
        Log.d(TAG, "Name: " + scribaName + " Address: " + scribaAddress);
        if (mConnected) {
            startActivity(new Intent(PairActivity.this, MainActivity.class));
        }
        else {
            mManager.tryToConnect(device);
        }
    }
}
