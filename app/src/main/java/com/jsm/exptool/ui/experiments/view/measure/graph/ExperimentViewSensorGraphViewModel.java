package com.jsm.exptool.ui.experiments.view.measure.graph;

import android.app.Application;

import androidx.annotation.NonNull;

import com.jsm.exptool.core.ui.base.BaseViewModel;
import com.jsm.exptool.model.SensorConfig;
import com.jsm.exptool.model.register.SensorRegister;

import java.util.List;

public class ExperimentViewSensorGraphViewModel extends BaseViewModel {

    SensorConfig sensor;
    List<SensorRegister> registers;


    public ExperimentViewSensorGraphViewModel(@NonNull Application application) {
        super(application);
    }

    public ExperimentViewSensorGraphViewModel(Application app, SensorConfig sensor, List<SensorRegister> registers) {
        super(app);
        this.sensor = sensor;
        this.registers = registers;
    }
}
