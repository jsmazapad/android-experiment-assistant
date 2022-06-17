package com.jsm.exptool.ui.experiments.create.cameraconfiguration;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.jsm.exptool.R;
import com.jsm.exptool.core.ui.base.BaseFragment;
import com.jsm.exptool.databinding.ExperimentCreateCameraConfgurationFragmentBinding;
import com.jsm.exptool.libs.requestpermissions.PermissionResultCallbackForViewModel;
import com.jsm.exptool.entities.experimentconfig.CameraConfig;
import com.jsm.exptool.libs.requestpermissions.RequestPermissionsInterface;
import com.jsm.exptool.providers.RequestPermissionsProvider;

import java.util.List;

public class ExperimentCameraConfigurationFragment extends BaseFragment<ExperimentCreateCameraConfgurationFragmentBinding, ExperimentCameraConfigurationViewModel> implements RequestPermissionsInterface {

    ActivityResultLauncher<String[]> cameraRequestPermissions;
    PermissionResultCallbackForViewModel cameraPermissionsResultCallback = new PermissionResultCallbackForViewModel() {
        @Override
        public void onPermissionsAccepted() {
            viewModel.onCameraPermissionsAccepted(ExperimentCameraConfigurationFragment.this);
        }

        @Override
        public void onPermissionsError(List<String> rejectedPermissions) {
            viewModel.onPermissionsError(rejectedPermissions, ExperimentCameraConfigurationFragment.this);
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cameraRequestPermissions = RequestPermissionsProvider.registerForCameraPermissions(this, cameraPermissionsResultCallback);
    }

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
        cameraPermissionsResultCallback.setViewModel(viewModel);
        viewModel.initCameraProvider(getContext(), this, binding.getRoot().findViewById(R.id.cameraPreview));

    }

    public void requestPermissions(){
        viewModel.handleRequestPermissions(this);
    }






}