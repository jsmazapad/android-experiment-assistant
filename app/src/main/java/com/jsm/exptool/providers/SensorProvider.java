package com.jsm.exptool.providers;

import static android.content.Context.SENSOR_SERVICE;

import static com.jsm.exptool.config.SensorConfigConstants.TYPE_SENSORS_TO_TYPE_STRING;

import android.content.Context;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.os.Build;

import com.jsm.exptool.R;
import com.jsm.exptool.config.FrequencyConstants;
import com.jsm.exptool.config.MeasureConfigConstants;
import com.jsm.exptool.config.SensorConfigConstants;
import com.jsm.exptool.entities.experimentconfig.SensorConfig;
import com.jsm.exptool.entities.sensoreventsreader.SensorEventInterface;
import com.jsm.exptool.entities.sensoreventsreader.TriggerEventInterface;
import com.jsm.exptool.entities.sensoreventsreader.SensorReader;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

public class SensorProvider {
    private SensorManager sensorManager;
    private LocationManager locationManager;
    private List<SensorConfig> sensors = new ArrayList();
    private static SensorProvider instance;

    public static SensorProvider getInstance() {
        if (instance == null) {
            instance = new SensorProvider();
        }
        return instance;
    }

    public void initialize(Context context) {
        this.sensorManager = (SensorManager) context.getSystemService(SENSOR_SERVICE);
        this.locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        createAvailableSensorsList();
    }

    private SensorProvider() {

    }


    public SensorManager getSensorManager() {
        return sensorManager;
    }

    public List<SensorConfig> getSensors() {
        return this.sensors;
    }

