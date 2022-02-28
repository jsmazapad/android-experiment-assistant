package com.jsm.exptool.ui.experiments.create.audioconfiguration;

import android.app.Application;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.jsm.exptool.model.experimentconfig.AudioConfig;

public class ExperimentAudioConfigurationViewModelFactory implements ViewModelProvider.Factory {

    private Application app;
    private AudioConfig audioConfig;


    public ExperimentAudioConfigurationViewModelFactory(Application app, AudioConfig audioConfig) {
        this.app = app;
        this.audioConfig = audioConfig;

    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass == ExperimentAudioConfigurationViewModel.class) {
            return (T) new ExperimentAudioConfigurationViewModel(app, audioConfig);
        }else{
            return null;
        }
    }
}
