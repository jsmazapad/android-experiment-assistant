package com.jsm.exptool.ui.experiments.list.actions;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.jsm.exptool.model.Experiment;


public class ExperimentListActionDialogViewModelFactory implements ViewModelProvider.Factory {

    private Application app;
    private Experiment experiment;


    public ExperimentListActionDialogViewModelFactory(Application app, Experiment experiment) {
        this.app = app;
        this.experiment = experiment;

    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass == ExperimentListActionsDialogViewModel.class) {
            return (T) new ExperimentListActionsDialogViewModel(app, experiment);
        }else{
            return null;
        }
    }
}
