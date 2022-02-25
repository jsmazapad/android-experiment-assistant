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
import com.jsm.exptool.model.AudioConfig;
import com.jsm.exptool.model.CameraConfig;
import com.jsm.exptool.model.Experiment;
import com.jsm.exptool.model.ExperimentConfiguration;
import com.jsm.exptool.model.MySensor;
import com.jsm.exptool.model.RepeatableElement;
import com.jsm.exptool.model.Sensor.Accelerometer;
import com.jsm.exptool.model.Sensor.Gravity;
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
        if (!BuildConfig.FLAVOR.equals("mock")) {
            experiment = ExperimentCreateConfigureDataFragmentArgs.fromBundle(getArguments()).getExperiment();
        } else {
            //TODO Código desarrollo
            experiment = new Experiment();
            experiment.setConfiguration(new ExperimentConfiguration());
            experiment.getConfiguration().setCameraConfig(new CameraConfig());
            experiment.getConfiguration().getCameraConfig().setEmbeddingAlgorithm(EmbeddingAlgorithmsProvider.getEmbeddingAlgorithms().get(0));
            experiment.getConfiguration().setAudioConfig(new AudioConfig());
            experiment.setSensors(new ArrayList<MySensor>() {
                {
                    add(new Accelerometer());
                    add(new Gravity());
                }
            });
        }
        ExperimentCreateConfigureDataViewModel viewModel = new ViewModelProvider(this, new ExperimentCreateConfigureDataViewModelFactory(getActivity().getApplication(), experiment)).get(ExperimentCreateConfigureDataViewModel.class);
        return viewModel;

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