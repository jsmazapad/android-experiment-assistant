package com.jsm.exptool.ui.experiments.create.cameraconfiguration;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.jsm.exptool.R;
import com.jsm.exptool.core.ui.base.BaseFragment;
import com.jsm.exptool.databinding.ExperimentCreateCameraConfgurationFragmentBinding;
import com.jsm.exptool.model.experimentconfig.CameraConfig;
import com.jsm.exptool.libs.requestpermissions.RequestPermissionsInterface;

public class ExperimentCameraConfigurationFragment extends BaseFragment<ExperimentCreateCameraConfgurationFragmentBinding, ExperimentCameraConfigurationViewModel> implements RequestPermissionsInterface {


    @Override
    protected ExperimentCameraConfigurationViewModel createViewModel() {
        CameraConfig cameraConfig = ExperimentCameraConfigurationFragmentArgs.fromBundle(getArguments()).getCameraConfig();
        //TODO Código pruebas, borrar
//        CameraConfig cameraConfig = new CameraConfig();
//        cameraConfig.setEmbeddingAlgorithm(EmbeddingAlgorithmsProvider.getEmbeddingAlgorithms().get(2));

        ExperimentCameraConfigurationViewModel viewModel = new ViewModelProvider(this, new ExperimentCameraConfigurationViewModelFactory(getActivity().getApplication(), cameraConfig)).get(ExperimentCameraConfigurationViewModel.class);

        return viewModel;
    }


    @Override
    protected ExperimentCreateCameraConfgurationFragmentBinding createDataBinding(@NonNull LayoutInflater inflater, ViewGroup container) {
        ExperimentCreateCameraConfgurationFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.experiment_create_camera_confguration_fragment, container, false);
        return binding;
    }

    @Override
    public void executeExtraActionsInsideBindingInit() {
        super.executeExtraActionsInsideBindingInit();
        viewModel.initCameraProvider(getContext(), this, binding.getRoot().findViewById(R.id.cameraPreview));

    }

    public void requestPermissions(){
        viewModel.handleRequestPermissions(this);
    }






}