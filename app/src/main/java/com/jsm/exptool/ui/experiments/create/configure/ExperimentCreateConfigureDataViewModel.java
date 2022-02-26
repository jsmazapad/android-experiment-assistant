package com.jsm.exptool.ui.experiments.create.configure;

import static com.jsm.exptool.config.ConfigConstants.CAMERA_CONFIG_ARG;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.navigation.NavController;

import com.jsm.exptool.R;
import com.jsm.exptool.config.SensorConfigConstants;
import com.jsm.exptool.core.data.repositories.responses.ListResponse;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerViewModel;
import com.jsm.exptool.databinding.ViewLayoutFrequencySelectorBinding;
import com.jsm.exptool.libs.camera.CameraProvider;

import com.jsm.exptool.model.Experiment;
import com.jsm.exptool.model.experimentconfig.AudioConfig;
import com.jsm.exptool.model.experimentconfig.CameraConfig;
import com.jsm.exptool.model.experimentconfig.ExperimentConfiguration;
import com.jsm.exptool.model.experimentconfig.FrequencyConfigurationVO;
import com.jsm.exptool.model.experimentconfig.GlobalConfig;
import com.jsm.exptool.model.MySensor;
import com.jsm.exptool.model.experimentconfig.RepeatableElement;
import com.jsm.exptool.providers.PreferencesProvider;
import com.jsm.exptool.providers.SelectFrequencyDialogProvider;
import com.jsm.exptool.providers.TimeDisplayStringProvider;
import com.jsm.exptool.ui.main.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class ExperimentCreateConfigureDataViewModel extends BaseRecyclerViewModel<FrequencyConfigurationVO, MySensor> implements SelectFrequencyDialogProvider.OnFrequencySelectedListener {


    private MutableLiveData<Boolean> cameraGlobalFreqEnabled = new MutableLiveData<>(true);
    private MutableLiveData<Boolean> audioGlobalFreqEnabled = new MutableLiveData<>(true);

    private final MutableLiveData<Boolean> cameraSettingsEnabled = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> imageEmbeddingEnabled = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> audioSettingsEnabled = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> sensorSettingsEnabled = new MutableLiveData<>(false);

    private MutableLiveData<Integer> cameraFlashImageResource = new MutableLiveData<>();
    private MutableLiveData<Integer> cameraPositionImageResource = new MutableLiveData<>();
    private MutableLiveData<String> embeddingAlgName = new MutableLiveData<>();


    private Experiment experiment;
    private final FrequencyConfigurationVO<CameraConfig> cameraConfig;
    private final FrequencyConfigurationVO<AudioConfig> audioConfig;
    private final GlobalConfig globalConfig;


    /*Frecuencias*/
    private final int MAX_FREQ = SensorConfigConstants.MAX_INTERVAL_MILLIS;
    private final int MIN_FREQ = SensorConfigConstants.MIN_INTERVAL_MILLIS;
    private final int DEFAULT_FREQ = PreferencesProvider.getDefaultFreq();
    private MutableLiveData<String> globalFreqValue = new MutableLiveData<>(String.valueOf(DEFAULT_FREQ));
    private MutableLiveData<String> cameraFreqValue = new MutableLiveData<>(String.valueOf(DEFAULT_FREQ));
    private MutableLiveData<String> audioFreqValue = new MutableLiveData<>(String.valueOf(DEFAULT_FREQ));


    public ExperimentCreateConfigureDataViewModel(Application app, Experiment experiment) {
        super(app, experiment);
        ExperimentConfiguration configuration = experiment.getConfiguration();
        cameraConfig = new FrequencyConfigurationVO<>(configuration.getCameraConfig());
        globalConfig = configuration.getGlobalConfig();
        audioConfig = new FrequencyConfigurationVO<>(configuration.getAudioConfig());

        /*Visualización de paneles*/
        this.cameraSettingsEnabled.setValue(this.experiment.getConfiguration().isCameraEnabled());
        this.audioSettingsEnabled.setValue(this.experiment.getConfiguration().isAudioEnabled());
        this.sensorSettingsEnabled.setValue(this.experiment.getSensors().size() > 0);
        this.imageEmbeddingEnabled.setValue(this.experiment.getConfiguration().isEmbeddingEnabled());

        globalConfig.setInterval(DEFAULT_FREQ);

        if (this.cameraSettingsEnabled.getValue() && cameraConfig != null) {
            cameraConfig.getRepeatableElement().setInterval(globalConfig.getInterval());
            this.cameraFreqValue.setValue(cameraConfig.isDefaultConfigurationEnabled() ? getApplication().getString(R.string.global_frequency_literal) : TimeDisplayStringProvider.millisecondsToStringBestDisplay(cameraConfig.getRepeatableElement().getInterval()));
        }
        if (this.audioSettingsEnabled.getValue() && audioConfig != null) {
            audioConfig.getRepeatableElement().setInterval(globalConfig.getInterval());
            this.audioFreqValue.setValue(audioConfig.isDefaultConfigurationEnabled() ? getApplication().getString(R.string.global_frequency_literal) : TimeDisplayStringProvider.millisecondsToStringBestDisplay(audioConfig.getRepeatableElement().getInterval()));

        }

        initCameraSettingsData();

    }

    @Override
    public List<FrequencyConfigurationVO> transformResponse(ListResponse<MySensor> response) {
        ArrayList<FrequencyConfigurationVO> listToReturn = new ArrayList<>();
        for (RepeatableElement sensor : response.getResultList()) {
            listToReturn.add(new FrequencyConfigurationVO(sensor));
        }
        return listToReturn;
    }

    @Override
    public void onItemSelected(int position, NavController navController, Context c) {
        SelectFrequencyDialogProvider.createDialog(c, elements.getValue().get(position), this, getSliderMinValue(), getSliderMaxValue());
    }

    public void onCameraSelectFrequency(Context c) {
        SelectFrequencyDialogProvider.createDialog(c, this.cameraConfig, this, getSliderMinValue(), getSliderMaxValue());
    }

    public void onAudioSelectFrequency(Context c) {
        SelectFrequencyDialogProvider.createDialog(c, this.audioConfig, this, getSliderMinValue(), getSliderMaxValue());
    }


    public void onCameraSelectConfiguration(Context c) {
        NavController navController = ((MainActivity)c).getNavController();
        navController.navigate(ExperimentCreateConfigureDataFragmentDirections.actionNavExperimentConfigureToNavExperimentCreateCameraConfiguration(this.cameraConfig.getRepeatableElement()));
    }


    @Override
    public void setConstructorParameters(Object... args) {
        if (args.length > 0 && args[0] != null && args[0] instanceof Experiment) {
            this.experiment = (Experiment) args[0];
        }
    }

    @Override
    public void callRepositoryForData() {
        apiResponseRepositoryHolder.setValue(new ListResponse<>(experiment.getSensors()));

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

    public MutableLiveData<String> getGlobalFreqValue() {
        return globalFreqValue;
    }

    public void setGlobalFreqValue(MutableLiveData<String> globalFreqValue) {
        this.globalFreqValue = globalFreqValue;
    }

    /*Frecuencias*/
    public MutableLiveData<Boolean> getCameraGlobalFreqEnabled() {
        return cameraGlobalFreqEnabled;
    }

    public void setCameraGlobalFreqEnabled(MutableLiveData<Boolean> cameraGlobalFreqEnabled) {
        this.cameraGlobalFreqEnabled = cameraGlobalFreqEnabled;
    }

    public MutableLiveData<Boolean> getAudioGlobalFreqEnabled() {
        return audioGlobalFreqEnabled;
    }

    public void setAudioGlobalFreqEnabled(MutableLiveData<Boolean> audioGlobalFreqEnabled) {
        this.audioGlobalFreqEnabled = audioGlobalFreqEnabled;
    }

    public MutableLiveData<String> getCameraFreqValue() {
        return cameraFreqValue;
    }

    public void setCameraFreqValue(MutableLiveData<String> cameraFreqValue) {
        this.cameraFreqValue = cameraFreqValue;
    }

    public MutableLiveData<String> getAudioFreqValue() {
        return audioFreqValue;
    }

    public void setAudioFreqValue(MutableLiveData<String> audioFreqValue) {
        this.audioFreqValue = audioFreqValue;
    }

    public int getSliderMinValue() {
        return MIN_FREQ;
    }

    public int getSliderMaxValue() {
        return MAX_FREQ;
    }

    public int getSliderDefaultValue() {
        return DEFAULT_FREQ;
    }


    @Override
    public void onFrequencySelected(FrequencyConfigurationVO sensorConfiguration) {
        RepeatableElement element = sensorConfiguration.getRepeatableElement();
        //Reseteamos el elemento que corresponda para que se actualice el valor en la vista
        //Databinding con LiveData no tiene manera de saber si el elemento ha cambiado sólo una propiedad
        if (element instanceof MySensor) {
            elements.setValue(elements.getValue());
        } else if (element instanceof CameraConfig) {
            cameraFreqValue.setValue(sensorConfiguration.isDefaultConfigurationEnabled() ? getApplication().getString(R.string.global_frequency_literal) : TimeDisplayStringProvider.millisecondsToStringBestDisplay(cameraConfig.getRepeatableElement().getInterval()));
        } else if (element instanceof AudioConfig) {
            audioFreqValue.setValue(sensorConfiguration.isDefaultConfigurationEnabled() ? getApplication().getString(R.string.global_frequency_literal) : TimeDisplayStringProvider.millisecondsToStringBestDisplay(audioConfig.getRepeatableElement().getInterval()));
        }

    }

    public void initGlobalFrequencySelector(ViewLayoutFrequencySelectorBinding includedSelectorBinding) {
        FrequencySelectorHelper.initFrequencySelector(includedSelectorBinding, new FrequencyConfigurationVO<>(globalConfig), this, getSliderMinValue(), getSliderMaxValue());
    }

    public void initCameraSettingsData(){
        if (this.cameraSettingsEnabled.getValue() && cameraConfig != null) {
            this.cameraFlashImageResource.setValue(CameraProvider.getInstance().getFlashImageResource(cameraConfig.getRepeatableElement().getFlashMode()));
            this.cameraPositionImageResource.setValue(CameraProvider.getInstance().getCameraPositionImageResource(cameraConfig.getRepeatableElement().getCameraPosition()));
            if(this.imageEmbeddingEnabled.getValue()){
                this.embeddingAlgName.setValue(cameraConfig.getRepeatableElement().getEmbeddingAlgorithm().getDisplayName());
            }
        }
    }

    protected void initBackStackEntryObserver(Context context, LifecycleOwner owner){
        SavedStateHandle savedStateHandle = ((MainActivity) context).getNavController().getCurrentBackStackEntry().getSavedStateHandle();
        savedStateHandle.getLiveData(CAMERA_CONFIG_ARG).observe(owner, configValue -> {
            if(configValue != null) {
                this.cameraConfig.setRepeatableElement((CameraConfig) configValue);
                initCameraSettingsData();
                //Eliminamos para no leerlo dos veces
                savedStateHandle.remove(CAMERA_CONFIG_ARG);
            }
        });
    }
}