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

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;


//TODO crear clase hija llamada SensorReader con dos subhijas TriggeredSensorReader y EventSensorReader
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
    private boolean defaultConfigurationEnabled;
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
        this.defaultConfigurationEnabled = true;
        initSensor();
    }



    public SensorConfig(int interval, int intervalMin, int intervalMax, int nameStringResource, long internalId, long experimentId, int sensorType, boolean defaultConfigurationEnabled) {
        this(sensorType, intervalMin, intervalMax, interval, nameStringResource);
        this.internalId = internalId;
        this.experimentId = experimentId;
        this.sensorType = sensorType;
        this.defaultConfigurationEnabled = defaultConfigurationEnabled;
        initSensor();
    }

    @Ignore
    public SensorConfig(int sensorType, int intervalMin,int intervalMax, int interval,  int nameStringResource) {
        super(interval, intervalMin, intervalMax, nameStringResource);
        this.sensorType = sensorType;
        this.nameStringResource = nameStringResource;
        this.intervalMin = intervalMin;
        this.interval = interval;
        this.defaultConfigurationEnabled = true;
        initSensor();
    }

    //TODO Separar Reader de Config
    @Ignore
    public SensorConfig(int sensorType, int nameStringResource, int intervalMin, SensorEventInterface sensorEventInterface, SortedMap<String, Float> measure) {
        //TODO Setear intervalMax
        this(sensorType, intervalMin, intervalMin, intervalMin, nameStringResource);
        this.sensorEventInterface = sensorEventInterface;
        this.measure = measure;
        this.defaultConfigurationEnabled = true;
        initSensor();
    }

    //TODO Separar Reader de Config
    @Ignore
    public SensorConfig(int sensorType, int nameStringResource, int intervalMin, TriggerEventInterface triggerEventInterface, SortedMap<String, Float> measure, boolean restartInitialMeasureAfterRead) {
        //TODO Setear intervalMax
        this(sensorType, intervalMin, intervalMin, intervalMin, nameStringResource);
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

    /**
     * Devuelve el mínimo delay que puede soportar el sensor en ms, dado por el sistema
     * @return
     */
    public final int getSensorSystemMinDelay(){
        return SensorHandler.getInstance().getSensorManager().getDefaultSensor(sensorType).getMinDelay() / 1000;
    }

    public boolean isDefaultConfigurationEnabled() {
        return defaultConfigurationEnabled;
    }

    public void setDefaultConfigurationEnabled(boolean defaultConfigurationEnabled) {
        this.defaultConfigurationEnabled = defaultConfigurationEnabled;
    }

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeLong(this.internalId);
        dest.writeLong(this.experimentId);
        dest.writeByte(this.defaultConfigurationEnabled ? (byte) 1 : (byte) 0);
        dest.writeInt(this.sensorType);
        dest.writeInt(this.accuracy);
        //dest.writeParcelable(this.sensor, flags);
        dest.writeInt(this.measure.size());
        for (Map.Entry<String, Float> entry : this.measure.entrySet()) {
            dest.writeString(entry.getKey());
            dest.writeValue(entry.getValue());
        }
        dest.writeInt(this.initialMeasure.size());
        for (Map.Entry<String, Float> entry : this.initialMeasure.entrySet()) {
            dest.writeString(entry.getKey());
            dest.writeValue(entry.getValue());
        }
//        dest.writeParcelable(this.sensorEventInterface, flags);
//        dest.writeParcelable(this.triggerEventInterface, flags);
//        dest.writeParcelable(this.triggerEventListener, flags);
        dest.writeByte(this.restartInitialMeasureAfterRead ? (byte) 1 : (byte) 0);
    }

    public void readFromParcel(Parcel source) {
        super.readFromParcel(source);
        this.internalId = source.readLong();
        this.experimentId = source.readLong();
        this.defaultConfigurationEnabled = source.readByte() != 0;
        this.sensorType = source.readInt();
        this.accuracy = source.readInt();
        //this.sensor = source.readParcelable(Sensor.class.getClassLoader());
        int measureSize = source.readInt();
        this.measure = new TreeMap<>();
        for (int i = 0; i < measureSize; i++) {
            String key = source.readString();
            Float value = (Float) source.readValue(Float.class.getClassLoader());
            this.measure.put(key, value);
        }
        int initialMeasureSize = source.readInt();
        this.initialMeasure = new TreeMap<>();
        for (int i = 0; i < initialMeasureSize; i++) {
            String key = source.readString();
            Float value = (Float) source.readValue(Float.class.getClassLoader());
            this.initialMeasure.put(key, value);
        }
//        this.sensorEventInterface = source.readParcelable(SensorEventInterface.class.getClassLoader());
//        this.triggerEventInterface = source.readParcelable(TriggerEventInterface.class.getClassLoader());
//        this.triggerEventListener = source.readParcelable(TriggerEventListener.class.getClassLoader());
        this.restartInitialMeasureAfterRead = source.readByte() != 0;
    }

    protected SensorConfig(Parcel in) {
        readFromParcel(in);
    }

    public static final Creator<SensorConfig> CREATOR = new Creator<SensorConfig>() {
        @Override
        public SensorConfig createFromParcel(Parcel source) {
            return new SensorConfig(source);
        }

        @Override
        public SensorConfig[] newArray(int size) {
            return new SensorConfig[size];
        }
    };
}
