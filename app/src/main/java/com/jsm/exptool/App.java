package com.jsm.exptool;

import android.app.Application;
import android.util.Log;


import com.flurry.android.FlurryAgent;
import com.flurry.android.FlurryPerformance;
import com.google.firebase.auth.FirebaseAuth;
import com.jsm.exptool.libs.PreferenceManager;
import com.jsm.exptool.data.database.DBHelper;
import com.jsm.exptool.providers.FirebaseProvider;
import com.jsm.exptool.providers.RemoteSyncMethodsProvider;
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
//            PreferencesProvider.setPassword("prueba");
//            PreferencesProvider.setUser("prueba");
//            PreferencesProvider.setRemoteServer("https://experiment-assistant-backend.getsandbox.com/");
            PreferencesProvider.setFirebasePassword("prueba");
            PreferencesProvider.setFirebaseUser("jsainz.developer.pruebas@gmail.com");
            PreferencesProvider.setFirebaseKey("AIzaSyBBD41C8IAedS_98SPgxUnn-a1qKzpodvk");
            PreferencesProvider.setFirebaseApp("1:815591212824:android:0e3ca018a4ccd1774d806d");
            PreferencesProvider.setFirebaseProject("experiments-assistant");
            PreferencesProvider.setFirebaseStorageBucket("experiments-assistant.appspot.com");
            PreferencesProvider.setRemoteSyncMethod(RemoteSyncMethodsProvider.REMOTE_METHOD_FIREBASE);



            //TODO Recoger cambios cuando se setea en configuración

            new FlurryAgent.Builder()
                    .withCaptureUncaughtExceptions(true)
                    .withIncludeBackgroundSessionsInMetrics(true)
                    .withLogLevel(Log.VERBOSE)
                    .withPerformanceMetrics(FlurryPerformance.ALL)
                    .build(this, "8YXD6WS2T8NRBQFGNVPD");

            RemoteSyncMethodsProvider.initStrategy(this);


            WorksOrchestratorProvider orchestratorProvider = WorksOrchestratorProvider.getInstance();
            orchestratorProvider.init(this);
            FirebaseAuth.getInstance(FirebaseProvider.getActiveInstance()).signOut();
            //MockExamples.createRandomCompletedExperiments(this);


            // App.sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        } catch (Exception e) {
            //No se debe llegar a este punto nunca
            Log.w(this.getClass().getSimpleName(), e.getMessage(), e);
        }
    }

}
