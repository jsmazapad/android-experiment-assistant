package com.jsm.exptool.config;

import android.hardware.Sensor;

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


    public static final Map<Integer, Integer> TYPE_SENSORS = new HashMap<Integer, Integer>() {{
        put(TYPE_STATIONARY_DETECT, R.string.stationary_detect);
        put(TYPE_MOTION_DETECT, R.string.motion_detect);
        put(TYPE_HEART_BEAT, R.string.heart_beat);
        put(TYPE_HEART_RATE, R.string.heart_rate);
        put(TYPE_GEOMAGNETIC_ROTATION_VECTOR, R.string.geomagnetic_rotation_vector);
        put(TYPE_STEP_COUNTER, R.string.step_counter);
        put(TYPE_STEP_DETECTOR, R.string.step_detect);
        put(TYPE_SIGNIFICANT_MOTION, R.string.significant_motion);
        put(TYPE_GAME_ROTATION_VECTOR, R.string.game_rotation_vector);
        put(TYPE_AMBIENT_TEMPERATURE, R.string.ambient_temperature);
        put(TYPE_RELATIVE_HUMIDITY, R.string.relative_humidity);
        put(TYPE_ROTATION_VECTOR, R.string.rotation_vector);
        put(TYPE_LINEAR_ACCELERATION, R.string.linear_acceleration);
        put(TYPE_GRAVITY, R.string.gravity);
        put(TYPE_PROXIMITY, R.string.proximity);
        put(TYPE_TEMPERATURE,R.string.temperature);
        put(TYPE_PRESSURE, R.string.pressure);
        put(TYPE_LIGHT, R.string.light);
        put(TYPE_GYROSCOPE,R.string.gyroscope);
        put(TYPE_ORIENTATION, R.string.orientation);
        put(TYPE_MAGNETIC_FIELD, R.string.magnetic_field);
        put(TYPE_ACCELEROMETER, R.string.accelerometer);
        put(TYPE_GPS, R.string.gps);
    }};

}
