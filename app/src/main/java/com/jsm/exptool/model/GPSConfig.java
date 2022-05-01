package com.jsm.exptool.model;

import android.location.Location;
import android.location.LocationListener;

import androidx.annotation.NonNull;

import com.jsm.exptool.R;
import com.jsm.exptool.config.SensorConfigConstants;

import java.util.List;

public class GPSConfig extends SensorConfig implements LocationListener {
    public GPSConfig() {
        super(SensorConfigConstants.TYPE_GPS, R.string.gps);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

    }

    @Override
    public void onLocationChanged(@NonNull List<Location> locations) {
        LocationListener.super.onLocationChanged(locations);
    }

    @Override
    public void onFlushComplete(int requestCode) {
        LocationListener.super.onFlushComplete(requestCode);
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        LocationListener.super.onProviderEnabled(provider);
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        LocationListener.super.onProviderDisabled(provider);
    }
}
