package com.jsm.exptool.model.experimentconfig;

import android.os.Parcel;

import androidx.room.Ignore;

import com.jsm.exptool.R;
import com.jsm.exptool.model.SensorConfig;


import java.util.List;


public class SensorsGlobalConfig extends RepeatableElementConfig {

    //TODO meter en base de datos listado
    @Ignore  List<SensorConfig> sensors;

    public SensorsGlobalConfig(int interval, int intervalMin, int intervalMax, int nameStringResource /*, List<SensorConfig> sensors*/) {
        super(interval, intervalMin, intervalMax, nameStringResource);
//        this.sensors = sensors;
    }
    @Ignore
    public SensorsGlobalConfig() {
        this.nameStringResource = R.string.global_frequency_text;
    }
    @Ignore
    public SensorsGlobalConfig(List<SensorConfig> sensors) {
        this.nameStringResource = R.string.global_frequency_text;
        this.sensors = sensors;
    }

    public List<SensorConfig> getSensors() {
        return sensors;
    }

    public void setSensors(List<SensorConfig> sensors) {
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
        this.sensors = source.createTypedArrayList(SensorConfig.CREATOR);
    }
    @Ignore
    protected SensorsGlobalConfig(Parcel in) {
        super(in);
        readFromParcel(in);
    }

    public static final Creator<SensorsGlobalConfig> CREATOR = new Creator<SensorsGlobalConfig>() {
        @Override
        public SensorsGlobalConfig createFromParcel(Parcel source) {
            return new SensorsGlobalConfig(source);
        }

        @Override
        public SensorsGlobalConfig[] newArray(int size) {
            return new SensorsGlobalConfig[size];
        }
    };
}
