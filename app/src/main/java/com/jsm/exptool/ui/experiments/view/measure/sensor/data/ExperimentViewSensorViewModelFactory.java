package com.jsm.exptool.ui.experiments.view.measure.sensor.data;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.jsm.exptool.model.Experiment;
import com.jsm.exptool.model.MySensor;
import com.jsm.exptool.ui.experiments.view.ExperimentViewViewModel;


public class ExperimentViewSensorViewModelFactory implements ViewModelProvider.Factory {

    private Application app;
    MySensor sensor;
    long experimentId;


    public ExperimentViewSensorViewModelFactory(Application app, MySensor sensor, long experimentId) {
        this.app = app;
        this.sensor = sensor;
        this.experimentId = experimentId;

    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass == ExperimentViewViewModel.class) {
            return (T) new ExperimentViewSensorMeasuresViewModel(app, sensor, experimentId);
        }else{
            return null;
        }
    }
}
