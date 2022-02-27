package com.jsm.exptool.ui.experiments.perform;

import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.OBTAIN_EMBEDDED_IMAGE;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.REGISTER_IMAGE;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.work.WorkInfo;

import com.jsm.exptool.R;
import com.jsm.exptool.core.ui.base.BaseViewModel;
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

public class ExperimentPerformViewModel extends BaseViewModel {

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
            initExperiment(context);
        } else {
            finishExperiment(context);
        }
    }

    private void initExperiment(Context context) {
        changeStateText.setValue(getApplication().getString(R.string.perform_expriment_finish_text));
        this.experiment.setInitDate(new Date());
        this.experiment.setSdkDevice(Build.VERSION.SDK_INT);
        this.experiment.setDevice(Build.DEVICE);
        this.experiment.setStatus(Experiment.ExperimentStatus.INITIATED);
        initImageCapture(context);
        ExperimentsRepository.updateExperiment(this.experiment);
    }

    private void finishExperiment(Context context) {
        if(!enableHandleExperimentButton.getValue()) {
            enableHandleExperimentButton.setValue(false);
            for (Timer timer : timers) {
                timer.cancel();
            }
            timers = new ArrayList<>();
            this.experiment.setEndDate(new Date());
            this.experiment.setStatus(Experiment.ExperimentStatus.FINISHED);
            ExperimentsRepository.updateExperiment(this.experiment);
        }
    }

    public void initCameraProvider(Context context, CameraPermissionsInterface cameraPermission, PreviewView previewView) {
        if (experiment.getConfiguration().isCameraEnabled()) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                cameraPermission.requestPermissions();
            }
            CameraProvider.getInstance().initCamera(context, previewView, null);
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
}