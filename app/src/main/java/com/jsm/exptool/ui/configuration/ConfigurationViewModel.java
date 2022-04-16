package com.jsm.exptool.ui.configuration;

import android.app.Application;
import android.content.Context;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavController;

import com.jsm.exptool.R;
import com.jsm.exptool.config.FrequencyConstants;
import com.jsm.exptool.core.ui.base.BaseActivity;
import com.jsm.exptool.core.ui.base.BaseViewModel;
import com.jsm.exptool.core.utils.ModalMessage;
import com.jsm.exptool.databinding.ViewLayoutFrequencySelectorBinding;
import com.jsm.exptool.generated.callback.OnClickListener;
import com.jsm.exptool.libs.SeekbarSelectorHelper;
import com.jsm.exptool.providers.PreferencesProvider;
import com.jsm.exptool.providers.TimeDisplayStringProvider;
import com.jsm.exptool.repositories.CommentSuggestionsRepository;

public class ConfigurationViewModel extends BaseViewModel implements SeekbarSelectorHelper.FrequencySelectorListener {

    private final String SENSOR_TAG = "SENSOR_TAG";
    private final String AUDIO_TAG = "AUDIO_TAG";
    private final String CAMERA_TAG = "CAMERA_TAG";

    private final MutableLiveData<String> username = new MutableLiveData<>();
    private final MutableLiveData<String> password = new MutableLiveData<>();
    private final MutableLiveData<String> serverUrl = new MutableLiveData<>();
    private final MutableLiveData<String> analyticsKey = new MutableLiveData<>();
    private int sensorDefaultFrequency;
    private int cameraDefaultFrequency;
    private int audioDefaultFrequency;
    private final MutableLiveData<String> extraServerCardText = new MutableLiveData<>();
    private final MutableLiveData<String> extraFlurryCardText = new MutableLiveData<>();
    private final MutableLiveData<String> extraCameraCardText = new MutableLiveData<>();
    private final MutableLiveData<String> extraSensorCardText = new MutableLiveData<>();
    private final MutableLiveData<String> extraAudioCardText = new MutableLiveData<>();


    public ConfigurationViewModel(@NonNull Application application) {
        super(application);
        username.setValue(PreferencesProvider.getUser());
        password.setValue(PreferencesProvider.getPassword());
        serverUrl.setValue(PreferencesProvider.getRemoteServer());
        analyticsKey.setValue(PreferencesProvider.getAnalyticsKey());
        sensorDefaultFrequency  = PreferencesProvider.getSensorDefaultFreq();
        cameraDefaultFrequency = PreferencesProvider.getCameraDefaultFreq();
        audioDefaultFrequency = PreferencesProvider.getAudioDefaultFreq();
        extraSensorCardText.setValue(TimeDisplayStringProvider.millisecondsToStringBestDisplay(sensorDefaultFrequency));
        extraCameraCardText.setValue(TimeDisplayStringProvider.millisecondsToStringBestDisplay(cameraDefaultFrequency));
        extraAudioCardText.setValue(TimeDisplayStringProvider.millisecondsToStringBestDisplay(audioDefaultFrequency));

    }

    public MutableLiveData<String> getUsername() {
        return username;
    }

    public MutableLiveData<String> getPassword() {
        return password;
    }

    public MutableLiveData<String> getServerUrl() {
        return serverUrl;
    }

    public MutableLiveData<String> getAnalyticsKey() {
        return analyticsKey;
    }

//    public int getSensorDefaultFrequency() {
//        return sensorDefaultFrequency;
//    }
//
//    public void setSensorDefaultFrequency(int sensorDefaultFrequency) {
//        this.sensorDefaultFrequency = sensorDefaultFrequency;
//    }
//
//    public int getCameraDefaultFrequency() {
//        return cameraDefaultFrequency;
//    }
//
//    public void setCameraDefaultFrequency(int cameraDefaultFrequency) {
//        this.cameraDefaultFrequency = cameraDefaultFrequency;
//    }
//
//    public int getAudioDefaultFrequency() {
//        return audioDefaultFrequency;
//    }
//
//    public void setAudioDefaultFrequency(int audioDefaultFrequency) {
//        this.audioDefaultFrequency = audioDefaultFrequency;
//    }


    public MutableLiveData<String> getExtraServerCardText() {
        return extraServerCardText;
    }

    public MutableLiveData<String> getExtraFlurryCardText() {
        return extraFlurryCardText;
    }


