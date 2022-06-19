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
import com.jsm.exptool.core.libs.ModalMessage;
import com.jsm.exptool.databinding.ViewLayoutFrequencySelectorBinding;
import com.jsm.exptool.providers.RemoteSyncMethodsProvider;
import com.jsm.exptool.providers.SeekbarSelectorProvider;
import com.jsm.exptool.providers.PreferencesProvider;
import com.jsm.exptool.providers.TimeDisplayStringProvider;
import com.jsm.exptool.data.repositories.CommentSuggestionsRepository;

import java.util.List;

public class ConfigurationViewModel extends BaseViewModel implements SeekbarSelectorProvider.FrequencySelectorListener {

    private final String SENSOR_TAG = "SENSOR_TAG";
    private final String AUDIO_TAG = "AUDIO_TAG";
    private final String CAMERA_TAG = "CAMERA_TAG";
    private final String LOCATION_TAG = "LOCATION_TAG";

    private final MutableLiveData<String> username = new MutableLiveData<>();
    private final MutableLiveData<String> password = new MutableLiveData<>();
    private final MutableLiveData<String> serverUrl = new MutableLiveData<>();
    private final MutableLiveData<String> firebaseKey = new MutableLiveData<>();
    private final MutableLiveData<Boolean> remoteServerEnabled = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> firebaseEnabled = new MutableLiveData<>(false);
    private final MutableLiveData<RemoteSyncMethodsProvider.RemoteConfigMethods> remoteConfigMethod = new MutableLiveData<>(RemoteSyncMethodsProvider.RemoteConfigMethods.NONE);
    private int sensorDefaultFrequency;
    private int cameraDefaultFrequency;
    private int audioDefaultFrequency;
    private int locationDefaultFrequency;
    private final List<RemoteSyncMethodsProvider.RemoteConfigMethods> remoteSyncMethods = RemoteSyncMethodsProvider.getRemoteConfigMethodsAsList();
    private final MutableLiveData<String> extraServerCardText = new MutableLiveData<>();
    private final MutableLiveData<String> extraLocationCardText = new MutableLiveData<>();
    private final MutableLiveData<String> extraCameraCardText = new MutableLiveData<>();
    private final MutableLiveData<String> extraSensorCardText = new MutableLiveData<>();
    private final MutableLiveData<String> extraAudioCardText = new MutableLiveData<>();


