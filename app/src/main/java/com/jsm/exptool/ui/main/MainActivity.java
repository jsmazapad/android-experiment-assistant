package com.jsm.exptool.ui.main;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.jsm.exptool.R;
import com.jsm.exptool.core.ui.base.BaseActivity;

public class MainActivity extends BaseActivity<MainViewModel> {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity);
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

    }
    @Override
    protected int getNavigationDiagramResource() {
        return R.id.nav_main_host_fragment;
    }
}

