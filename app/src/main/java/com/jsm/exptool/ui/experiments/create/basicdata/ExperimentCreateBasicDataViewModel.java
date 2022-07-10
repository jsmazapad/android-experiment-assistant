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
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerViewModel;
import com.jsm.exptool.libs.MultiSpinner;
import com.jsm.exptool.entities.QuickCommentsCollection;
import com.jsm.exptool.entities.experimentconfig.AudioConfig;
import com.jsm.exptool.entities.experimentconfig.CameraConfig;
import com.jsm.exptool.entities.Experiment;
import com.jsm.exptool.entities.experimentconfig.ExperimentConfiguration;
import com.jsm.exptool.entities.experimentconfig.SensorConfig;
import com.jsm.exptool.entities.experimentconfig.LocationConfig;
import com.jsm.exptool.entities.experimentconfig.RepeatableElementConfig;
import com.jsm.exptool.entities.experimentconfig.SensorsGlobalConfig;
import com.jsm.exptool.providers.AudioProvider;
import com.jsm.exptool.providers.EmbeddingAlgorithmsProvider;
import com.jsm.exptool.providers.LocationProvider;
import com.jsm.exptool.providers.PreferencesProvider;
import com.jsm.exptool.data.repositories.QuickCommentsCollectionsRepository;
import com.jsm.exptool.data.repositories.SensorRepository;
import com.jsm.exptool.ui.main.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class ExperimentCreateBasicDataViewModel extends BaseRecyclerViewModel<SensorConfig, SensorConfig> implements MultiSpinner.MultiSpinnerListener, DeleteActionListener<SensorConfig> {

    private String title = "";
    private String description = "";
    private final MutableLiveData<List<QuickCommentsCollection>> quickCommentsCollections = new MutableLiveData<>(new ArrayList<>());
    private QuickCommentsCollection quickCommentsCollectionSelected;
    private final MutableLiveData<ListResponse<QuickCommentsCollection>> quickCommentsCollectionsApiResponse = new MutableLiveData<>();
    private MutableLiveData<Boolean> cameraEnabled = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> remoteSyncEnabled = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> embeddingEnabled = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> audioEnabled = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> locationEnabled = new MutableLiveData<>(false);
    private List<SensorConfig> sensors;
    private List<String> sensorStrings;
    private boolean[] selectedSensorPositions;
    private final Experiment experimentTemplate;
    private final boolean initialConfigured;
    private boolean firstQuickCommentCollectionsResponseRequested;


    public ExperimentCreateBasicDataViewModel(Application app, Experiment experimentTemplate) {
        super(app);
        this.experimentTemplate = experimentTemplate;
        initialConfigured = experimentTemplate != null;
        firstQuickCommentCollectionsResponseRequested = true;
        initSpinnerData();
        initEnabledElements();
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

    public MutableLiveData<Boolean> isRemoteSyncEnabled() {
        return remoteSyncEnabled;
    }

    public void setRemoteSyncEnabled(MutableLiveData<Boolean> remoteSyncEnabled) {
        this.remoteSyncEnabled = remoteSyncEnabled;
    }

    public MutableLiveData<Boolean> isAudioEnabled() {
        return audioEnabled;
    }

    public void setAudioEnabled(MutableLiveData<Boolean> audioEnabled) {
        this.audioEnabled = audioEnabled;
    }

    public MutableLiveData<Boolean> isEmbeddingEnabled() {
        return embeddingEnabled;
    }

    public void setEmbeddingEnabled(MutableLiveData<Boolean> embeddingEnabled) {
        this.embeddingEnabled = embeddingEnabled;
    }

    public MutableLiveData<Boolean> isLocationEnabled() {
        return locationEnabled;
    }

    public void setLocationEnabled(MutableLiveData<Boolean> locationEnabled) {
        this.locationEnabled = locationEnabled;
    }

    public QuickCommentsCollection getQuickCommentsCollectionSelected() {
        return quickCommentsCollectionSelected;
    }

    public void setQuickCommentsCollectionSelected(QuickCommentsCollection quickCommentsCollectionSelected) {
        this.quickCommentsCollectionSelected = quickCommentsCollectionSelected;
    }

    public List<SensorConfig> getSensors() {
        return sensors;
    }

    public void setSensors(List<SensorConfig> sensors) {
        this.sensors = sensors;
    }

    private void initEnabledElements() {
        if (initialConfigured && experimentTemplate.getConfiguration() != null) {
            remoteSyncEnabled.setValue(experimentTemplate.getConfiguration().isRemoteSyncEnabled());
            cameraEnabled.setValue(experimentTemplate.getConfiguration().isCameraEnabled());
            embeddingEnabled.setValue(experimentTemplate.getConfiguration().isEmbeddingEnabled());
            audioEnabled.setValue(experimentTemplate.getConfiguration().isAudioEnabled());
            locationEnabled.setValue(experimentTemplate.getConfiguration().isLocationEnabled());
        }
    }

    private void initSpinnerData() {
        sensors = SensorRepository.getSensors();
        sensorStrings = new ArrayList<>();
        selectedSensorPositions = new boolean[sensors.size()];
        boolean requiresSensorInitialConfig = initialConfigured && experimentTemplate.getConfiguration() != null && experimentTemplate.getConfiguration().isSensorEnabled() && experimentTemplate.getConfiguration().getSensorConfig().getSensors() != null;
        List<SensorConfig> initialSensorsList = new ArrayList<>();
        for (int i = 0; i < sensors.size(); i++) {
            SensorConfig sensor = sensors.get(i);
            boolean selected = false;
            //Si tiene configuración inicial comprobamos que exista para colocarlo inicialmente como seleccionado
            if (requiresSensorInitialConfig) {
                for (SensorConfig sensorConfigFromExistingExperiment : experimentTemplate.getConfiguration().getSensorConfig().getSensors()) {
                    if (sensorConfigFromExistingExperiment.getNameStringRes() == sensor.getNameStringRes()) {
                        selected = true;
                        initialSensorsList.add(sensor);
                        break;
                    }

                }
                elements.setValue(initialSensorsList);
            }
            selectedSensorPositions[i] = selected;
            String string = getApplication().getString(sensor.getNameStringRes());
            sensorStrings.add(string);
        }

        QuickCommentsCollectionsRepository.getQuickCommentsCollections(quickCommentsCollectionsApiResponse);

    }

    public void configureSpinner(MultiSpinner spinner) {
        spinner.setItems(sensorStrings, "", getApplication().getString(R.string.select_sensors_prompt), this, selectedSensorPositions);

    }

    public void selectSensorsButtonClicked(MultiSpinner spinner) {
        configureSpinner(spinner);
        spinner.performClick();
    }

    @Override
    public void onItemsSelected(boolean[] selected) {

        selectedSensorPositions = selected;
        List<SensorConfig> selectedSensors = new ArrayList<>();
        for (int i = 0; i < selected.length; i++) {
            if (selected[i]) {
                selectedSensors.add(sensors.get(i));
            }
        }
        elements.setValue(selectedSensors);
    }

    @Override
    public void delete(SensorConfig element, Context context) {
        if (element != null) {
            List<SensorConfig> elementsValue = elements.getValue();

            assert elementsValue != null;
            elementsValue.remove(element);
            elements.setValue(elementsValue);
        }
    }

    public QuickCommentsCollectionsSpinnerAdapter getQuickCommentsCollectionsAdapter(Context context) {
        return new QuickCommentsCollectionsSpinnerAdapter(context, quickCommentsCollections.getValue(), R.layout.generic_title_description_spinner_list_item);
    }

    public void onQuickCommentsCollectionSelected(int position) {
        if (position == 0) {
            quickCommentsCollectionSelected = null;
        } else {
            if (quickCommentsCollections.getValue() != null) {
                quickCommentsCollectionSelected = quickCommentsCollections.getValue().get(position);
            }
        }

    }

    public void addQuickCommentCollection(Context context) {

        NavController navController = ((MainActivity) context).getNavController();
        navController.navigate(ExperimentCreateBasicDataFragmentDirections.actionNavExperimentCreateToNavManageQuickCommentsConfiguration(null));

    }


    public void completeStep(Context context) {
        boolean validTitle = this.title != null && !this.title.isEmpty();
        boolean validAtLeastOneOption = (audioEnabled.getValue() != null && audioEnabled.getValue()) || (cameraEnabled.getValue() != null && cameraEnabled.getValue()) || (elements.getValue() != null && elements.getValue().size() > 0) || (locationEnabled.getValue() != null && locationEnabled.getValue());
        if (validTitle && validAtLeastOneOption) {
            Experiment experiment = initializeExperiment();
            NavController navController = ((MainActivity) context).getNavController();
            navController.navigate(ExperimentCreateBasicDataFragmentDirections.actionNavExperimentCreateToNavExperimentConfigure(experiment));
        } else {
            String error = "";
            if (!validTitle) {
                error = context.getString(R.string.mandatory_name_field_error);
            }
            if (!validAtLeastOneOption) {
                error += context.getString(R.string.at_least_one_experiment_conf_option_error);
            }

            handleError(new BaseException(error, false), context);
        }
    }

    private Experiment initializeExperiment() {
        Experiment experiment = new Experiment();
        ExperimentConfiguration configuration = new ExperimentConfiguration();
        experiment.setTitle(this.title);
        experiment.setDescription(this.description);
        experiment.setSyncPending(false);
        experiment.setExportedPending(true);
        experiment.setEmbeddingPending(false);



        if (this.audioEnabled.getValue() != null && this.audioEnabled.getValue()) {

            AudioConfig audioConfig = new AudioConfig(PreferencesProvider.getAudioDefaultFreq(), FrequencyConstants.MIN_AUDIO_INTERVAL_MILLIS, FrequencyConstants.MAX_AUDIO_INTERVAL_MILLIS);
            audioConfig.setRecordingDuration(PreferencesProvider.getAudioDefaultFreq());
            //Si tiene configuración inicial,  tiene la funcionalidad y está seleccionada, copiamos la configuración
            if (initialConfigured && experimentTemplate.getConfiguration().isAudioEnabled()) {
                audioConfig = experimentTemplate.getConfiguration().getAudioConfig();
            }

            if (audioConfig.getRecordingOption() == null) {
                audioConfig.setRecordingOption(AudioProvider.getInstance().getAudioRecordingOptions().get(0));
            }

            configuration.setAudioConfig(audioConfig);
        }

        if (this.cameraEnabled.getValue() != null && this.cameraEnabled.getValue()) {

            CameraConfig cameraConfig = new CameraConfig(PreferencesProvider.getCameraDefaultFreq(), FrequencyConstants.MIN_CAMERA_INTERVAL_MILLIS, FrequencyConstants.MAX_CAMERA_INTERVAL_MILLIS);
            //Si tiene configuración inicial,  tiene la funcionalidad y está seleccionada, copiamos la configuración
            if (initialConfigured && experimentTemplate.getConfiguration().isCameraEnabled()) {
                cameraConfig = experimentTemplate.getConfiguration().getCameraConfig();
            }
            if (this.embeddingEnabled.getValue() != null && this.embeddingEnabled.getValue()) {

                if (cameraConfig.getEmbeddingAlgorithm() == null) {
                    cameraConfig.setEmbeddingAlgorithm(EmbeddingAlgorithmsProvider.getEmbeddingAlgorithms().get(0));
                }
            } else {
                cameraConfig.setEmbeddingAlgorithm(null);
            }
            configuration.setCameraConfig(cameraConfig);
        }


        if (this.locationEnabled.getValue() != null && this.locationEnabled.getValue()) {
            LocationConfig locationConfig = new LocationConfig(PreferencesProvider.getLocationDefaultFreq(), FrequencyConstants.MIN_LOCATION_INTERVAL_MILLIS, FrequencyConstants.MAX_LOCATION_INTERVAL_MILLIS);
            //Si tiene configuración inicial,  tiene la funcionalidad y está seleccionada, copiamos la configuración
            if (initialConfigured && experimentTemplate.getConfiguration().isLocationEnabled()) {
                locationConfig = experimentTemplate.getConfiguration().getLocationConfig();
            }
            if (locationConfig.getLocationOption() == null) {
                locationConfig.setLocationOption(LocationProvider.getLocationOptions().get(0));

            }
            configuration.setLocationConfig(locationConfig);
        }

        //Los elementos almacenados en recycler son los sensores seleccionados finalmente
        List<SensorConfig> selectedSensors = this.elements.getValue();
        if (selectedSensors != null && selectedSensors.size() > 0) {
            int defaultSensorFrequency = PreferencesProvider.getSensorDefaultFreq();
            SensorsGlobalConfig sensorsGlobalConfig = new SensorsGlobalConfig(defaultSensorFrequency, FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS, FrequencyConstants.MAX_SENSOR_INTERVAL_MILLIS);
            if (initialConfigured && experimentTemplate.getConfiguration().isSensorEnabled()) {
                sensorsGlobalConfig = experimentTemplate.getConfiguration().getSensorConfig();
            }


            for (int i = 0; i < selectedSensors.size(); i++) {
                SensorConfig sensorConfigFromViewModel = selectedSensors.get(i);
                //Modificamos frecuencia por defecto para que sea la seleccionada por el usuario (puede haber cambiado desde que se inició la aplicación)
                sensorConfigFromViewModel.setInterval(defaultSensorFrequency);
                //Si tiene configuración inicial,  tiene la funcionalidad y está seleccionada, copiamos la configuración
                if (initialConfigured && experimentTemplate.getConfiguration().isSensorEnabled()) {

                    for (SensorConfig sensorConfigFromTemplate : experimentTemplate.getConfiguration().getSensorConfig().getSensors()) {

                        if (sensorConfigFromTemplate.getNameStringRes() == sensorConfigFromViewModel.getNameStringRes()) {
                            //Aunque debe venir sin referencia a DB la reseteamos para asegurar que no hay problemas de coherencia en inserción
                            sensorConfigFromTemplate.setInternalId(0);
                            selectedSensors.set(i, sensorConfigFromTemplate);
                            break;
                        }
                    }
                }
            }
            sensorsGlobalConfig.setSensors(selectedSensors);
            configuration.setSensorConfig(sensorsGlobalConfig);
        }

        if (quickCommentsCollectionSelected != null) {
            configuration.setQuickComments(quickCommentsCollectionSelected.getQuickComments());
            configuration.setQuickCommentsCollectionUsedId(quickCommentsCollectionSelected.getInternalId());
        }

        if (this.remoteSyncEnabled.getValue() != null && this.remoteSyncEnabled.getValue()) {
            RepeatableElementConfig remoteConfig = new RepeatableElementConfig(PreferencesProvider.getRemoteSyncDefaultFreq(), FrequencyConstants.MIN_REMOTE_SYNC_INTERVAL_MILLIS, FrequencyConstants.MAX_REMOTE_SYNC_INTERVAL_MILLIS, R.string.remote_sync);
            //Si tiene configuración inicial,  tiene la funcionalidad y está seleccionada, copiamos la configuración
            if (initialConfigured && experimentTemplate.getConfiguration().isRemoteSyncEnabled()) {
                remoteConfig = experimentTemplate.getConfiguration().getRemoteSyncConfig();
                remoteConfig.setNameStringRes(R.string.remote_sync);
            }
            configuration.setRemoteSyncConfig(remoteConfig);
        }

        experiment.setConfiguration(configuration);
        return experiment;
    }

    @Override
    public void initObservers(LifecycleOwner owner) {
        super.initObservers(owner);
        quickCommentsCollectionsApiResponse.observe(owner, response -> {
            if (response != null) {
                ArrayList<QuickCommentsCollection> comments = new ArrayList<>();
                comments.add(new QuickCommentsCollection(getApplication().getString(R.string.experiment_create_no_quick_comments_option), new ArrayList<>()));
                comments.addAll(response.getResultList());
                //Si es la primera llamada y está configurado inicialmente
                if (firstQuickCommentCollectionsResponseRequested && initialConfigured) {
                    firstQuickCommentCollectionsResponseRequested = false;
                    //Comprobamos si el id empleado está en la colección
                    long id = experimentTemplate.getConfiguration().getQuickCommentsCollectionUsedId();
                    if (id > 0) {
                        for (QuickCommentsCollection collection : comments) {
                            if (collection.getInternalId() == id) {
                                quickCommentsCollectionSelected = collection;
                                break;
                            }

                        }
                    }
                }
                quickCommentsCollections.setValue(comments);
            }
        });
    }

    protected void initBackStackEntryObserver(Context context, LifecycleOwner owner) {
        SavedStateHandle savedStateHandle = ((MainActivity) context).getNavController().getCurrentBackStackEntry().getSavedStateHandle();
        savedStateHandle.getLiveData(CONFIG_SAVED_ARG).observe(owner, configValue -> {
            if (configValue != null) {
                if ((Boolean) configValue) {
                    QuickCommentsCollectionsRepository.getQuickCommentsCollections(quickCommentsCollectionsApiResponse);
                    quickCommentsCollectionSelected = null;
                }
                //Eliminamos para no leerlo dos veces
                savedStateHandle.remove(CONFIG_SAVED_ARG);
            }
        });
    }
}