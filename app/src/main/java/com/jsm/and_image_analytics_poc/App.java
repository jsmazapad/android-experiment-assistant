package com.jsm.and_image_analytics_poc;

import android.app.Application;


import com.jsm.and_image_analytics_poc.core.utils.PreferenceManager;
import com.jsm.and_image_analytics_poc.data.database.DBHelper;


public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            DBHelper.initialize(this);
            PreferenceManager.initialize(this);

        } catch (Exception e) {
            //No se debe llegar a este punto nunca
            e.printStackTrace();
        }
    }
}
