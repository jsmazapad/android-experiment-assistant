package com.jsm.exptool.libs;

import static android.content.Context.SENSOR_SERVICE;

import android.content.Context;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.os.Build;

import com.jsm.exptool.App;
import com.jsm.exptool.config.SensorConfigConstants;
import com.jsm.exptool.model.MySensor;
import com.jsm.exptool.model.experimentconfig.RepeatableElement;
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
    private SensorManager sensorManager;
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

    public void initialize(Context context)
    {
        this.sensorManager = (SensorManager) context.getSystemService(SENSOR_SERVICE);
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


    private void createAvailableSensorsList(){
        sensors.add(new GPS());

        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_ACCELEROMETER) != null
                && sensorManager.getSensorList(SensorConfigConstants.TYPE_ACCELEROMETER).size() > 0) {
            sensors.add(new Accelerometer());
        }

        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_MAGNETIC_FIELD) != null
                && sensorManager.getSensorList(SensorConfigConstants.TYPE_MAGNETIC_FIELD).size() > 0) {
            sensors.add(new MagneticField());
        }

//        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_ORIENTATION) != null
//                && sensorManager.getSensorList(SensorConfigConstants.TYPE_ORIENTATION).size() > 0) {
//            sensors.add(new Orientation());
//        }
//
//        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_GYROSCOPE) != null
//                && sensorManager.getSensorList(SensorConfigConstants.TYPE_GYROSCOPE).size() > 0) {
//            sensors.add(new Gyroscope());
//        }
//
//        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_LIGHT) != null
//                && sensorManager.getSensorList(SensorConfigConstants.TYPE_LIGHT).size() > 0) {
//            sensors.add(new Light());
//        }
//
//        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_PRESSURE) != null
//                && sensorManager.getSensorList(SensorConfigConstants.TYPE_PRESSURE).size() > 0){
//            sensors.add(new Pressure());
//        }
//
//        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_TEMPERATURE) != null
//                && sensorManager.getSensorList(SensorConfigConstants.TYPE_TEMPERATURE).size() > 0){
//            sensors.add(new Temperature());
//        }
//
//        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_PROXIMITY) != null
//                && sensorManager.getSensorList(SensorConfigConstants.TYPE_PROXIMITY).size() > 0){
//            sensors.add(new Proximity());
//        }
//
//        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_GRAVITY) != null
//                && sensorManager.getSensorList(SensorConfigConstants.TYPE_GRAVITY).size() > 0){
//            sensors.add(new Gravity());
//        }
//
//        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_LINEAR_ACCELERATION) != null
//                && sensorManager.getSensorList(SensorConfigConstants.TYPE_LINEAR_ACCELERATION).size() > 0){
//            sensors.add(new LinearAcceleration());
//        }
//
//        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_ROTATION_VECTOR) != null
//                && sensorManager.getSensorList(SensorConfigConstants.TYPE_ROTATION_VECTOR).size() > 0){
//            sensors.add(new RotationVector());
//        }
//
//        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_RELATIVE_HUMIDITY) != null
//                && sensorManager.getSensorList(SensorConfigConstants.TYPE_RELATIVE_HUMIDITY).size() > 0){
//            sensors.add(new RelativeHumidity());
//        }
//
//        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_AMBIENT_TEMPERATURE) != null
//                && sensorManager.getSensorList(SensorConfigConstants.TYPE_AMBIENT_TEMPERATURE).size() > 0){
//            sensors.add(new AmbientTemperature());
//        }
//
//        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_GAME_ROTATION_VECTOR) != null
//                && sensorManager.getSensorList(SensorConfigConstants.TYPE_GAME_ROTATION_VECTOR).size() > 0){
//            sensors.add(new GameRotationVector());
//        }
//
//        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_SIGNIFICANT_MOTION) != null
//                && sensorManager.getSensorList(SensorConfigConstants.TYPE_SIGNIFICANT_MOTION).size() > 0){
//            sensors.add(new SignificantMotion());
//        }
//
//        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_STEP_DETECTOR) != null
//                && sensorManager.getSensorList(SensorConfigConstants.TYPE_STEP_DETECTOR).size() > 0){
//            sensors.add(new StepDetector());
//        }
//
//        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_STEP_COUNTER) != null
//                && sensorManager.getSensorList(SensorConfigConstants.TYPE_STEP_COUNTER).size() > 0){
//            sensors.add(new StepCounter());
//        }
//
//        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_GEOMAGNETIC_ROTATION_VECTOR) != null
//                && sensorManager.getSensorList(SensorConfigConstants.TYPE_GEOMAGNETIC_ROTATION_VECTOR).size() > 0){
//            sensors.add(new GeomagneticRotationVector());
//        }
//
//        if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_HEART_RATE) != null
//                && sensorManager.getSensorList(SensorConfigConstants.TYPE_HEART_RATE).size() > 0){
//            sensors.add(new HeartRate());
//        }
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_STATIONARY_DETECT) != null
//                    && sensorManager.getSensorList(SensorConfigConstants.TYPE_STATIONARY_DETECT).size() > 0){
//                sensors.add(new StationaryDetect());
//            }
//
//            if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_MOTION_DETECT) != null
//                    && sensorManager.getSensorList(SensorConfigConstants.TYPE_MOTION_DETECT).size() > 0){
//                sensors.add(new MotionDetect());
//            }
//
//            if (sensorManager.getDefaultSensor(SensorConfigConstants.TYPE_HEART_BEAT) != null
//                    && sensorManager.getSensorList(SensorConfigConstants.TYPE_HEART_BEAT).size() > 0){
//                sensors.add(new HeartBeat());
//            }
//
//        }
    }
}
