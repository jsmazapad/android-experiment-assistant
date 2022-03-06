package com.jsm.exptool.model.experimentconfig;

import android.os.Parcel;

import androidx.room.Ignore;

import com.jsm.exptool.model.MySensor;


import java.util.List;


public class SensorsConfig extends RepeatableElement {

    //TODO meter en base de datos listado
    @Ignore  List<MySensor> sensors;

    public SensorsConfig(int interval, int intervalMin, int nameStringResource /*, List<MySensor> sensors*/) {
        super(interval, intervalMin, nameStringResource);
//        this.sensors = sensors;
    }
    @Ignore
    public SensorsConfig() {
    }
    @Ignore
    public SensorsConfig(List<MySensor> sensors) {
        this.sensors = sensors;
    }

    public List<MySensor> getSensors() {
        return sensors;
    }

    public void setSensors(List<MySensor> sensors) {
        this.sensors = sensors;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeTypedList(this.sensors);
    }

    public void readFromParcel(Parcel source) {
        super.readFromParcel(source);
        this.sensors = source.createTypedArrayList(MySensor.CREATOR);
    }
    @Ignore
    protected SensorsConfig(Parcel in) {
        super(in);
        readFromParcel(in);
    }

    public static final Creator<SensorsConfig> CREATOR = new Creator<SensorsConfig>() {
        @Override
        public SensorsConfig createFromParcel(Parcel source) {
            return new SensorsConfig(source);
        }

        @Override
        public SensorsConfig[] newArray(int size) {
            return new SensorsConfig[size];
        }
    };
}
