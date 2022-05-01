package com.jsm.exptool.ui.experiments.create.basicdata;

import static com.jsm.exptool.config.ConfigConstants.CONFIG_SAVED_ARG;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.navigation.NavController;

import com.jsm.exptool.R;
import com.jsm.exptool.config.FrequencyConstants;
import com.jsm.exptool.core.data.repositories.responses.ListResponse;
import com.jsm.exptool.core.exceptions.BaseException;
import com.jsm.exptool.core.ui.DeleteActionListener;
import com.jsm.exptool.core.ui.base.BaseActivity;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerViewModel;
import com.jsm.exptool.libs.MultiSpinner;
import com.jsm.exptool.model.QuickCommentsCollection;
import com.jsm.exptool.model.experimentconfig.AudioConfig;
import com.jsm.exptool.model.experimentconfig.CameraConfig;
import com.jsm.exptool.model.Experiment;
import com.jsm.exptool.model.experimentconfig.ExperimentConfiguration;
import com.jsm.exptool.model.SensorConfig;
import com.jsm.exptool.model.experimentconfig.LocationConfig;
import com.jsm.exptool.model.experimentconfig.RepeatableElementConfig;
import com.jsm.exptool.model.experimentconfig.SensorsGlobalConfig;
import com.jsm.exptool.providers.AudioProvider;
import com.jsm.exptool.providers.EmbeddingAlgorithmsProvider;
import com.jsm.exptool.providers.LocationProvider;
import com.jsm.exptool.repositories.QuickCommentsCollectionsRepository;
import com.jsm.exptool.repositories.SensorsRepository;
import com.jsm.exptool.ui.main.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class ExperimentCreateBasicDataViewModel extends BaseRecyclerViewModel<SensorConfig, SensorConfig> implements MultiSpinner.MultiSpinnerListener, DeleteActionListener<SensorConfig> {

    private String title = "";
    private String description = "";
    private MutableLiveData<List<QuickCommentsCollection>> quickCommentsCollections = new MutableLiveData<>(new ArrayList<>());
    private QuickCommentsCollection quickCommentsCollectionSelected;
    private MutableLiveData<ListResponse<QuickCommentsCollection>> quickCommentsCollectionsApiResponse =  new MutableLiveData<>();
    private MutableLiveData<Boolean> cameraEnabled = new MutableLiveData<>(false);
    private boolean remoteSyncEnabled =false;
    private boolean embeddingEnabled = false;
    private boolean audioEnabled = false;
    private boolean locationEnabled = false;
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

    public MutableLiveData<List<QuickCommentsCollection>> getQuickCommentsCollections() {
        return quickCommentsCollections;
    }

    public MutableLiveData<Boolean> getCameraEnabled() {
        return cameraEnabled;
    }

    public void setCameraEnabled(MutableLiveData<Boolean> cameraEnabled) {
        this.cameraEnabled = cameraEnabled;
    }

    public boolean isRemoteSyncEnabled() {
        return remoteSyncEnabled;
    }

    public void setRemoteSyncEnabled(boolean remoteSyncEnabled) {
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

    public boolean isLocationEnabled() {
        return locationEnabled;
    }

    public void setLocationEnabled(boolean locationEnabled) {
        this.locationEnabled = locationEnabled;
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
            RepeatableElementConfig sensor = sensors.get(i);
            selectedSensorPositions[i] = false;
            String string = getApplication().getString(sensor.getNameStringResource());
            sensorStrings.add(string);
        }

        QuickCommentsCollectionsRepository.getQuickCommentsCollections(quickCommentsCollectionsApiResponse);

    }

    public void configureSpinner(MultiSpinner spinner){
        spinner.setItems(sensorStrings, "",getApplication().getString(R.string.select_sensors_prompt), this, selectedSensorPositions);

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

    public QuickCommentsCollectionsSpinnerAdapter getQuickCommentsCollectionsAdapter(Context context) {
        return new QuickCommentsCollectionsSpinnerAdapter(context, quickCommentsCollections.getValue(), R.layout.generic_title_description_spinner_list_item);
    }

    public void onQuickCommentsCollectionSelected(int position){
        if(position == 0){
            quickCommentsCollectionSelected = null;
        }else{
            quickCommentsCollectionSelected = quickCommentsCollections.getValue().get(position);
        }

    }

    public void addQuickCommentCollection(Context context){

        NavController navController = ((BaseActivity)context).getNavController();
        navController.navigate(ExperimentCreateBasicDataFragmentDirections.actionNavExperimentCreateToNavManageQuickCommentsConfiguration(null));

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

        if(this.audioEnabled){
            AudioConfig audioConfig = new AudioConfig();
            audioConfig.setRecordingOption(AudioProvider.getInstance().getAudioRecordingOptions().get(0));
            configuration.setAudioConfig(audioConfig);
        }

        CameraConfig cameraConfig = null;
        if(this.cameraEnabled.getValue() != null && this.cameraEnabled.getValue()) {
            cameraConfig = new CameraConfig();
            if (this.embeddingEnabled){
                cameraConfig.setEmbeddingAlgorithm(EmbeddingAlgorithmsProvider.getEmbeddingAlgorithms().get(0));
            }
        }
        configuration.setCameraConfig(cameraConfig);

        if(this.locationEnabled){
            LocationConfig locationConfig = new LocationConfig();
            locationConfig.setLocationOption(LocationProvider.getLocationOptions().get(0));
            configuration.setLocationConfig(locationConfig);
        }

        //Los elementos almacenados en recycler son los sensores seleccionados finalmente
        List <SensorConfig> selectedSensors= this.elements.getValue();
        if(selectedSensors != null && selectedSensors.size() > 0) {
            configuration.setSensorConfig(new SensorsGlobalConfig(this.elements.getValue()));
        }
        //TODO Añadir comentarios rápidos a configuración
        if (quickCommentsCollectionSelected != null){
            configuration.setQuickComments(quickCommentsCollectionSelected.getQuickComments());
        }

        if(this.remoteSyncEnabled){
            configuration.setRemoteSyncConfig(new RepeatableElementConfig(FrequencyConstants.DEFAULT_REMOTE_SYNC_FREQ,FrequencyConstants.MIN_REMOTE_SYNC_INTERVAL_MILLIS, FrequencyConstants. MAX_REMOTE_SYNC_INTERVAL_MILLIS, R.string.remote_sync));
        }

        experiment.setConfiguration(configuration);
        return experiment;
    }

    @Override
    public void initObservers(LifecycleOwner owner) {
        super.initObservers(owner);
        quickCommentsCollectionsApiResponse.observe(owner, response-> {
            if (response != null){
                ArrayList<QuickCommentsCollection> comments = new ArrayList<>();
                comments.add(new QuickCommentsCollection(getApplication().getString(R.string.experiment_create_no_quick_comments_option), new ArrayList<>()));
                comments.addAll(response.getResultList());
                quickCommentsCollections.setValue(comments);
            }
        });
    }

    protected void initBackStackEntryObserver(Context context, LifecycleOwner owner) {
        SavedStateHandle savedStateHandle = ((MainActivity) context).getNavController().getCurrentBackStackEntry().getSavedStateHandle();
        savedStateHandle.getLiveData(CONFIG_SAVED_ARG).observe(owner, configValue -> {
            if (configValue != null) {
                if((Boolean) configValue) {
                    QuickCommentsCollectionsRepository.getQuickCommentsCollections(quickCommentsCollectionsApiResponse);
                    quickCommentsCollectionSelected = null;
                }
                //Eliminamos para no leerlo dos veces
                savedStateHandle.remove(CONFIG_SAVED_ARG);
            }
        });
    }
}