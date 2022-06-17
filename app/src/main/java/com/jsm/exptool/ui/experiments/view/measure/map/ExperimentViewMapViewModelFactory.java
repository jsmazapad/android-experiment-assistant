package com.jsm.exptool.ui.experiments.view.measure.map;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.jsm.exptool.entities.register.ExperimentRegister;
import com.jsm.exptool.ui.experiments.view.measure.data.ExperimentViewDataMeasuresViewModel;

import java.util.List;


public class ExperimentViewMapViewModelFactory implements ViewModelProvider.Factory {

    private Application app;
    List<ExperimentRegister> registers;


    public ExperimentViewMapViewModelFactory(Application app,  List<ExperimentRegister> registers ) {
        this.app = app;
        this.registers = registers;

    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass == ExperimentViewDataMeasuresViewModel.class) {
            return (T) new ExperimentViewMapViewModel(app, registers);
        }else{
            return null;
        }
    }
}
