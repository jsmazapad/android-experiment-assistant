package com.jsm.exptool.ui.experiments.create.cameraconfiguration;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.jsm.exptool.entities.experimentconfig.CameraConfig;

public class ExperimentCameraConfigurationViewModelFactory implements ViewModelProvider.Factory {

    private Application app;
    private CameraConfig cameraConfig;


    public ExperimentCameraConfigurationViewModelFactory(Application app, CameraConfig cameraConfig) {
        this.app = app;
        this.cameraConfig = cameraConfig;

    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass == ExperimentCameraConfigurationViewModel.class) {
            return (T) new ExperimentCameraConfigurationViewModel(app, cameraConfig);
        }else{
            return null;
        }
    }
}
