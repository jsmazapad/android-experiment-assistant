package com.jsm.exptool.ui.configuration;

import android.app.Application;
import android.content.Context;
import android.webkit.URLUtil;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavController;

import com.jsm.exptool.R;
import com.jsm.exptool.config.FrequencyConstants;
import com.jsm.exptool.core.data.network.exceptions.InvalidSessionException;
import com.jsm.exptool.core.exceptions.BaseException;
import com.jsm.exptool.core.ui.base.BaseActivity;
import com.jsm.exptool.core.ui.base.BaseViewModel;
import com.jsm.exptool.core.libs.ModalMessage;
import com.jsm.exptool.data.repositories.RemoteSyncRepository;
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

    private final MutableLiveData<String> apiUsername = new MutableLiveData<>();
    private final MutableLiveData<String> apiPassword = new MutableLiveData<>();
    private final MutableLiveData<String> firebaseUsername = new MutableLiveData<>();
    private final MutableLiveData<String> firebasePassword = new MutableLiveData<>();
    private final MutableLiveData<String> serverUrl = new MutableLiveData<>();
    private final MutableLiveData<String> firebaseKey = new MutableLiveData<>();
    private final MutableLiveData<String> firebaseApp = new MutableLiveData<>();
    private final MutableLiveData<String> firebaseProject = new MutableLiveData<>();
    private final MutableLiveData<String> firebaseStorageBucket = new MutableLiveData<>();
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
    //Para comprobar conectividad
    private String tempApiUser;
    private String tempApiPassword;
    private String tempApiRemoteServer;
    private RemoteSyncMethodsProvider.RemoteConfigMethods tempMethod;
    private String tempFirebaseUser;
    private String tempFirebasePassword;
    private String tempFirebaseKey;
    private String tempFirebaseApp;
    private String tempFirebaseProject;
    private String tempFirebaseStorageBucket;


    public ConfigurationViewModel(@NonNull Application application) {
        super(application);
        apiUsername.setValue(PreferencesProvider.getApiUser());
        apiPassword.setValue(PreferencesProvider.getApiPassword());
        serverUrl.setValue(PreferencesProvider.getRemoteServer());
        firebaseUsername.setValue(PreferencesProvider.getFirebaseUser());
        firebasePassword.setValue(PreferencesProvider.getFirebasePassword());
        firebaseKey.setValue(PreferencesProvider.getFirebaseKey());
        firebaseApp.setValue(PreferencesProvider.getFirebaseApp());
        firebaseProject.setValue(PreferencesProvider.getFirebaseProject());
        firebaseStorageBucket.setValue(PreferencesProvider.getFirebaseStorageBucket());
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

    public MutableLiveData<String> getApiUsername() {
        return apiUsername;
    }

    public MutableLiveData<String> getApiPassword() {
        return apiPassword;
    }

    public MutableLiveData<String> getServerUrl() {
        return serverUrl;
    }

    public MutableLiveData<String> getFirebaseUsername() {
        return firebaseUsername;
    }

    public MutableLiveData<String> getFirebasePassword() {
        return firebasePassword;
    }

    public MutableLiveData<String> getFirebaseKey() {
        return firebaseKey;
    }

    public MutableLiveData<String> getFirebaseApp() {
        return firebaseApp;
    }

    public MutableLiveData<String> getFirebaseProject() {
        return firebaseProject;
    }

    public MutableLiveData<String> getFirebaseStorageBucket() {
        return firebaseStorageBucket;
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

    public MutableLiveData<RemoteSyncMethodsProvider.RemoteConfigMethods> getRemoteConfigMethod() {
        return remoteConfigMethod;
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


    public void savePreferences(Context context) {

        PreferencesProvider.setSensorDefaultFreq(sensorDefaultFrequency);
        PreferencesProvider.setAudioDefaultFreq(audioDefaultFrequency);
        PreferencesProvider.setCameraDefaultFreq(cameraDefaultFrequency);
        PreferencesProvider.setLocationDefaultFreq(locationDefaultFrequency);

        if (saveRemoteMethod(context)){
            ModalMessage.showModalMessage(context, context.getString(R.string.default_info_title), context.getString(R.string.configuration_success_save), null, null, null, null);
        }
    }


    private boolean saveRemoteMethod(Context context) {
        boolean ok = true;
        if (remoteConfigMethod.getValue() != null) {

            switch (remoteConfigMethod.getValue()) {
                case API:
                    if (apiUsername.getValue() != null && !"".equals(apiUsername.getValue()) &&
                            apiPassword.getValue() != null && !"".equals(apiPassword.getValue()) &&
                            serverUrl.getValue() != null && !"".equals(serverUrl.getValue())) {
                        if (URLUtil.isHttpUrl(serverUrl.getValue() ) ||
                        URLUtil.isHttpsUrl(serverUrl.getValue() )) {

                            PreferencesProvider.setApiUser(apiUsername.getValue());
                            PreferencesProvider.setApiPassword(apiPassword.getValue());
                            PreferencesProvider.setRemoteServer(serverUrl.getValue());
                            PreferencesProvider.setRemoteSyncMethod(remoteConfigMethod.getValue().getInternalId());
                        }else {
                            handleError(new BaseException(getApplication().getString(R.string.configuration_remote_sync_url_error), false), context);
                            ok = false;
                        }

                    } else {
                        handleError(new BaseException(getApplication().getString(R.string.configuration_remote_sync_error), false), context);
                        ok = false;
                    }
                    break;
                case FIREBASE:
                    if (firebaseKey.getValue() != null && !"".equals(firebaseKey.getValue()) &&
                            firebaseApp.getValue() != null && !"".equals(firebaseApp.getValue()) &&
                            firebaseProject.getValue() != null && !"".equals(firebaseProject.getValue()) &&
                            firebaseStorageBucket.getValue() != null && !"".equals(firebaseStorageBucket.getValue())) {

                        PreferencesProvider.setFirebaseUser(firebaseUsername.getValue());
                        PreferencesProvider.setFirebasePassword(firebasePassword.getValue());
                        PreferencesProvider.setFirebaseKey(firebaseKey.getValue());
                        PreferencesProvider.setFirebaseApp(firebaseApp.getValue());
                        PreferencesProvider.setFirebaseProject(firebaseProject.getValue());
                        PreferencesProvider.setFirebaseStorageBucket(firebaseStorageBucket.getValue());
                        PreferencesProvider.setRemoteSyncMethod(remoteConfigMethod.getValue().getInternalId());
                        RemoteSyncMethodsProvider.initStrategy(getApplication());
                    } else {
                        handleError(new BaseException(getApplication().getString(R.string.configuration_remote_sync_error), false), context);
                        ok = false;
                    }
                    break;
                case NONE:
                    PreferencesProvider.setRemoteSyncMethod(remoteConfigMethod.getValue().getInternalId());
                    break;
            }
        }
        return ok;
    }
    public void storeTempRemoteMethod() {

            tempApiUser = PreferencesProvider.getApiUser();
            tempApiPassword = PreferencesProvider.getApiPassword();
            tempApiRemoteServer = PreferencesProvider.getRemoteServer();
            tempMethod = RemoteSyncMethodsProvider.getRemoteConfigMethodFromInternalId(PreferencesProvider.getRemoteSyncMethod());
            tempFirebaseUser = PreferencesProvider.getFirebaseUser();
            tempFirebasePassword = PreferencesProvider.getFirebasePassword();
            tempFirebaseKey = PreferencesProvider.getFirebaseKey();
            tempFirebaseApp = PreferencesProvider.getFirebaseApp();
            tempFirebaseStorageBucket = PreferencesProvider.getFirebaseStorageBucket();
            tempFirebaseProject = PreferencesProvider.getFirebaseProject();
            tempMethod = RemoteSyncMethodsProvider.getRemoteConfigMethodFromInternalId(PreferencesProvider.getRemoteSyncMethod());
    }

    public void restoreRemoteMethod() {

        PreferencesProvider.setApiUser(tempApiUser);
        PreferencesProvider.setApiPassword(tempApiPassword);
        PreferencesProvider.setRemoteServer(tempApiRemoteServer);
        PreferencesProvider.setRemoteSyncMethod(tempApiRemoteServer);
        PreferencesProvider.setFirebaseUser(tempFirebaseUser);
        PreferencesProvider.setFirebasePassword(tempFirebasePassword);
        PreferencesProvider.setFirebaseKey(tempFirebaseKey);
        PreferencesProvider.setFirebaseApp(tempFirebaseApp);
        PreferencesProvider.setFirebaseProject(tempFirebaseProject);
        PreferencesProvider.setFirebaseStorageBucket(tempFirebaseStorageBucket);
        PreferencesProvider.setRemoteSyncMethod(tempMethod.getInternalId());
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

        remoteConfigMethod.observe(owner, value -> {
            String extraInfo = getApplication().getString(R.string.configuration_no_remote_configuration_text);
            if (value != null) {
                switch (value) {
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

    public void checkConnectivity(Context context) {
        storeTempRemoteMethod();
        if (saveRemoteMethod(context)) {
            RemoteSyncRepository.login(response -> {
                restoreRemoteMethod();
                if (response.getError() != null) {
                    BaseException exceptionToShow;
                    if(response.getError() instanceof InvalidSessionException){
                        exceptionToShow = new BaseException(context.getString(R.string.sync_credentials_error), false);
                    }else{
                        String errorString = context.getString(R.string.error_network_connection);
                        if(tempMethod.equals(RemoteSyncMethodsProvider.RemoteConfigMethods.FIREBASE)){
                            errorString += "\n" +response.getError().getLocalizedMessage();
                        }
                        exceptionToShow = new BaseException(errorString, false);
                    }
                    handleError(exceptionToShow, context);
                } else {
                    ModalMessage.showModalMessage(context, context.getString(R.string.configuration_remote_check_ok_title), context.getString(R.string.configuration_remote_check_ok_text), null, null, null, null);
                }
            }, false);
        }else{
            restoreRemoteMethod();
        }
    }
}
