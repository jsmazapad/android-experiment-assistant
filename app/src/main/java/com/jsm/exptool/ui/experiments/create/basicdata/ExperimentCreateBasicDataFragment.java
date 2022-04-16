package com.jsm.exptool.ui.experiments.create.basicdata;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.jsm.exptool.R;
import com.jsm.exptool.core.ui.base.BaseActivity;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerAdapter;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerFragment;
import com.jsm.exptool.databinding.ExperimentCreateBasicDataFragmentBinding;
import com.jsm.exptool.libs.MultiSpinner;
import com.jsm.exptool.ui.main.MainActivity;


public class ExperimentCreateBasicDataFragment extends BaseRecyclerFragment<ExperimentCreateBasicDataFragmentBinding, ExperimentCreateBasicDataViewModel> {


    MultiSpinner sensorSpinner;
    AppCompatSpinner quickCommentsCollectionsSpinner;

    @Override
    public void onResume() {
        super.onResume();
        //Necesario inicializar aquí para que navCtrl esté cargado
        viewModel.initBackStackEntryObserver(getContext(), getViewLifecycleOwner());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  super.onCreateView(inflater, container, savedInstanceState);
        sensorSpinner = binding.sensorSpinner;
        quickCommentsCollectionsSpinner = binding.quickCommentsSpinner;
        viewModel.configureSpinner(sensorSpinner);
        viewModel.getQuickCommentsCollections().removeObservers(getViewLifecycleOwner());
        viewModel.getQuickCommentsCollections().observe(getViewLifecycleOwner(), response->{
            if (response != null){
                quickCommentsCollectionsSpinner.setAdapter(viewModel.getQuickCommentsCollectionsAdapter(getContext()));
            }
        });
        return view;
    }

    @Override
    protected ExperimentCreateBasicDataFragmentBinding createDataBinding(@NonNull LayoutInflater inflater, ViewGroup container) {
        return DataBindingUtil.inflate(inflater, R.layout.experiment_create_basic_data_fragment, container, false);

    }

    @Override
    protected BaseRecyclerAdapter createAdapter() {
        return new ExperimentCreateBasicDataAdapter(getContext(), viewModel, viewModel.getElements(), ((BaseActivity) this.getActivity()).getNavController(), getListItemResourceId(), viewModel);
    }

    @Override
    protected ExperimentCreateBasicDataViewModel createViewModel() {
        return new ViewModelProvider(this).get(ExperimentCreateBasicDataViewModel.class);

    }

    @Override
    protected int getListItemResourceId() {
        return R.layout.generic_cloud_tag_with_deletion_list_item;
    }

    @Override
    protected void setupRecyclerView() {
        super.setupRecyclerView();
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
    }
}