package com.jsm.exptool.providers;

import android.content.SharedPreferences;

import com.jsm.exptool.config.FrequencyConstants;
import com.jsm.exptool.core.utils.PreferenceManager;

/**
 * Almacen central de preferencias de usuario
 * Hace uso de sharedpreferences
 */
public class PreferencesProvider {

    private final static String PASSWORD = "PASSWORD";
    private final static String USER = "USER";
    private final static String SESSION_TOKEN = "SESSION_TOKEN";
    private final static String REMOTE_SERVER = "REMOTE_SERVER";
    private final static String SENSOR_DEFAULT_FREQ = "SENSOR_DEFAULT_FREQ";
    private final static String CAMERA_DEFAULT_FREQ = "CAMERA_DEFAULT_FREQ";
    private final static String AUDIO_DEFAULT_FREQ = "AUDIO_DEFAULT_FREQ";
    private final static String LOCATION_DEFAULT_FREQ = "LOCATION_DEFAULT_FREQ";
    private final static String REMOTE_DEFAULT_FREQ = "REMOTE_DEFAULT_FREQ";
    private final static String ANALYTICS_KEY = "ANALYTICS_KEY";


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

    public static String getAnalyticsKey() {
        String data = PreferenceManager.getSharedPreferences().getString(ANALYTICS_KEY, "");
        return data;

    }

    public static void setAnalyticsKey(String value) {
        try {
            SharedPreferences.Editor editor = PreferenceManager
                    .getSharedPreferences().edit();
            editor.putString(ANALYTICS_KEY, value);
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

    public static int getLocationDefaultFreq() {
        int data = PreferenceManager.getSharedPreferences().getInt(LOCATION_DEFAULT_FREQ, FrequencyConstants.DEFAULT_LOCATION_FREQ);
        return data;

    }

    public static void setLocationDefaultFreq(int value) {
        try {
            SharedPreferences.Editor editor = PreferenceManager
                    .getSharedPreferences().edit();
            editor.putInt(LOCATION_DEFAULT_FREQ, value);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getRemoteSyncDefaultFreq() {
        int data = PreferenceManager.getSharedPreferences().getInt(REMOTE_DEFAULT_FREQ, FrequencyConstants.DEFAULT_REMOTE_SYNC_FREQ);
        return data;

    }

    public static void setRemoteSyncDefaultFreq(int value) {
        try {
            SharedPreferences.Editor editor = PreferenceManager
                    .getSharedPreferences().edit();
            editor.putInt(REMOTE_DEFAULT_FREQ, value);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected static String getSessionToken() {
        String data = PreferenceManager.getSharedPreferences().getString(SESSION_TOKEN, "");
        return data;

    }

    protected static void setSessionToken(String value) {
        try {
            SharedPreferences.Editor editor = PreferenceManager
                    .getSharedPreferences().edit();
            editor.putString(SESSION_TOKEN, value);
            editor.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

