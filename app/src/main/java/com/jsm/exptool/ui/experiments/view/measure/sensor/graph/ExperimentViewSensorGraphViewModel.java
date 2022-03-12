package com.jsm.exptool.ui.experiments.view.measure.sensor.graph;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;

import com.jsm.exptool.core.data.repositories.responses.ListResponse;
import com.jsm.exptool.core.ui.base.BaseViewModel;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerViewModel;
import com.jsm.exptool.model.MySensor;
import com.jsm.exptool.model.register.SensorRegister;
import com.jsm.exptool.repositories.SensorsRepository;

import java.util.List;

public class ExperimentViewSensorGraphViewModel extends BaseViewModel {

    MySensor sensor;
    long experimentId;


    public ExperimentViewSensorGraphViewModel(@NonNull Application application) {
        super(application);
    }
}
