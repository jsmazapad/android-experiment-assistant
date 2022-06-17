package com.jsm.exptool.entities;

import android.hardware.SensorEvent;

import java.util.SortedMap;


public interface SensorEventInterface {
    void readSensor(SensorEvent event, SortedMap<String, Float> measure);
}
