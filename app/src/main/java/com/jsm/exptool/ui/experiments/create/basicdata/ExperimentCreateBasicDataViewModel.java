package com.jsm.exptool.ui.experiments.create.basicdata;

import static com.jsm.exptool.config.ConfigConstants.MAX_QUICK_COMMENTS;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavController;

import com.jsm.exptool.R;
import com.jsm.exptool.core.data.repositories.responses.ListResponse;
import com.jsm.exptool.core.exceptions.BaseException;
import com.jsm.exptool.core.ui.DeleteActionListener;
import com.jsm.exptool.core.ui.base.BaseActivity;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerViewModelListener;
import com.jsm.exptool.libs.MultiSpinner;
import com.jsm.exptool.model.experimentconfig.AudioConfig;
import com.jsm.exptool.model.experimentconfig.CameraConfig;
import com.jsm.exptool.model.Experiment;
import com.jsm.exptool.model.experimentconfig.ExperimentConfiguration;
import com.jsm.exptool.model.SensorConfig;
import com.jsm.exptool.model.experimentconfig.RepeatableElement;
import com.jsm.exptool.model.experimentconfig.SensorsGlobalConfig;
import com.jsm.exptool.repositories.SensorsRepository;

import java.util.ArrayList;
import java.util.List;

public class ExperimentCreateBasicDataViewModel extends BaseRecyclerViewModelListener<SensorConfig, SensorConfig> implements MultiSpinner.MultiSpinnerListener, DeleteActionListener<SensorConfig> {

    private String title = "";
    private String description = "";
    private MutableLiveData<String> quickCommentValue = new MutableLiveData<>("");
    private MutableLiveData<List<String>> quickComments = new MutableLiveData<>(new ArrayList<>());
    private MutableLiveData<Boolean> cameraEnabled = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> remoteSyncEnabled = new MutableLiveData<>(false);
    private boolean embeddingEnabled = false;
    private boolean audioEnabled = false;
    private List<SensorConfig> sensors;
    private List<String> sensorStrings;
    private boolean [] selectedSensorPositions;


    public ExperimentCreateBasicDataViewModel(Application app){
        super(app);
        initSpinnerData();
    }

    @Override
    public List<SensorConfig> transformResponse(ListResponse<SensorConfig> response) {
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

    public MutableLiveData<String> getQuickCommentValue() {
        return quickCommentValue;
    }

    public void setQuickCommentValue(MutableLiveData<String> quickCommentValue) {
        this.quickCommentValue = quickCommentValue;
    }

    public MutableLiveData<List<String>> getQuickComments() {
        return quickComments;
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

    public List<SensorConfig> getSensors() {
        return sensors;
    }

    public void setSensors(List<SensorConfig> sensors) {
        this.sensors = sensors;
    }

    private void initSpinnerData(){
        sensors = SensorsRepository.getSensors();
        sensorStrings = new ArrayList<>();
        selectedSensorPositions = new boolean[sensors.size()];
        for (int i = 0; i< sensors.size(); i++) {
            RepeatableElement sensor = sensors.get(i);
            selectedSensorPositions[i] = false;
            String string = getApplication().getString(sensor.getNameStringResource());
            sensorStrings.add(string);
        }

    }

    public void configureSpinner(MultiSpinner spinner){
        spinner.setItems(sensorStrings, "","Seleccione sensores", this, selectedSensorPositions);

    }

    public void selectSensorsButtonClicked(MultiSpinner spinner){
        configureSpinner(spinner);
        spinner.performClick();
    }

    @Override
    public void onItemsSelected(boolean[] selected) {

        selectedSensorPositions = selected;
        List <SensorConfig> selectedSensors = new ArrayList();
        for(int i=0; i<selected.length; i++){
            if (selected[i]){
                selectedSensors.add(sensors.get(i));
            }
        }
        elements.setValue(selectedSensors);
    }

    @Override
    public void delete(SensorConfig element, Context context) {
        if(element != null) {
            List<SensorConfig> elementsValue= elements.getValue();
            elementsValue.remove(element);
            elements.setValue(elementsValue);
        }
    }

    public void addQuickComment(){

        List<String> elementsValue= quickComments.getValue();
        if(elementsValue != null && elementsValue.size()< MAX_QUICK_COMMENTS && !"".equals(quickCommentValue.getValue())) {
            elementsValue.add(quickCommentValue.getValue());
            quickComments.setValue(elementsValue);
            quickCommentValue.setValue("");
        }

    }
    public void deleteQuickComment(String element){
        if(element != null) {
            List<String> elementsValue= quickComments.getValue();
            elementsValue.remove(element);
            quickComments.setValue(elementsValue);
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
                error =context.getString(R.string.mandatory_name_field_error);
            }
            if(!validAtLeastOneOption){
                error += context.getString(R.string.at_least_one_experiment_conf_option_error);
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
            configuration.setCameraConfig(this.cameraEnabled.getValue() ? new CameraConfig() : null);}
        //Los elementos almacenados en recycler son los sensores seleccionados finalmente
        List <SensorConfig> selectedSensors= this.elements.getValue();
        if(selectedSensors != null && selectedSensors.size() > 0) {
            configuration.setSensorConfig(new SensorsGlobalConfig(this.elements.getValue()));
        }
        if(quickComments.getValue() != null && quickComments.getValue().size() > 0) {
            experiment.setQuickComments(this.quickComments.getValue());
        }
        return experiment;
    }
}