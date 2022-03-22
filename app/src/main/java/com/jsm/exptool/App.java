package com.jsm.exptool;

import android.app.Application;
import android.content.Context;
import android.hardware.SensorManager;
import android.util.Log;


import com.flurry.android.FlurryAgent;
import com.flurry.android.FlurryPerformance;
import com.jsm.exptool.core.utils.PreferenceManager;
import com.jsm.exptool.data.database.DBHelper;
import com.jsm.exptool.libs.SensorHandler;
import com.jsm.exptool.providers.PreferencesProvider;
import com.jsm.exptool.providers.WorksOrchestratorProvider;


public class App extends Application {




    @Override
    public void onCreate() {
        super.onCreate();
        try {
            //Inicialización de singletons que dependen del contexto de la aplicación
            DBHelper.initialize(this);
            PreferenceManager.initialize(this);
            SensorHandler.getInstance().initialize(this);

            //TODO Recoger cambios cuando se setea en configuración
            String analyticsKey = PreferencesProvider.getAnalyticsKey();
            if (!"".equals(analyticsKey)) {
                new FlurryAgent.Builder()
                        .withCaptureUncaughtExceptions(true)
                        .withIncludeBackgroundSessionsInMetrics(true)
                        .withLogLevel(Log.VERBOSE)
                        .withPerformanceMetrics(FlurryPerformance.ALL)
                        .build(this, analyticsKey);
            }

            WorksOrchestratorProvider orchestratorProvider = WorksOrchestratorProvider.getInstance();
            orchestratorProvider.init(this);




           // App.sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        } catch (Exception e) {
            //No se debe llegar a este punto nunca
            e.printStackTrace();
        }
    }

}
