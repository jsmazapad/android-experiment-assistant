package com.jsm.exptool.ui.camera;



import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;


import android.Manifest;

import android.util.Log;
import android.view.LayoutInflater;

import android.view.ViewGroup;

import com.jsm.exptool.R;
import com.jsm.exptool.repositories.EmbeddingRepository;
import com.jsm.exptool.core.data.repositories.responses.ElementResponse;
import com.jsm.exptool.core.exceptions.BaseException;
import com.jsm.exptool.core.ui.base.BaseFragment;
import com.jsm.exptool.databinding.CameraFragmentBinding;
import com.jsm.exptool.model.ImageEmbeddingVector;

import java.util.Map;

public class CameraFragment extends BaseFragment<CameraFragmentBinding, CameraViewModel> implements CameraPermissionsInterface {


    @Override
    protected CameraViewModel createViewModel() {
        CameraViewModel viewModel = new ViewModelProvider(this).get(CameraViewModel.class);
        viewModel.getServerResponse().observe(this, response ->{
            processEmbeddedServerResponse(viewModel, response);
        });


        return viewModel;
    }

    private void processEmbeddedServerResponse(CameraViewModel viewModel, ElementResponse<ImageEmbeddingVector> response) {
        if (response != null){
            if (response.getError() != null){
                Log.e("ERROR", response.getError().getLocalizedMessage());
            }else{
                Log.d("RESPONSE", response.getResultElement().toString());
                EmbeddingRepository.insertImageEmbedding(viewModel.getActualFile(), response.getResultElement());
            }
        }
    }

    @Override
    protected CameraFragmentBinding createDataBinding(@NonNull LayoutInflater inflater, ViewGroup container) {
        CameraFragmentBinding binding = DataBindingUtil.inflate(inflater, R.layout.camera_fragment, container, false);
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