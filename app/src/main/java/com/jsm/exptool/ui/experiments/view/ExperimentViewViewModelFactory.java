package com.jsm.exptool.ui.experiments.view;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.jsm.exptool.entities.Experiment;


public class ExperimentViewViewModelFactory implements ViewModelProvider.Factory {

    private Application app;
    private Experiment experiment;


    public ExperimentViewViewModelFactory(Application app, Experiment experiment) {
        this.app = app;
        this.experiment = experiment;

    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass == ExperimentViewViewModel.class) {
            return (T) new ExperimentViewViewModel(app, experiment);
        }else{
            return null;
        }
    }
}
