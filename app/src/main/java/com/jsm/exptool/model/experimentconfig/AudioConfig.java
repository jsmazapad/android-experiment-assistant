package com.jsm.exptool.model.experimentconfig;


import androidx.room.Ignore;

public class AudioConfig extends RepeatableElement{


    public AudioConfig(int interval, int intervalMin, int nameStringResource) {
        super(interval, intervalMin, nameStringResource);
    }
    @Ignore
    public AudioConfig(){

    }
}