    public MutableLiveData<String> getExtraCameraCardText() {
        return extraCameraCardText;
    }


    public MutableLiveData<String> getExtraSensorCardText() {
        return extraSensorCardText;
    }


    public MutableLiveData<String> getExtraAudioCardText() {
        return extraAudioCardText;
    }



    public void initFrequencySelectors(ViewLayoutFrequencySelectorBinding [] includedSelectorsBinding) {
        for (ViewLayoutFrequencySelectorBinding selectorBinding:includedSelectorsBinding) {
            int id = selectorBinding.getRoot().getId();
            if (id == R.id.sensorFrequencySelectorIncluded) {
                SeekbarSelectorHelper.initFrequencySelector(selectorBinding, this, FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS, FrequencyConstants.MAX_SENSOR_INTERVAL_MILLIS, sensorDefaultFrequency, SENSOR_TAG);
            } else if (id == R.id.audioFrequencySelectorIncluded) {
                SeekbarSelectorHelper.initFrequencySelector(selectorBinding, this, FrequencyConstants.MIN_AUDIO_INTERVAL_MILLIS, FrequencyConstants.MAX_AUDIO_INTERVAL_MILLIS, audioDefaultFrequency, AUDIO_TAG);
            } else if (id == R.id.cameraFrequencySelectorIncluded) {
                SeekbarSelectorHelper.initFrequencySelector(selectorBinding, this, FrequencyConstants.MIN_CAMERA_INTERVAL_MILLIS, FrequencyConstants.MAX_CAMERA_INTERVAL_MILLIS, cameraDefaultFrequency, CAMERA_TAG);
            }

        }

    }


    public void savePreferences(){
        PreferencesProvider.setUser(username.getValue());
        PreferencesProvider.setPassword(password.getValue());
        PreferencesProvider.setRemoteServer(serverUrl.getValue());
        PreferencesProvider.setSensorDefaultFreq(sensorDefaultFrequency);
        PreferencesProvider.setAudioDefaultFreq(audioDefaultFrequency);
        PreferencesProvider.setCameraDefaultFreq(cameraDefaultFrequency);
        PreferencesProvider.setAnalyticsKey(analyticsKey.getValue());
    }

    @Override
    public void onSeekBarValueSelected(int value, SeekBar seekBar) {

        String tag = (String) seekBar.getTag();
        switch (tag){
            case SENSOR_TAG:
                sensorDefaultFrequency = value;
                extraSensorCardText.setValue(TimeDisplayStringProvider.millisecondsToStringBestDisplay(value));

                break;
            case AUDIO_TAG:
                audioDefaultFrequency = value;
                extraAudioCardText.setValue(TimeDisplayStringProvider.millisecondsToStringBestDisplay(value));
                break;
            case CAMERA_TAG:
                cameraDefaultFrequency = value;
                extraCameraCardText.setValue(TimeDisplayStringProvider.millisecondsToStringBestDisplay(value));
                break;
        }



    }

    @Override
    public void initObservers(LifecycleOwner owner) {
        super.initObservers(owner);
        analyticsKey.observe(owner, value ->{
            if (value != null && !"".equals(value)){
                extraFlurryCardText.setValue(value);
            }else{
                extraFlurryCardText.setValue("No hay configuración");
            }
        });

        serverUrl.observe(owner, value ->{
            if (value != null && !"".equals(value)) {
                extraServerCardText.setValue(value);
            }else{
                extraServerCardText.setValue("No hay configuración");
                }
        });

    }

    public void openSuggestionsConfig(Context context){
        NavController navController = ((BaseActivity) context).getNavController();
        navController.navigate(ConfigurationFragmentDirections.actionNavConfigurationToNavSuggestionConfiguration());

    }

    public void openQuickCommentsConfig(Context context){
        NavController navController = ((BaseActivity) context).getNavController();
        navController.navigate(ConfigurationFragmentDirections.actionNavConfigurationToNavQuickCommentsConfiguration());

    }

    public void resetSuggestionsCounter(Context context){
        ModalMessage.showModalMessage(context, context.getString(R.string.default_warning_title),
                context.getString(R.string.reset_suggestion_counter_warning_text),
                null, (dialog, which) -> {
                    CommentSuggestionsRepository.resetSuggestionsCounter();
                    ModalMessage.showSuccessfulOperation(context,null );
                }, context.getString(R.string.default_modal_cancelButton), null);



    }
}
