package com.jsm.exptool.ui.experiments.view.measure.graph;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.jsm.exptool.entities.SensorConfig;
import com.jsm.exptool.entities.register.SensorRegister;
import com.jsm.exptool.ui.experiments.view.measure.data.ExperimentViewDataMeasuresViewModel;

import java.util.List;


public class ExperimentViewSensorGraphViewModelFactory implements ViewModelProvider.Factory {

    private Application app;
    SensorConfig sensor;
    List<SensorRegister> registers;


    public ExperimentViewSensorGraphViewModelFactory(Application app, SensorConfig sensor, List<SensorRegister> registers ) {
        this.app = app;
        this.sensor = sensor;
        this.registers = registers;

    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass == ExperimentViewDataMeasuresViewModel.class) {
            return (T) new ExperimentViewSensorGraphViewModel(app, sensor, registers);
        }else{
            return null;
        }
    }
}
