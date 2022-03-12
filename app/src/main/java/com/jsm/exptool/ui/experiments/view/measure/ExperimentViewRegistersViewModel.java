package com.jsm.exptool.ui.experiments.view.measure;

import android.app.Application;
import android.content.Context;

import androidx.navigation.NavController;

import com.jsm.exptool.core.data.repositories.responses.ListResponse;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerViewModel;
import com.jsm.exptool.model.MySensor;
import com.jsm.exptool.model.experimentconfig.AudioConfig;
import com.jsm.exptool.model.experimentconfig.CameraConfig;
import com.jsm.exptool.model.experimentconfig.RepeatableElement;
import com.jsm.exptool.model.register.ExperimentRegister;
import com.jsm.exptool.repositories.AudioRepository;
import com.jsm.exptool.repositories.ImagesRepository;
import com.jsm.exptool.repositories.SensorsRepository;

import java.util.List;

public class ExperimentViewRegistersViewModel extends BaseRecyclerViewModel<ExperimentRegister, ExperimentRegister> {
    private long experimentId;
    private RepeatableElement measurableItem;


    public ExperimentViewRegistersViewModel(Application application, long experimentId, RepeatableElement measurableItem) {
        super(application, experimentId, measurableItem);
    }

    public long getExperimentId() {
        return experimentId;
    }

    public void setExperimentId(long experimentId) {
        this.experimentId = experimentId;
    }

    public RepeatableElement getMeasurableItem() {
        return measurableItem;
    }


    @Override
    public List<ExperimentRegister> transformResponse(ListResponse<ExperimentRegister> response) {
        return response.getResultList();
    }

    @Override
    public void onItemSelected(int position, NavController navController, Context c) {

    }

    @Override
    public void setConstructorParameters(Object... args) {
        experimentId = (long)args[0];
        measurableItem = (RepeatableElement) args[1];
    }

    @Override
    public void callRepositoryForData() {
        if(measurableItem instanceof MySensor) {
            SensorsRepository.getRegistersByTypeAndExperimentIdAsExperimentRegister(((MySensor)measurableItem).getSensorType(), experimentId, apiResponseRepositoryHolder);
        }else if(measurableItem instanceof CameraConfig){
            ImagesRepository.getRegistersByExperimentIdAsExperimentRegister(experimentId, apiResponseRepositoryHolder);
        }else if(measurableItem instanceof AudioConfig){
            AudioRepository.getRegistersByExperimentIdAsExperimentRegister(experimentId, apiResponseRepositoryHolder);
        }
    }
}