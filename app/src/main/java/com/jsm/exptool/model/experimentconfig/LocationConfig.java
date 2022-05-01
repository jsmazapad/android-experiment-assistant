package com.jsm.exptool.model.experimentconfig;

import androidx.room.Embedded;
import androidx.room.Ignore;

import com.jsm.exptool.model.LocationOption;

public class LocationConfig extends RepeatableElementConfig {
    @Embedded private LocationOption locationOption;
    public LocationConfig(int interval, int intervalMin, int intervalMax, int nameStringResource) {
        super(interval, intervalMin, intervalMax, nameStringResource);
    }

    public LocationOption getLocationOption() {
        return locationOption;
    }

    public void setLocationOption(LocationOption locationOption) {
        this.locationOption = locationOption;
    }

    @Ignore
    public LocationConfig(){

    }


}
