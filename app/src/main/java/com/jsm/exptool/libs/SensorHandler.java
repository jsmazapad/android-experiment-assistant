package com.jsm.exptool.libs;

import static android.content.Context.SENSOR_SERVICE;

import android.content.Context;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.os.Build;

import com.jsm.exptool.R;
import com.jsm.exptool.config.FrequencyConstants;
import com.jsm.exptool.config.MeasureConfigConstants;
import com.jsm.exptool.config.SensorConfigConstants;
import com.jsm.exptool.model.SensorConfig;
import com.jsm.exptool.model.SensorEventInterface;
import com.jsm.exptool.model.TriggerEventInterface;
import com.jsm.exptool.model.experimentconfig.RepeatableElementConfig;
import com.jsm.exptool.model.externaleventsreader.SensorReader;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class SensorHandler {
    private SensorManager sensorManager;
    private LocationManager locationManager;
    private List<SensorConfig> sensors = new ArrayList();
    private static SensorHandler instance;

    public static SensorHandler getInstance(){
        if (instance == null){
            instance = new SensorHandler();
        }
        return instance;
    }

    public void initialize(Context context)
    {
        this.sensorManager = (SensorManager) context.getSystemService(SENSOR_SERVICE);
        this.locationManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
        createAvailableSensorsList();
    }

    private SensorHandler() {

    }



    public SensorManager getSensorManager() {
        return sensorManager;
    }

    public List<SensorConfig> getSensors() {
        return this.sensors;
    }

    public SensorReader getSensorReader(int type){
        SensorReader sensorReader = null;
        for (SensorConfig sensorConfig:sensors) {
            if (sensorConfig.getSensorReader().getSensorType() == type)
            {
                sensorReader = sensorConfig.getSensorReader();
                break;
            }
        }
        return sensorReader;
    }

    /**
     * Crea listado de sensores disponibles para el dispositivo
     * Mas info en @linktourl https://developer.android.com/reference/android/hardware/SensorEvent#values
     */
    private void createAvailableSensorsList(){


        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_ACCELEROMETER) != null
                && sensorManager.getSensorList(SensorConfigConstants.TYPE_ACCELEROMETER).size() > 0) {
            sensors.add(new SensorConfig(new SensorReader(SensorConfigConstants.TYPE_ACCELEROMETER, measureSpatialValues, (SortedMap<String, Float>) spatialValues.clone()),  R.string.accelerometer, FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS, FrequencyConstants.MAX_SENSOR_INTERVAL_MILLIS));
        }

        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_MAGNETIC_FIELD) != null
                && sensorManager.getSensorList(SensorConfigConstants.TYPE_MAGNETIC_FIELD).size() > 0) {
            sensors.add(new SensorConfig(new SensorReader(SensorConfigConstants.TYPE_MAGNETIC_FIELD, measureSpatialValues, (SortedMap<String, Float>) spatialValues.clone()), R.string.magnetic_field, FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS,FrequencyConstants.MAX_SENSOR_INTERVAL_MILLIS));

        }

        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_ORIENTATION) != null
                && sensorManager.getSensorList(SensorConfigConstants.TYPE_ORIENTATION).size() > 0) {
            sensors.add(new SensorConfig(new SensorReader(SensorConfigConstants.TYPE_ORIENTATION, measureSpatialValues, (SortedMap<String, Float>) spatialValues.clone()), R.string.orientation, FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS, FrequencyConstants.MAX_SENSOR_INTERVAL_MILLIS));
        }

        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_GYROSCOPE) != null
                && sensorManager.getSensorList(SensorConfigConstants.TYPE_GYROSCOPE).size() > 0) {
            sensors.add(new SensorConfig(new SensorReader(SensorConfigConstants.TYPE_GYROSCOPE, measureSpatialValues, (SortedMap<String, Float>) spatialValues.clone()), R.string.gyroscope, FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS, FrequencyConstants.MAX_SENSOR_INTERVAL_MILLIS));
        }

        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_LIGHT) != null
                && sensorManager.getSensorList(SensorConfigConstants.TYPE_LIGHT).size() > 0) {
            sensors.add(new SensorConfig(new SensorReader(SensorConfigConstants.TYPE_LIGHT, measureOneValue, createOneValueMap(MeasureConfigConstants.ILLUMINANCE)), R.string.light, FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS, FrequencyConstants.MAX_SENSOR_INTERVAL_MILLIS));
        }

        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_PRESSURE) != null
                && sensorManager.getSensorList(SensorConfigConstants.TYPE_PRESSURE).size() > 0){
            sensors.add(new SensorConfig(new SensorReader(SensorConfigConstants.TYPE_PRESSURE,  measureOneValue, createOneValueMap(MeasureConfigConstants.PRESSURE)), R.string.pressure, FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS, FrequencyConstants.MAX_SENSOR_INTERVAL_MILLIS));
        }

        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_TEMPERATURE) != null
                && sensorManager.getSensorList(SensorConfigConstants.TYPE_TEMPERATURE).size() > 0){
            sensors.add(new SensorConfig(new SensorReader(SensorConfigConstants.TYPE_TEMPERATURE, measureOneValue, createOneValueMap(MeasureConfigConstants.TEMPERATURE)), R.string.temperature, FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS,FrequencyConstants.MAX_SENSOR_INTERVAL_MILLIS ));
        }

        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_PROXIMITY) != null
                && sensorManager.getSensorList(SensorConfigConstants.TYPE_PROXIMITY).size() > 0){
            sensors.add(new SensorConfig(new SensorReader(SensorConfigConstants.TYPE_PROXIMITY, measureOneValue, createOneValueMap(MeasureConfigConstants.PROXIMITY)), R.string.proximity, FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS, FrequencyConstants.MAX_SENSOR_INTERVAL_MILLIS));
        }

        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_GRAVITY) != null
                && sensorManager.getSensorList(SensorConfigConstants.TYPE_GRAVITY).size() > 0){
            sensors.add(new SensorConfig(new SensorReader(SensorConfigConstants.TYPE_GRAVITY,  measureSpatialValues, (SortedMap<String, Float>) spatialValues.clone()), R.string.gravity, FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS, FrequencyConstants.MAX_SENSOR_INTERVAL_MILLIS));
        }

        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_LINEAR_ACCELERATION) != null
                && sensorManager.getSensorList(SensorConfigConstants.TYPE_LINEAR_ACCELERATION).size() > 0){
            sensors.add(new SensorConfig(new SensorReader(SensorConfigConstants.TYPE_LINEAR_ACCELERATION, measureSpatialValues, (SortedMap<String, Float>) spatialValues.clone()), R.string.linear_acceleration, FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS, FrequencyConstants.MAX_SENSOR_INTERVAL_MILLIS));
        }

        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_ROTATION_VECTOR) != null
                && sensorManager.getSensorList(SensorConfigConstants.TYPE_ROTATION_VECTOR).size() > 0){
            sensors.add(new SensorConfig(new SensorReader(SensorConfigConstants.TYPE_ROTATION_VECTOR,  measureSpatialValues, (SortedMap<String, Float>) spatialValues.clone()), R.string.rotation_vector, FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS,FrequencyConstants.MAX_SENSOR_INTERVAL_MILLIS));
        }

        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_RELATIVE_HUMIDITY) != null
                && sensorManager.getSensorList(SensorConfigConstants.TYPE_RELATIVE_HUMIDITY).size() > 0){
            sensors.add(new SensorConfig(new SensorReader(SensorConfigConstants.TYPE_RELATIVE_HUMIDITY, measureOneValue, createOneValueMap(MeasureConfigConstants.HUMIDITY)), R.string.relative_humidity, FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS, FrequencyConstants.MAX_SENSOR_INTERVAL_MILLIS));
        }

        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_AMBIENT_TEMPERATURE) != null
                && sensorManager.getSensorList(SensorConfigConstants.TYPE_AMBIENT_TEMPERATURE).size() > 0){
            sensors.add(new SensorConfig(new SensorReader(SensorConfigConstants.TYPE_AMBIENT_TEMPERATURE, measureOneValue, createOneValueMap(MeasureConfigConstants.TEMPERATURE)), R.string.ambient_temperature, FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS, FrequencyConstants.MAX_SENSOR_INTERVAL_MILLIS));
        }

        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_GAME_ROTATION_VECTOR) != null
                && sensorManager.getSensorList(SensorConfigConstants.TYPE_GAME_ROTATION_VECTOR).size() > 0){
            sensors.add(new SensorConfig(new SensorReader(SensorConfigConstants.TYPE_GAME_ROTATION_VECTOR, measureSpatialValues, (SortedMap<String, Float>) spatialValues.clone()), R.string.game_rotation_vector, FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS, FrequencyConstants.MAX_SENSOR_INTERVAL_MILLIS));

        }

        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_SIGNIFICANT_MOTION) != null
                && sensorManager.getSensorList(SensorConfigConstants.TYPE_SIGNIFICANT_MOTION).size() > 0){
            sensors.add(new SensorConfig(new SensorReader(SensorConfigConstants.TYPE_SIGNIFICANT_MOTION, measureTriggeredValue, createOneValueMap(MeasureConfigConstants.SIGNIFICANT_MOTION), true), R.string.significant_motion, FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS, FrequencyConstants.MAX_SENSOR_INTERVAL_MILLIS));
        }

        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_STEP_DETECTOR) != null
                && sensorManager.getSensorList(SensorConfigConstants.TYPE_STEP_DETECTOR).size() > 0){
            sensors.add(new SensorConfig(new SensorReader(SensorConfigConstants.TYPE_STEP_DETECTOR, measureOneTriggeredValueSum, createOneValueMap(MeasureConfigConstants.STEP_DETECTED), true), R.string.step_detect, FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS, FrequencyConstants.MAX_SENSOR_INTERVAL_MILLIS));
        }

        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_STEP_COUNTER) != null
                && sensorManager.getSensorList(SensorConfigConstants.TYPE_STEP_COUNTER).size() > 0){
            sensors.add(new SensorConfig(new SensorReader(SensorConfigConstants.TYPE_STEP_COUNTER,  measureOneTriggeredValueSum, createOneValueMap(MeasureConfigConstants.STEP_COUNTED), true), R.string.step_detect, FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS,FrequencyConstants.MAX_SENSOR_INTERVAL_MILLIS));
        }


        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_GEOMAGNETIC_ROTATION_VECTOR) != null
                && sensorManager.getSensorList(SensorConfigConstants.TYPE_GEOMAGNETIC_ROTATION_VECTOR).size() > 0){
            sensors.add(new SensorConfig(new SensorReader(SensorConfigConstants.TYPE_GEOMAGNETIC_ROTATION_VECTOR, measureSpatialValues, (SortedMap<String, Float>) spatialValues.clone()), R.string.geomagnetic_rotation_vector, FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS, FrequencyConstants.MAX_SENSOR_INTERVAL_MILLIS));
        }

        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_HEART_RATE) != null
                && sensorManager.getSensorList(SensorConfigConstants.TYPE_HEART_RATE).size() > 0){
            sensors.add(new SensorConfig(new SensorReader(SensorConfigConstants.TYPE_HEART_RATE, measureOneValue, createOneValueMap(MeasureConfigConstants.HEART_RATE)), R.string.heart_rate, FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS, FrequencyConstants.MAX_SENSOR_INTERVAL_MILLIS));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_STATIONARY_DETECT) != null
                    && sensorManager.getSensorList(SensorConfigConstants.TYPE_STATIONARY_DETECT).size() > 0){
                sensors.add(new SensorConfig(new SensorReader(SensorConfigConstants.TYPE_STATIONARY_DETECT, measureTriggeredValue, createOneValueMap(MeasureConfigConstants.STATIONARY), true), R.string.stationary_detect, FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS, FrequencyConstants.MAX_SENSOR_INTERVAL_MILLIS));
            }

            if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_MOTION_DETECT) != null
                    && sensorManager.getSensorList(SensorConfigConstants.TYPE_MOTION_DETECT).size() > 0){
                sensors.add(new SensorConfig(new SensorReader(SensorConfigConstants.TYPE_MOTION_DETECT, measureTriggeredValue, createOneValueMap(MeasureConfigConstants.MOTION_DETECTED), true), R.string.stationary_detect, FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS, FrequencyConstants.MAX_SENSOR_INTERVAL_MILLIS));
            }

            if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_HEART_BEAT) != null
                    && sensorManager.getSensorList(SensorConfigConstants.TYPE_HEART_BEAT).size() > 0){
                sensors.add(new SensorConfig(new SensorReader(SensorConfigConstants.TYPE_HEART_BEAT,  measureOneValue, createOneValueMap(MeasureConfigConstants.HEART_BEAT)),  R.string.heart_beat, FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS, FrequencyConstants.MAX_SENSOR_INTERVAL_MILLIS));
            }
