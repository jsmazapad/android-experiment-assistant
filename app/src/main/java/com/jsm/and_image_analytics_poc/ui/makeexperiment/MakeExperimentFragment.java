package com.jsm.and_image_analytics_poc.ui.makeexperiment;


import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;


import android.view.LayoutInflater;

import android.view.ViewGroup;


import com.jsm.and_image_analytics_poc.R;
import com.jsm.and_image_analytics_poc.core.ui.base.BaseFragment;
import com.jsm.and_image_analytics_poc.databinding.MakeExperimentFragmentBinding;

public class MakeExperimentFragment extends BaseFragment<MakeExperimentFragmentBinding, MakeExperimentViewModel> {


    @Override
    protected MakeExperimentViewModel getViewModel() {
        return new ViewModelProvider(this).get(MakeExperimentViewModel.class);
    }

    @Override
    protected MakeExperimentFragmentBinding getDataBinding(@NonNull LayoutInflater inflater, ViewGroup container) {
        return DataBindingUtil.inflate(inflater, R.layout.make_experiment_fragment, container, false);
    }
}