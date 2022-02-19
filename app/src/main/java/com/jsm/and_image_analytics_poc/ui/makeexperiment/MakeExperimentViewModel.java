package com.jsm.and_image_analytics_poc.ui.makeexperiment;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.work.Data;
import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkContinuation;
import androidx.work.WorkManager;

import com.jsm.and_image_analytics_poc.core.ui.base.BaseViewModel;
import com.jsm.and_image_analytics_poc.workers.InputOutputExampleWorker;
import com.jsm.and_image_analytics_poc.workers.SensorWorker;

public class MakeExperimentViewModel extends BaseViewModel {

    WorkManager mWorkManager;
    public MakeExperimentViewModel(@NonNull Application application) {
        super(application);
        mWorkManager = WorkManager.getInstance(application);
    }

    public void createPeriodicWorkRequest(){
        mWorkManager.enqueue(OneTimeWorkRequest.from(SensorWorker.class));
    }

    public void createInputWorkRequest(){
        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(InputOutputExampleWorker.class).setInputData(createInputDataForUri()).build();
        mWorkManager.enqueue(request)
    }

    public void createChainWorkRequest(){
        // Se comienza con un trabajo y se añaden a la cadena para ejecutarlos posteriormente
        OneTimeWorkRequest request = new OneTimeWorkRequest.Builder(InputOutputExampleWorker.class).setInputData(createInputDataForUri()).build();
        WorkContinuation continuation = mWorkManager.beginWith(request);
        OneTimeWorkRequest secondRequest = new OneTimeWorkRequest.Builder(InputOutputExampleWorker.class).setInputData(createInputDataForUri()).build();
        continuation.then(secondRequest).then(secondRequest).enqueue();


    }

    public void createUniqueChainWorkRequest(){
        //Crea una cadena única, significa que sólo se permite una cadena a la vez con ese nombre,
        // si aparece otra hay tres opciones: ignorarla, sustituirla o ejecutarla cuando termine la primera
        //Para operaciones de actualización de datos de servidor usaremos este tipo de cadena
        WorkContinuation continuation = mWorkManager
                .beginUniqueWork("TAG UNICO DE CADENA",
                        ExistingWorkPolicy.APPEND,
                        OneTimeWorkRequest.from(InputOutputExampleWorker.class));
        continuation.enqueue();


    }

    private Data createInputDataForUri() {
        Data.Builder builder = new Data.Builder();
        String mImageUri = "EJEMPLO";
        if (mImageUri != null) {
            builder.putString("CONSTANTE_URI", mImageUri.toString());
        }
        return builder.build();
    }
}