package com.jsm.exptool.config;

import android.hardware.Sensor;

import androidx.annotation.StringRes;

import com.jsm.exptool.R;

import java.util.HashMap;
import java.util.Map;

public class SensorConfigConstants {

    public static final int TYPE_STATIONARY_DETECT = Sensor.TYPE_STATIONARY_DETECT;
    public static final int TYPE_MOTION_DETECT = Sensor.TYPE_MOTION_DETECT;
    public static final int TYPE_HEART_BEAT = Sensor.TYPE_HEART_BEAT;
    public static final int TYPE_HEART_RATE = Sensor.TYPE_HEART_RATE;
    public static final int TYPE_GEOMAGNETIC_ROTATION_VECTOR = Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR;
    public static final int TYPE_STEP_COUNTER = Sensor.TYPE_STEP_COUNTER;
    public static final int TYPE_STEP_DETECTOR = Sensor.TYPE_STEP_DETECTOR;
    public static final int TYPE_SIGNIFICANT_MOTION = Sensor.TYPE_SIGNIFICANT_MOTION;
    public static final int TYPE_GAME_ROTATION_VECTOR = Sensor.TYPE_GAME_ROTATION_VECTOR;
    public static final int TYPE_AMBIENT_TEMPERATURE = Sensor.TYPE_AMBIENT_TEMPERATURE;
    public static final int TYPE_RELATIVE_HUMIDITY = Sensor.TYPE_RELATIVE_HUMIDITY;
    public static final int TYPE_ROTATION_VECTOR = Sensor.TYPE_ROTATION_VECTOR;
    public static final int TYPE_LINEAR_ACCELERATION = Sensor.TYPE_LINEAR_ACCELERATION;
    public static final int TYPE_GRAVITY = Sensor.TYPE_GRAVITY;
    public static final int TYPE_PROXIMITY = Sensor.TYPE_PROXIMITY;
    public static final int TYPE_TEMPERATURE = Sensor.TYPE_TEMPERATURE;
    public static final int TYPE_PRESSURE = Sensor.TYPE_PRESSURE;
    public static final int TYPE_LIGHT = Sensor.TYPE_LIGHT;
    public static final int TYPE_GYROSCOPE = Sensor.TYPE_GYROSCOPE;
    public static final int TYPE_ORIENTATION = Sensor.TYPE_ORIENTATION;
    public static final int TYPE_MAGNETIC_FIELD = Sensor.TYPE_MAGNETIC_FIELD;
    public static final int TYPE_ACCELEROMETER = Sensor.TYPE_ACCELEROMETER;
    public static final int TYPE_GPS = 0;
    public static final int TYPE_CAMERA = 1000;
    public static final int TYPE_AUDIO= 1001;

    public static final String TYPE_GPS_SENSOR_NAME = "Ubicación";

    public static @StringRes int  getResStringFromTypeSensor(int type){
        Integer resToReturn = TYPE_SENSORS_TO_RES_STRING.get(type);
        if(resToReturn == null){
            resToReturn = R.string.undetermined;
        }
        return resToReturn;
    }



    private static final Map<Integer, Integer> TYPE_SENSORS_TO_RES_STRING = createTypeSensorsToResMap();

    public static final Map<Integer, String> TYPE_SENSORS_TO_TYPE_STRING = createTypeSensorsToTypeStringMap();

