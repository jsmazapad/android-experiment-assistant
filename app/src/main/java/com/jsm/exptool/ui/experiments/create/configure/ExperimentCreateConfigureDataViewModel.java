package com.jsm.exptool.ui.experiments.create.configure;

import static com.jsm.exptool.config.ConfigConstants.AUDIO_CONFIG_ARG;
import static com.jsm.exptool.config.ConfigConstants.CAMERA_CONFIG_ARG;

import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.navigation.NavController;

import com.jsm.exptool.R;
import com.jsm.exptool.config.FrequencyConstants;
import com.jsm.exptool.core.data.repositories.responses.ListResponse;
import com.jsm.exptool.core.ui.base.BaseActivity;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerViewModel;
import com.jsm.exptool.core.libs.ModalMessage;
import com.jsm.exptool.entities.experimentconfig.AudioRecordingOption;
import com.jsm.exptool.entities.experimentconfig.LocationOption;
import com.jsm.exptool.entities.experimentconfig.SensorConfig;
import com.jsm.exptool.entities.experimentconfig.LocationConfig;
import com.jsm.exptool.entities.experimentconfig.SensorsGlobalConfig;
import com.jsm.exptool.providers.CameraProvider;

import com.jsm.exptool.entities.Experiment;
import com.jsm.exptool.entities.experimentconfig.AudioConfig;
import com.jsm.exptool.entities.experimentconfig.CameraConfig;
import com.jsm.exptool.entities.experimentconfig.ExperimentConfiguration;
import com.jsm.exptool.entities.experimentconfig.RepeatableElementConfig;
import com.jsm.exptool.providers.LocationProvider;
import com.jsm.exptool.providers.PreferencesProvider;
import com.jsm.exptool.providers.SelectFrequencyDialogProvider;
import com.jsm.exptool.providers.TimeDisplayStringProvider;
import com.jsm.exptool.data.repositories.ExperimentsRepository;
import com.jsm.exptool.ui.main.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class ExperimentCreateConfigureDataViewModel extends BaseRecyclerViewModel<SensorConfig, SensorConfig> implements SelectFrequencyDialogProvider.OnFrequencySelectedListener {

    public static final String CONFIGURING_AUDIO_DURATION_TAG = "CONFIGURING_AUDIO_DURATION_TAG";

    private final MutableLiveData<Boolean> cameraSettingsEnabled = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> imageEmbeddingEnabled = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> audioSettingsEnabled = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> sensorSettingsEnabled = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> remoteSyncSettingsEnabled = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> locationSettingsEnabled = new MutableLiveData<>(false);

    //Camera
    private final MutableLiveData<Integer> cameraFlashImageResource = new MutableLiveData<>();
    private final MutableLiveData<Integer> cameraPositionImageResource = new MutableLiveData<>();
    private final MutableLiveData<String> embeddingAlgName = new MutableLiveData<>();
    //AUDIO
    private final MutableLiveData<Integer> audioRecordBitRate = new MutableLiveData<>();
    private final MutableLiveData<String> audioRecordOptionTitle = new MutableLiveData<>();
    //Ubicación
    private final List<LocationOption> locationMethods = LocationProvider.getLocationOptions();
    private MutableLiveData<LocationOption> selectedLocationMethod = new MutableLiveData<>();


    private Experiment experiment;

    /*Frecuencias*/
    private final int SENSOR_MAX_FREQ = FrequencyConstants.MAX_SENSOR_INTERVAL_MILLIS;
    private final int SENSOR_MIN_FREQ = FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS;
    private final int SENSOR_DEFAULT_FREQ = PreferencesProvider.getSensorDefaultFreq();
    private final int CAMERA_MAX_FREQ = FrequencyConstants.MAX_CAMERA_INTERVAL_MILLIS;
    private final int CAMERA_MIN_FREQ = FrequencyConstants.MIN_CAMERA_INTERVAL_MILLIS;
    private final int CAMERA_DEFAULT_FREQ = PreferencesProvider.getCameraDefaultFreq();
    private final int AUDIO_MAX_FREQ = FrequencyConstants.MAX_AUDIO_INTERVAL_MILLIS;
    private final int AUDIO_MIN_FREQ = FrequencyConstants.MIN_AUDIO_INTERVAL_MILLIS;
    private final int AUDIO_DEFAULT_FREQ = PreferencesProvider.getAudioDefaultFreq();
    private final int LOCATION_MAX_FREQ = FrequencyConstants.MAX_LOCATION_INTERVAL_MILLIS;
    private final int LOCATION_MIN_FREQ = FrequencyConstants.MIN_LOCATION_INTERVAL_MILLIS;
    private final int LOCATION_DEFAULT_FREQ = PreferencesProvider.getLocationDefaultFreq();
    private final int REMOTE_SYNC_MAX_FREQ = FrequencyConstants.MAX_REMOTE_SYNC_INTERVAL_MILLIS;
    private final int REMOTE_SYNC_MIN_FREQ = FrequencyConstants.MIN_REMOTE_SYNC_INTERVAL_MILLIS;
    private final int REMOTE_SYNC_DEFAULT_FREQ = FrequencyConstants.DEFAULT_REMOTE_SYNC_FREQ;
    private MutableLiveData<String> sensorGlobalFreqValueText = new MutableLiveData<>(String.valueOf(SENSOR_DEFAULT_FREQ));
    private MutableLiveData<String> cameraFreqValueText = new MutableLiveData<>(String.valueOf(CAMERA_DEFAULT_FREQ));
    private MutableLiveData<String> audioFreqValueText = new MutableLiveData<>(String.valueOf(AUDIO_DEFAULT_FREQ));
    private MutableLiveData<String> audioDurationValueText = new MutableLiveData<>(String.valueOf(AUDIO_DEFAULT_FREQ));
    private MutableLiveData<String> remoteSyncFreqValueText = new MutableLiveData<>(String.valueOf(REMOTE_SYNC_DEFAULT_FREQ));
    private MutableLiveData<String> locationFreqValueText = new MutableLiveData<>(String.valueOf(LOCATION_DEFAULT_FREQ));


    public ExperimentCreateConfigureDataViewModel(Application app, Experiment experiment) {
        super(app, experiment);
        ExperimentConfiguration configuration = experiment.getConfiguration();

        /*Visualización de paneles*/
        this.cameraSettingsEnabled.setValue(this.experiment.getConfiguration().isCameraEnabled());
        this.audioSettingsEnabled.setValue(this.experiment.getConfiguration().isAudioEnabled());
        this.sensorSettingsEnabled.setValue(this.experiment.getConfiguration().isSensorEnabled());
        this.imageEmbeddingEnabled.setValue(this.experiment.getConfiguration().isEmbeddingEnabled());
        this.locationSettingsEnabled.setValue(this.experiment.getConfiguration().isLocationEnabled());
        this.remoteSyncSettingsEnabled.setValue(this.experiment.getConfiguration().isRemoteSyncEnabled());

        //TODO Ajustar frecuencias mínimas con respecto a las del sensor y quitarlo del dialog
        if (this.cameraSettingsEnabled.getValue()!= null && this.cameraSettingsEnabled.getValue()) {
//            configuration.getCameraConfig().setInterval(CAMERA_DEFAULT_FREQ);
            this.cameraFreqValueText.setValue(TimeDisplayStringProvider.millisecondsToStringBestDisplay(configuration.getCameraConfig().getInterval()));
            initCameraSettingsData();
        }
        if (this.audioSettingsEnabled.getValue()!= null && this.audioSettingsEnabled.getValue()) {
//            configuration.getAudioConfig().setInterval(AUDIO_DEFAULT_FREQ);
//            configuration.getAudioConfig().setRecordingDuration(AUDIO_DEFAULT_FREQ);
            this.audioFreqValueText.setValue(TimeDisplayStringProvider.millisecondsToStringBestDisplay(configuration.getAudioConfig().getInterval()));
            this.audioDurationValueText.setValue(TimeDisplayStringProvider.millisecondsToStringBestDisplay(configuration.getAudioConfig().getRecordingDuration()));
            initAudioSettingsData();
        }
        if (this.locationSettingsEnabled.getValue()!= null && this.locationSettingsEnabled.getValue()) {
            //configuration.getLocationConfig().setInterval(LOCATION_DEFAULT_FREQ);
            this.locationFreqValueText.setValue(TimeDisplayStringProvider.millisecondsToStringBestDisplay(configuration.getLocationConfig().getInterval()));
            this.selectedLocationMethod.setValue(configuration.getLocationConfig().getLocationOption());
            initCameraSettingsData();
        }
        if (this.remoteSyncSettingsEnabled.getValue()!= null && this.remoteSyncSettingsEnabled.getValue()) {
            //configuration.getRemoteSyncConfig().setInterval(REMOTE_SYNC_DEFAULT_FREQ);
            this.remoteSyncFreqValueText.setValue(TimeDisplayStringProvider.millisecondsToStringBestDisplay(configuration.getRemoteSyncConfig().getInterval()));
        }
        if (this.sensorSettingsEnabled.getValue()!= null && this.sensorSettingsEnabled.getValue()) {
            //configuration.getSensorConfig().setInterval(SENSOR_DEFAULT_FREQ);
            this.sensorGlobalFreqValueText.setValue(TimeDisplayStringProvider.millisecondsToStringBestDisplay(configuration.getSensorConfig().getInterval()));
        }

    }



    @Override
    public List<SensorConfig> transformResponse(ListResponse<SensorConfig> response) {
        return response.getResultList();
    }

    @Override
    public void onItemSelected(int position, NavController navController, Context c) {
        SelectFrequencyDialogProvider.createDialog(c,  elements.getValue().get(position), this, SENSOR_MIN_FREQ, SENSOR_MAX_FREQ, elements.getValue().get(position).getInterval(), true, null, null);
    }

    @Override
    public void setConstructorParameters(Object... args) {
        if (args.length > 0 && args[0] != null && args[0] instanceof Experiment) {
            this.experiment = (Experiment) args[0];
        }
    }

    @Override
    public void callRepositoryForData() {
        List<SensorConfig> sensors = new ArrayList<>();
        if(experiment.getConfiguration().isSensorEnabled()){
            sensors=  experiment.getConfiguration().getSensorConfig().getSensors();
        }
        apiResponseRepositoryHolder.setValue(new ListResponse<>(sensors));

    }

    public MutableLiveData<Integer> getCameraFlashImageResource() {
        return cameraFlashImageResource;
    }

    public MutableLiveData<Integer> getCameraPositionImageResource() {
        return cameraPositionImageResource;
    }

    public MutableLiveData<String> getEmbeddingAlgName() {
        return embeddingAlgName;
    }
    /*Configuración*/

    public MutableLiveData<Boolean> getCameraSettingsEnabled() {
        return cameraSettingsEnabled;
    }

    public MutableLiveData<Boolean> getAudioSettingsEnabled() {
        return audioSettingsEnabled;
    }

    public MutableLiveData<Boolean> getSensorSettingsEnabled() {
        return sensorSettingsEnabled;
    }

    public MutableLiveData<Boolean> getImageEmbeddingEnabled() {
        return imageEmbeddingEnabled;
    }

    public MutableLiveData<Boolean> getRemoteSyncSettingsEnabled() {
        return remoteSyncSettingsEnabled;
    }

    public MutableLiveData<Boolean> getLocationSettingsEnabled() {
        return locationSettingsEnabled;
    }

    public List<LocationOption> getLocationMethods() {
        return locationMethods;
    }

    public MutableLiveData<LocationOption> getSelectedLocationMethod() {
        return selectedLocationMethod;
    }

    public void setSelectedLocationMethod(MutableLiveData<LocationOption> selectedLocationMethod) {
        this.selectedLocationMethod = selectedLocationMethod;
    }

    /*Frecuencias*/

    public MutableLiveData<String> getCameraFreqValueText() {
        return cameraFreqValueText;
    }

    public void setCameraFreqValueText(MutableLiveData<String> cameraFreqValueText) {
        this.cameraFreqValueText = cameraFreqValueText;
    }

    public MutableLiveData<String> getAudioFreqValueText() {
        return audioFreqValueText;
    }

    public void setAudioFreqValueText(MutableLiveData<String> audioFreqValueText) {
        this.audioFreqValueText = audioFreqValueText;
    }

    public MutableLiveData<String> getAudioDurationValueText(){
        return audioDurationValueText;
    }

    public void setAudioDurationValueText(MutableLiveData<String> audioDurationValueText) {
        this.audioDurationValueText = audioDurationValueText;
    }

    public MutableLiveData<String> getRemoteSyncFreqValueText() {
        return remoteSyncFreqValueText;
    }

    public MutableLiveData<String> getLocationFreqValueText() {
        return locationFreqValueText;
    }

    public MutableLiveData<String> getSensorGlobalFreqValueText() {
        return sensorGlobalFreqValueText;
    }

    //AUDIO
    public MutableLiveData<Integer> getAudioRecordBitRate() {
        return audioRecordBitRate;
    }

    public MutableLiveData<String> getAudioRecordOptionTitle() {
        return audioRecordOptionTitle;
    }

    @Override
    public void onFrequencySelected(RepeatableElementConfig element, String selectedAttributeTag) {
        //Reseteamos el elemento que corresponda para que se actualice el valor en la vista
        //Databinding con LiveData no tiene manera de saber si el elemento ha cambiado sólo una propiedad
        if (element instanceof SensorConfig) {
            elements.setValue(elements.getValue());
        } else if (element instanceof CameraConfig) {
            cameraFreqValueText.setValue(TimeDisplayStringProvider.millisecondsToStringBestDisplay(this.experiment.getConfiguration().getCameraConfig().getInterval()));
        } else if (element instanceof AudioConfig) {
            if(CONFIGURING_AUDIO_DURATION_TAG.equals(selectedAttributeTag) )
            {
                audioDurationValueText.setValue(TimeDisplayStringProvider.millisecondsToStringBestDisplay(this.experiment.getConfiguration().getAudioConfig().getRecordingDuration()));
            }else{
                audioFreqValueText.setValue(TimeDisplayStringProvider.millisecondsToStringBestDisplay(this.experiment.getConfiguration().getAudioConfig().getInterval()));
            }
        }else if (element instanceof LocationConfig){
            locationFreqValueText.setValue(TimeDisplayStringProvider.millisecondsToStringBestDisplay(this.experiment.getConfiguration().getLocationConfig().getInterval()));
        }else if (element instanceof SensorsGlobalConfig){
            sensorGlobalFreqValueText.setValue(TimeDisplayStringProvider.millisecondsToStringBestDisplay(this.experiment.getConfiguration().getSensorConfig().getInterval()));
        }else if (element != null){
            //RemoteSyncConfig
            remoteSyncFreqValueText.setValue(TimeDisplayStringProvider.millisecondsToStringBestDisplay(this.experiment.getConfiguration().getRemoteSyncConfig().getInterval()));
        }

    }

    public void onCameraSelectFrequency(Context c) {
        SelectFrequencyDialogProvider.createDialog(c, this.experiment.getConfiguration().getCameraConfig(), this, CAMERA_MIN_FREQ, CAMERA_MAX_FREQ, this.experiment.getConfiguration().getCameraConfig().getInterval(),  false, null, null);
    }

    public void onAudioSelectFrequency(Context c) {
        SelectFrequencyDialogProvider.createDialog(c, this.experiment.getConfiguration().getAudioConfig(), this, AUDIO_MIN_FREQ, AUDIO_MAX_FREQ, this.experiment.getConfiguration().getAudioConfig().getInterval(), false, null, null);
    }

    public void onAudioSelectDuration(Context c) {
        SelectFrequencyDialogProvider.createDialog(c, this.experiment.getConfiguration().getAudioConfig(), this, AUDIO_MIN_FREQ, this.experiment.getConfiguration().getAudioConfig().getInterval(), this.experiment.getConfiguration().getAudioConfig().getRecordingDuration(), false, CONFIGURING_AUDIO_DURATION_TAG, String.format(c.getString(R.string.configure_duration), c.getString(R.string.audio)));
    }

    public void onLocationSelectFrequency(Context c) {
        SelectFrequencyDialogProvider.createDialog(c, this.experiment.getConfiguration().getLocationConfig(), this, LOCATION_MIN_FREQ, LOCATION_MAX_FREQ, this.experiment.getConfiguration().getLocationConfig().getInterval(), false, null, null);
    }

    public void onRemoteSyncSelectFrequency(Context c) {
        SelectFrequencyDialogProvider.createDialog(c, this.experiment.getConfiguration().getRemoteSyncConfig(), this, REMOTE_SYNC_MIN_FREQ, REMOTE_SYNC_MAX_FREQ, this.experiment.getConfiguration().getRemoteSyncConfig().getInterval(), false, null, null);
    }

    public void onSensorGlobalSelectFrequency(Context c) {
        SelectFrequencyDialogProvider.createDialog(c, this.experiment.getConfiguration().getSensorConfig(), this, SENSOR_MIN_FREQ, SENSOR_MAX_FREQ, this.experiment.getConfiguration().getSensorConfig().getInterval(), false, null, null);
    }

    public void onCameraSelectConfiguration(Context c) {
        NavController navController = ((MainActivity)c).getNavController();
        navController.navigate(ExperimentCreateConfigureDataFragmentDirections.actionNavExperimentConfigureToNavExperimentCreateCameraConfiguration(this.experiment.getConfiguration().getCameraConfig()));
    }

    public void onAudioSelectConfiguration(Context c) {
        NavController navController = ((MainActivity)c).getNavController();
        navController.navigate(ExperimentCreateConfigureDataFragmentDirections.actionNavExperimentConfigureToNavExperimentCreateAudioConfiguration(this.experiment.getConfiguration().getAudioConfig()));
    }




    public void initCameraSettingsData(){
        CameraConfig cameraConfig = this.experiment.getConfiguration().getCameraConfig();
        if (cameraConfig != null) {
            this.cameraFlashImageResource.setValue(CameraProvider.getInstance().getFlashImageResource(cameraConfig.getFlashMode()));
            this.cameraPositionImageResource.setValue(CameraProvider.getInstance().getCameraPositionImageResource(cameraConfig.getCameraPosition()));
            if(this.imageEmbeddingEnabled.getValue()){
                this.embeddingAlgName.setValue(cameraConfig.getEmbeddingAlgorithm().getDisplayName());
            }
        }
    }

    public void initAudioSettingsData(){
        AudioConfig audioConfig = this.experiment.getConfiguration().getAudioConfig();
        if (audioConfig != null) {
            AudioRecordingOption recordingOption = audioConfig.getRecordingOption();
            this.audioRecordBitRate.setValue(recordingOption != null? recordingOption.getSelectedEncodingBitRate() : null);
            this.audioRecordOptionTitle.setValue(recordingOption != null? recordingOption.getDisplayName() : "");

        }
    }

    public LocationOptionsSpinnerAdapter getLocationOptionsAdapter(Context context){
        return new LocationOptionsSpinnerAdapter(context, locationMethods, R.layout.generic_title_description_spinner_list_item);
    }

    public void finishConfiguration(Context context){
        ModalMessage.showModalMessageWithThreeButtons(context, context.getString(R.string.experiment_configuration_dialog_end_title),
                context.getString(R.string.experiment_configuration_dialog_end_text),context.getString(R.string.now_button), (DialogInterface.OnClickListener) (dialog, which) -> {
                    long experimentId = registerExperiment();
                    this.experiment.setInternalId(experimentId);
                    ((BaseActivity)context).getNavController().navigate(ExperimentCreateConfigureDataFragmentDirections.actionNavExperimentConfigureToNavPerformExperiment(this.experiment));
                },
                context.getString(R.string.later_button), (dialog, which)->{
                    long experimentId = registerExperiment();
                    this.experiment.setInternalId(experimentId);
                    ((BaseActivity)context).getNavController().navigate(ExperimentCreateConfigureDataFragmentDirections.actionNavExperimentConfigureToNavExperiments());

                },
                context.getString(R.string.default_modal_cancelButton), (dialog, which)->{}
                );

    }

    public final long registerExperiment(){

        if(this.experiment.getConfiguration().isSensorEnabled()) {
            List<SensorConfig> sensorConfigurations = elements.getValue();
            ArrayList<SensorConfig> listToReturn = new ArrayList<>();
            for (SensorConfig sensorConfig : sensorConfigurations) {
                if(sensorConfig.isDefaultConfigurationEnabled()){
                    sensorConfig.setInterval(this.experiment.getConfiguration().getSensorConfig().getInterval());
                }
                listToReturn.add((sensorConfig));
            }
            this.experiment.getConfiguration().getSensorConfig().setSensors(listToReturn);
        }

        if(this.experiment.getConfiguration().isLocationEnabled()){
            this.experiment.getConfiguration().getLocationConfig().setLocationOption(selectedLocationMethod.getValue());
        }
        this.experiment.setStatus(Experiment.ExperimentStatus.CREATED);
        return ExperimentsRepository.registerExperiment(this.experiment);
    }

    protected void initBackStackEntryObserver(Context context, LifecycleOwner owner){
        SavedStateHandle savedStateHandle = ((MainActivity) context).getNavController().getCurrentBackStackEntry().getSavedStateHandle();
        savedStateHandle.getLiveData(CAMERA_CONFIG_ARG).observe(owner, configValue -> {
            if(configValue != null) {
                this.experiment.getConfiguration().setCameraConfig((CameraConfig) configValue);
                initCameraSettingsData();
                //Eliminamos para no leerlo dos veces
                savedStateHandle.remove(CAMERA_CONFIG_ARG);
            }
        });
        savedStateHandle.getLiveData(AUDIO_CONFIG_ARG).observe(owner, configValue -> {
            if(configValue != null) {
                this.experiment.getConfiguration().setAudioConfig((AudioConfig) configValue);
                initAudioSettingsData();
                //Eliminamos para no leerlo dos veces
                savedStateHandle.remove(AUDIO_CONFIG_ARG);
            }
        });
    }

}