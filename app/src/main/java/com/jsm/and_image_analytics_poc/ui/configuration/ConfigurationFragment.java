package com.jsm.and_image_analytics_poc.ui.configuration;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.jsm.and_image_analytics_poc.R;
import com.jsm.and_image_analytics_poc.core.ui.base.BaseFragment;
import com.jsm.and_image_analytics_poc.core.ui.base.BaseViewModel;


public class ConfigurationFragment extends BaseFragment {


    @Override
    protected BaseViewModel getViewModel() {
        return new ViewModelProvider(this).get(ConfigurationViewModel.class);
    }

    @Override
    protected ViewDataBinding getDataBinding(@NonNull LayoutInflater inflater, ViewGroup container) {
        return DataBindingUtil.inflate(inflater, R.layout.configuration_fragment, container,false );
    }
}
