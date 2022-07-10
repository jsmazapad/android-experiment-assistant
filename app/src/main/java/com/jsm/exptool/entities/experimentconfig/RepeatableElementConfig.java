package com.jsm.exptool.entities.experimentconfig;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.StringRes;
import androidx.room.Ignore;

import com.google.gson.annotations.Expose;
import com.jsm.exptool.R;

public class RepeatableElementConfig implements Parcelable {
    // Interval in milliseconds
    @Expose protected int interval;
    @Expose protected int intervalMin;
    @Expose protected int intervalMax;
    @Ignore protected @StringRes int nameStringRes;

    public RepeatableElementConfig(int interval, int intervalMin, int intervalMax) {
        this.interval = interval;
        this.intervalMin = intervalMin;
        this.intervalMax = intervalMax;
    }

    @Ignore public RepeatableElementConfig(int interval, int intervalMin, int intervalMax, @StringRes int nameStringRes) {
        this.interval = interval;
        this.intervalMin = intervalMin;
        this.intervalMax = intervalMax;
        this.nameStringRes = nameStringRes;
    }

    @Ignore public RepeatableElementConfig(){

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

    public int getNameStringRes(){
        return R.string.remote_sync;
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.interval);
        dest.writeInt(this.intervalMin);
        dest.writeInt(this.intervalMax);

    }

    public void readFromParcel(Parcel source) {
        this.interval = source.readInt();
        this.intervalMin = source.readInt();
        this.intervalMax = source.readInt();

    }

    protected RepeatableElementConfig(Parcel in) {
       readFromParcel(in);
    }

}
