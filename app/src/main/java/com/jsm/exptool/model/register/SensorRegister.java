package com.jsm.exptool.model.register;

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
    private int accuracy;

    public SensorRegister(long internalId,  long experimentId, Date date, boolean dataRemoteSynced, float value1,
                          String value1String, float value2, String value2String, float value3, String value3String, String sensorName, int accuracy) {
        super(internalId, experimentId, date, dataRemoteSynced);
        this.value1 = value1;
        this.value1String = value1String;
        this.value2 = value2;
        this.value2String = value2String;
        this.value3 = value3;
        this.value3String = value3String;
        this.sensorName = sensorName;
        this.accuracy = accuracy;
    }

    @Ignore
    public SensorRegister( long experimentId, Date date, boolean dataRemoteSynced, float value1,
                           String value1String, float value2, String value2String, float value3, String value3String, String sensorName, int accuracy) {
        super(experimentId, date, dataRemoteSynced);
        this.value1 = value1;
        this.value1String = value1String;
        this.value2 = value2;
        this.value2String = value2String;
        this.value3 = value3;
        this.value3String = value3String;
        this.sensorName = sensorName;
        this.accuracy = accuracy;
    }



    public static String getTableName() {
        return TABLE_NAME;
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

    public int getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }
}
