package com.jsm.exptool.ui.makeexperiment;


import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;


import android.view.LayoutInflater;

import android.view.ViewGroup;


import com.jsm.exptool.R;
import com.jsm.exptool.core.ui.base.BaseFragment;
import com.jsm.exptool.databinding.MakeExperimentFragmentBinding;

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