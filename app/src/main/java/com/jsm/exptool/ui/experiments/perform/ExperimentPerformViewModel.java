package com.jsm.exptool.ui.experiments.perform;

import android.Manifest;
import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.work.ListenableWorker;

import com.jsm.exptool.core.ui.base.BaseViewModel;
import com.jsm.exptool.libs.camera.CameraProvider;
import com.jsm.exptool.libs.camera.ImageReceivedCallback;
import com.jsm.exptool.model.experimentconfig.CameraConfig;
import com.jsm.exptool.model.Experiment;
import com.jsm.exptool.model.experimentconfig.ExperimentConfiguration;
import com.jsm.exptool.providers.WorksOrchestratorProvider;
import com.jsm.exptool.ui.camera.CameraPermissionsInterface;

import java.io.File;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class ExperimentPerformViewModel extends BaseViewModel {

    Experiment experiment;
    WorksOrchestratorProvider orchestratorProvider;
    Timer timer;

    public ExperimentPerformViewModel(@NonNull Application application, Experiment experiment) {
        super(application);
        this.experiment = experiment;
        orchestratorProvider = WorksOrchestratorProvider.getInstance();
        orchestratorProvider.init(getApplication());
        timer = new Timer();
    }
    public void initExperiment(Context context){
        initImageCapture(context);


    }

    public void initCameraProvider(Context context, CameraPermissionsInterface cameraPermission, PreviewView previewView){
        if(experiment.getConfiguration().isCameraEnabled()) {
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                cameraPermission.requestPermissions();
            }
            CameraProvider.getInstance().initCamera(context, previewView, null);
        }
    }

    private void initImageCapture(Context context){
        if(experiment.getConfiguration().isCameraEnabled() && experiment.getConfiguration().getCameraConfig() != null) {
            CameraConfig cameraConfig = experiment.getConfiguration().getCameraConfig();
            timer.scheduleAtFixedRate(new TimerTask() {
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
}