package com.jsm.exptool.providers;

import android.app.Application;

import androidx.work.WorkManager;


public class WorksOrchestratorProvider {

    private WorkManager mWorkManager;

    private static WorksOrchestratorProvider INSTANCE = null;

    // other instance variables can be here

    private WorksOrchestratorProvider() {

    };

    public static synchronized WorksOrchestratorProvider getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new WorksOrchestratorProvider();
        }
        return(INSTANCE);
    }

    public void init(Application application){
        mWorkManager = WorkManager.getInstance(application);
    }



}
