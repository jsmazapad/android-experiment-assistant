package com.jsm.exptool.ui.configuration;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;

import com.jsm.exptool.R;
import com.jsm.exptool.core.ui.base.BaseFragment;
import com.jsm.exptool.core.ui.base.BaseViewModel;


public class ConfigurationFragment extends BaseFragment {


    @Override
    protected BaseViewModel createViewModel() {
        return new ViewModelProvider(this).get(ConfigurationViewModel.class);
    }

    @Override
    protected ViewDataBinding createDataBinding(@NonNull LayoutInflater inflater, ViewGroup container) {
        return DataBindingUtil.inflate(inflater, R.layout.configuration_fragment, container,false );
    }
}
