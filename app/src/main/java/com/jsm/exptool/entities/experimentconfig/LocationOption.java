package com.jsm.exptool.entities.experimentconfig;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.StringRes;

public class LocationOption implements Parcelable {

    private @StringRes int titleTranslatableRes;
    private @StringRes int descriptionTranslatableRes;
    private int locationMode;
    private String locationReferenceName;

    public LocationOption(int titleTranslatableRes, int descriptionTranslatableRes, int locationMode, String locationReferenceName) {
        this.titleTranslatableRes = titleTranslatableRes;
        this.descriptionTranslatableRes = descriptionTranslatableRes;
        this.locationMode = locationMode;
        this.locationReferenceName = locationReferenceName;
    }

    public int getTitleTranslatableRes() {
        return titleTranslatableRes;
    }

    public void setTitleTranslatableRes(int titleTranslatableRes) {
        this.titleTranslatableRes = titleTranslatableRes;
    }

    public int getDescriptionTranslatableRes() {
        return descriptionTranslatableRes;
    }

    public void setDescriptionTranslatableRes(int descriptionTranslatableRes) {
        this.descriptionTranslatableRes = descriptionTranslatableRes;
    }

    public int getLocationMode() {
        return locationMode;
    }

    public void setLocationMode(int locationMode) {
        this.locationMode = locationMode;
    }

    public String getLocationReferenceName() {
        return locationReferenceName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.titleTranslatableRes);
        dest.writeInt(this.descriptionTranslatableRes);
        dest.writeInt(this.locationMode);
        dest.writeString(this.locationReferenceName);
    }

    public void readFromParcel(Parcel source) {
        this.titleTranslatableRes = source.readInt();
        this.descriptionTranslatableRes = source.readInt();
        this.locationMode = source.readInt();
        this.locationReferenceName = source.readString();
    }

    protected LocationOption(Parcel in) {
        this.readFromParcel(in);
    }

    public static final Parcelable.Creator<LocationOption> CREATOR = new Parcelable.Creator<LocationOption>() {
        @Override
        public LocationOption createFromParcel(Parcel source) {
            return new LocationOption(source);
        }

        @Override
        public LocationOption[] newArray(int size) {
            return new LocationOption[size];
        }
    };
}
