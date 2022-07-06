package com.jsm.exptool.entities.experimentconfig;

import android.os.Parcel;

public abstract class MultimediaConfig extends RepeatableElementConfig {

    public MultimediaConfig(int interval, int intervalMin, int intervalMax) {
        super(interval, intervalMin, intervalMax);
    }

//    public MultimediaConfig() {
//    }

    public MultimediaConfig(Parcel in) {
        super(in);
    }
}
