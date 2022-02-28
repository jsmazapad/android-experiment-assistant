package com.jsm.exptool.ui.experiments.perform;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import androidx.annotation.NonNull;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.jsm.exptool.R;
import com.jsm.exptool.core.ui.base.BaseFragment;
import com.jsm.exptool.databinding.ExperimentPerformFragmentBinding;
import com.jsm.exptool.model.Experiment;
import com.jsm.exptool.model.experimentconfig.CameraConfig;
import com.jsm.exptool.model.experimentconfig.ExperimentConfiguration;
import com.jsm.exptool.providers.EmbeddingAlgorithmsProvider;
import com.jsm.exptool.repositories.ExperimentsRepository;
import com.jsm.exptool.libs.requestpermissions.RequestPermissionsInterface;

import java.util.Date;

public class ExperimentPerformFragment extends BaseFragment<ExperimentPerformFragmentBinding, ExperimentPerformViewModel> implements RequestPermissionsInterface {


    @Override
    protected ExperimentPerformViewModel createViewModel() {
        //TODO Código pruebas, comentar
        Experiment experiment = new Experiment();
        experiment.setTitle("Experimento "+ new Date().getTime());
        experiment.setDescription("Descripción del experimento originado en pruebas en la fecha:  "+ new Date().getTime());
        ExperimentConfiguration configuration = new ExperimentConfiguration();
        CameraConfig cameraConfig = new CameraConfig();
        cameraConfig.setInterval(1000);
        cameraConfig.setEmbeddingAlgorithm(EmbeddingAlgorithmsProvider.getEmbeddingAlgorithms().get(0));
        configuration.setCameraConfig(cameraConfig );
        experiment.setConfiguration(configuration);
        long id = ExperimentsRepository.registerExperiment(experiment);
        experiment.setInternalId(id);

        return new ViewModelProvider(this, new ExperimentPerformViewModelFactory(getActivity().getApplication(), experiment)).get(ExperimentPerformViewModel.class);
    }

    @Override
    protected ExperimentPerformFragmentBinding createDataBinding(@NonNull LayoutInflater inflater, ViewGroup container) {
         return DataBindingUtil.inflate(inflater, R.layout.experiment_perform_fragment, container, false);
    }

    @Override
    public void executeExtraActionsInsideBindingInit() {
        super.executeExtraActionsInsideBindingInit();
        viewModel.initCameraProvider(getContext(), getViewLifecycleOwner(), this, binding.getRoot().findViewById(R.id.cameraPreview));
        viewModel.initWorkInfoObservers(getViewLifecycleOwner());

    }

    @Override
    public void requestPermissions(){
        viewModel.handleRequestPermissions(this);
    }
}