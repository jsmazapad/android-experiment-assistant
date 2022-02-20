package com.jsm.exptool.ui.experiments.create.configure;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavController;

import com.jsm.exptool.config.SensorConfigConstants;
import com.jsm.exptool.core.data.repositories.responses.ListResponse;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerViewModel;
import com.jsm.exptool.libs.MultiSpinner;
import com.jsm.exptool.model.Experiment;
import com.jsm.exptool.model.MySensor;
import com.jsm.exptool.providers.PreferencesProvider;
import com.jsm.exptool.repositories.SensorsRepository;
import com.jsm.exptool.ui.experiments.create.basicdata.DeleteActionListener;

import java.util.ArrayList;
import java.util.List;

public class ExperimentCreateConfigureDataViewModel extends BaseRecyclerViewModel<MySensor, MySensor> implements  SelectFreqForSensorActionListener<MySensor> {



    private MutableLiveData<Boolean> cameraGlobalFreqEnabled = new MutableLiveData<>(true);
    private MutableLiveData<Boolean> audioGlobalFreqEnabled = new MutableLiveData<>(true);

    private MutableLiveData<Boolean> cameraSettingsEnabled = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> audioSettingsEnabled = new MutableLiveData<>(false);
    private MutableLiveData<Boolean> sensorSettingsEnabled = new MutableLiveData<>(false);

    private Experiment experiment;
    private final int MAX_FREQ= SensorConfigConstants.MAX_INTERVAL_MILLIS;
    private final int MIN_FREQ= SensorConfigConstants.MIN_INTERVAL_MILLIS;
    private final int DEFAULT_FREQ= PreferencesProvider.getDefaultFreq();

    private MutableLiveData<Float> globalFreqValue = new MutableLiveData<>((float) DEFAULT_FREQ);
    private MutableLiveData<Float> cameraFreqValue = new MutableLiveData<>((float) DEFAULT_FREQ);
    private MutableLiveData<Float> audioFreqValue = new MutableLiveData<>((float) DEFAULT_FREQ);


    public ExperimentCreateConfigureDataViewModel(Application app, Experiment experiment){
        super(app);
        this.experiment = experiment;

        this.cameraSettingsEnabled.setValue(this.experiment.getConfiguration().isCameraEnabled());
        this.audioSettingsEnabled.setValue(this.experiment.getConfiguration().isAudioEnabled());
        this.sensorSettingsEnabled.setValue(this.experiment.getSensors().size()>0);
        this.elements.setValue(this.experiment.getSensors());
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

    public MutableLiveData<Boolean> getCameraSettingsEnabled() {
        return cameraSettingsEnabled;
    }

    public MutableLiveData<Boolean> getAudioSettingsEnabled() {
        return audioSettingsEnabled;
    }

    public MutableLiveData<Boolean> getSensorSettingsEnabled() {
        return sensorSettingsEnabled;
    }

    public MutableLiveData<Float> getGlobalFreqValue() {
        return globalFreqValue;
    }

    public void setGlobalFreqValue(MutableLiveData<Float> globalFreqValue) {
        this.globalFreqValue = globalFreqValue;
    }

    public MutableLiveData<Float> getCameraFreqValue() {
        return cameraFreqValue;
    }

    public void setCameraFreqValue(MutableLiveData<Float> cameraFreqValue) {
        this.cameraFreqValue = cameraFreqValue;
    }

    public MutableLiveData<Float> getAudioFreqValue() {
        return audioFreqValue;
    }

    public void setAudioFreqValue(MutableLiveData<Float> audioFreqValue) {
        this.audioFreqValue = audioFreqValue;
    }

    @Override
    public void setFreqForSensor(MySensor element, int freq) {
        int index = elements.getValue() != null ? elements.getValue().indexOf(element) : -1;
        if (index >= 0){
            elements.getValue().get(index).setInterval(freq);
        }
    }

    @Override
    public void setDefaultFreqForSensor(MySensor element, boolean isDefault, int freq) {
        int index = elements.getValue() != null ? elements.getValue().indexOf(element) : -1;
        if (index >= 0){
            elements.getValue().get(index).setInterval((int)(isDefault?  globalFreqValue.getValue() : freq));
        }
    }

    @Override
    public int getSliderMinValue() {
        return MIN_FREQ;
    }

    @Override
    public int getSliderMaxValue() {
        return MAX_FREQ;
    }

    @Override
    public int getSliderDefaultValue() {
        return DEFAULT_FREQ;
    }



}