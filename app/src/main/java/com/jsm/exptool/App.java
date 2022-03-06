package com.jsm.exptool;

import android.app.Application;
import android.content.Context;
import android.hardware.SensorManager;


import com.jsm.exptool.core.utils.PreferenceManager;
import com.jsm.exptool.data.database.DBHelper;
import com.jsm.exptool.libs.SensorHandler;


public class App extends Application {

    private static Context context;
//    private static SensorManager sensorManager;

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            DBHelper.initialize(this);
            PreferenceManager.initialize(this);
            App.context = getApplicationContext();
            SensorHandler.getInstance().initialize(this);

           // App.sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        } catch (Exception e) {
            //No se debe llegar a este punto nunca
            e.printStackTrace();
        }
    }

    public static Context getAppContext() {
        return App.context;
    }

//    public static SensorManager getSensorManager(){
//        return  App.sensorManager;
//    }
}
