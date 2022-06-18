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
import com.jsm.exptool.entities.experimentconfig.SensorConfig;
import com.jsm.exptool.entities.experimentconfig.AudioConfig;
import com.jsm.exptool.entities.experimentconfig.CameraConfig;
import com.jsm.exptool.entities.experimentconfig.LocationConfig;
import com.jsm.exptool.entities.experimentconfig.MultimediaConfig;
import com.jsm.exptool.entities.experimentconfig.RepeatableElementConfig;
import com.jsm.exptool.entities.register.ExperimentRegister;
import com.jsm.exptool.data.repositories.AudioRepository;
import com.jsm.exptool.data.repositories.CommentRepository;
import com.jsm.exptool.data.repositories.ImageRepository;
import com.jsm.exptool.data.repositories.SensorRepository;

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
                secondTabTitle =R.string.graph_tab_title;
        }else if(measurableItem instanceof MultimediaConfig){
            secondTabTitle = R.string.gallery_tab_title;
        }else if (measurableItem instanceof LocationConfig){
            secondTabTitle = R.string.map_tab_title;
        }else{
            //Comentarios
            secondTabTitle = R.string.graph_tab_title;
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
            SensorRepository.getRegistersByTypeAndExperimentIdAsExperimentRegister(((SensorConfig)measurableItem).getSensorReader().getSensorType(), experimentId, apiResponseRepositoryHolder);
        }else if(measurableItem instanceof CameraConfig){
            ImageRepository.getRegistersByExperimentIdAsExperimentRegister(experimentId, apiResponseRepositoryHolder);
        }else if(measurableItem instanceof AudioConfig){
            AudioRepository.getRegistersByExperimentIdAsExperimentRegister(experimentId, apiResponseRepositoryHolder);
        }else if(measurableItem instanceof LocationConfig){
            SensorRepository.getRegistersByTypeAndExperimentIdAsExperimentRegister(SensorConfigConstants.TYPE_GPS, experimentId, apiResponseRepositoryHolder);
        }else{
            //Comentarios
            CommentRepository.getRegistersByExperimentIdAsExperimentRegister(experimentId, apiResponseRepositoryHolder);
        }
    }
}