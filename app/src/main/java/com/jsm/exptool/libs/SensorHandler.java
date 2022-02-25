package com.jsm.exptool.libs;

import android.content.Context;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.os.Build;

import com.jsm.exptool.App;
import com.jsm.exptool.config.SensorConfigConstants;
import com.jsm.exptool.model.MySensor;
import com.jsm.exptool.model.RepeatableElement;
import com.jsm.exptool.model.Sensor.Accelerometer;
import com.jsm.exptool.model.Sensor.AmbientTemperature;
import com.jsm.exptool.model.Sensor.GPS;
import com.jsm.exptool.model.Sensor.GameRotationVector;
import com.jsm.exptool.model.Sensor.GeomagneticRotationVector;
import com.jsm.exptool.model.Sensor.Gravity;
import com.jsm.exptool.model.Sensor.Gyroscope;
import com.jsm.exptool.model.Sensor.HeartBeat;
import com.jsm.exptool.model.Sensor.HeartRate;
import com.jsm.exptool.model.Sensor.Light;
import com.jsm.exptool.model.Sensor.LinearAcceleration;
import com.jsm.exptool.model.Sensor.MagneticField;
import com.jsm.exptool.model.Sensor.MotionDetect;
import com.jsm.exptool.model.Sensor.Orientation;
import com.jsm.exptool.model.Sensor.Pressure;
import com.jsm.exptool.model.Sensor.Proximity;
import com.jsm.exptool.model.Sensor.RelativeHumidity;
import com.jsm.exptool.model.Sensor.RotationVector;
import com.jsm.exptool.model.Sensor.SignificantMotion;
import com.jsm.exptool.model.Sensor.StationaryDetect;
import com.jsm.exptool.model.Sensor.StepCounter;
import com.jsm.exptool.model.Sensor.StepDetector;
import com.jsm.exptool.model.Sensor.Temperature;

import java.util.ArrayList;
import java.util.List;

public class SensorHandler {
    private SensorManager sensorManager = App.getSensorManager();
    private LocationManager locationManager = (LocationManager) App.getAppContext().getSystemService(Context.LOCATION_SERVICE);
    private List<MySensor> sensors = new ArrayList();
    private ArrayList<RepeatableElement> selectedSensors = new ArrayList();
    private static SensorHandler instance;

    public static SensorHandler getInstance(){
        if (instance == null){
            instance = new SensorHandler();
        }
        return instance;
    }

    private SensorHandler() {

        sensors.add(new GPS());

        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_ACCELEROMETER) != null) {
            sensors.add(new Accelerometer());
        }

        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_MAGNETIC_FIELD) != null) {
            sensors.add(new MagneticField());
        }

        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_ORIENTATION) != null) {
            sensors.add(new Orientation());
        }

        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_GYROSCOPE) != null) {
            sensors.add(new Gyroscope());
        }

        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_LIGHT) != null) {
            sensors.add(new Light());
        }

        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_PRESSURE) != null) {
            sensors.add(new Pressure());
        }

        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_TEMPERATURE) != null) {
            sensors.add(new Temperature());
        }

        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_PROXIMITY) != null) {
            sensors.add(new Proximity());
        }

        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_GRAVITY) != null) {
            sensors.add(new Gravity());
        }

        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_LINEAR_ACCELERATION) != null) {
            sensors.add(new LinearAcceleration());
        }

        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_ROTATION_VECTOR) != null) {
            sensors.add(new RotationVector());
        }

        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_RELATIVE_HUMIDITY) != null) {
            sensors.add(new RelativeHumidity());
        }

        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_AMBIENT_TEMPERATURE) != null) {
            sensors.add(new AmbientTemperature());
        }

        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_GAME_ROTATION_VECTOR) != null) {
            sensors.add(new GameRotationVector());
        }

        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_SIGNIFICANT_MOTION) != null) {
            sensors.add(new SignificantMotion());
        }

        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_STEP_DETECTOR) != null) {
            sensors.add(new StepDetector());
        }

        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_STEP_COUNTER) != null) {
            sensors.add(new StepCounter());
        }

        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_GEOMAGNETIC_ROTATION_VECTOR) != null) {
            sensors.add(new GeomagneticRotationVector());
        }

        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_HEART_RATE) != null) {
            sensors.add(new HeartRate());
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_STATIONARY_DETECT) != null) {
                sensors.add(new StationaryDetect());
            }

            if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_MOTION_DETECT) != null) {
                sensors.add(new MotionDetect());
            }

            if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_HEART_BEAT) != null) {
                sensors.add(new HeartBeat());
            }

        }

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
}
