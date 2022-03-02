package com.jsm.exptool.ui.experiments.create.audioconfiguration;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.jsm.exptool.R;
import com.jsm.exptool.core.ui.base.BaseFragment;
import com.jsm.exptool.databinding.ExperimentCreateAudioConfgurationFragmentBinding;
import com.jsm.exptool.model.experimentconfig.AudioConfig;
import com.jsm.exptool.providers.AudioProvider;

public class ExperimentAudioConfigurationFragment extends BaseFragment<ExperimentCreateAudioConfgurationFragmentBinding, ExperimentAudioConfigurationViewModel> {


    @Override
    protected ExperimentAudioConfigurationViewModel createViewModel() {
        AudioConfig audioConfig = ExperimentAudioConfigurationFragmentArgs.fromBundle(getArguments()).getAudioConfig();
        //TODO Código pruebas, borrar
//        AudioConfig audioConfig = new AudioConfig();
//        audioConfig.setRecordingOption(AudioProvider.getInstance().getAudioRecordingOptions().get(2));
//        audioConfig.getRecordingOption().setSelectedEncodingBitRate(audioConfig.getRecordingOption().getEncodingBitRatesOptions().get(1));

        ExperimentAudioConfigurationViewModel viewModel = new ViewModelProvider(this, new ExperimentAudioConfigurationViewModelFactory(getActivity().getApplication(), audioConfig)).get(ExperimentAudioConfigurationViewModel.class);

        return viewModel;
    }


    @Override
    protected ExperimentCreateAudioConfgurationFragmentBinding createDataBinding(@NonNull LayoutInflater inflater, ViewGroup container) {
        ExperimentCreateAudioConfgurationFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.experiment_create_audio_confguration_fragment, container, false);
        return binding;
    }



}