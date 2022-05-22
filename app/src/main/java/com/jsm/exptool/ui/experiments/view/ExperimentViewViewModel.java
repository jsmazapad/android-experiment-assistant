package com.jsm.exptool.ui.experiments.view;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import androidx.navigation.NavController;

import com.jsm.exptool.R;
import com.jsm.exptool.core.data.repositories.responses.ListResponse;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerViewModel;
import com.jsm.exptool.model.Experiment;
import com.jsm.exptool.model.experimentconfig.ExperimentConfiguration;
import com.jsm.exptool.model.experimentconfig.RepeatableElementConfig;
import com.jsm.exptool.providers.DateProvider;
import com.jsm.exptool.providers.ExperimentProvider;
import com.jsm.exptool.providers.TimeDisplayStringProvider;

import java.util.ArrayList;
import java.util.List;

public class ExperimentViewViewModel extends BaseRecyclerViewModel<RepeatableElementConfig, RepeatableElementConfig> {

   private Experiment experiment;

   private final MutableLiveData<String> initDateString = new MutableLiveData<>();
   private final MutableLiveData<String> endDateString = new MutableLiveData<>();
   private final MutableLiveData<String> description = new MutableLiveData<>();
   private final MutableLiveData<String> title = new MutableLiveData<>();
   private final MutableLiveData<String> status = new MutableLiveData<>();
   private final MutableLiveData<String> duration = new MutableLiveData<>();
   private final MutableLiveData<Boolean> sensorEnabled = new MutableLiveData<>(false);
   private final MutableLiveData<Boolean> cameraEnabled = new MutableLiveData<>(false);
   private final MutableLiveData<Boolean> embeddingEnabled = new MutableLiveData<>(false);
   private final MutableLiveData<Boolean> audioEnabled = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> locationEnabled = new MutableLiveData<>(false);
   private final MutableLiveData<Boolean> syncEnabled = new MutableLiveData<>(false);

    public ExperimentViewViewModel(Application app, Experiment experiment) {
        super(app);
        this.experiment = experiment;
        initExperimentComponents();


    }

    public void initExperimentComponents(){
        ExperimentConfiguration experimentConfiguration = this.experiment.getConfiguration();
        ArrayList<RepeatableElementConfig> experimentElements = new ArrayList<>();
        if(experimentConfiguration.isCameraEnabled()) {
            experimentElements.add(experimentConfiguration.getCameraConfig());
            cameraEnabled.setValue(true);
            if(experimentConfiguration.isEmbeddingEnabled())
                embeddingEnabled.setValue(true);
        }
        if(experimentConfiguration.isAudioEnabled()) {
            experimentElements.add(experimentConfiguration.getAudioConfig());
            audioEnabled.setValue(true);
        }
        if(experimentConfiguration.isLocationEnabled()) {
            experimentElements.add(experimentConfiguration.getLocationConfig());
            locationEnabled.setValue(true);
        }
        if(experimentConfiguration.isSensorEnabled()) {
            experimentElements.addAll(experimentConfiguration.getSensorConfig().getSensors());
            sensorEnabled.setValue(true);
        }

        experimentElements.add(new RepeatableElementConfig(0,0,0, R.string.comments));

        description.setValue(experiment.getDescription());
        title.setValue(experiment.getTitle());
        status.setValue(String.format(getApplication().getString(R.string.experiment_view_status_format), ExperimentProvider.getTranslatableStringFromExperimentStatus(experiment.getStatus(), getApplication())));
        initDateString.setValue(DateProvider.dateToDisplayStringWithTime(experiment.getInitDate()));
        endDateString.setValue(DateProvider.dateToDisplayStringWithTime(experiment.getEndDate()));
        try{
            duration.setValue(TimeDisplayStringProvider.millisecondsToStringBestDisplay(experiment.getEndDate().getTime()-experiment.getInitDate().getTime()));
        }catch (Exception e){
            e.printStackTrace();

        }

        this.apiResponseRepositoryHolder.setValue(new ListResponse<>(experimentElements));
    }

    public MutableLiveData<String> getInitDateString() {
        return initDateString;
    }

    public MutableLiveData<String> getEndDateString() {
        return endDateString;
    }

    public MutableLiveData<String> getDescription() {
        return description;
    }

    public MutableLiveData<String> getTitle() {
        return title;
    }

    public MutableLiveData<String> getStatus() {
        return status;
    }

    public MutableLiveData<String> getDuration() {
        return duration;
    }

    public MutableLiveData<Boolean> getSensorEnabled() {
        return sensorEnabled;
    }

    public MutableLiveData<Boolean> getCameraEnabled() {
        return cameraEnabled;
    }

    public MutableLiveData<Boolean> getEmbeddingEnabled() {
        return embeddingEnabled;
    }

    public MutableLiveData<Boolean> getAudioEnabled() {
        return audioEnabled;
    }

    public MutableLiveData<Boolean> getSyncEnabled() {
        return syncEnabled;
    }

    public MutableLiveData<Boolean> getLocationEnabled() {
        return locationEnabled;
    }

    @Override
    public List<RepeatableElementConfig> transformResponse(ListResponse<RepeatableElementConfig> response) {
        return response.getResultList();
    }

    @Override
    public void onItemSelected(int position, NavController navController, Context c) {
        navController.navigate(ExperimentViewFragmentDirections.actionNavViewExperimentToNavViewExperimentMeasures(experiment.getInternalId(), elements.getValue().get(position)));
    }

    @Override
    public void setConstructorParameters(Object... args) {

    }

    @Override
    public void callRepositoryForData() {

    }
}