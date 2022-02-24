package com.jsm.exptool.ui.experiments.create.cameraconfiguration;

import static com.jsm.exptool.config.ConfigConstants.CAMERA_CONFIG_ARG;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavController;

import com.jsm.exptool.core.ui.base.BaseViewModel;
import com.jsm.exptool.libs.camera.CameraProvider;
import com.jsm.exptool.model.CameraConfig;
import com.jsm.exptool.ui.camera.CameraPermissionsInterface;
import com.jsm.exptool.ui.main.MainActivity;


public class ExperimentCameraConfigurationViewModel extends BaseViewModel {

    MutableLiveData<Integer> flashImageResource = new MutableLiveData<>();
    MutableLiveData<Integer> cameraSelectedImageResource = new MutableLiveData<>();
    CameraConfig configuration = new CameraConfig();


    public ExperimentCameraConfigurationViewModel(@NonNull Application application) {
        super(application);

        flashImageResource.setValue(CameraProvider.getInstance().getFlashImageResource(configuration.getFlashMode()));
        cameraSelectedImageResource.setValue(CameraProvider.getInstance().getCameraPositionImageResource(configuration.getCameraPosition()));
    }

    public void initCameraProvider(Context context, CameraPermissionsInterface cameraPermission, PreviewView previewView) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            cameraPermission.requestPermissions();
        }
        CameraProvider.getInstance().initCamera(context, previewView, null);
    }

    public MutableLiveData<Integer> getFlashImageResource() {
        return flashImageResource;
    }

    public MutableLiveData<Integer> getCameraSelectedImageResource() {
        return cameraSelectedImageResource;
    }

    public void selectCamera(View view) {
        CameraProvider.CameraPositions cameraPosition = CameraProvider.getInstance().switchCamera(view.getContext());
        cameraSelectedImageResource.setValue(CameraProvider.getInstance().getCameraPositionImageResource(cameraPosition));


    }


    public void changeFlash(View view) {
        CameraProvider.FlashModes nextFlashMode = getNextFlashMode(CameraProvider.getInstance().getFlashMode());
        CameraProvider.getInstance().setFlashMode(view.getContext(), getNextFlashMode(CameraProvider.getInstance().getFlashMode()));
        flashImageResource.setValue(CameraProvider.getInstance().getFlashImageResource(nextFlashMode));

    }

    private CameraProvider.FlashModes getNextFlashMode(CameraProvider.FlashModes previousMode) {
        CameraProvider.FlashModes flashModeToReturn = null;
        CameraProvider.FlashModes[] values = CameraProvider.FlashModes.values();
        for (int i = 0; i < values.length; i++) {
            if (previousMode == values[i]) {
                if (i == values.length - 1) {
                    flashModeToReturn = values[0];
                } else {
                    flashModeToReturn = values[i + 1];
                }
            }
        }

        return flashModeToReturn;
    }

    public void saveData(Context context) {
        configuration.setFlashMode(CameraProvider.getInstance().getFlashMode());
        configuration.setCameraPosition(CameraProvider.getInstance().getCameraPosition());
        NavController navController = ((MainActivity)context).getNavController();
        navController.getPreviousBackStackEntry().getSavedStateHandle().set(CAMERA_CONFIG_ARG, configuration);
        navController.popBackStack();


    }


}