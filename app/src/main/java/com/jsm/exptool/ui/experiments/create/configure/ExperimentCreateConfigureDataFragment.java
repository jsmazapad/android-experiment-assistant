package com.jsm.exptool.ui.experiments.create.configure;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.jsm.exptool.R;
import com.jsm.exptool.core.ui.base.BaseActivity;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerAdapter;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerFragment;
import com.jsm.exptool.databinding.ExperimentCreateConfigureDataFragmentBinding;
import com.jsm.exptool.libs.MultiSpinner;
import com.jsm.exptool.model.Experiment;


public class ExperimentCreateConfigureDataFragment extends BaseRecyclerFragment<ExperimentCreateConfigureDataFragmentBinding, ExperimentCreateConfigureDataViewModel> {


    MultiSpinner spinner;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    protected ExperimentCreateConfigureDataFragmentBinding getDataBinding(@NonNull LayoutInflater inflater, ViewGroup container) {
        return DataBindingUtil.inflate(inflater, R.layout.experiment_create_configure_data_fragment, container, false);

    }

    @Override
    protected BaseRecyclerAdapter createAdapter() {
        return new ExperimentCreateConfigureDataAdapter(getContext(), viewModel, this, ((BaseActivity) this.getActivity()).getNavController(), getListItemResourceId(), viewModel);
    }

    @Override
    protected ExperimentCreateConfigureDataViewModel getViewModel() {

        Experiment experiment = ExperimentCreateConfigureDataFragmentArgs.fromBundle(getArguments()).getExperiment();
        return new ViewModelProvider(this, new ExperimentCreateConfigureDataViewModelFactory(getActivity().getApplication(), experiment)).get(ExperimentCreateConfigureDataViewModel.class);

    }

    @Override
    protected int getListItemResourceId() {
        return R.layout.experiment_create_configure_data_list_item;
    }




}