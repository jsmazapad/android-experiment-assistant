package com.jsm.exptool.ui.experiments.create.basicdata;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavController;

import com.jsm.exptool.core.data.repositories.responses.ListResponse;
import com.jsm.exptool.core.exceptions.BaseException;
import com.jsm.exptool.core.ui.base.BaseActivity;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerViewModel;
import com.jsm.exptool.libs.MultiSpinner;
import com.jsm.exptool.model.AudioConfig;
import com.jsm.exptool.model.CameraConfig;
import com.jsm.exptool.model.Experiment;
import com.jsm.exptool.model.ExperimentConfiguration;
import com.jsm.exptool.model.MySensor;
import com.jsm.exptool.model.RepeatableElement;
import com.jsm.exptool.repositories.SensorsRepository;

import java.util.ArrayList;
import java.util.List;

public class ExperimentCreateBasicDataViewModel extends BaseRecyclerViewModel<MySensor, MySensor> implements MultiSpinner.MultiSpinnerListener, DeleteActionListener<MySensor> {

    private String title = "";
    private String description = "";
    private MutableLiveData<Boolean> cameraEnabled = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> remoteSyncEnabled = new MutableLiveData<>(false);
    private boolean embeddingEnabled = false;
    private boolean audioEnabled = false;
    private List<MySensor> sensors;


    public ExperimentCreateBasicDataViewModel(Application app){
        super(app);
        sensors = SensorsRepository.getSensors();
    }

    @Override
    public List<MySensor> transformResponse(ListResponse<MySensor> response) {
        return response.getResultList();
    }

    @Override
    public void onItemSelected(int position, NavController navController, Context c) {

    }

    @Override
    public void setConstructorParameters(Object... args) {

    }

    @Override
    public void callRepositoryForData() {

    }
    // TODO: Implement the ViewModel


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MutableLiveData<Boolean> getCameraEnabled() {
        return cameraEnabled;
    }

    public void setCameraEnabled(MutableLiveData<Boolean> cameraEnabled) {
        this.cameraEnabled = cameraEnabled;
    }

    public MutableLiveData<Boolean> getRemoteSyncEnabled() {
        return remoteSyncEnabled;
    }

    public void setRemoteSyncEnabled(MutableLiveData<Boolean> remoteSyncEnabled) {
        this.remoteSyncEnabled = remoteSyncEnabled;
    }

    public boolean isAudioEnabled() {
        return audioEnabled;
    }

    public void setAudioEnabled(boolean audioEnabled) {
        this.audioEnabled = audioEnabled;
    }

    public boolean isEmbeddingEnabled() {
        return embeddingEnabled;
    }

    public void setEmbeddingEnabled(boolean embeddingEnabled) {
        this.embeddingEnabled = embeddingEnabled;
    }

    public List<MySensor> getSensors() {
        return sensors;
    }

    public void setSensors(List<MySensor> sensors) {
        this.sensors = sensors;
    }

    public void configureSpinner(MultiSpinner spinner){
        List<String> list = new ArrayList<>();
        for (RepeatableElement sensor : sensors) {
            String string = getApplication().getString(sensor.getNameStringResource());
            list.add(string);
        }
        spinner.setItems(list, "","Seleccione sensores", this);

    }

    public void selectSensorsButtonClicked(MultiSpinner spinner){
        configureSpinner(spinner);
        spinner.performClick();
    }

    @Override
    public void onItemsSelected(boolean[] selected) {

        List <MySensor> selectedSensors = new ArrayList();
        for(int i=0; i<selected.length; i++){
            if (selected[i]){
                selectedSensors.add(sensors.get(i));
            }
        }
        elements.setValue(selectedSensors);
    }

    @Override
    public void delete(MySensor element) {
        if(element != null) {
            List<MySensor> elementsValue= elements.getValue();
            elementsValue.remove(element);
            elements.setValue(elementsValue);
        }
    }

    public void completeStep(Context context){
        boolean validTitle = this.title != null && !this.title.isEmpty();
        boolean validAtLeastOneOption = audioEnabled || cameraEnabled.getValue() || (elements.getValue() != null && elements.getValue().size() > 0);
        if (validTitle && validAtLeastOneOption){
            Experiment experiment = initializeExperiment();
            NavController navController = ((BaseActivity)context).getNavController();
            navController.navigate(ExperimentCreateBasicDataFragmentDirections.actionNavExperimentCreateToNavExperimentConfigure(experiment));
        }else{
            String error = "";
            if(!validTitle){
                error ="El campo nombre es obligatorio\n";
            }
            if(!validAtLeastOneOption){
                error += "Debe seleccionar al menos una función multimedia o un sensor";
            }

            handleError(new BaseException(error, false),context);
        }
    }

    private Experiment initializeExperiment() {
        Experiment experiment = new Experiment();
        ExperimentConfiguration configuration = new ExperimentConfiguration();
        experiment.setTitle(this.title);
        experiment.setDescription(this.description);
        configuration.setAudioConfig(this.audioEnabled ? new AudioConfig() : null);
        experiment.setConfiguration(configuration);
        if(this.cameraEnabled.getValue() != null) {
            configuration.setCameraConfig(this.cameraEnabled.getValue() ? new CameraConfig() : null);        }
        experiment.setSensors(this.elements.getValue());
        return experiment;
    }
}