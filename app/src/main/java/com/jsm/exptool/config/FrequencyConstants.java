package com.jsm.exptool.config;

public class FrequencyConstants {
    public static final int MIN_SENSOR_INTERVAL_MILLIS = 500;
    public static final int MAX_SENSOR_INTERVAL_MILLIS = 180000; // 3 min

    public static final int MIN_CAMERA_INTERVAL_MILLIS = 1000;
    public static final int MAX_CAMERA_INTERVAL_MILLIS = 180000; // 3 min

    public static final int MIN_AUDIO_INTERVAL_MILLIS = 3000;
    public static final int MAX_AUDIO_INTERVAL_MILLIS = 180000; // 3 min


    public static final int MIN_LOCATION_INTERVAL_MILLIS = 30000; //0,5 Min
    public static final int MAX_LOCATION_INTERVAL_MILLIS = 1800000; // 30 min

    //TODO Frecuencia de prueba
    //public static final int MIN_REMOTE_SYNC_INTERVAL_MILLIS = 30000; //0,5 Min
    public static final int MIN_REMOTE_SYNC_INTERVAL_MILLIS = 300000; // 5 min
    public static final int MAX_REMOTE_SYNC_INTERVAL_MILLIS = 600000; // 10 min

    /**
     * La frecuencia por defecto debe estar entre MIN_INTERVAL_MILLIS y MAX_INTERVAL_MILLIS
     */
    public static final int DEFAULT_SENSOR_FREQ = 3000; // 3 sec
    public static final int DEFAULT_CAMERA_FREQ = 3000; // 3 sec
    public static final int DEFAULT_AUDIO_FREQ = 5000; // 5 sec
    public static final int DEFAULT_LOCATION_FREQ = MIN_LOCATION_INTERVAL_MILLIS; // 5 sec
    public static final int DEFAULT_REMOTE_SYNC_FREQ = MIN_REMOTE_SYNC_INTERVAL_MILLIS;

    public static final int DEFAULT_STEPS_FREQ = 500; // 0,5 sec
}
