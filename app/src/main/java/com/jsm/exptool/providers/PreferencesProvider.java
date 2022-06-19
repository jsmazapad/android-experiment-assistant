package com.jsm.exptool.providers;

import android.content.SharedPreferences;
import android.util.Log;

import com.jsm.exptool.config.FrequencyConstants;
import com.jsm.exptool.libs.PreferenceManager;

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
    private final static String FIREBASE_KEY = "FIREBASE_KEY";
    private final static String REMOTE_SYNC_METHOD = "REMOTE_SYNC_METHOD";


    public static String getPassword() {
        return PreferenceManager.getSharedPreferences().getString(PASSWORD, "");

    }

    public static void setPassword(String value) {
        try {
            SharedPreferences.Editor editor = PreferenceManager
                    .getSharedPreferences().edit();
            editor.putString(PASSWORD, value);
            editor.commit();
        } catch (Exception e) {
            Log.w(PreferencesProvider.class.getSimpleName(), e.getMessage(), e);
        }
    }

    public static String getUser() {
        return PreferenceManager.getSharedPreferences().getString(USER, "");

    }

    public static void setUser(String value) {
        try {
            SharedPreferences.Editor editor = PreferenceManager
                    .getSharedPreferences().edit();
            editor.putString(USER, value);
            editor.commit();
        } catch (Exception e) {
            Log.w(PreferencesProvider.class.getSimpleName(), e.getMessage(), e);
        }
    }

    public static String getRemoteServer() {
        return PreferenceManager.getSharedPreferences().getString(REMOTE_SERVER, "");

    }

    public static void setRemoteServer(String value) {
        try {
            SharedPreferences.Editor editor = PreferenceManager
                    .getSharedPreferences().edit();
            editor.putString(REMOTE_SERVER, value);
            editor.commit();
        } catch (Exception e) {
            Log.w(PreferencesProvider.class.getSimpleName(), e.getMessage(), e);
        }
    }

    public static String getFirebaseKey() {
        return PreferenceManager.getSharedPreferences().getString(FIREBASE_KEY, "");

    }

    public static void setFirebaseKey(String value) {
        try {
            SharedPreferences.Editor editor = PreferenceManager
                    .getSharedPreferences().edit();
            editor.putString(FIREBASE_KEY, value);
            editor.commit();
        } catch (Exception e) {
            Log.w(PreferencesProvider.class.getSimpleName(), e.getMessage(), e);
        }
    }

    public static int getSensorDefaultFreq() {
        return PreferenceManager.getSharedPreferences().getInt(SENSOR_DEFAULT_FREQ, FrequencyConstants.DEFAULT_SENSOR_FREQ);

    }

    public static void setSensorDefaultFreq(int value) {
        try {
            SharedPreferences.Editor editor = PreferenceManager
                    .getSharedPreferences().edit();
            editor.putInt(SENSOR_DEFAULT_FREQ, value);
            editor.commit();
        } catch (Exception e) {
            Log.w(PreferencesProvider.class.getSimpleName(), e.getMessage(), e);
        }
    }

    public static int getCameraDefaultFreq() {
        return PreferenceManager.getSharedPreferences().getInt(CAMERA_DEFAULT_FREQ, FrequencyConstants.DEFAULT_CAMERA_FREQ);

    }

    public static void setCameraDefaultFreq(int value) {
        try {
            SharedPreferences.Editor editor = PreferenceManager
                    .getSharedPreferences().edit();
            editor.putInt(CAMERA_DEFAULT_FREQ, value);
            editor.commit();
        } catch (Exception e) {
            Log.w(PreferencesProvider.class.getSimpleName(), e.getMessage(), e);
        }
    }

    public static int getAudioDefaultFreq() {
        return PreferenceManager.getSharedPreferences().getInt(AUDIO_DEFAULT_FREQ, FrequencyConstants.DEFAULT_AUDIO_FREQ);

    }

    public static void setAudioDefaultFreq(int value) {
        try {
            SharedPreferences.Editor editor = PreferenceManager
                    .getSharedPreferences().edit();
            editor.putInt(AUDIO_DEFAULT_FREQ, value);
            editor.commit();
        } catch (Exception e) {
            Log.w(PreferencesProvider.class.getSimpleName(), e.getMessage(), e);
        }
    }

    public static int getLocationDefaultFreq() {
        return PreferenceManager.getSharedPreferences().getInt(LOCATION_DEFAULT_FREQ, FrequencyConstants.DEFAULT_LOCATION_FREQ);

    }

    public static void setLocationDefaultFreq(int value) {
        try {
            SharedPreferences.Editor editor = PreferenceManager
                    .getSharedPreferences().edit();
            editor.putInt(LOCATION_DEFAULT_FREQ, value);
            editor.commit();
        } catch (Exception e) {
            Log.w(PreferencesProvider.class.getSimpleName(), e.getMessage(), e);
        }
    }

    public static int getRemoteSyncDefaultFreq() {
        return PreferenceManager.getSharedPreferences().getInt(REMOTE_DEFAULT_FREQ, FrequencyConstants.DEFAULT_REMOTE_SYNC_FREQ);

    }

    public static void setRemoteSyncDefaultFreq(int value) {
        try {
            SharedPreferences.Editor editor = PreferenceManager
                    .getSharedPreferences().edit();
            editor.putInt(REMOTE_DEFAULT_FREQ, value);
            editor.commit();
        } catch (Exception e) {
            Log.w(PreferencesProvider.class.getSimpleName(), e.getMessage(), e);
        }
    }

    protected static String getSessionToken() {
        return PreferenceManager.getSharedPreferences().getString(SESSION_TOKEN, "");

    }

    protected static void setSessionToken(String value) {
        try {
            SharedPreferences.Editor editor = PreferenceManager
                    .getSharedPreferences().edit();
            editor.putString(SESSION_TOKEN, value);
            editor.commit();
        } catch (Exception e) {
            Log.w(PreferencesProvider.class.getSimpleName(), e.getMessage(), e);
        }
    }

    public static String getRemoteSyncMethod() {
        return PreferenceManager.getSharedPreferences().getString(REMOTE_SYNC_METHOD, RemoteSyncMethodsProvider.REMOTE_METHOD_NONE);

    }

    public static void setRemoteSyncMethod(String value) {
        try {
            SharedPreferences.Editor editor = PreferenceManager
                    .getSharedPreferences().edit();
            editor.putString(REMOTE_SYNC_METHOD, value);
            editor.commit();
        } catch (Exception e) {
            Log.w(PreferencesProvider.class.getSimpleName(), e.getMessage(), e);
        }
    }

}