    public ConfigurationViewModel(@NonNull Application application) {
        super(application);
        username.setValue(PreferencesProvider.getUser());
        password.setValue(PreferencesProvider.getPassword());
        serverUrl.setValue(PreferencesProvider.getRemoteServer());
        firebaseKey.setValue(PreferencesProvider.getFirebaseKey());
        remoteConfigMethod.setValue(RemoteSyncMethodsProvider.getRemoteConfigMethodFromInternalId(PreferencesProvider.getRemoteSyncMethod()));
        sensorDefaultFrequency = PreferencesProvider.getSensorDefaultFreq();
        cameraDefaultFrequency = PreferencesProvider.getCameraDefaultFreq();
        audioDefaultFrequency = PreferencesProvider.getAudioDefaultFreq();
        locationDefaultFrequency = PreferencesProvider.getLocationDefaultFreq();
        extraSensorCardText.setValue(TimeDisplayStringProvider.millisecondsToStringBestDisplay(sensorDefaultFrequency));
        extraCameraCardText.setValue(TimeDisplayStringProvider.millisecondsToStringBestDisplay(cameraDefaultFrequency));
        extraAudioCardText.setValue(TimeDisplayStringProvider.millisecondsToStringBestDisplay(audioDefaultFrequency));
        extraLocationCardText.setValue(TimeDisplayStringProvider.millisecondsToStringBestDisplay(locationDefaultFrequency));

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

    public MutableLiveData<String> getFirebaseKey() {
        return firebaseKey;
    }

    public MutableLiveData<String> getExtraServerCardText() {
        return extraServerCardText;
    }

    public MutableLiveData<String> getExtraLocationCardText() {
        return extraLocationCardText;
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

    public MutableLiveData<Boolean> getRemoteServerEnabled() {
        return remoteServerEnabled;
    }

    public MutableLiveData<Boolean> getFirebaseEnabled() {
        return firebaseEnabled;
    }

    public void initFrequencySelectors(ViewLayoutFrequencySelectorBinding[] includedSelectorsBinding) {
        for (ViewLayoutFrequencySelectorBinding selectorBinding : includedSelectorsBinding) {
            int id = selectorBinding.getRoot().getId();
            if (id == R.id.sensorFrequencySelectorIncluded) {
                SeekbarSelectorProvider.initFrequencySelector(selectorBinding, this, FrequencyConstants.MIN_SENSOR_INTERVAL_MILLIS, FrequencyConstants.MAX_SENSOR_INTERVAL_MILLIS, sensorDefaultFrequency, SENSOR_TAG);
            } else if (id == R.id.audioFrequencySelectorIncluded) {
                SeekbarSelectorProvider.initFrequencySelector(selectorBinding, this, FrequencyConstants.MIN_AUDIO_INTERVAL_MILLIS, FrequencyConstants.MAX_AUDIO_INTERVAL_MILLIS, audioDefaultFrequency, AUDIO_TAG);
            } else if (id == R.id.cameraFrequencySelectorIncluded) {
                SeekbarSelectorProvider.initFrequencySelector(selectorBinding, this, FrequencyConstants.MIN_CAMERA_INTERVAL_MILLIS, FrequencyConstants.MAX_CAMERA_INTERVAL_MILLIS, cameraDefaultFrequency, CAMERA_TAG);
            } else if (id == R.id.locationFrequencySelectorIncluded) {
                SeekbarSelectorProvider.initFrequencySelector(selectorBinding, this, FrequencyConstants.MIN_LOCATION_INTERVAL_MILLIS, FrequencyConstants.MAX_LOCATION_INTERVAL_MILLIS, locationDefaultFrequency, LOCATION_TAG);
            }

        }

    }


    public void savePreferences() {
        PreferencesProvider.setUser(username.getValue());
        PreferencesProvider.setPassword(password.getValue());
        PreferencesProvider.setRemoteServer(serverUrl.getValue());
        PreferencesProvider.setSensorDefaultFreq(sensorDefaultFrequency);
        PreferencesProvider.setAudioDefaultFreq(audioDefaultFrequency);
        PreferencesProvider.setCameraDefaultFreq(cameraDefaultFrequency);
        PreferencesProvider.setLocationDefaultFreq(locationDefaultFrequency);
        PreferencesProvider.setFirebaseKey(firebaseKey.getValue());
        if (remoteConfigMethod.getValue() != null) {
            PreferencesProvider.setRemoteSyncMethod(remoteConfigMethod.getValue().getInternalId());
        }
    }

    @Override
    public void onSeekBarValueSelected(int value, SeekBar seekBar) {

        String tag = (String) seekBar.getTag();
        switch (tag) {
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
            case LOCATION_TAG:
                locationDefaultFrequency = value;
                extraLocationCardText.setValue(TimeDisplayStringProvider.millisecondsToStringBestDisplay(value));
                break;
        }

    }

    @Override
    public void initObservers(LifecycleOwner owner) {
        super.initObservers(owner);

        remoteConfigMethod.observe(owner, value->{
            String extraInfo = getApplication().getString(R.string.configuration_no_remote_configuration_text);
            if (value != null){
                switch (value){
                    case NONE:
                        firebaseEnabled.setValue(false);
                        remoteServerEnabled.setValue(false);

                        break;
                    case API:
                        firebaseEnabled.setValue(false);
                        remoteServerEnabled.setValue(true);
                        extraInfo = getApplication().getString(value.getTitleStringRes());
                        break;
                    case FIREBASE:
                        firebaseEnabled.setValue(true);
                        remoteServerEnabled.setValue(false);
                        extraInfo = getApplication().getString(value.getTitleStringRes());
                        break;

                }

                extraServerCardText.setValue(extraInfo);
            }
        });

    }

    public RemoteSyncMethodsSpinnerAdapter getRemoteSyncMethodsAdapter(Context context) {
        return new RemoteSyncMethodsSpinnerAdapter(context, RemoteSyncMethodsProvider.getRemoteConfigMethodsAsList(), R.layout.generic_title_description_spinner_list_item);
    }

    public void onRemoteSyncMethodSelected(int position) {

        remoteConfigMethod.setValue(remoteSyncMethods.get(position));
    }

    public void openSuggestionsConfig(Context context) {
        NavController navController = ((BaseActivity) context).getNavController();
        navController.navigate(ConfigurationFragmentDirections.actionNavConfigurationToNavSuggestionConfiguration());

    }

    public void openQuickCommentsConfig(Context context) {
        NavController navController = ((BaseActivity) context).getNavController();
        navController.navigate(ConfigurationFragmentDirections.actionNavConfigurationToNavQuickCommentsConfiguration());

    }

    public void resetSuggestionsCounter(Context context) {
        ModalMessage.showModalMessage(context, context.getString(R.string.default_warning_title),
                context.getString(R.string.reset_suggestion_counter_warning_text),
                null, (dialog, which) -> {
                    CommentSuggestionsRepository.resetSuggestionsCounter();
                    ModalMessage.showSuccessfulOperation(context, null);
                }, context.getString(R.string.default_modal_cancelButton), null);


    }
}
