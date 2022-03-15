package com.jsm.exptool.core.ui.menu;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BaseMenuViewModel extends AndroidViewModel {

    private final MutableLiveData<String> versionApp = new MutableLiveData<>();

    public BaseMenuViewModel(@NonNull Application application) {
        super(application);
    }


    public void setVisibleLateralMenuLogo(Context context, ImageView imageView) {
        int orientation = context.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            imageView.setVisibility(View.GONE);
        } else {
            imageView.setVisibility(View.VISIBLE);
        }

    }

    public MutableLiveData<String> getVersionApp() {
        return versionApp;
    }

    public void setVersionApp(String versionApps) {
        versionApp.setValue(versionApps);
    }

}
