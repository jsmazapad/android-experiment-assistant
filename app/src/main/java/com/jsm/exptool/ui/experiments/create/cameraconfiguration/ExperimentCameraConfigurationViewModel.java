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
import androidx.lifecycle.SavedStateHandle;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavController;

import com.jsm.exptool.R;
import com.jsm.exptool.core.ui.base.BaseViewModel;
import com.jsm.exptool.libs.camera.CameraProvider;
import com.jsm.exptool.model.CameraConfig;
import com.jsm.exptool.model.embedding.EmbeddingAlgorithm;
import com.jsm.exptool.providers.EmbeddingAlgorithmsProvider;
import com.jsm.exptool.ui.camera.CameraPermissionsInterface;
import com.jsm.exptool.ui.main.MainActivity;

import java.util.List;


public class ExperimentCameraConfigurationViewModel extends BaseViewModel {

    private MutableLiveData<Integer> flashImageResource = new MutableLiveData<>();
    private MutableLiveData<Integer> cameraSelectedImageResource = new MutableLiveData<>();
    private CameraConfig cameraConfig;
    private List<EmbeddingAlgorithm> embbedingAlgorithms = EmbeddingAlgorithmsProvider.getEmbeddingAlgorithms();
    private MutableLiveData<EmbeddingAlgorithm> selectedEmbeddingAlgorithm = new MutableLiveData<>();



    public ExperimentCameraConfigurationViewModel(@NonNull Application application, CameraConfig cameraConfig) {
        super(application);
        this.cameraConfig = cameraConfig;
        flashImageResource.setValue(CameraProvider.getInstance().getFlashImageResource(this.cameraConfig.getFlashMode()));
        cameraSelectedImageResource.setValue(CameraProvider.getInstance().getCameraPositionImageResource(this.cameraConfig.getCameraPosition()));
        selectedEmbeddingAlgorithm.setValue(cameraConfig.getEmbeddingAlgorithm() != null ? cameraConfig.getEmbeddingAlgorithm(): embbedingAlgorithms.get(0));
    }

    public void initCameraProvider(Context context, CameraPermissionsInterface cameraPermission, PreviewView previewView) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            cameraPermission.requestPermissions();
        }
        CameraProvider.getInstance().initCamera(context, previewView, null, cameraConfig.getFlashMode(), cameraConfig.getCameraPosition());
    }

    public MutableLiveData<Integer> getFlashImageResource() {
        return flashImageResource;
    }

    public MutableLiveData<Integer> getCameraSelectedImageResource() {
        return cameraSelectedImageResource;
    }

    public List<EmbeddingAlgorithm> getEmbbedingAlgorithms() {
        return embbedingAlgorithms;
    }

    public MutableLiveData<EmbeddingAlgorithm> getSelectedEmbeddingAlgorithm() {
        return selectedEmbeddingAlgorithm;
    }

//    public void setSelectedEmbeddingAlgorithm(MutableLiveData<EmbeddingAlgorithm> selectedEmbeddingAlgorithm) {
//        this.selectedEmbeddingAlgorithm = selectedEmbeddingAlgorithm;
//    }

    public boolean isEmbeddedAlgorithmEnabled(){
        return cameraConfig.getEmbeddingAlgorithm() != null;
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
        cameraConfig.setFlashMode(CameraProvider.getInstance().getFlashMode());
        cameraConfig.setCameraPosition(CameraProvider.getInstance().getCameraPosition());
        if (cameraConfig.getEmbeddingAlgorithm() != null){
            cameraConfig.setEmbeddingAlgorithm(selectedEmbeddingAlgorithm.getValue());
        }
        NavController navController = ((MainActivity)context).getNavController();
        NavBackStackEntry entry = navController.getPreviousBackStackEntry();
        if(entry != null) {
            entry.getSavedStateHandle().set(CAMERA_CONFIG_ARG, cameraConfig);
        }
        navController.popBackStack();


    }

    public EmbeddingAlgorithmsSpinnerAdapter getEmbeddingAlgAdapter(Context context){
        return new EmbeddingAlgorithmsSpinnerAdapter(context, embbedingAlgorithms, R.layout.experiment_create_camera_configuration_spinner_list_item);
    }


}