package com.jsm.exptool.model.experimentconfig;

import android.os.Parcel;
import android.os.Parcelable;

public class RepeatableElement implements Parcelable {
    // Interval in milliseconds
    public int interval;
    public int intervalMin;
    protected int nameStringResource;

    public RepeatableElement(int interval, int intervalMin, int nameStringResource) {
        this.interval = interval;
        this.intervalMin = intervalMin;
        this.nameStringResource = nameStringResource;
    }

    public RepeatableElement(){

    }

    public final int getNameStringResource() {
        return this.nameStringResource;
    }

    public final int getInterval() {
        return this.interval;
    }

    public final void setInterval(int interval) {
        this.interval = interval;
    }

    public final int getIntervalMin() {
        return this.intervalMin;
    }

    public final void setIntervalMin(int intervalMin) {
        this.intervalMin = interval;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.interval);
        dest.writeInt(this.intervalMin);
        dest.writeInt(this.nameStringResource);
    }

    public void readFromParcel(Parcel source) {
        this.interval = source.readInt();
        this.intervalMin = source.readInt();
        this.nameStringResource = source.readInt();
    }

    protected RepeatableElement(Parcel in) {
        readFromParcel(in);
    }

}
