package com.jsm.exptool;

import android.app.Application;
import android.util.Log;


import com.flurry.android.FlurryAgent;
import com.flurry.android.FlurryPerformance;
import com.jsm.exptool.libs.PreferenceManager;
import com.jsm.exptool.data.database.DBHelper;
import com.jsm.exptool.providers.SensorProvider;
import com.jsm.exptool.providers.PreferencesProvider;
import com.jsm.exptool.providers.SessionProvider;
import com.jsm.exptool.providers.worksorchestrator.WorksOrchestratorProvider;


public class App extends Application {




    @Override
    public void onCreate() {
        super.onCreate();
        try {
            //Inicialización de singletons que dependen del contexto de la aplicación
            DBHelper.initialize(this);
            PreferenceManager.initialize(this);
            SensorProvider.getInstance().initialize(this);
            //Al iniciar la aplicación, eliminamos los datos de sesión que hubiera guardados
            SessionProvider.clearSession();

            //TODO CODIGO PRUEBAS BORRAR
            PreferencesProvider.setPassword("prueba");
            PreferencesProvider.setUser("prueba");
            PreferencesProvider.setRemoteServer("https://experiment-assistant-backend.getsandbox.com/");

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

            //MockExamples.createRandomCompletedExperiments(this);




           // App.sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        } catch (Exception e) {
            //No se debe llegar a este punto nunca
            Log.w(this.getClass().getSimpleName(), e.getMessage(), e);
        }
    }

}
