package com.jsm.exptool.entities.experimentconfig;

import android.os.Parcel;

import androidx.room.Embedded;
import androidx.room.Ignore;

import com.jsm.exptool.R;

public class LocationConfig extends RepeatableElementConfig {
    @Embedded private LocationOption locationOption;
    public LocationConfig(int interval, int intervalMin, int intervalMax) {
        super(interval, intervalMin, intervalMax);
    }

    public LocationOption getLocationOption() {
        return locationOption;
    }

    public void setLocationOption(LocationOption locationOption) {
        this.locationOption = locationOption;
    }

    @Override
    public int getNameStringRes() {
        return R.string.location;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeParcelable(this.locationOption, flags);
    }

    public void readFromParcel(Parcel source) {
        super.readFromParcel(source);
        this.locationOption = source.readParcelable(LocationOption.class.getClassLoader());
    }

    protected LocationConfig(Parcel in) {
        this.readFromParcel(in);
    }

    public static final Creator<LocationConfig> CREATOR = new Creator<LocationConfig>() {
        @Override
        public LocationConfig createFromParcel(Parcel source) {
            return new LocationConfig(source);
        }

        @Override
        public LocationConfig[] newArray(int size) {
            return new LocationConfig[size];
        }
    };
}
