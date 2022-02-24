package com.jsm.exptool.ui.experiments.create.cameraconfiguration;


import android.Manifest;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.jsm.exptool.R;
import com.jsm.exptool.core.exceptions.BaseException;
import com.jsm.exptool.core.ui.base.BaseFragment;
import com.jsm.exptool.databinding.CameraFragmentBinding;
import com.jsm.exptool.databinding.ExperimentCreateCameraConfgurationFragmentBinding;
import com.jsm.exptool.ui.camera.CameraPermissionsInterface;


import java.util.Map;

public class ExperimentCameraConfigurationFragment extends BaseFragment<ExperimentCreateCameraConfgurationFragmentBinding, ExperimentCameraConfigurationViewModel> implements CameraPermissionsInterface {


    @Override
    protected ExperimentCameraConfigurationViewModel getViewModel() {
        ExperimentCameraConfigurationViewModel viewModel = new ViewModelProvider(this).get(ExperimentCameraConfigurationViewModel.class);

        return viewModel;
    }


    @Override
    protected ExperimentCreateCameraConfgurationFragmentBinding getDataBinding(@NonNull LayoutInflater inflater, ViewGroup container) {
        ExperimentCreateCameraConfgurationFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.experiment_create_camera_confguration_fragment, container, false);
        return binding;
    }

    @Override
    public void executeExtraActionsInsideBindingInit() {
        super.executeExtraActionsInsideBindingInit();
        viewModel.initCameraProvider(getContext(), this, binding.getRoot().findViewById(R.id.cameraPreview));

    }

    public void requestPermissions(){
        ActivityResultLauncher<String[]> mPermissionResult = registerForActivityResult(
                new ActivityResultContracts.RequestMultiplePermissions(),
                result -> {
                    boolean permissionOk = true;
                    for (Map.Entry<String, Boolean> entry : result.entrySet()) {
                        if(entry.getValue()) {
                            Log.e("PERMISSIONS", entry.getKey() + "onActivityResult: PERMISSION GRANTED");
                        } else {
                            Log.e("PERMISSIONS", entry.getKey() +  "onActivityResult: PERMISSION DENIED");
                            permissionOk = false;
                        }
                    }

                    if (permissionOk){
                        viewModel.initCameraProvider(getContext(), this, binding.getRoot().findViewById(R.id.cameraPreview));
                    }else{
                        //TODO Sustituir por mensaje de error descriptivo
                        handleError(new BaseException("Error en permisos", false), getContext(), null);
                    }


                });
        mPermissionResult.launch(new String[]{
                Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE
        });

    }






}