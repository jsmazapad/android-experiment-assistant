package com.jsm.exptool.ui.main;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import com.jsm.exptool.R;
import com.jsm.exptool.config.ConfigConstants;
import com.jsm.exptool.core.ui.base.BaseActivity;
import com.jsm.exptool.core.ui.menu.BaseMenuActivity;
import com.jsm.exptool.providers.LocationProvider;

public class MainActivity extends BaseMenuActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.main_activity);

    }


    @Override
    protected int getNavigationDiagramResource() {
        return R.id.nav_host_fragment;
    }

    @Override
    public MainViewModel createViewModel() {
        return  new ViewModelProvider(this).get(MainViewModel.class);
    }

    //TODO Pasar a activity propio cuando se pase ExperimentPerform
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("ACTIVIY_RESULT", "onActivityResult() called with: " + "requestCode = [" + requestCode + "], resultCode = [" + resultCode + "], data = [" + data + "]");
        switch (requestCode) {
            case ConfigConstants.REQUEST_GPS_PERMISSION:
                    LocationProvider.getInstance().handleGpsActivationResult(this,requestCode, resultCode);
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
                break;
        }
    }
}

