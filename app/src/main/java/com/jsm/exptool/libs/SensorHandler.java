package com.jsm.exptool.libs;

import static android.content.Context.SENSOR_SERVICE;

import android.content.Context;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.os.Build;

import com.jsm.exptool.App;
import com.jsm.exptool.R;
import com.jsm.exptool.config.FrequencyConstants;
import com.jsm.exptool.config.MeasureConfigConstants;
import com.jsm.exptool.config.SensorConfigConstants;
import com.jsm.exptool.model.MySensor;
import com.jsm.exptool.model.SensorEventInterface;
import com.jsm.exptool.model.TriggerEventInterface;
import com.jsm.exptool.model.experimentconfig.RepeatableElement;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class SensorHandler {
    private SensorManager sensorManager;
    private LocationManager locationManager;
    private List<MySensor> sensors = new ArrayList();
    private ArrayList<RepeatableElement> selectedSensors = new ArrayList();
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

    public List<MySensor> getSensors() {
        return this.sensors;
    }

    public boolean hasSelectedSensor() {
        if (!this.selectedSensors.isEmpty()){
            return true;
        }else{
            return false;
        }
    }

    public ArrayList<RepeatableElement> getSelectedSensors() {
        return this.selectedSensors;
    }

    public void setSelectedSensors(ArrayList <MySensor> newSelectedSensors) throws CloneNotSupportedException {
        clearSelectedSensors();
        for (MySensor sensor : newSelectedSensors){
            this.selectedSensors.add((MySensor) sensor.clone());
        }
    }

    public void clearSelectedSensors(){
        this.selectedSensors.clear();
    }


    /**
     * Crea listado de sensores disponibles para el dispositivo
     * Mas info en @linktourl https://developer.android.com/reference/android/hardware/SensorEvent#values
     */
    private void createAvailableSensorsList(){
        //TODO, que hacer con GPS
//        sensors.add(new GPS());

        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_ACCELEROMETER) != null
                && sensorManager.getSensorList(SensorConfigConstants.TYPE_ACCELEROMETER).size() > 0) {
            sensors.add(new MySensor(SensorConfigConstants.TYPE_ACCELEROMETER, R.string.accelerometer, FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS, measureSpatialValues , (SortedMap<String, Float>) spatialValues.clone()));
        }

        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_MAGNETIC_FIELD) != null
                && sensorManager.getSensorList(SensorConfigConstants.TYPE_MAGNETIC_FIELD).size() > 0) {
            sensors.add(new MySensor(SensorConfigConstants.TYPE_MAGNETIC_FIELD, R.string.magnetic_field, FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS, measureSpatialValues , (SortedMap<String, Float>) spatialValues.clone()));

        }

        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_ORIENTATION) != null
                && sensorManager.getSensorList(SensorConfigConstants.TYPE_ORIENTATION).size() > 0) {
            sensors.add(new MySensor(SensorConfigConstants.TYPE_ORIENTATION, R.string.orientation, FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS, measureSpatialValues , (SortedMap<String, Float>) spatialValues.clone()));
        }

        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_GYROSCOPE) != null
                && sensorManager.getSensorList(SensorConfigConstants.TYPE_GYROSCOPE).size() > 0) {
            sensors.add(new MySensor(SensorConfigConstants.TYPE_GYROSCOPE, R.string.gyroscope, FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS, measureSpatialValues , (SortedMap<String, Float>) spatialValues.clone()));
        }

        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_LIGHT) != null
                && sensorManager.getSensorList(SensorConfigConstants.TYPE_LIGHT).size() > 0) {
            sensors.add(new MySensor(SensorConfigConstants.TYPE_LIGHT, R.string.light, FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS, measureOneValue , createOneValueMap(MeasureConfigConstants.ILLUMINANCE)));
        }

        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_PRESSURE) != null
                && sensorManager.getSensorList(SensorConfigConstants.TYPE_PRESSURE).size() > 0){
            sensors.add(new MySensor(SensorConfigConstants.TYPE_PRESSURE, R.string.pressure, FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS, measureOneValue , createOneValueMap(MeasureConfigConstants.PRESSURE)));
        }

        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_TEMPERATURE) != null
                && sensorManager.getSensorList(SensorConfigConstants.TYPE_TEMPERATURE).size() > 0){
            sensors.add(new MySensor(SensorConfigConstants.TYPE_TEMPERATURE, R.string.temperature, FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS, measureOneValue , createOneValueMap(MeasureConfigConstants.TEMPERATURE)));
        }

        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_PROXIMITY) != null
                && sensorManager.getSensorList(SensorConfigConstants.TYPE_PROXIMITY).size() > 0){
            sensors.add(new MySensor(SensorConfigConstants.TYPE_PROXIMITY, R.string.proximity, FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS, measureOneValue , createOneValueMap(MeasureConfigConstants.PROXIMITY)));
        }

        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_GRAVITY) != null
                && sensorManager.getSensorList(SensorConfigConstants.TYPE_GRAVITY).size() > 0){
            sensors.add(new MySensor(SensorConfigConstants.TYPE_GRAVITY, R.string.gravity, FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS, measureSpatialValues , (SortedMap<String, Float>) spatialValues.clone()));
        }

        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_LINEAR_ACCELERATION) != null
                && sensorManager.getSensorList(SensorConfigConstants.TYPE_LINEAR_ACCELERATION).size() > 0){
            sensors.add(new MySensor(SensorConfigConstants.TYPE_LINEAR_ACCELERATION, R.string.linear_acceleration, FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS, measureSpatialValues , (SortedMap<String, Float>) spatialValues.clone()));
        }

        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_ROTATION_VECTOR) != null
                && sensorManager.getSensorList(SensorConfigConstants.TYPE_ROTATION_VECTOR).size() > 0){
            sensors.add(new MySensor(SensorConfigConstants.TYPE_ROTATION_VECTOR, R.string.rotation_vector, FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS, measureSpatialValues , (SortedMap<String, Float>) spatialValues.clone()));
        }

        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_RELATIVE_HUMIDITY) != null
                && sensorManager.getSensorList(SensorConfigConstants.TYPE_RELATIVE_HUMIDITY).size() > 0){
            sensors.add(new MySensor(SensorConfigConstants.TYPE_RELATIVE_HUMIDITY, R.string.relative_humidity, FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS, measureOneValue , createOneValueMap(MeasureConfigConstants.HUMIDITY)));
        }

        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_AMBIENT_TEMPERATURE) != null
                && sensorManager.getSensorList(SensorConfigConstants.TYPE_AMBIENT_TEMPERATURE).size() > 0){
            sensors.add(new MySensor(SensorConfigConstants.TYPE_AMBIENT_TEMPERATURE, R.string.ambient_temperature, FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS, measureOneValue , createOneValueMap(MeasureConfigConstants.TEMPERATURE)));
        }

        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_GAME_ROTATION_VECTOR) != null
                && sensorManager.getSensorList(SensorConfigConstants.TYPE_GAME_ROTATION_VECTOR).size() > 0){
            sensors.add(new MySensor(SensorConfigConstants.TYPE_GAME_ROTATION_VECTOR, R.string.game_rotation_vector, FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS, measureSpatialValues , (SortedMap<String, Float>) spatialValues.clone()));

        }

        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_SIGNIFICANT_MOTION) != null
                && sensorManager.getSensorList(SensorConfigConstants.TYPE_SIGNIFICANT_MOTION).size() > 0){
            sensors.add(new MySensor(SensorConfigConstants.TYPE_SIGNIFICANT_MOTION, R.string.significant_motion, FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS, measureTriggeredValue , createOneValueMap(MeasureConfigConstants.SIGNIFICANT_MOTION), true));
        }

        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_STEP_DETECTOR) != null
                && sensorManager.getSensorList(SensorConfigConstants.TYPE_STEP_DETECTOR).size() > 0){
            sensors.add(new MySensor(SensorConfigConstants.TYPE_STEP_DETECTOR, R.string.step_detect, FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS, measureOneTriggeredValueSum , createOneValueMap(MeasureConfigConstants.STEP_DETECTED), true));
        }

        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_STEP_COUNTER) != null
                && sensorManager.getSensorList(SensorConfigConstants.TYPE_STEP_COUNTER).size() > 0){
            sensors.add(new MySensor(SensorConfigConstants.TYPE_STEP_COUNTER, R.string.step_detect, FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS, measureOneTriggeredValueSum , createOneValueMap(MeasureConfigConstants.STEP_COUNTED), true));
        }


        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_GEOMAGNETIC_ROTATION_VECTOR) != null
                && sensorManager.getSensorList(SensorConfigConstants.TYPE_GEOMAGNETIC_ROTATION_VECTOR).size() > 0){
            sensors.add(new MySensor(SensorConfigConstants.TYPE_GEOMAGNETIC_ROTATION_VECTOR, R.string.geomagnetic_rotation_vector, FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS, measureSpatialValues , (SortedMap<String, Float>) spatialValues.clone()));
        }

        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_HEART_RATE) != null
                && sensorManager.getSensorList(SensorConfigConstants.TYPE_HEART_RATE).size() > 0){
            sensors.add(new MySensor(SensorConfigConstants.TYPE_HEART_RATE, R.string.heart_rate, FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS, measureOneValue , createOneValueMap(MeasureConfigConstants.HEART_RATE)));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_STATIONARY_DETECT) != null
                    && sensorManager.getSensorList(SensorConfigConstants.TYPE_STATIONARY_DETECT).size() > 0){
                sensors.add(new MySensor(SensorConfigConstants.TYPE_STATIONARY_DETECT, R.string.stationary_detect, FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS, measureTriggeredValue , createOneValueMap(MeasureConfigConstants.STATIONARY), true));
            }

            if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_MOTION_DETECT) != null
                    && sensorManager.getSensorList(SensorConfigConstants.TYPE_MOTION_DETECT).size() > 0){
                sensors.add(new MySensor(SensorConfigConstants.TYPE_MOTION_DETECT, R.string.stationary_detect, FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS, measureTriggeredValue , createOneValueMap(MeasureConfigConstants.MOTION_DETECTED), true));
            }

            if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_HEART_BEAT) != null
                    && sensorManager.getSensorList(SensorConfigConstants.TYPE_HEART_BEAT).size() > 0){
                sensors.add(new MySensor(SensorConfigConstants.TYPE_HEART_BEAT, R.string.heart_beat, FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS, measureOneValue , createOneValueMap(MeasureConfigConstants.HEART_BEAT)));
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
