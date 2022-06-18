package com.jsm.exptool.entities.experimentconfig;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Ignore;

import com.google.gson.annotations.Expose;

public class RepeatableElementConfig implements Parcelable {
    // Interval in milliseconds
    @Expose protected int interval;
    @Expose protected int intervalMin;
    @Expose protected int intervalMax;
    @Expose protected int nameStringResource;

    public RepeatableElementConfig(int interval, int intervalMin, int intervalMax, int nameStringResource) {
        this.interval = interval;
        this.intervalMin = intervalMin;
        this.intervalMax = intervalMax;
        this.nameStringResource = nameStringResource;
    }

    @Ignore public RepeatableElementConfig(){

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

    public int getIntervalMax() {
        return intervalMax;
    }

    public void setIntervalMax(int intervalMax) {
        this.intervalMax = intervalMax;
    }

    public static final Creator<RepeatableElementConfig> CREATOR = new Creator<RepeatableElementConfig>() {
        @Override
        public RepeatableElementConfig createFromParcel(Parcel in) {
            return new RepeatableElementConfig(in);
        }

        @Override
        public RepeatableElementConfig[] newArray(int size) {
            return new RepeatableElementConfig[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.interval);
        dest.writeInt(this.intervalMin);
        dest.writeInt(this.intervalMax);
        dest.writeInt(this.nameStringResource);
    }

    public void readFromParcel(Parcel source) {
        this.interval = source.readInt();
        this.intervalMin = source.readInt();
        this.intervalMax = source.readInt();
        this.nameStringResource = source.readInt();
    }

    protected RepeatableElementConfig(Parcel in) {
       readFromParcel(in);
    }

}
