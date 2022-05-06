package com.jsm.exptool.ui.experiments.create.basicdata;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.jsm.exptool.model.Experiment;
import com.jsm.exptool.ui.experiments.perform.ExperimentPerformViewModel;


public class ExperimentCreateBasicDataViewModelFactory implements ViewModelProvider.Factory {

    private Application app;
    private Experiment experiment;


    public ExperimentCreateBasicDataViewModelFactory(Application app, Experiment experiment) {
        this.app = app;
        this.experiment = experiment;
    }

    //TODO Extraer factoría común a todos los VM que usan sólo experiment
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass == ExperimentCreateBasicDataViewModel.class) {
            return (T) new ExperimentCreateBasicDataViewModel(app, experiment);
        }else{
            return null;
        }
    }
}
