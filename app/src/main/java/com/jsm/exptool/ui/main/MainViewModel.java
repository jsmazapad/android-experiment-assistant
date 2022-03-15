package com.jsm.exptool.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.jsm.exptool.core.ui.menu.BaseMenuViewModel;

public class MainViewModel extends BaseMenuViewModel {
    public MainViewModel(@NonNull Application application) {
        super(application);
    }
}
