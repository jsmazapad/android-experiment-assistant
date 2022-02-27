package com.jsm.exptool.ui.experiments.perform;

import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.OBTAIN_EMBEDDED_IMAGE;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.REGISTER_IMAGE;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.text.Html;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.work.WorkInfo;

import com.jsm.exptool.R;
import com.jsm.exptool.core.exceptions.BaseException;
import com.jsm.exptool.core.ui.RetryAction;
import com.jsm.exptool.core.ui.base.BaseViewModel;
import com.jsm.exptool.core.ui.loading.LoadingViewModel;
import com.jsm.exptool.core.utils.ModalMessage;
import com.jsm.exptool.libs.DeviceUtils;
import com.jsm.exptool.libs.camera.CameraProvider;
import com.jsm.exptool.libs.camera.ImageReceivedCallback;
import com.jsm.exptool.model.experimentconfig.CameraConfig;
import com.jsm.exptool.model.Experiment;
import com.jsm.exptool.providers.WorksOrchestratorProvider;
import com.jsm.exptool.repositories.ExperimentsRepository;
import com.jsm.exptool.ui.camera.CameraPermissionsInterface;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ExperimentPerformViewModel extends LoadingViewModel {

    private final Experiment experiment;
    private final WorksOrchestratorProvider orchestratorProvider;
    private List<Timer> timers = new ArrayList<>();
    private boolean experimentInitiated = false;
    private final MutableLiveData<Boolean> enableHandleExperimentButton = new MutableLiveData<>(true);
    private final MutableLiveData<String> changeStateText = new MutableLiveData<>("");

    private final MutableLiveData<Integer> numImages = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> numEmbeddings = new MutableLiveData<>(0);
    private final MutableLiveData<Boolean> imageCardEnabled = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> embeddedInfoEnabled = new MutableLiveData<>(false);


    public ExperimentPerformViewModel(@NonNull Application application, Experiment experiment) {
        super(application);
        this.experiment = experiment;
        orchestratorProvider = WorksOrchestratorProvider.getInstance();
        orchestratorProvider.init(getApplication());
        initImageComponents();
        changeStateText.setValue(application.getString(R.string.perform_experiment_init_text));
    }

    public MutableLiveData<Integer> getNumImages() {
        return numImages;
    }

    public MutableLiveData<Integer> getNumEmbeddings() {
        return numEmbeddings;
    }

    public MutableLiveData<Boolean> getImageCardEnabled() {
        return imageCardEnabled;
    }

    public MutableLiveData<Boolean> getEmbeddedInfoEnabled() {
        return embeddedInfoEnabled;
    }

    public MutableLiveData<String> getChangeStateText() {
        return changeStateText;
    }

    public MutableLiveData<Boolean> getEnableHandleExperimentButton() {
        return enableHandleExperimentButton;
    }

    public void handleExperimentState(Context context) {
        if (!experimentInitiated) {
            experimentInitiated = true;
            initExperiment(context);
        } else {
            finishExperiment(context);
        }
    }

    private void initExperiment(Context context) {
        //TODO Extraer comportamiento a provider
        this.experiment.setInitDate(new Date());
        this.experiment.setSdkDevice(DeviceUtils.getDeviceSDK());
        this.experiment.setDevice(DeviceUtils.getDeviceModel());
        this.experiment.setStatus(Experiment.ExperimentStatus.INITIATED);
        changeStateText.setValue(getApplication().getString(R.string.perform_expriment_finish_text));

        if(saveExperiment(context)){
           initImageCapture(context);
        }
    }

    private void finishExperiment(Context context) {


        enableHandleExperimentButton.setValue(false);
        for (Timer timer : timers) {
            timer.cancel();
        }
        timers = new ArrayList<>();
        //TODO Extraer comportamiento a provider
        this.experiment.setEndDate(new Date());
        this.experiment.setStatus(Experiment.ExperimentStatus.FINISHED);
        if(saveExperiment(context)){
            showResumeDialog(context);
        }
    }

    private boolean saveExperiment(Context context){
        boolean operationSuccess = false;
        int updatedRows = ExperimentsRepository.updateExperiment(this.experiment);
        if(updatedRows < 1){
            errorHandler.handleError(new BaseException(getApplication().getString(R.string.default_error_database_register_entity), true), context, () -> {
                saveExperiment(context);
            }, this);
        }else{
            operationSuccess = true;
        }
        return operationSuccess;

    }

    public void initCameraProvider(Context context, LifecycleOwner owner, CameraPermissionsInterface cameraPermission, PreviewView previewView) {
        if (experiment.getConfiguration().isCameraEnabled()) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                cameraPermission.requestPermissions();
                return;
            }
            CameraProvider.getInstance().initCamera(context, previewView, null);
            previewView.getPreviewStreamState().observe(owner, streamState -> {
                isLoading.setValue(streamState.equals(PreviewView.StreamState.IDLE));
            });
        }
    }

    private void initImageCapture(Context context) {
        if (experiment.getConfiguration().isCameraEnabled() && experiment.getConfiguration().getCameraConfig() != null) {
            CameraConfig cameraConfig = experiment.getConfiguration().getCameraConfig();
            Timer imageTimer = new Timer();
            timers.add(imageTimer);

            imageTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    Log.d("WORKER", "Pic requested");
                    Date date = new Date();
                    File mFile = new File(context.getExternalFilesDir(null), date + "pic.jpg");
                    CameraProvider.getInstance().takePicture(mFile, new ImageReceivedCallback() {
                        @Override
                        public void onImageReceived(File imageFile) {
                            orchestratorProvider.executeImageChain(getApplication(), imageFile, date, experiment);
                        }

                        @Override
                        public void onErrorReceived(Exception error) {
                            //TODO Tratamiento mas adecuado de errores
                            Log.d("Error taking pic", "Error taking pic");
                        }
                    });
                }
            }, 0, cameraConfig.getInterval());
        }
    }

    private void initImageComponents() {
        CameraConfig cameraConfig = experiment.getConfiguration().getCameraConfig();
        imageCardEnabled.setValue(experiment.getConfiguration().isCameraEnabled());
        embeddedInfoEnabled.setValue(experiment.getConfiguration().isCameraEnabled() && cameraConfig.getEmbeddingAlgorithm() != null);

    }

    public void initWorkInfoObservers(LifecycleOwner owner) {
        //Image
        LiveData<List<WorkInfo>> registerImageWorkInfo = orchestratorProvider.getWorkInfoByTag(REGISTER_IMAGE);
        LiveData<List<WorkInfo>> embeddingImageWorkInfo = orchestratorProvider.getWorkInfoByTag(OBTAIN_EMBEDDED_IMAGE);
        registerImageWorkInfo.observe(owner, workInfoList -> {
            numImages.setValue(orchestratorProvider.countSuccessWorks(workInfoList));
        });
        embeddingImageWorkInfo.observe(owner, workInfoList -> {
            numEmbeddings.setValue(orchestratorProvider.countSuccessWorks(workInfoList));
        });

    }

    public void showResumeDialog(Context context){
        StringBuilder builder = new StringBuilder();
        if(experiment.getConfiguration().isCameraEnabled()) {
            builder.append(Html.fromHtml(String.format(context.getString(R.string.experiment_perform_resume_dialog_num_images), numImages.getValue())));
            if(experiment.getConfiguration().getCameraConfig().getEmbeddingAlgorithm() != null){
                builder.append(Html.fromHtml(String.format(context.getString(R.string.experiment_perform_resume_dialog_num_embedded), numEmbeddings.getValue())));
            }
        }

        ModalMessage.showModalMessage(context, "Resumen del experimento", builder.toString(), null, (dialog, which) -> {
            //TODO Navegar a listado experimentos
        }, null, null);
    }
}