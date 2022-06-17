package com.jsm.exptool.ui.experiments.create.cameraconfiguration;

import static com.jsm.exptool.config.ConfigConstants.CAMERA_CONFIG_ARG;

import android.app.Application;
import android.content.Context;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.camera.view.PreviewView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavController;

import com.jsm.exptool.R;
import com.jsm.exptool.core.exceptions.BaseException;
import com.jsm.exptool.core.ui.base.BaseFragment;
import com.jsm.exptool.core.ui.base.BaseViewModel;
import com.jsm.exptool.providers.CameraProvider;
import com.jsm.exptool.entities.experimentconfig.CameraConfig;
import com.jsm.exptool.entities.embedding.EmbeddingAlgorithm;
import com.jsm.exptool.providers.EmbeddingAlgorithmsProvider;
import com.jsm.exptool.providers.RequestPermissionsProvider;
import com.jsm.exptool.libs.requestpermissions.RequestPermissionsInterface;
import com.jsm.exptool.ui.main.MainActivity;

import java.util.List;


public class ExperimentCameraConfigurationViewModel extends BaseViewModel {

    private final MutableLiveData<Integer> flashImageResource = new MutableLiveData<>();
    private final MutableLiveData<Integer> cameraSelectedImageResource = new MutableLiveData<>();
    private final CameraConfig cameraConfig;
    private final List<EmbeddingAlgorithm> embbedingAlgorithms = EmbeddingAlgorithmsProvider.getEmbeddingAlgorithms();
    private final MutableLiveData<EmbeddingAlgorithm> selectedEmbeddingAlgorithm = new MutableLiveData<>();



    public ExperimentCameraConfigurationViewModel(@NonNull Application application, CameraConfig cameraConfig) {
        super(application);
        this.cameraConfig = cameraConfig;
        flashImageResource.setValue(CameraProvider.getInstance().getFlashImageResource(this.cameraConfig.getFlashMode()));
        cameraSelectedImageResource.setValue(CameraProvider.getInstance().getCameraPositionImageResource(this.cameraConfig.getCameraPosition()));
        selectedEmbeddingAlgorithm.setValue(cameraConfig.getEmbeddingAlgorithm() != null ? cameraConfig.getEmbeddingAlgorithm(): embbedingAlgorithms.get(0));
    }

    public void initCameraProvider(Context context, RequestPermissionsInterface cameraPermission, PreviewView previewView) {
        if (RequestPermissionsProvider.handleCheckPermissionsForCamera(context, cameraPermission))
            return;

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
        return new EmbeddingAlgorithmsSpinnerAdapter(context, embbedingAlgorithms, R.layout.generic_title_description_spinner_list_item);
    }

    //TODO REfactorizar como experiments perform
    public void handleRequestPermissions(BaseFragment fragment){
        RequestPermissionsProvider.requestPermissionsForCamera(((ExperimentCameraConfigurationFragment)fragment).cameraRequestPermissions);
    }

    public void onCameraPermissionsAccepted(Fragment fragment) {

        initCameraProvider(fragment.getContext(), (RequestPermissionsInterface) fragment, fragment.getView().findViewById(R.id.cameraPreview));

    }


    public void onPermissionsError(List<String> rejectedPermissions, Fragment fragment) {
        //TODO Mejorar error, desconectar camara e informar
        handleError(new BaseException("Error en permisos", false), fragment.getContext());
    }


}