package com.jsm.exptool.model.experimentconfig;

import android.os.Parcel;

public class MultimediaConfig extends RepeatableElementConfig {

    public MultimediaConfig(int interval, int intervalMin, int intervalMax, int nameStringResource) {
        super(interval, intervalMin, intervalMax, nameStringResource);
    }

    public MultimediaConfig() {
    }

    public MultimediaConfig(Parcel in) {
        super(in);
    }
}
