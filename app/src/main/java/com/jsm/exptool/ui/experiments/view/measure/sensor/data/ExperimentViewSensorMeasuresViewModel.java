package com.jsm.exptool.ui.experiments.view.measure.sensor.data;

import android.app.Application;
import android.content.Context;

import androidx.navigation.NavController;

import com.jsm.exptool.core.data.repositories.responses.ListResponse;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerViewModel;
import com.jsm.exptool.model.Experiment;
import com.jsm.exptool.model.MySensor;
import com.jsm.exptool.model.register.SensorRegister;
import com.jsm.exptool.repositories.ExperimentsRepository;
import com.jsm.exptool.repositories.SensorsRepository;

import java.util.List;

public class ExperimentViewSensorMeasuresViewModel extends BaseRecyclerViewModel<SensorRegister, SensorRegister> {

    MySensor sensor;
    long experimentId;

    public ExperimentViewSensorMeasuresViewModel(Application app, MySensor sensor, long experimentId) {
        super(app, sensor, (long) experimentId);
    }

    @Override
    public List<SensorRegister> transformResponse(ListResponse<SensorRegister> response) {
        return response.getResultList();
    }

    @Override
    public void onItemSelected(int position, NavController navController, Context c) {

    }

    @Override
    public void setConstructorParameters(Object... args) {
        this.sensor = (MySensor) args[0];
        this.experimentId = (Long) args [1];

    }

    @Override
    public void callRepositoryForData() {
        SensorsRepository.getRegistersByTypeAndExperimentId(sensor.getSensorType(), sensor.getExperimentId(), apiResponseRepositoryHolder);
    }
}
