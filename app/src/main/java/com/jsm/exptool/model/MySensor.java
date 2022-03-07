package com.jsm.exptool.model;

import android.os.Handler;
import android.os.Parcel;
import android.provider.BaseColumns;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.jsm.exptool.model.experimentconfig.RepeatableElement;

import java.util.ArrayList;
import java.util.Timer;

@Entity(tableName = MySensor.TABLE_NAME)
public class MySensor extends RepeatableElement implements Cloneable {
    /** The name of the table. */
    public static final String TABLE_NAME = "sensors";

    /** The name of the ID column. */
    public static final String COLUMN_ID = BaseColumns._ID;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = COLUMN_ID)
    private long internalId;
    private long experimentId;
    public int type;
    @Ignore public boolean active;
    @Ignore public boolean isRecording = false;
    @Ignore  public ArrayList <Measure> data = new ArrayList();
    @Ignore  public Timer timer;
    @Ignore  public SensorEventInterface sensorEventInterface;
    @Ignore  public Handler myHandler = new Handler();
    @Ignore
    public MySensor(int type, int rName){
        this.type = type;
        this.nameStringResource = rName;
    }

    public MySensor(int interval, int intervalMin, int nameStringResource, long internalId, long experimentId, int type) {
        super(interval, intervalMin, nameStringResource);
        this.internalId = internalId;
        this.experimentId = experimentId;
        this.type = type;
    }

    @Ignore
    public MySensor(int type, int nameStringResource, int intervalMin, int interval) {
        this.type = type;
        this.nameStringResource = nameStringResource;
        this.intervalMin = intervalMin;
        this.interval = interval;
    }

    @Ignore
    public MySensor(int type, int nameStringResource, int intervalMin) {
        this.type = type;

        this.nameStringResource = nameStringResource;
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
        nameStringResource = in.readInt();
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
        dest.writeInt(nameStringResource);
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
