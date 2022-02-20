package com.jsm.exptool.ui.experiments.create;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;


import androidx.annotation.NonNull;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jsm.exptool.R;
import com.jsm.exptool.core.ui.base.BaseActivity;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerAdapter;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerFragment;
import com.jsm.exptool.databinding.ExperimentCreateBasicDataFragmentBinding;
import com.jsm.exptool.libs.MultiSpinner;


public class ExperimentCreateBasicDataFragment extends BaseRecyclerFragment<ExperimentCreateBasicDataFragmentBinding, ExperimentCreateBasicDataViewModel> {


    MultiSpinner spinner;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  super.onCreateView(inflater, container, savedInstanceState);
        spinner = view.findViewById(R.id.sensorSpinner);
        viewModel.configureSpinner(spinner);
        return view;
    }

    @Override
    protected ExperimentCreateBasicDataFragmentBinding getDataBinding(@NonNull LayoutInflater inflater, ViewGroup container) {
        return DataBindingUtil.inflate(inflater, R.layout.experiment_create_basic_data_fragment, container, false);

    }

    @Override
    protected BaseRecyclerAdapter createAdapter() {
        return new ExperimentCreateBasicDataAdapter(getContext(), viewModel, this, ((BaseActivity) this.getActivity()).getNavController(), getListItemResourceId(), viewModel);
    }

    @Override
    protected ExperimentCreateBasicDataViewModel getViewModel() {
        return new ViewModelProvider(this).get(ExperimentCreateBasicDataViewModel.class);

    }

    @Override
    protected int getListItemResourceId() {
        return R.layout.experiments_create_basic_data_list_item;
    }




}