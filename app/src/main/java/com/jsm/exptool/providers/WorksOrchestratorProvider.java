package com.jsm.exptool.providers;

import static com.jsm.exptool.config.WorkerPropertiesConstants.IMAGE_FILE_NAME;
import static com.jsm.exptool.config.WorkerPropertiesConstants.PROCESSED_IMAGE_FILE_MANE;

import android.app.Application;
import android.content.Context;

import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkContinuation;
import androidx.work.WorkManager;

import com.jsm.exptool.workers.image.ObtainEmbeddingWorker;
import com.jsm.exptool.workers.image.ObtainImageWorker;
import com.jsm.exptool.workers.image.ProcessImageWorker;

import java.io.File;
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

    public void executeImageChain(Context context, File mFile){
        String processedFileName = mFile.getParent() + "/resized"+mFile.getName();
        Map <String,String> valuesMap = new HashMap<String,String>(){{
            put(IMAGE_FILE_NAME, mFile.getPath());
            put(PROCESSED_IMAGE_FILE_MANE, processedFileName);
        }};
        Data initialData = createInputDataForStrings(valuesMap);
        OneTimeWorkRequest processImageRequest = new OneTimeWorkRequest.Builder(ProcessImageWorker.class).setInputData(initialData).build();
        OneTimeWorkRequest obtainEmbeddingRequest = new OneTimeWorkRequest.Builder(ObtainEmbeddingWorker.class).build();

        WorkContinuation continuation = mWorkManager.beginWith(processImageRequest);
        continuation = continuation.then(obtainEmbeddingRequest);
        continuation.enqueue();
    }

    private Data createInputDataForString(String key, String value) {
        Data.Builder builder = new Data.Builder();
        if (value != null) {
            builder.putString(key, value);
        }
        return builder.build();
    }

    private Data createInputDataForStrings(Map<String, String> valuesMap) {
        Data.Builder builder = new Data.Builder();
        for (Map.Entry<String,String> entry:valuesMap.entrySet()) {
            if (entry.getKey() != null && entry.getValue() != null) {
                builder.putString(entry.getKey(), entry.getValue());
            }
        }
        return builder.build();
    }



}
