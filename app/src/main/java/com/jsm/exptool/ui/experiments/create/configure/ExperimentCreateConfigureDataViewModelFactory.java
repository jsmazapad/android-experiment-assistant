package com.jsm.exptool.ui.experiments.create.configure;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.jsm.exptool.model.Experiment;

public class ExperimentCreateConfigureDataViewModelFactory implements ViewModelProvider.Factory {

    private Application app;
    private Experiment experiment;


    public ExperimentCreateConfigureDataViewModelFactory(Application app, Experiment experiment) {
        this.app = app;
        this.experiment = experiment;

    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass == ExperimentCreateConfigureDataViewModel.class) {
            return (T) new ExperimentCreateConfigureDataViewModel(app, experiment);
        }else{
            return null;
        }
    }
}
