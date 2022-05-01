package com.jsm.exptool.providers;

import com.jsm.exptool.config.LocationConfigConstants;
import com.jsm.exptool.model.LocationOption;

import java.util.ArrayList;

public class LocationProvider {

    public static ArrayList<LocationOption> getLocationOptions(){
        return LocationConfigConstants.SUPPORTED_LOCATION_CONFIGURATIONS;
    }
}
