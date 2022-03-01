package com.jsm.exptool.ui.experiments.create.configure;

import static com.jsm.exptool.config.ConfigConstants.AUDIO_CONFIG_ARG;
import static com.jsm.exptool.config.ConfigConstants.CAMERA_CONFIG_ARG;

import android.app.Application;
import android.content.Context;
import android.widget.SeekBar;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.navigation.NavController;

import com.jsm.exptool.R;
import com.jsm.exptool.config.FrequencyConstants;
import com.jsm.exptool.core.data.repositories.responses.ListResponse;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerViewModel;
import com.jsm.exptool.databinding.ViewLayoutFrequencySelectorBinding;
import com.jsm.exptool.libs.SeekbarSelectorHelper;
import com.jsm.exptool.model.AudioRecordingOption;
import com.jsm.exptool.providers.CameraProvider;

import com.jsm.exptool.model.Experiment;
import com.jsm.exptool.model.experimentconfig.AudioConfig;
import com.jsm.exptool.model.experimentconfig.CameraConfig;
import com.jsm.exptool.model.experimentconfig.ExperimentConfiguration;
import com.jsm.exptool.model.experimentconfig.FrequencyConfigurationVO;
import com.jsm.exptool.model.MySensor;
import com.jsm.exptool.model.experimentconfig.RepeatableElement;
import com.jsm.exptool.providers.PreferencesProvider;
import com.jsm.exptool.providers.SelectFrequencyDialogProvider;
import com.jsm.exptool.providers.TimeDisplayStringProvider;
import com.jsm.exptool.ui.main.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class ExperimentCreateConfigureDataViewModel extends BaseRecyclerViewModel<FrequencyConfigurationVO, MySensor> implements SelectFrequencyDialogProvider.OnFrequencySelectedListener, SeekbarSelectorHelper.FrequencySelectorListener {

    public static final String CONFIGURING_AUDIO_DURATION_TAG = "CONFIGURING_AUDIO_DURATION_TAG";

    private final MutableLiveData<Boolean> cameraSettingsEnabled = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> imageEmbeddingEnabled = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> audioSettingsEnabled = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> sensorSettingsEnabled = new MutableLiveData<>(false);

    //Camera
    private MutableLiveData<Integer> cameraFlashImageResource = new MutableLiveData<>();
    private MutableLiveData<Integer> cameraPositionImageResource = new MutableLiveData<>();
    private MutableLiveData<String> embeddingAlgName = new MutableLiveData<>();
    //AUDIO
    private MutableLiveData<Integer> audioRecordBitRate = new MutableLiveData<>();
    private MutableLiveData<String> audioRecordOptionTitle = new MutableLiveData<>();


    private Experiment experiment;
    //TODO Desemwrappar de FrequencyVO audio y cámara
    private final FrequencyConfigurationVO<CameraConfig> cameraConfigFrequencyVO;
    private final FrequencyConfigurationVO<AudioConfig> audioConfigFrequencyVO;
//    private final GlobalConfig globalConfig;


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
    private MutableLiveData<String> globalFreqValue = new MutableLiveData<>(String.valueOf(SENSOR_DEFAULT_FREQ));
    private MutableLiveData<String> cameraFreqValue = new MutableLiveData<>(String.valueOf(CAMERA_DEFAULT_FREQ));
    private MutableLiveData<String> audioFreqValue = new MutableLiveData<>(String.valueOf(AUDIO_DEFAULT_FREQ));
    private MutableLiveData<String> audioDurationValue = new MutableLiveData<>(String.valueOf(AUDIO_DEFAULT_FREQ));


    public ExperimentCreateConfigureDataViewModel(Application app, Experiment experiment) {
        super(app, experiment);
        ExperimentConfiguration configuration = experiment.getConfiguration();
        cameraConfigFrequencyVO = new FrequencyConfigurationVO<>(configuration.getCameraConfig(), false);
//        globalConfig = configuration.getGlobalConfig();
        audioConfigFrequencyVO = new FrequencyConfigurationVO<>(configuration.getAudioConfig(), false);

        /*Visualización de paneles*/
        this.cameraSettingsEnabled.setValue(this.experiment.getConfiguration().isCameraEnabled());
        this.audioSettingsEnabled.setValue(this.experiment.getConfiguration().isAudioEnabled());
        this.sensorSettingsEnabled.setValue(this.experiment.getSensors().size() > 0);
        this.imageEmbeddingEnabled.setValue(this.experiment.getConfiguration().isEmbeddingEnabled());

//        globalConfig.setInterval(SENSOR_DEFAULT_FREQ);

        if (this.cameraSettingsEnabled.getValue() && cameraConfigFrequencyVO != null) {
            cameraConfigFrequencyVO.getRepeatableElement().setInterval(getSliderCameraDefaultValue());
            this.cameraFreqValue.setValue(TimeDisplayStringProvider.millisecondsToStringBestDisplay(cameraConfigFrequencyVO.getRepeatableElement().getInterval()));
            initCameraSettingsData();
        }
        if (this.audioSettingsEnabled.getValue() && audioConfigFrequencyVO != null) {
            audioConfigFrequencyVO.getRepeatableElement().setInterval(getSliderAudioDefaultValue());
            audioConfigFrequencyVO.getRepeatableElement().setRecordingDuration(getSliderAudioDefaultValue());
            this.audioFreqValue.setValue(TimeDisplayStringProvider.millisecondsToStringBestDisplay(audioConfigFrequencyVO.getRepeatableElement().getInterval()));
            this.audioDurationValue.setValue(TimeDisplayStringProvider.millisecondsToStringBestDisplay(audioConfigFrequencyVO.getRepeatableElement().getRecordingDuration()));
            initAudioSettingsData();
        }



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
        FrequencyConfigurationVO<MySensor> sensorFreqVO = elements.getValue().get(position);
        SelectFrequencyDialogProvider.createDialog(c, sensorFreqVO, this, getSliderSensorMinValue(), getSliderSensorMaxValue(), sensorFreqVO.getRepeatableElement().getInterval(), true, null, null);
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

    public MutableLiveData<String> getAudioDurationValue(){
        return audioDurationValue;
    }

    public void setAudioDurationValue(MutableLiveData<String> audioDurationValue) {
        this.audioDurationValue = audioDurationValue;
    }

    public int getSliderSensorMinValue() {
        return SENSOR_MIN_FREQ;
    }

    public int getSliderSensorMaxValue() {
        return SENSOR_MAX_FREQ;
    }

    public int getSliderSensorDefaultValue() {
        return SENSOR_DEFAULT_FREQ;
    }

    public int getSliderCameraMinValue() {
        return CAMERA_MIN_FREQ;
    }

    public int getSliderCameraMaxValue() {
        return CAMERA_MAX_FREQ;
    }

    public int getSliderCameraDefaultValue() {
        return CAMERA_DEFAULT_FREQ;
    }

    public int getSliderAudioMinValue() {
        return AUDIO_MIN_FREQ;
    }

    public int getSliderAudioMaxValue() {
        return AUDIO_MAX_FREQ;
    }

    public int getSliderAudioDefaultValue() {
        return AUDIO_DEFAULT_FREQ;
    }

    //AUDIO
    public MutableLiveData<Integer> getAudioRecordBitRate() {
        return audioRecordBitRate;
    }

    public MutableLiveData<String> getAudioRecordOptionTitle() {
        return audioRecordOptionTitle;
    }

    @Override
    public void onFrequencySelected(FrequencyConfigurationVO sensorConfiguration, String selectedAttributeTag) {
        RepeatableElement element = sensorConfiguration.getRepeatableElement();
        //Reseteamos el elemento que corresponda para que se actualice el valor en la vista
        //Databinding con LiveData no tiene manera de saber si el elemento ha cambiado sólo una propiedad
        if (element instanceof MySensor) {
            elements.setValue(elements.getValue());
        } else if (element instanceof CameraConfig) {
            cameraFreqValue.setValue(TimeDisplayStringProvider.millisecondsToStringBestDisplay(cameraConfigFrequencyVO.getRepeatableElement().getInterval()));
        } else if (element instanceof AudioConfig) {
            if(CONFIGURING_AUDIO_DURATION_TAG.equals(selectedAttributeTag) )
            {
                audioDurationValue.setValue(TimeDisplayStringProvider.millisecondsToStringBestDisplay(audioConfigFrequencyVO.getRepeatableElement().getRecordingDuration()));
            }else{
                audioFreqValue.setValue(TimeDisplayStringProvider.millisecondsToStringBestDisplay(audioConfigFrequencyVO.getRepeatableElement().getInterval()));
            }
        }

    }

    public void onCameraSelectFrequency(Context c) {
        SelectFrequencyDialogProvider.createDialog(c, this.cameraConfigFrequencyVO, this, getSliderCameraMinValue(), getSliderCameraMaxValue(), this.cameraConfigFrequencyVO.getRepeatableElement().getInterval(),  false, null, null);
    }

    public void onAudioSelectFrequency(Context c) {
        SelectFrequencyDialogProvider.createDialog(c, this.audioConfigFrequencyVO, this, getSliderAudioMinValue(), getSliderAudioMaxValue(), this.audioConfigFrequencyVO.getRepeatableElement().getInterval(), false, null, null);
    }

    public void onAudioSelectDuration(Context c) {
        SelectFrequencyDialogProvider.createDialog(c, this.audioConfigFrequencyVO, this, getSliderAudioMinValue(), this.audioConfigFrequencyVO.getRepeatableElement().getInterval(), this.audioConfigFrequencyVO.getRepeatableElement().getRecordingDuration(), false, CONFIGURING_AUDIO_DURATION_TAG, String.format(c.getString(R.string.configure_duration), c.getString(R.string.audio)));
    }

    public void onCameraSelectConfiguration(Context c) {
        NavController navController = ((MainActivity)c).getNavController();
        navController.navigate(ExperimentCreateConfigureDataFragmentDirections.actionNavExperimentConfigureToNavExperimentCreateCameraConfiguration(this.cameraConfigFrequencyVO.getRepeatableElement()));
    }

    public void onAudioSelectConfiguration(Context c) {
        NavController navController = ((MainActivity)c).getNavController();
        navController.navigate(ExperimentCreateConfigureDataFragmentDirections.actionNavExperimentConfigureToNavExperimentCreateAudioConfiguration(this.audioConfigFrequencyVO.getRepeatableElement()));
    }

    public void initGlobalFrequencySelector(ViewLayoutFrequencySelectorBinding includedSelectorBinding) {
        SeekbarSelectorHelper.initFrequencySelector(includedSelectorBinding,this, getSliderSensorMinValue(), getSliderSensorMaxValue(), getSliderSensorDefaultValue());
    }

    public void initCameraSettingsData(){
        if (this.cameraSettingsEnabled.getValue() && cameraConfigFrequencyVO != null) {
            this.cameraFlashImageResource.setValue(CameraProvider.getInstance().getFlashImageResource(cameraConfigFrequencyVO.getRepeatableElement().getFlashMode()));
            this.cameraPositionImageResource.setValue(CameraProvider.getInstance().getCameraPositionImageResource(cameraConfigFrequencyVO.getRepeatableElement().getCameraPosition()));
            if(this.imageEmbeddingEnabled.getValue()){
                this.embeddingAlgName.setValue(cameraConfigFrequencyVO.getRepeatableElement().getEmbeddingAlgorithm().getDisplayName());
            }
        }
    }

    public void initAudioSettingsData(){
        AudioConfig audioConfig = this.audioConfigFrequencyVO.getRepeatableElement();
        if (this.audioSettingsEnabled.getValue() && audioConfig != null) {
            AudioRecordingOption recordingOption = audioConfig.getRecordingOption();
            this.audioRecordBitRate.setValue(recordingOption != null? recordingOption.getSelectedEncodingBitRate() : null);
            this.audioRecordOptionTitle.setValue(recordingOption != null? recordingOption.getDisplayName() : "");

        }
    }

    protected void initBackStackEntryObserver(Context context, LifecycleOwner owner){
        SavedStateHandle savedStateHandle = ((MainActivity) context).getNavController().getCurrentBackStackEntry().getSavedStateHandle();
        savedStateHandle.getLiveData(CAMERA_CONFIG_ARG).observe(owner, configValue -> {
            if(configValue != null) {
                this.cameraConfigFrequencyVO.setRepeatableElement((CameraConfig) configValue);
                initCameraSettingsData();
                //Eliminamos para no leerlo dos veces
                savedStateHandle.remove(CAMERA_CONFIG_ARG);
            }
        });
        savedStateHandle.getLiveData(AUDIO_CONFIG_ARG).observe(owner, configValue -> {
            if(configValue != null) {
                this.audioConfigFrequencyVO.setRepeatableElement((AudioConfig) configValue);
                initAudioSettingsData();
                //Eliminamos para no leerlo dos veces
                savedStateHandle.remove(AUDIO_CONFIG_ARG);
            }
        });
    }

    @Override
    public void onSeekBarValueSelected(int value, SeekBar seekbarId) {
        this.globalFreqValue.setValue(TimeDisplayStringProvider.millisecondsToStringBestDisplay(value));
    }
}