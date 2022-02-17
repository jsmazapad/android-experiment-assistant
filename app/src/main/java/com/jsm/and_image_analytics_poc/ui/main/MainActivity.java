package com.jsm.and_image_analytics_poc.ui.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import com.jsm.and_image_analytics_poc.R;
import com.jsm.and_image_analytics_poc.core.ui.base.BaseActivity;
import com.jsm.and_image_analytics_poc.ui.camera.CameraFragment;

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

