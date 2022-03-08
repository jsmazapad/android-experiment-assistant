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

import com.jsm.exptool.config.SensorConfigConstants;
import com.jsm.exptool.libs.SensorHandler;
import com.jsm.exptool.model.experimentconfig.RepeatableElement;

import java.util.ArrayList;
import java.util.LinkedHashMap;
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
    private long internalId;
    private long experimentId;
    public int type;
    protected int accuracy;
    protected SensorManager sensorManager = SensorHandler.getInstance().getSensorManager();
    protected Sensor sensor;
    @Ignore  protected SortedMap<String, Float> measure = new TreeMap<>();
    @Ignore  public SensorEventInterface sensorEventInterface;
    @Ignore
    public MySensor(int type, int rName){
        this.type = type;
        this.nameStringResource = rName;
        this.sensor = sensorManager.getDefaultSensor(type);
    }

    public MySensor(int interval, int intervalMin, int nameStringResource, long internalId, long experimentId, int type) {
        super(interval, intervalMin, nameStringResource);
        this.internalId = internalId;
        this.experimentId = experimentId;
        this.type = type;
        this.sensor = sensorManager.getDefaultSensor(type);
    }

    @Ignore
    public MySensor(int type, int nameStringResource, int intervalMin, int interval) {
        this.type = type;
        this.nameStringResource = nameStringResource;
        this.intervalMin = intervalMin;
        this.interval = interval;
        this.sensor = sensorManager.getDefaultSensor(type);
    }

    @Ignore
    public MySensor(int type, int nameStringResource, int intervalMin) {
        this.type = type;
        this.nameStringResource = nameStringResource;
        this.intervalMin = intervalMin;
        this.interval = intervalMin;
        this.sensor = sensorManager.getDefaultSensor(type);
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

    public final int getType(){return  this.type;}


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
        type = in.readInt();
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
        dest.writeInt(type);
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
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //No se usa en el presente desarrollo, no se reacciona a los cambios de precisión, sólo se reflejan
    }

    public void initListener(){
        sensorManager.registerListener(this, sensor, 0);
    }

    public void cancelListener(){
        sensorManager.unregisterListener(this);
    }
}