//
        }
    }

    /*
    MEDICIÓN DE SENSORES NO DISPARADOS
     */

    private final TreeMap<String,Float> spatialValues = new TreeMap<String, Float>() {{
        put(MeasureConfigConstants.POSITION_X, 0f);
        put(MeasureConfigConstants.POSITION_Y, 0f);
        put(MeasureConfigConstants.POSITION_Z, 0f);

    }};

    private final SortedMap<String,Float> createOneValueMap(String key){
        return new TreeMap<String, Float>() {{
            put(key, 0f);

        }};
    }

    private final SensorEventInterface measureSpatialValues = (event, measure) -> {
        measure.put(MeasureConfigConstants.POSITION_X, event.values[0]);
        measure.put(MeasureConfigConstants.POSITION_Y, event.values[1]);
        measure.put(MeasureConfigConstants.POSITION_Z, event.values[2]);
    };

    private final SensorEventInterface measureOneValue = (event, measure) -> {
        measure.put(measure.firstKey(), event.values[0]);
    };

    private final SensorEventInterface measureOneValueSum = (event, measure) -> {
        measure.put(measure.firstKey(), (measure.get(measure.firstKey()) == null ? 0f : measure.get(measure.firstKey()))  + event.values[0]);
    };

     /*
    MEDICIÓN DE SENSORES DISPARADOS
     */

    private final TriggerEventInterface measureOneTriggeredValueSum = (event, measure) -> {
        measure.put(measure.firstKey(), (measure.get(measure.firstKey()) == null ? 0f : measure.get(measure.firstKey()))  + event.values[0]);
    };
    private final TriggerEventInterface measureTriggeredValue = (event, measure) -> {
        measure.put(measure.firstKey(), event.values[0]);
    };

}
