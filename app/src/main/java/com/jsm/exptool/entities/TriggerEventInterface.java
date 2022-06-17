package com.jsm.exptool.entities;

import android.hardware.TriggerEvent;

import java.util.SortedMap;

public interface TriggerEventInterface {
    void readTriggeredSensor(TriggerEvent event, SortedMap<String, Float> measure);

}
