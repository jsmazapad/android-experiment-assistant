package com.jsm.exptool.ui.main;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.os.PersistableBundle;

import com.jsm.exptool.R;
import com.jsm.exptool.core.ui.base.BaseActivity;
import com.jsm.exptool.core.ui.menu.BaseMenuActivity;

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
}

