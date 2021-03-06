package com.jsm.exptool.ui.experiments.view.measure;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.jsm.exptool.entities.experimentconfig.RepeatableElementConfig;


public class ExperimentViewRegistersViewModelFactory implements ViewModelProvider.Factory {

    private Application app;
    long experimentId;
    RepeatableElementConfig measurableItem;


    public ExperimentViewRegistersViewModelFactory(Application app, long experimentId, RepeatableElementConfig measurableItem) {
        this.app = app;
        this.experimentId = experimentId;
        this.measurableItem = measurableItem;

    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass == ExperimentViewRegistersViewModel.class) {
            return (T) new ExperimentViewRegistersViewModel(app, experimentId, measurableItem);
        }else{
            return null;
        }
    }
}
