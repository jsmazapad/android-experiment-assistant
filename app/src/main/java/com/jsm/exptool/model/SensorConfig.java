package com.jsm.exptool.model;

import static android.hardware.SensorManager.SENSOR_STATUS_ACCURACY_HIGH;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.TriggerEvent;
import android.hardware.TriggerEventListener;
import android.os.Parcel;
import android.provider.BaseColumns;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.jsm.exptool.config.FrequencyConstants;
import com.jsm.exptool.libs.SensorHandler;
import com.jsm.exptool.model.experimentconfig.RepeatableElementConfig;

import java.util.SortedMap;
import java.util.TreeMap;

@Entity(tableName = SensorConfig.TABLE_NAME)
public class SensorConfig extends RepeatableElementConfig implements Cloneable, SensorEventListener {
    /** The name of the table. */
    public static final String TABLE_NAME = "experiment_sensors";

    /** The name of the ID column. */
    public static final String COLUMN_ID = BaseColumns._ID;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = COLUMN_ID)
    @Expose private long internalId;
    @Expose private long experimentId;
    @Expose protected int sensorType;
    @Expose protected int accuracy;
    @Ignore protected Sensor sensor;
    //TODO Separar configuración de medicion
    @Expose @Ignore  protected SortedMap<String, Float> measure = new TreeMap<>();
    @Ignore  protected SortedMap<String, Float> initialMeasure = new TreeMap<>();
    @Ignore  protected SensorEventInterface sensorEventInterface;
    @Ignore  protected TriggerEventInterface triggerEventInterface;
    @Ignore private TriggerEventListener triggerEventListener = new TriggerEventListener() {
        @Override
        public void onTrigger(TriggerEvent event) {
            SensorConfig.this.onTrigger(event);
        }
    };
    private boolean restartInitialMeasureAfterRead = false;
    @Ignore
    public SensorConfig(int sensorType, int rName){
        this.sensorType = sensorType;
        this.nameStringResource = rName;
        initSensor();
    }



    public SensorConfig(int interval, int intervalMin, int intervalMax, int nameStringResource, long internalId, long experimentId, int sensorType) {
        super(interval, intervalMin, intervalMax, nameStringResource);
        this.internalId = internalId;
        this.experimentId = experimentId;
        this.sensorType = sensorType;
        initSensor();
    }

    @Ignore
    public SensorConfig(int sensorType, int nameStringResource, int intervalMin, int interval) {
        this.sensorType = sensorType;
        this.nameStringResource = nameStringResource;
        this.intervalMin = intervalMin;
        this.interval = interval;
        initSensor();
    }

    @Ignore
    public SensorConfig(int sensorType, int nameStringResource, int intervalMin, SensorEventInterface sensorEventInterface, SortedMap<String, Float> measure) {
        this.sensorType = sensorType;
        this.nameStringResource = nameStringResource;
        this.intervalMin = intervalMin;
        this.interval = intervalMin;
        this.sensorEventInterface = sensorEventInterface;
        this.measure = measure;
        initSensor();
    }

    @Ignore
    public SensorConfig(int sensorType, int nameStringResource, int intervalMin, TriggerEventInterface triggerEventInterface, SortedMap<String, Float> measure, boolean restartInitialMeasureAfterRead) {
        this.sensorType = sensorType;
        this.nameStringResource = nameStringResource;
        this.intervalMin = intervalMin;
        this.interval = intervalMin;
        this.measure = measure;
        this.triggerEventInterface = triggerEventInterface;
        this.restartInitialMeasureAfterRead = restartInitialMeasureAfterRead;
        this.initialMeasure = measure;
        initSensor();
    }

    public void initSensor(){
        this.sensor = SensorHandler.getInstance().getSensorManager().getDefaultSensor(sensorType);
        if (sensor.getMinDelay() / 1000 > FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS) {
            this.intervalMin = sensor.getMinDelay() / 1000;
            this.interval = sensor.getMinDelay() / 1000;
        }
        if (sensor.getMaxDelay() / 1000 < FrequencyConstants.MAX_SENSOR_INTERVAL_MILLIS) {
            this.intervalMax = sensor.getMaxDelay() / 1000;

        }
    }




    public long getInternalId() {
        return internalId;
    }

    public void setInternalId(long internalId) {
        this.internalId = internalId;
    }

    public long getExperimentId() {
        return experimentId;
    }

    public void setExperimentId(long experimentId) {
        this.experimentId = experimentId;
    }

    public final int getSensorType(){return  this.sensorType;}

    public int getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
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

    public boolean isRestartInitialMeasureAfterRead() {
        return restartInitialMeasureAfterRead;
    }

    public void setRestartInitialMeasureAfterRead(boolean restartInitialMeasureAfterRead) {
        this.restartInitialMeasureAfterRead = restartInitialMeasureAfterRead;
    }

    protected SensorConfig(Parcel in) {
        sensorType = in.readInt();
        nameStringResource = in.readInt();
        interval = in.readInt();
        intervalMin = in.readInt();

    }

    public static final Creator<SensorConfig> CREATOR = new Creator<SensorConfig>() {
        @Override
        public SensorConfig createFromParcel(Parcel in) {
            return new SensorConfig(in);
        }

        @Override
        public SensorConfig[] newArray(int size) {
            return new SensorConfig[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(sensorType);
        dest.writeInt(nameStringResource);
        dest.writeInt(interval);
        dest.writeInt(intervalMin);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
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
