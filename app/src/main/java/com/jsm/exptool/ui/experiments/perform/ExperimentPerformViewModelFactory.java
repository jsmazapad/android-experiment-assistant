package com.jsm.exptool.ui.experiments.perform;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.jsm.exptool.entities.Experiment;


public class ExperimentPerformViewModelFactory implements ViewModelProvider.Factory {

    private Application app;
    private Experiment experiment;


    public ExperimentPerformViewModelFactory(Application app, Experiment experiment) {
        this.app = app;
        this.experiment = experiment;

    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass == ExperimentPerformViewModel.class) {
            return (T) new ExperimentPerformViewModel(app, experiment);
        }else{
            return null;
        }
    }
}
