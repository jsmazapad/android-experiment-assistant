package com.jsm.exptool.entities.experimentconfig;

import static com.jsm.exptool.config.SensorConfigConstants.TYPE_SENSORS_TO_RES_STRING;

import android.hardware.Sensor;

import android.os.Parcel;
import android.provider.BaseColumns;

import androidx.room.ColumnInfo;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.jsm.exptool.config.FrequencyConstants;
import com.jsm.exptool.providers.SensorProvider;
import com.jsm.exptool.entities.sensoreventsreader.SensorReader;



@Entity(tableName = SensorConfig.TABLE_NAME)
public class SensorConfig extends RepeatableElementConfig implements Cloneable {
    /** The name of the table. */
    public static final String TABLE_NAME = "experiment_sensors";

    /** The name of the ID column. */
    public static final String COLUMN_ID = BaseColumns._ID;

    @Expose @Embedded private SensorReader sensorReader;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = COLUMN_ID)
    @Expose private long internalId;
    @Expose private long experimentId;
    private boolean defaultConfigurationEnabled;


    /**
     * Constructor de objeto a partir de registro de DB
     * @param interval
     * @param intervalMin
     * @param intervalMax
     * @param nameStringResource
     * @param internalId
     * @param experimentId
     * @param sensorReader
     * @param defaultConfigurationEnabled
     */
    public SensorConfig(int interval, int intervalMin, int intervalMax, int nameStringResource, long internalId, long experimentId, SensorReader sensorReader, boolean defaultConfigurationEnabled) {
        this(sensorReader, intervalMin, intervalMax, interval, nameStringResource, defaultConfigurationEnabled);
        this.internalId = internalId;
        this.experimentId = experimentId;
        //Obtenemos una instancia completa del reader en base al tipo de sensor
        //Necesario para rescatar sensor de base de datos
        this.sensorReader = SensorProvider.getInstance().getSensorReader(sensorReader.getSensorType());

    }

    @Ignore
    public SensorConfig(SensorReader sensorReader, int intervalMin,int intervalMax, int interval,  int nameStringResource, boolean defaultConfigurationEnabled) {
        super(interval, intervalMin, intervalMax, nameStringResource);
        this.defaultConfigurationEnabled = defaultConfigurationEnabled;
        this.sensorReader = sensorReader;
        initSensorLimits();
    }


    @Ignore
    public SensorConfig(SensorReader sensorReader, int nameStringResource, int intervalMin, int intervalMax, int interval) {
        //TODO Estudiar cuando aplicar intervalMx e intervalMin de sensor
        this(sensorReader, intervalMin, intervalMax, interval, nameStringResource, true);
        this.defaultConfigurationEnabled = true;

    }

    @Override
    public int getNameStringResource() {
        return TYPE_SENSORS_TO_RES_STRING.get(sensorReader.getSensorType());
    }

    public void initSensorLimits(){
        Sensor sensor = sensorReader.getSensor();
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

    public SensorReader getSensorReader() {
        return sensorReader;
    }

    /**
     * Devuelve el mínimo delay que puede soportar el sensor en ms, dado por el sistema
     * @return
     */
    public final int getSensorSystemMinDelay(){
        return SensorProvider.getInstance().getSensorManager().getDefaultSensor(sensorReader.getSensorType()).getMinDelay() / 1000;
    }

    public boolean isDefaultConfigurationEnabled() {
        return defaultConfigurationEnabled;
    }

    public void setDefaultConfigurationEnabled(boolean defaultConfigurationEnabled) {
        this.defaultConfigurationEnabled = defaultConfigurationEnabled;
    }


    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
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
        //dest.writeParcelable(this.sensorReader, flags);

    }

    public void readFromParcel(Parcel source) {
        super.readFromParcel(source);
        this.internalId = source.readLong();
        this.experimentId = source.readLong();
        this.defaultConfigurationEnabled = source.readByte() != 0;
        //this.sensorReader = source.readParcelable(SensorReader.class.getClassLoader());

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
