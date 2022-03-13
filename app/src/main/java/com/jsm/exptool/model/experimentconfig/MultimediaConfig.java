package com.jsm.exptool.model.experimentconfig;

import android.os.Parcel;

public class MultimediaConfig extends RepeatableElement{

    public MultimediaConfig(int interval, int intervalMin, int nameStringResource) {
        super(interval, intervalMin, nameStringResource);
    }

    public MultimediaConfig() {
    }

    public MultimediaConfig(Parcel in) {
        super(in);
    }
}
