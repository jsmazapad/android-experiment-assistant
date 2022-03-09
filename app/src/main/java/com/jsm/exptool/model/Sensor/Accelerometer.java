package com.jsm.exptool.model.Sensor;

import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;


import com.jsm.exptool.config.FrequencyConstants;
import com.jsm.exptool.config.MeasureConfigConstants;
import com.jsm.exptool.config.SensorConfigConstants;
import com.jsm.exptool.model.MySensor;

import com.jsm.exptool.R;

import java.util.TreeMap;

//public class Accelerometer extends MySensor implements SensorEventListener {
//
//    public Accelerometer() {
//        super(SensorConfigConstants.TYPE_ACCELEROMETER, R.string.accelerometer, FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS);
//
//
//        measure = new TreeMap<String, Float>() {{
//            put(MeasureConfigConstants.POSITION_X, 0f);
//            put(MeasureConfigConstants.POSITION_Y, 0f);
//            put(MeasureConfigConstants.POSITION_Z, 0f);
//
//        }};
//
//    }
//
//
//    @Override
//    public void onSensorChanged(SensorEvent event) {
//        if (event.sensor.getType() == sensorType) {
//            measure.put(MeasureConfigConstants.POSITION_X, event.values[0]);
//            measure.put(MeasureConfigConstants.POSITION_Y, event.values[0]);
//            measure.put(MeasureConfigConstants.POSITION_Z, event.values[0]);
//            this.accuracy = event.accuracy;
//        }
//    }
//
//}
