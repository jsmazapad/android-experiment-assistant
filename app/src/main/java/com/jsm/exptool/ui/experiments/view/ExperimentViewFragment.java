package com.jsm.exptool.ui.experiments.view;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jsm.exptool.R;
import com.jsm.exptool.core.ui.base.BaseActivity;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerAdapter;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerFragment;
import com.jsm.exptool.databinding.ExperimentViewFragmentBinding;
import com.jsm.exptool.libs.SensorHandler;
import com.jsm.exptool.model.Experiment;
import com.jsm.exptool.model.MySensor;
import com.jsm.exptool.model.experimentconfig.CameraConfig;
import com.jsm.exptool.model.experimentconfig.ExperimentConfiguration;
import com.jsm.exptool.model.experimentconfig.RepeatableElement;
import com.jsm.exptool.model.experimentconfig.SensorsConfig;
import com.jsm.exptool.providers.EmbeddingAlgorithmsProvider;
import com.jsm.exptool.repositories.ExperimentsRepository;
import com.jsm.exptool.ui.experiments.perform.ExperimentPerformAdapter;
import com.jsm.exptool.ui.experiments.perform.ExperimentPerformViewModel;
import com.jsm.exptool.ui.experiments.perform.ExperimentPerformViewModelFactory;

import java.util.ArrayList;
import java.util.Date;

public class ExperimentViewFragment extends BaseRecyclerFragment<ExperimentViewFragmentBinding, ExperimentViewViewModel> {


    @Override
    protected ExperimentViewFragmentBinding createDataBinding(@NonNull LayoutInflater inflater, ViewGroup container) {
        return DataBindingUtil.inflate(inflater, R.layout.experiment_view_fragment, container, false);
    }

    @Override
    protected BaseRecyclerAdapter createAdapter() {
        return new ExperimentViewAdapter(getContext(), viewModel, getViewLifecycleOwner(), ((BaseActivity)getActivity()).getNavController(), getListItemResourceId());

    }

    @Override
    protected ExperimentViewViewModel createViewModel() {
        //TODO Código pruebas, comentar
        Experiment experiment = new Experiment();
        experiment.setStatus(Experiment.ExperimentStatus.CREATED);
        experiment.setTitle("Experimento "+ new Date().getTime());
        experiment.setDescription("Descripción del experimento originado en pruebas en la fecha:  "+ new Date().getTime());
        ExperimentConfiguration configuration = new ExperimentConfiguration();
        CameraConfig cameraConfig = new CameraConfig();
        cameraConfig.setInterval(1000);
        cameraConfig.setEmbeddingAlgorithm(EmbeddingAlgorithmsProvider.getEmbeddingAlgorithms().get(0));
        configuration.setCameraConfig(cameraConfig );
        SensorsConfig sensorsConfig = new SensorsConfig();
        sensorsConfig.setSensors(new ArrayList<MySensor>(){{
            addAll(SensorHandler.getInstance().getSensors());
        }});
        configuration.setSensorConfig(sensorsConfig);
        experiment.setConfiguration(configuration);
        long id = ExperimentsRepository.registerExperiment(experiment);
        experiment.setInternalId(id);
        return new ViewModelProvider(this, new ExperimentViewViewModelFactory(getActivity().getApplication(), experiment)).get(ExperimentViewViewModel.class);
    }

    @Override
    protected int getListItemResourceId() {
        return R.layout.experiment_view_list_item;
    }
}