package com.jsm.exptool.model;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Parcel;
import android.provider.BaseColumns;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.jsm.exptool.config.FrequencyConstants;
import com.jsm.exptool.libs.SensorHandler;
import com.jsm.exptool.model.experimentconfig.RepeatableElement;

import java.util.SortedMap;
import java.util.TreeMap;

@Entity(tableName = MySensor.TABLE_NAME)
public class MySensor extends RepeatableElement implements Cloneable, SensorEventListener {
    /** The name of the table. */
    public static final String TABLE_NAME = "sensors";

    /** The name of the ID column. */
    public static final String COLUMN_ID = BaseColumns._ID;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = COLUMN_ID)
    @Expose private long internalId;
    @Expose private long experimentId;
    @Expose protected int sensorType;
    @Expose protected int accuracy;
    @Ignore protected Sensor sensor;
    @Expose @Ignore  protected SortedMap<String, Float> measure = new TreeMap<>();
    @Ignore  public SensorEventInterface sensorEventInterface;
    @Ignore
    public MySensor(int sensorType, int rName){
        this.sensorType = sensorType;
        this.nameStringResource = rName;
        initSensor();
    }

    public MySensor(int interval, int intervalMin, int nameStringResource, long internalId, long experimentId, int sensorType) {
        super(interval, intervalMin, nameStringResource);
        this.internalId = internalId;
        this.experimentId = experimentId;
        this.sensorType = sensorType;
        initSensor();
    }

    @Ignore
    public MySensor(int sensorType, int nameStringResource, int intervalMin, int interval) {
        this.sensorType = sensorType;
        this.nameStringResource = nameStringResource;
        this.intervalMin = intervalMin;
        this.interval = interval;
        initSensor();
    }

    @Ignore
    public MySensor(int sensorType, int nameStringResource, int intervalMin, SensorEventInterface sensorEventInterface, SortedMap<String, Float> measure) {
        this.sensorType = sensorType;
        this.nameStringResource = nameStringResource;
        this.intervalMin = intervalMin;
        this.interval = intervalMin;
        this.sensorEventInterface = sensorEventInterface;
        this.measure = measure;
        initSensor();
    }

    public void initSensor(){
        this.sensor = SensorHandler.getInstance().getSensorManager().getDefaultSensor(sensorType);
        if (sensor.getMinDelay() / 1000 > FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS) {
            this.intervalMin = sensor.getMinDelay() / 1000;
            this.interval = sensor.getMinDelay() / 1000;
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
        return measure;
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

    protected MySensor(Parcel in) {
        sensorType = in.readInt();
        nameStringResource = in.readInt();
        interval = in.readInt();
        intervalMin = in.readInt();

    }

    public static final Creator<MySensor> CREATOR = new Creator<MySensor>() {
        @Override
        public MySensor createFromParcel(Parcel in) {
            return new MySensor(in);
        }

        @Override
        public MySensor[] newArray(int size) {
            return new MySensor[size];
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

    public void initListener(){
        SensorHandler.getInstance().getSensorManager().registerListener(this, sensor, 0);
    }

    public void cancelListener(){
        SensorHandler.getInstance().getSensorManager().unregisterListener(this);
    }
}
