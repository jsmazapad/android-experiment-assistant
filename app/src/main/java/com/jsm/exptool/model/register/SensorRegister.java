package com.jsm.exptool.model.register;

import android.os.Parcel;

import androidx.annotation.StringRes;
import androidx.room.Entity;
import androidx.room.Ignore;

import java.util.Date;

@Entity(tableName = com.jsm.exptool.model.register.SensorRegister.TABLE_NAME, inheritSuperIndices = true)
public class SensorRegister extends ExperimentRegister {

    /**
     * The name of the table.
     */
    public static final String TABLE_NAME = "sensorRegisters";

    private float value1;
    private String value1String;
    private float value2;
    private String value2String;
    private float value3;
    private String value3String;
    private String sensorName;
    private int sensorType;
    private @StringRes int sensorNameResource;
    private int accuracy;

    public SensorRegister(long internalId, long experimentId, Date date, boolean dataRemoteSynced, float value1, String value1String,
                          float value2, String value2String, float value3, String value3String, String sensorName,
                          int sensorType, @StringRes int sensorNameResource, int accuracy) {
        super(internalId, experimentId, date, dataRemoteSynced);
        this.value1 = value1;
        this.value1String = value1String;
        this.value2 = value2;
        this.value2String = value2String;
        this.value3 = value3;
        this.value3String = value3String;
        this.sensorName = sensorName;
        this.sensorType = sensorType;
        this.sensorNameResource = sensorNameResource;
        this.accuracy = accuracy;
    }
    @Ignore
    public SensorRegister(long experimentId, Date date, boolean dataRemoteSynced, float value1, String value1String,
                          float value2, String value2String, float value3, String value3String, String sensorName,
                          int sensorType, @StringRes int sensorNameResource, int accuracy) {
        super(experimentId, date, dataRemoteSynced);
        this.value1 = value1;
        this.value1String = value1String;
        this.value2 = value2;
        this.value2String = value2String;
        this.value3 = value3;
        this.value3String = value3String;
        this.sensorName = sensorName;
        this.sensorType = sensorType;
        this.sensorNameResource = sensorNameResource;
        this.accuracy = accuracy;
    }


    public float getValue1() {
        return value1;
    }

    public void setValue1(float value1) {
        this.value1 = value1;
    }

    public String getValue1String() {
        return value1String;
    }

    public void setValue1String(String value1String) {
        this.value1String = value1String;
    }

    public float getValue2() {
        return value2;
    }

    public void setValue2(float value2) {
        this.value2 = value2;
    }

    public String getValue2String() {
        return value2String;
    }

    public void setValue2String(String value2String) {
        this.value2String = value2String;
    }

    public float getValue3() {
        return value3;
    }

    public void setValue3(float value3) {
        this.value3 = value3;
    }

    public String getValue3String() {
        return value3String;
    }

    public void setValue3String(String value3String) {
        this.value3String = value3String;
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public int getSensorType() {
        return sensorType;
    }

    public void setSensorType(@StringRes int sensorType) {
        this.sensorType = sensorType;
    }

    public @StringRes int getSensorNameResource() {
        return sensorNameResource;
    }

    public void setSensorNameResource(int sensorNameResource) {
        this.sensorNameResource = sensorNameResource;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    public String getDisplayString(){
        StringBuilder builder = new StringBuilder();
        if(value1String !=null && !"".equals(value1String)){
            String value = String.format("%s: %f\n", value1String, value1);
            builder.append(value);
        }
        if(value2String !=null && !"".equals(value2String)){
            String value = String.format("%s: %f\n", value2String, value2);
            builder.append(value);
        }
        if(value3String !=null && !"".equals(value3String)){
            String value = String.format("%s: %f\n", value3String, value3);
            builder.append(value);
        }

        return builder.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeFloat(this.value1);
        dest.writeString(this.value1String);
        dest.writeFloat(this.value2);
        dest.writeString(this.value2String);
        dest.writeFloat(this.value3);
        dest.writeString(this.value3String);
        dest.writeString(this.sensorName);
        dest.writeInt(this.sensorType);
        dest.writeInt(this.sensorNameResource);
        dest.writeInt(this.accuracy);
    }

    public void readFromParcel(Parcel source) {
        super.readFromParcel(source);
        this.value1 = source.readFloat();
        this.value1String = source.readString();
        this.value2 = source.readFloat();
        this.value2String = source.readString();
        this.value3 = source.readFloat();
        this.value3String = source.readString();
        this.sensorName = source.readString();
        this.sensorType = source.readInt();
        this.sensorNameResource = source.readInt();
        this.accuracy = source.readInt();
    }

    protected SensorRegister(Parcel in) {
        super(in);
        readFromParcel(in);
    }

    public static final Creator<SensorRegister> CREATOR = new Creator<SensorRegister>() {
        @Override
        public SensorRegister createFromParcel(Parcel source) {
            return new SensorRegister(source);
        }

        @Override
        public SensorRegister[] newArray(int size) {
            return new SensorRegister[size];
        }
    };
}
