package com.getscriba.sampleapp.android.Game.Game;

import android.bluetooth.BluetoothDevice;
import android.os.Parcel;
import android.os.Parcelable;

import no.nordicsemi.android.ble.BleManager;

/**
 * Created by alannaogrady on 06/03/2018.
 */

public class ScribaStylusDevice implements Parcelable {
    public static final Creator<ScribaStylusDevice> CREATOR = new Creator<ScribaStylusDevice>() {
        @Override
        public ScribaStylusDevice createFromParcel(Parcel in) {
            return new ScribaStylusDevice(in);
        }

        @Override
        public ScribaStylusDevice[] newArray(int size) {
            return new ScribaStylusDevice[size];
        }
    };
    private int mRSSI;
    private BluetoothDevice mBluetoothDevice;
    private boolean mEnabledBluetoothNotifications;
    private BleManager mManager;
    private ScribaDeviceState mState;
    private boolean mConnectedBefore;

    protected ScribaStylusDevice(Parcel in) {
        mRSSI = in.readInt();
        mBluetoothDevice = in.readParcelable(BluetoothDevice.class.getClassLoader());
        mEnabledBluetoothNotifications = in.readByte() != 0;
        mState = in.readByte() == 1 ? ScribaDeviceState.SCRIBA_DEVICE_STATE_CONNECTED : ScribaDeviceState.SCRIBA_DEVICE_STATE_DISCONNECTED;
        mConnectedBefore = in.readByte() != 0;
    }

    public ScribaStylusDevice() {
        super();
    }

    public ScribaStylusDevice(BleManager bleManager, BluetoothDevice bluetoothDevice, int RSSI, boolean connectedBefore) {
        mBluetoothDevice = bluetoothDevice;
        mManager = bleManager;
        mRSSI = RSSI;
        mState = ScribaDeviceState.SCRIBA_DEVICE_STATE_DISCONNECTED;
        mConnectedBefore = connectedBefore;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mRSSI);
        dest.writeParcelable(mBluetoothDevice, flags);
        dest.writeByte((byte) (mEnabledBluetoothNotifications ? 1 : 0));
        dest.writeByte((byte) (mState == ScribaDeviceState.SCRIBA_DEVICE_STATE_CONNECTED ? 1 : 0));
        dest.writeByte((byte) (mConnectedBefore ? 1 : 0));
    }

    public int getRSSI() {
        return mRSSI;
    }

    public void setRSSI(int rssi) {
        this.mRSSI = rssi;
    }

    public BleManager getManager() {
        return mManager;
    }

    public void setManager(BleManager bleManager) {
        this.mManager = bleManager;
    }

    public BluetoothDevice getBluetoothDevice() {
        return mBluetoothDevice;
    }

    public void setBluetoothDevice(BluetoothDevice device) {
        this.mBluetoothDevice = device;
    }

    public ScribaDeviceState getState() {
        return mState;
    }

    public void setState(ScribaDeviceState state) {
        this.mState = state;
    }

    public boolean getConnectedBefore() {
        return mConnectedBefore;
    }

    public void setConnectedBefore(boolean connectedBefore) {
        this.mConnectedBefore = connectedBefore;
    }

    public boolean getEnabledBluetoothNotifications() {
        return mEnabledBluetoothNotifications;
    }

    public void setEnabledBluetoothNotifications(boolean enabled) {
        this.mEnabledBluetoothNotifications = true;
    }

    public String name() {
        if (mBluetoothDevice != null) {
            String name = mBluetoothDevice.getName();
            if (name != null)
                return name;
        }
        return null;
    }

    public float getScribaDeviceVersion() {
        float version = 0;
        String name = name();
        if (name != null) {
            //remove "Scriba V" to be left with version as string
            String filteredName = name.replace("Scriba V", "");
            if (!filteredName.equals("")) {     //checking thee is a version to be converted
                version = Float.parseFloat(filteredName);
            }
        }
        return version;
    }

    public String address() {
        if (mBluetoothDevice != null) {
            String deviceAddress = mBluetoothDevice.getAddress();
            if (deviceAddress != null)
                return deviceAddress;
        }
        return null;
    }

    public enum ScribaDeviceState {
        SCRIBA_DEVICE_STATE_CONNECTED,
        SCRIBA_DEVICE_STATE_DISCONNECTED
    }

}
