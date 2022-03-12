package com.jsm.exptool.ui.experiments.create.configure;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.jsm.exptool.BuildConfig;
import com.jsm.exptool.R;
import com.jsm.exptool.core.ui.base.BaseActivity;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerAdapter;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerFragment;
import com.jsm.exptool.databinding.ExperimentCreateConfigureDataFragmentBinding;
import com.jsm.exptool.libs.SensorHandler;
import com.jsm.exptool.model.experimentconfig.AudioConfig;
import com.jsm.exptool.model.experimentconfig.CameraConfig;
import com.jsm.exptool.model.Experiment;
import com.jsm.exptool.model.experimentconfig.ExperimentConfiguration;
import com.jsm.exptool.model.MySensor;
import com.jsm.exptool.providers.AudioProvider;
import com.jsm.exptool.providers.EmbeddingAlgorithmsProvider;

import java.util.ArrayList;


public class ExperimentCreateConfigureDataFragment extends BaseRecyclerFragment<ExperimentCreateConfigureDataFragmentBinding, ExperimentCreateConfigureDataViewModel> {


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //Necesario inicializar aquí para que navCtrl esté cargado
        viewModel.initBackStackEntryObserver(getContext(), getViewLifecycleOwner());
    }

    @Override
    protected ExperimentCreateConfigureDataFragmentBinding createDataBinding(@NonNull LayoutInflater inflater, ViewGroup container) {
        ExperimentCreateConfigureDataFragmentBinding binding =  DataBindingUtil.inflate(inflater, R.layout.experiment_create_configure_data_fragment, container, false);
        //Inicializamos los controles de la vista de selección de frecuencia global
        return binding;


    }

    @Override
    protected BaseRecyclerAdapter createAdapter() {
        return new ExperimentCreateConfigureDataAdapter(getContext(), viewModel, this, ((BaseActivity) this.getActivity()).getNavController(), getListItemResourceId());
    }

    @Override
    protected ExperimentCreateConfigureDataViewModel createViewModel() {

        Experiment experiment;
        experiment = ExperimentCreateConfigureDataFragmentArgs.fromBundle(getArguments()).getExperiment();
        return new ViewModelProvider(this, new ExperimentCreateConfigureDataViewModelFactory(getActivity().getApplication(), experiment)).get(ExperimentCreateConfigureDataViewModel.class);

    }

    @Override
    public void executeExtraActionsInsideBindingInit() {
        super.executeExtraActionsInsideBindingInit();
        viewModel.initGlobalFrequencySelector(binding.frequencySelectorIncluded);
    }

    @Override
    protected int getListItemResourceId() {
        return R.layout.experiment_create_configure_data_list_item;
    }


}