    public SensorReader getSensorReader(int type) {
        SensorReader sensorReader = null;
        for (SensorConfig sensorConfig : sensors) {
            if (sensorConfig.getSensorReader().getSensorType() == type) {
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
    private void createAvailableSensorsList() {


        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_ACCELEROMETER) != null
                && sensorManager.getSensorList(SensorConfigConstants.TYPE_ACCELEROMETER).size() > 0) {
            sensors.add(new SensorConfig(new SensorReader(SensorConfigConstants.TYPE_ACCELEROMETER, measureSpatialValues, (SortedMap<String, Float>) spatialValues.clone()), TYPE_SENSORS_TO_TYPE_STRING.get(SensorConfigConstants.TYPE_ACCELEROMETER), FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS, FrequencyConstants.MAX_SENSOR_INTERVAL_MILLIS, FrequencyConstants.DEFAULT_SENSOR_FREQ));
        }

        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_MAGNETIC_FIELD) != null
                && sensorManager.getSensorList(SensorConfigConstants.TYPE_MAGNETIC_FIELD).size() > 0) {
            sensors.add(new SensorConfig(new SensorReader(SensorConfigConstants.TYPE_MAGNETIC_FIELD, measureSpatialValues, (SortedMap<String, Float>) spatialValues.clone()), TYPE_SENSORS_TO_TYPE_STRING.get(SensorConfigConstants.TYPE_MAGNETIC_FIELD), FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS, FrequencyConstants.MAX_SENSOR_INTERVAL_MILLIS, FrequencyConstants.DEFAULT_SENSOR_FREQ));

        }

        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_ORIENTATION) != null
                && sensorManager.getSensorList(SensorConfigConstants.TYPE_ORIENTATION).size() > 0) {
            sensors.add(new SensorConfig(new SensorReader(SensorConfigConstants.TYPE_ORIENTATION, measureSpatialValues, (SortedMap<String, Float>) spatialValues.clone()), TYPE_SENSORS_TO_TYPE_STRING.get(SensorConfigConstants.TYPE_ORIENTATION), FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS, FrequencyConstants.MAX_SENSOR_INTERVAL_MILLIS, FrequencyConstants.DEFAULT_SENSOR_FREQ));
        }

        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_GYROSCOPE) != null
                && sensorManager.getSensorList(SensorConfigConstants.TYPE_GYROSCOPE).size() > 0) {
            sensors.add(new SensorConfig(new SensorReader(SensorConfigConstants.TYPE_GYROSCOPE, measureSpatialValues, (SortedMap<String, Float>) spatialValues.clone()), TYPE_SENSORS_TO_TYPE_STRING.get(SensorConfigConstants.TYPE_GYROSCOPE), FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS, FrequencyConstants.MAX_SENSOR_INTERVAL_MILLIS, FrequencyConstants.DEFAULT_SENSOR_FREQ));
        }

        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_LIGHT) != null
                && sensorManager.getSensorList(SensorConfigConstants.TYPE_LIGHT).size() > 0) {
            sensors.add(new SensorConfig(new SensorReader(SensorConfigConstants.TYPE_LIGHT, measureOneValue, createOneValueMap(MeasureConfigConstants.ILLUMINANCE)), TYPE_SENSORS_TO_TYPE_STRING.get(SensorConfigConstants.TYPE_LIGHT), FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS, FrequencyConstants.MAX_SENSOR_INTERVAL_MILLIS, FrequencyConstants.DEFAULT_SENSOR_FREQ));
        }

        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_PRESSURE) != null
                && sensorManager.getSensorList(SensorConfigConstants.TYPE_PRESSURE).size() > 0) {
            sensors.add(new SensorConfig(new SensorReader(SensorConfigConstants.TYPE_PRESSURE, measureOneValue, createOneValueMap(MeasureConfigConstants.PRESSURE)), TYPE_SENSORS_TO_TYPE_STRING.get(SensorConfigConstants.TYPE_PRESSURE), FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS, FrequencyConstants.MAX_SENSOR_INTERVAL_MILLIS, FrequencyConstants.DEFAULT_SENSOR_FREQ));
        }

        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_TEMPERATURE) != null
                && sensorManager.getSensorList(SensorConfigConstants.TYPE_TEMPERATURE).size() > 0) {
            sensors.add(new SensorConfig(new SensorReader(SensorConfigConstants.TYPE_TEMPERATURE, measureOneValue, createOneValueMap(MeasureConfigConstants.TEMPERATURE)), TYPE_SENSORS_TO_TYPE_STRING.get(SensorConfigConstants.TYPE_TEMPERATURE), FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS, FrequencyConstants.MAX_SENSOR_INTERVAL_MILLIS, FrequencyConstants.DEFAULT_SENSOR_FREQ));
        }

        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_PROXIMITY) != null
                && sensorManager.getSensorList(SensorConfigConstants.TYPE_PROXIMITY).size() > 0) {
            sensors.add(new SensorConfig(new SensorReader(SensorConfigConstants.TYPE_PROXIMITY, measureOneValue, createOneValueMap(MeasureConfigConstants.PROXIMITY)), TYPE_SENSORS_TO_TYPE_STRING.get(SensorConfigConstants.TYPE_PROXIMITY), FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS, FrequencyConstants.MAX_SENSOR_INTERVAL_MILLIS, FrequencyConstants.DEFAULT_SENSOR_FREQ));
        }

        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_GRAVITY) != null
                && sensorManager.getSensorList(SensorConfigConstants.TYPE_GRAVITY).size() > 0) {
            sensors.add(new SensorConfig(new SensorReader(SensorConfigConstants.TYPE_GRAVITY, measureSpatialValues, (SortedMap<String, Float>) spatialValues.clone()), TYPE_SENSORS_TO_TYPE_STRING.get(SensorConfigConstants.TYPE_GRAVITY), FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS, FrequencyConstants.MAX_SENSOR_INTERVAL_MILLIS, FrequencyConstants.DEFAULT_SENSOR_FREQ));
        }

        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_LINEAR_ACCELERATION) != null
                && sensorManager.getSensorList(SensorConfigConstants.TYPE_LINEAR_ACCELERATION).size() > 0) {
            sensors.add(new SensorConfig(new SensorReader(SensorConfigConstants.TYPE_LINEAR_ACCELERATION, measureSpatialValues, (SortedMap<String, Float>) spatialValues.clone()), TYPE_SENSORS_TO_TYPE_STRING.get(SensorConfigConstants.TYPE_LINEAR_ACCELERATION), FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS, FrequencyConstants.MAX_SENSOR_INTERVAL_MILLIS, FrequencyConstants.DEFAULT_SENSOR_FREQ));
        }

        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_ROTATION_VECTOR) != null
                && sensorManager.getSensorList(SensorConfigConstants.TYPE_ROTATION_VECTOR).size() > 0) {
            sensors.add(new SensorConfig(new SensorReader(SensorConfigConstants.TYPE_ROTATION_VECTOR, measureSpatialValues, (SortedMap<String, Float>) spatialValues.clone()), TYPE_SENSORS_TO_TYPE_STRING.get(SensorConfigConstants.TYPE_ROTATION_VECTOR), FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS, FrequencyConstants.MAX_SENSOR_INTERVAL_MILLIS, FrequencyConstants.DEFAULT_SENSOR_FREQ));
        }

        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_RELATIVE_HUMIDITY) != null
                && sensorManager.getSensorList(SensorConfigConstants.TYPE_RELATIVE_HUMIDITY).size() > 0) {
            sensors.add(new SensorConfig(new SensorReader(SensorConfigConstants.TYPE_RELATIVE_HUMIDITY, measureOneValue, createOneValueMap(MeasureConfigConstants.HUMIDITY)), TYPE_SENSORS_TO_TYPE_STRING.get(SensorConfigConstants.TYPE_RELATIVE_HUMIDITY), FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS, FrequencyConstants.MAX_SENSOR_INTERVAL_MILLIS, FrequencyConstants.DEFAULT_SENSOR_FREQ));
        }

        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_AMBIENT_TEMPERATURE) != null
                && sensorManager.getSensorList(SensorConfigConstants.TYPE_AMBIENT_TEMPERATURE).size() > 0) {
            sensors.add(new SensorConfig(new SensorReader(SensorConfigConstants.TYPE_AMBIENT_TEMPERATURE, measureOneValue, createOneValueMap(MeasureConfigConstants.TEMPERATURE)), TYPE_SENSORS_TO_TYPE_STRING.get(SensorConfigConstants.TYPE_AMBIENT_TEMPERATURE), FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS, FrequencyConstants.MAX_SENSOR_INTERVAL_MILLIS, FrequencyConstants.DEFAULT_SENSOR_FREQ));
        }

        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_GAME_ROTATION_VECTOR) != null
                && sensorManager.getSensorList(SensorConfigConstants.TYPE_GAME_ROTATION_VECTOR).size() > 0) {
            sensors.add(new SensorConfig(new SensorReader(SensorConfigConstants.TYPE_GAME_ROTATION_VECTOR, measureSpatialValues, (SortedMap<String, Float>) spatialValues.clone()), TYPE_SENSORS_TO_TYPE_STRING.get(SensorConfigConstants.TYPE_GAME_ROTATION_VECTOR), FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS, FrequencyConstants.MAX_SENSOR_INTERVAL_MILLIS, FrequencyConstants.DEFAULT_SENSOR_FREQ));

        }

        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_SIGNIFICANT_MOTION) != null
                && sensorManager.getSensorList(SensorConfigConstants.TYPE_SIGNIFICANT_MOTION).size() > 0) {
            sensors.add(new SensorConfig(new SensorReader(SensorConfigConstants.TYPE_SIGNIFICANT_MOTION, measureTriggeredValue, createOneValueMap(MeasureConfigConstants.SIGNIFICANT_MOTION), true), TYPE_SENSORS_TO_TYPE_STRING.get(SensorConfigConstants.TYPE_SIGNIFICANT_MOTION), FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS, FrequencyConstants.MAX_SENSOR_INTERVAL_MILLIS, FrequencyConstants.DEFAULT_SENSOR_FREQ));
        }

        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_STEP_DETECTOR) != null
                && sensorManager.getSensorList(SensorConfigConstants.TYPE_STEP_DETECTOR).size() > 0) {
            sensors.add(new SensorConfig(new SensorReader(SensorConfigConstants.TYPE_STEP_DETECTOR, measureOneTriggeredValueSum, createOneValueMap(MeasureConfigConstants.STEP_DETECTED), true), TYPE_SENSORS_TO_TYPE_STRING.get(SensorConfigConstants.TYPE_STEP_DETECTOR), FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS, FrequencyConstants.MAX_SENSOR_INTERVAL_MILLIS, FrequencyConstants.DEFAULT_SENSOR_FREQ));
        }

        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_STEP_COUNTER) != null
                && sensorManager.getSensorList(SensorConfigConstants.TYPE_STEP_COUNTER).size() > 0) {
            sensors.add(new SensorConfig(new SensorReader(SensorConfigConstants.TYPE_STEP_COUNTER, measureOneTriggeredValueSum, createOneValueMap(MeasureConfigConstants.STEP_COUNTED), true), TYPE_SENSORS_TO_TYPE_STRING.get(SensorConfigConstants.TYPE_STEP_COUNTER), FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS, FrequencyConstants.MAX_SENSOR_INTERVAL_MILLIS, FrequencyConstants.DEFAULT_SENSOR_FREQ));
        }


        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_GEOMAGNETIC_ROTATION_VECTOR) != null
                && sensorManager.getSensorList(SensorConfigConstants.TYPE_GEOMAGNETIC_ROTATION_VECTOR).size() > 0) {
            sensors.add(new SensorConfig(new SensorReader(SensorConfigConstants.TYPE_GEOMAGNETIC_ROTATION_VECTOR, measureSpatialValues, (SortedMap<String, Float>) spatialValues.clone()), TYPE_SENSORS_TO_TYPE_STRING.get(SensorConfigConstants.TYPE_GEOMAGNETIC_ROTATION_VECTOR), FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS, FrequencyConstants.MAX_SENSOR_INTERVAL_MILLIS, FrequencyConstants.DEFAULT_SENSOR_FREQ));
        }

        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_HEART_RATE) != null
                && sensorManager.getSensorList(SensorConfigConstants.TYPE_HEART_RATE).size() > 0) {
            sensors.add(new SensorConfig(new SensorReader(SensorConfigConstants.TYPE_HEART_RATE, measureOneValue, createOneValueMap(MeasureConfigConstants.HEART_RATE)), TYPE_SENSORS_TO_TYPE_STRING.get(SensorConfigConstants.TYPE_HEART_RATE), FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS, FrequencyConstants.MAX_SENSOR_INTERVAL_MILLIS, FrequencyConstants.DEFAULT_SENSOR_FREQ));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_STATIONARY_DETECT) != null
                    && sensorManager.getSensorList(SensorConfigConstants.TYPE_STATIONARY_DETECT).size() > 0) {
                sensors.add(new SensorConfig(new SensorReader(SensorConfigConstants.TYPE_STATIONARY_DETECT, measureTriggeredValue, createOneValueMap(MeasureConfigConstants.STATIONARY), true), TYPE_SENSORS_TO_TYPE_STRING.get(SensorConfigConstants.TYPE_STATIONARY_DETECT), FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS, FrequencyConstants.MAX_SENSOR_INTERVAL_MILLIS, FrequencyConstants.DEFAULT_SENSOR_FREQ));
            }

            if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_MOTION_DETECT) != null
                    && sensorManager.getSensorList(SensorConfigConstants.TYPE_MOTION_DETECT).size() > 0) {
                sensors.add(new SensorConfig(new SensorReader(SensorConfigConstants.TYPE_MOTION_DETECT, measureTriggeredValue, createOneValueMap(MeasureConfigConstants.MOTION_DETECTED), true), TYPE_SENSORS_TO_TYPE_STRING.get(SensorConfigConstants.TYPE_MOTION_DETECT), FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS, FrequencyConstants.MAX_SENSOR_INTERVAL_MILLIS, FrequencyConstants.DEFAULT_SENSOR_FREQ));
            }

            if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_HEART_BEAT) != null
                    && sensorManager.getSensorList(SensorConfigConstants.TYPE_HEART_BEAT).size() > 0) {
                sensors.add(new SensorConfig(new SensorReader(SensorConfigConstants.TYPE_HEART_BEAT, measureOneValue, createOneValueMap(MeasureConfigConstants.HEART_BEAT)), TYPE_SENSORS_TO_TYPE_STRING.get(SensorConfigConstants.TYPE_HEART_BEAT), FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS, FrequencyConstants.MAX_SENSOR_INTERVAL_MILLIS, FrequencyConstants.DEFAULT_SENSOR_FREQ));
            }
