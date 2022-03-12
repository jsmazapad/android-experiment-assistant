package com.jsm.exptool.ui.experiments.view.measure;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.jsm.exptool.model.Experiment;
import com.jsm.exptool.model.MySensor;
import com.jsm.exptool.ui.experiments.view.ExperimentViewViewModel;


public class ExperimentViewRegistersViewModelFactory implements ViewModelProvider.Factory {

    private Application app;
    long experimentId;
    MySensor sensor;


    public ExperimentViewRegistersViewModelFactory(Application app, long experimentId, MySensor sensor) {
        this.app = app;
        this.experimentId = experimentId;
        this.sensor = sensor;

    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass == ExperimentViewRegistersViewModel.class) {
            return (T) new ExperimentViewRegistersViewModel(app, experimentId, sensor);
        }else{
            return null;
        }
    }
}
