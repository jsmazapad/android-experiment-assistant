package com.jsm.exptool.model.externaleventsreader;

import static android.hardware.SensorManager.SENSOR_STATUS_ACCURACY_HIGH;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.TriggerEvent;
import android.hardware.TriggerEventListener;

import androidx.room.Ignore;

import com.google.gson.annotations.Expose;
import com.jsm.exptool.libs.SensorHandler;
import com.jsm.exptool.model.SensorEventInterface;
import com.jsm.exptool.model.TriggerEventInterface;

import java.util.SortedMap;
import java.util.TreeMap;

//TODO crear dos subhijas TriggeredSensorReader y EventSensorReader y padre, ExternalEventReader
public class SensorReader implements SensorEventListener {

    @Ignore
    protected int accuracy;
    @Ignore
    protected Sensor sensor;

    @Expose protected int sensorType;
    @Expose  protected SortedMap<String, Float> measure;
    @Ignore  protected SortedMap<String, Float> initialMeasure = new TreeMap<>();



    @Ignore  protected SensorEventInterface sensorEventInterface;
    @Ignore  protected TriggerEventInterface triggerEventInterface;
    @Ignore private TriggerEventListener triggerEventListener = new TriggerEventListener() {
        @Override
        public void onTrigger(TriggerEvent event) {
            SensorReader.this.onTrigger(event);
        }
    };

    private boolean restartInitialMeasureAfterRead = false;


    public SensorReader(int sensorType, SortedMap<String, Float> measure, boolean restartInitialMeasureAfterRead) {
        this(sensorType, measure);
        this.restartInitialMeasureAfterRead = restartInitialMeasureAfterRead;

    }

    @Ignore
    public SensorReader(int sensorType, SortedMap<String, Float> measure) {
        this.sensorType = sensorType;
        this.sensor = SensorHandler.getInstance().getSensorManager().getDefaultSensor(sensorType);
        this.measure = measure;

    }

    @Ignore
    public SensorReader(int sensorType,  SensorEventInterface sensorEventInterface, SortedMap<String, Float> measure) {
        this(sensorType, measure);
        this.sensorEventInterface = sensorEventInterface;
    }
    @Ignore
    public SensorReader(int sensorType, TriggerEventInterface triggerEventInterface, SortedMap<String, Float> measure, boolean restartInitialMeasureAfterRead) {
        this(sensorType, measure);
        this.triggerEventInterface = triggerEventInterface;
        this.restartInitialMeasureAfterRead = restartInitialMeasureAfterRead;
    }



    public int getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    public boolean isRestartInitialMeasureAfterRead() {
        return restartInitialMeasureAfterRead;
    }

    public void setRestartInitialMeasureAfterRead(boolean restartInitialMeasureAfterRead) {
        this.restartInitialMeasureAfterRead = restartInitialMeasureAfterRead;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public int getSensorType() {
        return sensorType;
    }

    public SortedMap<String, Float> getMeasure() {
        SortedMap<String, Float> measureRegistered = this.measure;
        if(restartInitialMeasureAfterRead){
            this.measure = initialMeasure;
        }
        return measureRegistered;
    }

    public void setMeasure(SortedMap<String, Float> measure) {
        this.measure = measure;
    }

    public SensorEventInterface getSensorEventInterface() {
        return sensorEventInterface;
    }

    public void setSensorEventInterface(SensorEventInterface sensorEventInterface) {
        this.sensorEventInterface = sensorEventInterface;
    }

    public TriggerEventInterface getTriggerEventInterface() {
        return triggerEventInterface;
    }

    public void setTriggerEventInterface(TriggerEventInterface triggerEventInterface) {
        this.triggerEventInterface = triggerEventInterface;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == sensorType) {
            if(sensorEventInterface != null) {
                this.accuracy = event.accuracy;
                sensorEventInterface.readSensor(event, measure);
            }
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //No se usa en el presente desarrollo, no se reacciona a los cambios de precisión, sólo se reflejan
    }


    public void onTrigger(TriggerEvent event) {
        if (this.triggerEventInterface != null){
            this.accuracy = SENSOR_STATUS_ACCURACY_HIGH;
            triggerEventInterface.readTriggeredSensor(event, measure);
        }

    }

    public void initListener(){
        if (this.triggerEventInterface != null){
            SensorHandler.getInstance().getSensorManager().requestTriggerSensor(triggerEventListener, sensor);
        }else{
            SensorHandler.getInstance().getSensorManager().registerListener(this, sensor, 0);
        }
    }

    public void cancelListener(){
        if (this.triggerEventInterface != null) {
            SensorHandler.getInstance().getSensorManager().cancelTriggerSensor(triggerEventListener, sensor);
        }else{
            SensorHandler.getInstance().getSensorManager().unregisterListener(this);
        }
    }

}
