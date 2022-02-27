package com.jsm.exptool.model.experimentconfig;

import androidx.room.Ignore;

import com.jsm.exptool.model.experimentconfig.RepeatableElement;

public class GlobalConfig extends RepeatableElement {
    public GlobalConfig(int interval, int intervalMin, int nameStringResource) {
        super(interval, intervalMin, nameStringResource);
    }
    @Ignore
    public GlobalConfig(){

    }

}
