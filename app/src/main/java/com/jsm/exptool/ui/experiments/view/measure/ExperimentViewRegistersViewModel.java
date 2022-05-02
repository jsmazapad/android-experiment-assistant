package com.jsm.exptool.ui.experiments.view.measure;

import android.app.Application;
import android.content.Context;

import androidx.annotation.StringRes;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavController;

import com.jsm.exptool.R;
import com.jsm.exptool.config.SensorConfigConstants;
import com.jsm.exptool.core.data.repositories.responses.ListResponse;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerViewModel;
import com.jsm.exptool.model.SensorConfig;
import com.jsm.exptool.model.experimentconfig.AudioConfig;
import com.jsm.exptool.model.experimentconfig.CameraConfig;
import com.jsm.exptool.model.experimentconfig.MultimediaConfig;
import com.jsm.exptool.model.experimentconfig.RepeatableElementConfig;
import com.jsm.exptool.model.register.ExperimentRegister;
import com.jsm.exptool.repositories.AudioRepository;
import com.jsm.exptool.repositories.ImagesRepository;
import com.jsm.exptool.repositories.SensorsRepository;

import java.util.List;

public class ExperimentViewRegistersViewModel extends BaseRecyclerViewModel<ExperimentRegister, ExperimentRegister> {
    private long experimentId;
    private RepeatableElementConfig measurableItem;
    private final MutableLiveData<String> title = new MutableLiveData<>();
    private  @StringRes int secondTabTitle = -1;


    public ExperimentViewRegistersViewModel(Application application, long experimentId, RepeatableElementConfig measurableItem) {
        super(application, experimentId, measurableItem);
       initViewStrings();

    }

    private void initViewStrings(){
        title.setValue(getApplication().getString(measurableItem.getNameStringResource()));

        if(measurableItem instanceof SensorConfig){
            if(((SensorConfig) measurableItem).getSensorReader().getSensorType() == SensorConfigConstants.TYPE_GPS){
                secondTabTitle = R.string.map_tab_title;
            }else{
                secondTabTitle =R.string.graph_tab_title;
            }

        }else if (measurableItem instanceof MultimediaConfig){
            secondTabTitle = R.string.gallery_tab_title;
        }

    }

    public long getExperimentId() {
        return experimentId;
    }

    public void setExperimentId(long experimentId) {
        this.experimentId = experimentId;
    }

    public RepeatableElementConfig getMeasurableItem() {
        return measurableItem;
    }

    public MutableLiveData<String> getTitle() {
        return title;
    }



    public @StringRes int getSecondTabTitle() {
        return secondTabTitle;
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
        measurableItem = (RepeatableElementConfig) args[1];
    }

    @Override
    public void callRepositoryForData() {
        if(measurableItem instanceof SensorConfig) {
            SensorsRepository.getRegistersByTypeAndExperimentIdAsExperimentRegister(((SensorConfig)measurableItem).getSensorReader().getSensorType(), experimentId, apiResponseRepositoryHolder);
        }else if(measurableItem instanceof CameraConfig){
            ImagesRepository.getRegistersByExperimentIdAsExperimentRegister(experimentId, apiResponseRepositoryHolder);
        }else if(measurableItem instanceof AudioConfig){
            AudioRepository.getRegistersByExperimentIdAsExperimentRegister(experimentId, apiResponseRepositoryHolder);
        }
    }
}