//
        }
    }

    /*
    MEDICIÓN DE SENSORES NO DISPARADOS
     */

    private final TreeMap<String, Float> spatialValues = createSpatialValues();

    private final TreeMap<String, Float> createSpatialValues() {
        TreeMap<String, Float> mapToReturn = new TreeMap<>();
        mapToReturn.put(MeasureConfigConstants.POSITION_X, 0f);
        mapToReturn.put(MeasureConfigConstants.POSITION_Y, 0f);
        mapToReturn.put(MeasureConfigConstants.POSITION_Z, 0f);
        return mapToReturn;
    }


    private final SortedMap<String, Float> createOneValueMap(String key) {
        TreeMap<String, Float> mapToReturn = new TreeMap<>();
        mapToReturn.put(key, 0f);
        return mapToReturn;
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
        measure.put(measure.firstKey(), (measure.get(measure.firstKey()) == null ? 0f : measure.get(measure.firstKey())) + event.values[0]);
    };

     /*
    MEDICIÓN DE SENSORES DISPARADOS
     */

    private final TriggerEventInterface measureOneTriggeredValueSum = (event, measure) -> {
        measure.put(measure.firstKey(), (measure.get(measure.firstKey()) == null ? 0f : measure.get(measure.firstKey())) + event.values[0]);
    };
    private final TriggerEventInterface measureTriggeredValue = (event, measure) -> {
        measure.put(measure.firstKey(), event.values[0]);
    };

}
