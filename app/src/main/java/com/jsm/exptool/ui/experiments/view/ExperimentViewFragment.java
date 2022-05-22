package com.jsm.exptool.ui.experiments.view;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import androidx.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.jsm.exptool.R;
import com.jsm.exptool.core.ui.base.BaseActivity;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerAdapter;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerFragment;
import com.jsm.exptool.data.mock.MockExamples;
import com.jsm.exptool.databinding.ExperimentViewFragmentBinding;
import com.jsm.exptool.libs.SensorHandler;
import com.jsm.exptool.model.Experiment;
import com.jsm.exptool.model.SensorConfig;
import com.jsm.exptool.model.experimentconfig.CameraConfig;
import com.jsm.exptool.model.experimentconfig.ExperimentConfiguration;
import com.jsm.exptool.model.experimentconfig.SensorsGlobalConfig;
import com.jsm.exptool.providers.EmbeddingAlgorithmsProvider;
import com.jsm.exptool.repositories.ExperimentsRepository;

import java.util.ArrayList;
import java.util.Date;

public class ExperimentViewFragment extends BaseRecyclerFragment<ExperimentViewFragmentBinding, ExperimentViewViewModel> {


    @Override
    protected ExperimentViewFragmentBinding createDataBinding(@NonNull LayoutInflater inflater, ViewGroup container) {
        return DataBindingUtil.inflate(inflater, R.layout.experiment_view_fragment, container, false);
    }

    @Override
    protected BaseRecyclerAdapter createAdapter() {
        return new ExperimentViewAdapter(getContext(), viewModel, viewModel.getElements(), ((BaseActivity)getActivity()).getNavController(), getListItemResourceId());

    }

    @Override
    protected ExperimentViewViewModel createViewModel() {
        //TODO Código pruebas, comentar

        //Experiment experiment = ExperimentViewFragmentArgs.fromBundle(getArguments()).getExperiment();
        Experiment experiment = MockExamples.registerExperimentForSensorVisualizationTest(getContext(), null);
        return new ViewModelProvider(this, new ExperimentViewViewModelFactory(getActivity().getApplication(), experiment)).get(ExperimentViewViewModel.class);
    }

    @Override
    protected int getListItemResourceId() {
        return R.layout.experiment_view_list_item;
    }
}