package com.jsm.exptool.model;

import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Timer;

public class MySensor implements Cloneable, Repeatable {
    public int type;
    private int rName;
    // Interval in milliseconds
    public int interval;
    public int intervalMin;
    public boolean active;
    public boolean isRecording = false;
    public ArrayList <Measure> data = new ArrayList();
    public Timer timer;
    public SensorEventInterface sensorEventInterface;
    public Handler myHandler = new Handler();

    public MySensor(int type, int rName){
        this.type = type;
        this.rName = rName;
    }

    public MySensor(int type, int rName, int intervalMin) {
        this.type = type;

        this.rName = rName;
        this.intervalMin = intervalMin;
        this.interval = intervalMin;
    }

    public final void record() {
        this.isRecording = true;
        this.timer = new Timer();
        MySensor.this.setActive(true);
        scheduleRecording();
    }

    public void scheduleRecording() {
    }

    public final void stop(){
        this.isRecording = false;
        MySensor.this.setActive(false);
        data.remove(0);
    }

    public final void reset() {
        this.data.clear();
        this.isRecording = false;
    }

    @Override
    public final int getRName(){return  this.rName;}

    public final int getType(){return  this.type;}

    public final boolean isActive() {
        return this.active;
    }

    public void setActive(boolean isActive) {
        this.active = isActive;
    }

    public final void setRecording(boolean isRecording2) {
        this.isRecording = isRecording2;
    }

    public final boolean isRecording() {
        return this.isRecording;
    }

    @Override
    public final int getInterval() {return this.interval;}

    @Override
    public final void setInterval(int interval) {
        this.interval = interval;
    }

    @Override
    public final int getIntervalMin() {return this.intervalMin;}

    @Override
    public final void setIntervalMin(int intervalMin) {
        this.intervalMin = interval;
    }


    public ArrayList <Measure> getData() {
        return data;
    }

    public void setData(ArrayList <Measure> data) {
        this.data = data;
    }

    public SensorEventInterface getSensorEventInterface() {
        return sensorEventInterface;
    }

    public void setSensorEventInterface(SensorEventInterface sensorEventInterface) {
        this.sensorEventInterface = sensorEventInterface;
    }

    protected MySensor(Parcel in) {
        type = in.readInt();
        rName = in.readInt();
        interval = in.readInt();
        intervalMin = in.readInt();
        active = in.readByte() != 0;
        isRecording = in.readByte() != 0;
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
        dest.writeInt(rName);
        dest.writeInt(interval);
        dest.writeInt(intervalMin);
        dest.writeByte((byte) (active ? 1 : 0));
        dest.writeByte((byte) (isRecording ? 1 : 0));
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
