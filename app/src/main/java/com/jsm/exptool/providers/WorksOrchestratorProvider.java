package com.jsm.exptool.providers;

import static com.jsm.exptool.config.WorkerPropertiesConstants.EMBEDDING_ALG;
import static com.jsm.exptool.config.WorkerPropertiesConstants.EXPERIMENT_ID;
import static com.jsm.exptool.config.WorkerPropertiesConstants.IMAGE_DATE_TIMESTAMP;
import static com.jsm.exptool.config.WorkerPropertiesConstants.IMAGE_FILE_NAME;
import static com.jsm.exptool.config.WorkerPropertiesConstants.PROCESSED_IMAGE_FILE_NAME;
import static com.jsm.exptool.config.WorkerPropertiesConstants.PROCESSED_IMAGE_HEIGHT;
import static com.jsm.exptool.config.WorkerPropertiesConstants.PROCESSED_IMAGE_WIDTH;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkContinuation;
import androidx.work.WorkManager;

import com.jsm.exptool.model.Experiment;
import com.jsm.exptool.model.experimentconfig.CameraConfig;
import com.jsm.exptool.workers.image.ObtainEmbeddingWorker;
import com.jsm.exptool.workers.image.ProcessImageWorker;
import com.jsm.exptool.workers.image.RegisterImageWorker;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class WorksOrchestratorProvider {

    private WorkManager mWorkManager;

    private static WorksOrchestratorProvider INSTANCE = null;

    // other instance variables can be here

    private WorksOrchestratorProvider() {

    };

    public static synchronized WorksOrchestratorProvider getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new WorksOrchestratorProvider();
        }
        return(INSTANCE);
    }

    public void init(Application application){
        mWorkManager = WorkManager.getInstance(application);
        mWorkManager.cancelAllWork();
        mWorkManager.pruneWork();
    }

    public void executeImageChain(Context context, File mFile, Date date, Experiment experiment) {
        Log.d("WORKER", "Image chain started");
        CameraConfig cameraConfig = experiment.getConfiguration().getCameraConfig();

        Map<String, Object> registerImageValues = new HashMap<String, Object>() {{
            put(IMAGE_FILE_NAME, mFile.getPath());
            put(EXPERIMENT_ID, experiment.getInternalId());
            put(IMAGE_DATE_TIMESTAMP, date.getTime());
        }};

        //Register image
        Data registerImageData = createInputData(registerImageValues);
        OneTimeWorkRequest registerImageRequest = new OneTimeWorkRequest.Builder(RegisterImageWorker.class).setInputData(registerImageData).build();
        WorkContinuation continuation = mWorkManager.beginWith(registerImageRequest);


        if (cameraConfig.getEmbeddingAlgorithm() != null) {
            String processedFileName = mFile.getParent() + "/resized" + mFile.getName();

            Map<String, Object> processImageValuesMap = new HashMap<String, Object>() {{
                put(IMAGE_FILE_NAME, mFile.getPath());
                put(PROCESSED_IMAGE_FILE_NAME, processedFileName);
                put(PROCESSED_IMAGE_HEIGHT, cameraConfig.getEmbeddingAlgorithm().getOptimalImageHeight());
                put(PROCESSED_IMAGE_WIDTH, cameraConfig.getEmbeddingAlgorithm().getOptimalImageHeight());
            }};
            Map<String, Object> embeddingAdditionalValuesMap = new HashMap<String, Object>() {{
                put(EMBEDDING_ALG, cameraConfig.getEmbeddingAlgorithm().getRemoteServerName());
            }};

            //Procesado de imagen
            Data processImageData = createInputData(processImageValuesMap);
            OneTimeWorkRequest processImageRequest = new OneTimeWorkRequest.Builder(ProcessImageWorker.class).setInputData(processImageData).build();
            continuation = continuation.then(processImageRequest);
            //Obtención de vector embebido
            Data embeddingAdditionalData = createInputData(embeddingAdditionalValuesMap);
            OneTimeWorkRequest obtainEmbeddingRequest = new OneTimeWorkRequest.Builder(ObtainEmbeddingWorker.class).setInputData(embeddingAdditionalData).build();
            continuation = continuation.then(obtainEmbeddingRequest);

        }
        continuation.enqueue();

    }

    private Data createInputDataForString(String key, String value) {
        Data.Builder builder = new Data.Builder();
        if (value != null) {
            builder.putString(key, value);
        }
        return builder.build();
    }

    private Data createInputData(Map<String, Object> valuesMap) {
        Data.Builder builder = new Data.Builder();
        for (Map.Entry<String,Object> entry:valuesMap.entrySet()) {
            if (entry.getKey() != null && entry.getValue() != null) {
                if (entry.getValue() instanceof String) {
                    builder.putString(entry.getKey(), (String)entry.getValue());
                }else if (entry.getValue() instanceof Integer) {
                    builder.putInt(entry.getKey(), (Integer) entry.getValue());
                }else if (entry.getValue() instanceof Long) {
                    builder.putLong(entry.getKey(), (Long) entry.getValue());
                }
            }
        }
        return builder.build();
    }



}
