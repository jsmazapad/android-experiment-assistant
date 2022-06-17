package com.jsm.exptool.providers;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.jsm.exptool.R;
import com.jsm.exptool.config.ConfigConstants;
import com.jsm.exptool.config.LocationConfigConstants;
import com.jsm.exptool.core.utils.ModalMessage;
import com.jsm.exptool.libs.GPSUtils;
import com.jsm.exptool.entities.LocationOption;

import java.util.List;

public class LocationProvider {

    private FusedLocationProviderClient mFusedLocationClient;
    private final int locationRequestCode = ConfigConstants.REQUEST_LOCATION_PERMISSION;
    private final int gpsRequestCode = ConfigConstants.REQUEST_GPS_PERMISSION;
    private LocationRequest locationRequest;
    private LocationOption locationOption;
    protected boolean isGpsEnabled = false;
    private GPSUtils gpsUtils;
    private Location currentLocation;
    private static LocationProvider INSTANCE = null;

    // other instance variables can be here

    private LocationProvider() {

    }


    public static synchronized LocationProvider getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new LocationProvider();
        }
        return (INSTANCE);
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void initLocation(Context context, LocationOption option){
        this.locationOption = option;
        initLocationServices(context);
    }

    public static List<LocationOption> getLocationOptions(){
        return LocationConfigConstants.SUPPORTED_LOCATION_CONFIGURATIONS;
    }

    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {

            if (locationResult == null) {
                return;
            }
            int locationsSize = locationResult.getLocations().size();
            if (locationsSize >= 1) {
                currentLocation = locationResult.getLocations().get(locationResult.getLocations().size() - 1);

//                if (mFusedLocationClient != null) {
//                    //Una vez obtenida la ubicación ya no es necesario seguir intentándolo
//                    mFusedLocationClient.removeLocationUpdates(locationCallback);
//                }

            }
        }

    };

    private void initLocationServices(Context context) {


        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        locationRequest = LocationRequest.create();
        locationRequest.setPriority(locationOption.getLocationMode());
        locationRequest.setInterval(2000);//2 segundos

            locateUser(context);

    }

    public void stopLocation(){
        if (mFusedLocationClient != null) {
            mFusedLocationClient.removeLocationUpdates(locationCallback);
        }
    }



    protected void locateUser(Context context) {

        if (gpsUtils == null) {
            gpsUtils = new GPSUtils(context, locationOption.getLocationMode());
        }
        gpsUtils.turnGPSOn(isGPSEnable -> {
            // turn on GPS
            LocationProvider.this.isGpsEnabled = isGPSEnable;
            if (isGPSEnable) {

                //Cuando pasamos por aquí ya tenemos permiso, no obstante colocamos esta condición para asegurarlo en el acceso
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ModalMessage.showError(context, context.getString(R.string.location_permission_error), null, null, null, null);
                    return;
                }
                mFusedLocationClient.getLocationAvailability().addOnSuccessListener(locationAvailability -> {

                    //TODO Revisar para localizaciones periódicas
                    if (locationAvailability.isLocationAvailable()) {

                        mFusedLocationClient.getLastLocation().addOnSuccessListener((Activity) context, updatedLocation -> {
                            if (updatedLocation != null) {
                                //TODO callback para obtener ubicación
                                currentLocation = updatedLocation;
                            } else {
                                //Si no puede obtener la última ubicación triggea la obtención de ubicación
                                mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
                            }
                        });

                        mFusedLocationClient.getLastLocation().addOnFailureListener((Activity) context, failure -> {

                            Log.d("Location Error", "Error al obtener la ubicación");


                        });
                    } else {
                        mFusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);


                    }
                });
            }
        });

    }


    //TODO Manejo de permisos GPS
    public void handleRequestPermissions(Context context, int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case locationRequestCode: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initLocationServices(context);
                } else {

                    ModalMessage.showError(context, context.getString(R.string.location_permission_error), null, null, null, null);
                }
                break;
            }

        }

    }
    //TODO Manejo de permisos GPS
    public void handleGpsActivationResult(Context context, int requestCode, int resultCode) {
        switch (requestCode) {
            case gpsRequestCode: {
                if (resultCode == AppCompatActivity.RESULT_OK) {
                    initLocationServices(context);
                }
                break;
            }

        }

    }
}
