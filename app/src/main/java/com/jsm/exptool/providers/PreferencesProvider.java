package com.jsm.exptool.providers;

import android.content.SharedPreferences;

import com.jsm.exptool.config.FrequencyConstants;
import com.jsm.exptool.core.utils.PreferenceManager;

/**
 * Almacen central de preferencias de usuario
 * Hace uso de sharedpreferences
 */
public class PreferencesProvider {

    //private final static String CAR_LOCATION = "CAR_LOCATION";
    private final static String PASSWORD = "PASSWORD";
    private final static String USER = "USER";
    private final static String REMOTE_SERVER = "REMOTE_SERVER";
    private final static String SENSOR_DEFAULT_FREQ = "SENSOR_DEFAULT_FREQ";
    private final static String CAMERA_DEFAULT_FREQ = "CAMERA_DEFAULT_FREQ";
    private final static String AUDIO_DEFAULT_FREQ = "AUDIO_DEFAULT_FREQ";


//    /**
//     * Obtiene la localización del vehículo guardada en SharedPreferences
//     * @return
//     */
//    public static VehicleLocation getVehicleLocation() {
//        String data = PreferenceManager.getSharedPreferences().getString(CAR_LOCATION, "");
//        if (!"".equals(data)) {
//            Gson gson = new GsonBuilder()
//                    .create();
//            return gson.fromJson(data, VehicleLocation.class);
//        }else {
//            return null;
//        }
//
//    }
//
//    public static void setVehicleLocation(VehicleLocation location) {
//        try {
//            Gson gson = new GsonBuilder()
//                    .create();
//            String value = gson.toJson(location);
//            SharedPreferences.Editor editor = PreferenceManager
//                    .getSharedPreferences().edit();
//            editor.putString(CAR_LOCATION, value);
//            editor.commit();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public static String getPassword() {
        String data = PreferenceManager.getSharedPreferences().getString(PASSWORD, "");
        return data;

    }

    public static void setPassword(String value) {
        try {
            SharedPreferences.Editor editor = PreferenceManager
                    .getSharedPreferences().edit();
            editor.putString(PASSWORD, value);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getUser() {
        String data = PreferenceManager.getSharedPreferences().getString(USER, "");
        return data;

    }

    public static void setUser(String value) {
        try {
            SharedPreferences.Editor editor = PreferenceManager
                    .getSharedPreferences().edit();
            editor.putString(USER, value);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getRemoteServer() {
        String data = PreferenceManager.getSharedPreferences().getString(REMOTE_SERVER, "");
        return data;

    }

    public static void setRemoteServer(String value) {
        try {
            SharedPreferences.Editor editor = PreferenceManager
                    .getSharedPreferences().edit();
            editor.putString(REMOTE_SERVER, value);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getSensorDefaultFreq() {
        int data = PreferenceManager.getSharedPreferences().getInt(SENSOR_DEFAULT_FREQ, FrequencyConstants.DEFAULT_SENSOR_FREQ);
        return data;

    }

    public static void setSensorDefaultFreq(int value) {
        try {
            SharedPreferences.Editor editor = PreferenceManager
                    .getSharedPreferences().edit();
            editor.putInt(SENSOR_DEFAULT_FREQ, value);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getCameraDefaultFreq() {
        int data = PreferenceManager.getSharedPreferences().getInt(CAMERA_DEFAULT_FREQ, FrequencyConstants.DEFAULT_CAMERA_FREQ);
        return data;

    }

    public static void setCameraDefaultFreq(int value) {
        try {
            SharedPreferences.Editor editor = PreferenceManager
                    .getSharedPreferences().edit();
            editor.putInt(CAMERA_DEFAULT_FREQ, value);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getAudioDefaultFreq() {
        int data = PreferenceManager.getSharedPreferences().getInt(AUDIO_DEFAULT_FREQ, FrequencyConstants.DEFAULT_AUDIO_FREQ);
        return data;

    }

    public static void setAudioDefaultFreq(int value) {
        try {
            SharedPreferences.Editor editor = PreferenceManager
                    .getSharedPreferences().edit();
            editor.putInt(AUDIO_DEFAULT_FREQ, value);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

