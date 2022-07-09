package com.jsm.exptool.ui.configuration;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.jsm.exptool.R;
import com.jsm.exptool.core.ui.base.BaseFragment;
import com.jsm.exptool.databinding.ConfigurationFragmentBinding;
import com.jsm.exptool.databinding.ViewLayoutFrequencySelectorBinding;


public class ConfigurationFragment extends BaseFragment<ConfigurationFragmentBinding, ConfigurationViewModel> {


    @Override
    protected ConfigurationViewModel createViewModel() {
        return new ViewModelProvider(this).get(ConfigurationViewModel.class);
    }

    @Override
    protected ConfigurationFragmentBinding createDataBinding(@NonNull LayoutInflater inflater, ViewGroup container) {
        return DataBindingUtil.inflate(inflater, R.layout.configuration_fragment, container,false );
    }

    @Override
    public void executeExtraActionsInsideBindingInit() {
        super.executeExtraActionsInsideBindingInit();
        viewModel.initFrequencySelectors(new ViewLayoutFrequencySelectorBinding[]{binding.audioFrequencySelectorIncluded, binding.cameraFrequencySelectorIncluded, binding.sensorFrequencySelectorIncluded, binding.locationFrequencySelectorIncluded});
    }
}
