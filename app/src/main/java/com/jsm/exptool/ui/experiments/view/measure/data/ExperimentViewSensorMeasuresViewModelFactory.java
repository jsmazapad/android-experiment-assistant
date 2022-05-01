package com.jsm.exptool.ui.experiments.view.measure.data;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.jsm.exptool.model.experimentconfig.RepeatableElementConfig;
import com.jsm.exptool.model.register.ExperimentRegister;

import java.util.List;


public class ExperimentViewSensorMeasuresViewModelFactory implements ViewModelProvider.Factory {

    private Application app;
    RepeatableElementConfig measurableItem;
    List<ExperimentRegister> registers;


    public ExperimentViewSensorMeasuresViewModelFactory(Application app, RepeatableElementConfig measurableItem, List<ExperimentRegister> registers ) {
        this.app = app;
        this.measurableItem = measurableItem;
        this.registers = registers;

    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass == ExperimentViewDataMeasuresViewModel.class) {
            return (T) new ExperimentViewDataMeasuresViewModel(app, measurableItem, registers);
        }else{
            return null;
        }
    }
}