    private static Map<Integer, Integer> createTypeSensorsToResMap(){

        HashMap<Integer, Integer> mapToReturn = new HashMap<>();
        mapToReturn.put(TYPE_STATIONARY_DETECT, R.string.stationary_detect);
        mapToReturn.put(TYPE_MOTION_DETECT, R.string.motion_detect);
        mapToReturn.put(TYPE_HEART_BEAT, R.string.heart_beat);
        mapToReturn.put(TYPE_HEART_RATE, R.string.heart_rate);
        mapToReturn.put(TYPE_GEOMAGNETIC_ROTATION_VECTOR, R.string.geomagnetic_rotation_vector);
        mapToReturn.put(TYPE_STEP_COUNTER, R.string.step_counter);
        mapToReturn.put(TYPE_STEP_DETECTOR, R.string.step_detect);
        mapToReturn.put(TYPE_SIGNIFICANT_MOTION, R.string.significant_motion);
        mapToReturn.put(TYPE_GAME_ROTATION_VECTOR, R.string.game_rotation_vector);
        mapToReturn.put(TYPE_AMBIENT_TEMPERATURE, R.string.ambient_temperature);
        mapToReturn.put(TYPE_RELATIVE_HUMIDITY, R.string.relative_humidity);
        mapToReturn.put(TYPE_ROTATION_VECTOR, R.string.rotation_vector);
        mapToReturn.put(TYPE_LINEAR_ACCELERATION, R.string.linear_acceleration);
        mapToReturn.put(TYPE_GRAVITY, R.string.gravity);
        mapToReturn.put(TYPE_PROXIMITY, R.string.proximity);
        mapToReturn.put(TYPE_TEMPERATURE,R.string.temperature);
        mapToReturn.put(TYPE_PRESSURE, R.string.pressure);
        mapToReturn.put(TYPE_LIGHT, R.string.light);
        mapToReturn.put(TYPE_GYROSCOPE,R.string.gyroscope);
        mapToReturn.put(TYPE_ORIENTATION, R.string.orientation);
        mapToReturn.put(TYPE_MAGNETIC_FIELD, R.string.magnetic_field);
        mapToReturn.put(TYPE_ACCELEROMETER, R.string.accelerometer);
        mapToReturn.put(TYPE_GPS, R.string.location);
        mapToReturn.put(TYPE_CAMERA, R.string.camera);
        mapToReturn.put(TYPE_AUDIO, R.string.audio);
        return mapToReturn;
    }

    private static final Map<Integer, String> createTypeSensorsToTypeStringMap(){

        HashMap<Integer, String> mapToReturn = new HashMap<>();
        mapToReturn.put(TYPE_STATIONARY_DETECT, "TYPE_STATIONARY_DETECT");
        mapToReturn.put(TYPE_MOTION_DETECT, "TYPE_MOTION_DETECT");
        mapToReturn.put(TYPE_HEART_BEAT, "TYPE_HEART_BEAT");
        mapToReturn.put(TYPE_HEART_RATE, "TYPE_HEART_RATE");
        mapToReturn.put(TYPE_GEOMAGNETIC_ROTATION_VECTOR, "TYPE_GEOMAGNETIC_ROTATION_VECTOR");
        mapToReturn.put(TYPE_STEP_COUNTER, "TYPE_STEP_COUNTER");
        mapToReturn.put(TYPE_STEP_DETECTOR, "TYPE_STEP_DETECTOR");
        mapToReturn.put(TYPE_SIGNIFICANT_MOTION, "TYPE_SIGNIFICANT_MOTION");
        mapToReturn.put(TYPE_GAME_ROTATION_VECTOR, "TYPE_GAME_ROTATION_VECTOR");
        mapToReturn.put(TYPE_AMBIENT_TEMPERATURE, "TYPE_AMBIENT_TEMPERATURE");
        mapToReturn.put(TYPE_RELATIVE_HUMIDITY,"TYPE_RELATIVE_HUMIDITY");
        mapToReturn.put(TYPE_ROTATION_VECTOR, "TYPE_ROTATION_VECTOR");
        mapToReturn.put(TYPE_LINEAR_ACCELERATION, "TYPE_LINEAR_ACCELERATION");
        mapToReturn.put(TYPE_GRAVITY, "TYPE_GRAVITY");
        mapToReturn.put(TYPE_PROXIMITY, "TYPE_PROXIMITY");
        mapToReturn.put(TYPE_TEMPERATURE,"TYPE_TEMPERATURE");
        mapToReturn.put(TYPE_PRESSURE, "TYPE_PRESSURE");
        mapToReturn.put(TYPE_LIGHT, "TYPE_LIGHT");
        mapToReturn.put(TYPE_GYROSCOPE,"TYPE_GYROSCOPE");
        mapToReturn.put(TYPE_ORIENTATION, "TYPE_ORIENTATION");
        mapToReturn.put(TYPE_MAGNETIC_FIELD, "TYPE_MAGNETIC_FIELD");
        mapToReturn.put(TYPE_ACCELEROMETER, "TYPE_ACCELEROMETER");
        mapToReturn.put(TYPE_GPS, "TYPE_GPS");
        mapToReturn.put(TYPE_CAMERA, "TYPE_CAMERA");
        mapToReturn.put(TYPE_AUDIO, "TYPE_AUDIO");
        return mapToReturn;
    }

}
