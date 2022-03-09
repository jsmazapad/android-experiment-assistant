package com.jsm.exptool.model;

import android.hardware.SensorEvent;

import java.util.SortedMap;


public interface SensorEventInterface {
    void readSensor(SensorEvent event, SortedMap<String, Float> measure);
}
