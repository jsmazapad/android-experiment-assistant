package com.jsm.exptool.model.Sensor;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.jsm.exptool.BuildConfig;
import com.jsm.exptool.config.SensorConfigConstants;
import com.jsm.exptool.model.Measure;
import com.jsm.exptool.model.MySensor;
import com.jsm.exptool.App;
import com.jsm.exptool.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimerTask;

public class GPS extends MySensor implements LocationListener {
    private LocationManager locationManager = (LocationManager) App.getAppContext().getSystemService(Context.LOCATION_SERVICE);
    public float longitude;
    public float latitude;
    public DecimalFormat format = new DecimalFormat("#.0000000");

    public GPS() {
        super(SensorConfigConstants.TYPE_GPS, R.string.gps, SensorConfigConstants.MIN_INTERVAL_MILLIS);
    }

    public void updateGUI() {
        this.myHandler.post(new Runnable() {
            public void run() {
                GPS.this.sensorEventInterface.eventData();
            }
        });
    }

    public void scheduleRecording() {
        this.timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if (GPS.this.isRecording) {
                    Date date = new Date();

                    ArrayList<Float> values = new ArrayList<Float>();
                    values.add(latitude);
                    values.add(longitude);

                    GPS.this.data.add(new Measure(date, type, values));
                    if (BuildConfig.DEBUG)
                        Log.d("Sensor", App.getAppContext().getString(GPS.this.getNameStringResource()) + ": " + values.toString());

                    updateGUI();
                } else {
                    this.cancel();
                }
            }
        }, 0, (long) this.interval);
    }

    public void setActive(boolean isActive) {

        if (isActive) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ActivityCompat.checkSelfPermission(App.getAppContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    this.active = false;
                    this.stop();
                }else{
                    this.active = true;
                    if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 3, this);
                    }else if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
                        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 3, this);
                    }else{
                        this.active = false;
                        this.stop();
                    }
                }
            }
        } else {
            this.active = false;
            locationManager.removeUpdates(this);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        this.longitude = (float) location.getLongitude();
        this.latitude = (float) location.getLatitude();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}

    @Override
    public void onProviderEnabled(String provider) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(App.getAppContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 3, this);
                } else if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 3, this);
                }
            }
        }
    }

    @Override
    public void onProviderDisabled(String provider) {
        this.longitude = 0.0f;
        this.latitude = 0.0f;
    }
}
