package com.getscriba.sampleapp.android.Game.Game;

import java.util.List;

/**
 * Created by alannaogrady on 06/03/2018.
 */

public interface ScribaStylusManagerCallbacks {

    void ensureBleEnabled();

    void whenDeviceNotSupported();

    void foundDevices(List<ScribaStylusDevice> devices);


    void connectedDevice(ScribaStylusDevice device);


    void disconnectedDevice(ScribaStylusDevice device);


    void updateBatteryStateOfDevice(ScribaStylusDevice device, int batteryState);


    void changeDepressionForDevice(ScribaStylusDevice device, float depression);


    void changeSqueezeZoneForDevice(ScribaStylusDevice device, int squeezeZone);


    void SingleClickWithDevice(ScribaStylusDevice device);


    void doubleClickWithDevice(ScribaStylusDevice device);


    void tripleClickWithDevice(ScribaStylusDevice device);
}
