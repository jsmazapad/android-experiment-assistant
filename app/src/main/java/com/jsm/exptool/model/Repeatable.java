package com.jsm.exptool.model;

import android.os.Parcelable;

public interface Repeatable extends Parcelable {
    int getRName();

    int getInterval();

    void setInterval(int interval);

    int getIntervalMin();

    void setIntervalMin(int intervalMin);
}
