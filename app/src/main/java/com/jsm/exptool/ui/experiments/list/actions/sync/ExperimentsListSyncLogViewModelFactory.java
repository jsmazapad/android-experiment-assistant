package com.jsm.exptool.ui.experiments.list.actions.sync;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.jsm.exptool.model.Experiment;
import com.jsm.exptool.ui.experiments.list.actions.ExperimentListActionsDialogViewModel;


public class ExperimentsListSyncLogViewModelFactory implements ViewModelProvider.Factory {

    private Application app;
    private Experiment experiment;


    public ExperimentsListSyncLogViewModelFactory(Application app, Experiment experiment) {
        this.app = app;
        this.experiment = experiment;

    }

    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass == ExperimentsListSyncLogViewModel.class) {
            return (T) new ExperimentsListSyncLogViewModel(app, experiment);
        }else{
            return null;
        }
    }
}
