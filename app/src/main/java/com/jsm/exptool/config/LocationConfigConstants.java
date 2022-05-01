package com.jsm.exptool.config;

import com.google.android.gms.location.LocationRequest;
import com.jsm.exptool.R;
import com.jsm.exptool.model.AudioRecordingOption;
import com.jsm.exptool.model.LocationOption;

import java.util.ArrayList;

public class LocationConfigConstants {
    public static final String HIGH_ACCURACY = "HIGH_ACCURACY";
    public static final String BALANCED_POWER = "BALANCED_POWER";
    public static final String LOW_POWER = "LOW_POWER";
    public static final ArrayList<LocationOption> SUPPORTED_LOCATION_CONFIGURATIONS = new ArrayList<LocationOption>() {
        {
            add(new LocationOption(R.string.high_accuracy_title, R.string.high_accuracy_description, LocationRequest.PRIORITY_HIGH_ACCURACY, HIGH_ACCURACY));
            add(new LocationOption(R.string.balanced_accuracy_title, R.string.balanced_accuracy_text, LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY, BALANCED_POWER));
            add(new LocationOption(R.string.low_accuracy_title, R.string.low_accuracy_text, LocationRequest.PRIORITY_LOW_POWER, LOW_POWER));

        }};
}
