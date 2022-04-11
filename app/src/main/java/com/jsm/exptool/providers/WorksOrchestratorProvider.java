package com.jsm.exptool.providers;

import static com.jsm.exptool.config.NetworkConstants.RETRY_DELAY;
import static com.jsm.exptool.config.NetworkConstants.RETRY_DELAY_UNIT;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.EMBEDDING_ALG;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.EXPERIMENT_ID;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.DATE_TIMESTAMP;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.FILE_NAME;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.PROCESSED_IMAGE_FILE_NAME;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.PROCESSED_IMAGE_HEIGHT;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.PROCESSED_IMAGE_WIDTH;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.SENSOR;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.SENSOR_NAME;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.TABLE_NAME;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.EXPORT_REGISTERS;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.OBTAIN_EMBEDDED_IMAGE;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.PROCESS_IMAGE;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.REGISTER_AUDIO;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.REGISTER_IMAGE;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.REGISTER_SENSOR;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.REMOTE_WORK;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.ZIP_EXPORTED;
import static com.jsm.exptool.providers.ExportDataProvider.ELEMENTS_TO_EXPORT;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.work.BackoffPolicy;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkContinuation;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jsm.exptool.model.Experiment;
import com.jsm.exptool.model.SensorConfig;
import com.jsm.exptool.model.experimentconfig.CameraConfig;
import com.jsm.exptool.workers.audio.RegisterAudioWorker;
import com.jsm.exptool.workers.export.ExportExperimentWorker;
import com.jsm.exptool.workers.export.ZipExportedExperimentWorker;
import com.jsm.exptool.workers.image.ObtainEmbeddingWorker;
import com.jsm.exptool.workers.image.ProcessImageWorker;
import com.jsm.exptool.workers.image.RegisterImageWorker;
import com.jsm.exptool.workers.sensor.RegisterSensorWorker;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class WorksOrchestratorProvider {

    private WorkManager mWorkManager;

    private static WorksOrchestratorProvider INSTANCE = null;

    // other instance variables can be here

    private WorksOrchestratorProvider() {

    }

    ;

    public static synchronized WorksOrchestratorProvider getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new WorksOrchestratorProvider();
        }
        return (INSTANCE);
    }

    public void init(Application application) {
        mWorkManager = WorkManager.getInstance(application);
        mWorkManager.cancelAllWork();
        mWorkManager.pruneWork();
    }

    public void executeSensorChain(Context context, SensorConfig sensor, Date date, Experiment experiment) {

        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        Map<String, Object> registerSensorValues = new HashMap<String, Object>() {{
            put(SENSOR, gson.toJson(sensor));
            put(SENSOR_NAME, context.getString(sensor.getNameStringResource()));
            put(EXPERIMENT_ID, experiment.getInternalId());
            put(DATE_TIMESTAMP, date.getTime());
        }};

        Data registerSensorData = createInputData(registerSensorValues);
        OneTimeWorkRequest registerSensorRequest = new OneTimeWorkRequest.Builder(RegisterSensorWorker.class)
                .setInputData(registerSensorData).addTag(REGISTER_SENSOR).build();
        mWorkManager.enqueue(registerSensorRequest);
    }

    public void executeAudioChain(Context context, File mFile, Date date, Experiment experiment){
        Map<String, Object> registerImageValues = new HashMap<String, Object>() {{
            put(FILE_NAME, mFile.getPath());
            put(EXPERIMENT_ID, experiment.getInternalId());
            put(DATE_TIMESTAMP, date.getTime());
        }};
        //Register image
        Data registerImageData = createInputData(registerImageValues);
        OneTimeWorkRequest registerAudioRequest = new OneTimeWorkRequest.Builder(RegisterAudioWorker.class)
                .setInputData(registerImageData).addTag(REGISTER_AUDIO).build();
        mWorkManager.enqueue(registerAudioRequest);
    }

    public void executeImageChain(Context context, File mFile, Date date, Experiment experiment) {
        Log.d("WORKER", "Image chain started");
        CameraConfig cameraConfig = experiment.getConfiguration().getCameraConfig();

        Map<String, Object> registerImageValues = new HashMap<String, Object>() {{
            put(FILE_NAME, mFile.getPath());
            put(EXPERIMENT_ID, experiment.getInternalId());
            put(DATE_TIMESTAMP, date.getTime());
        }};

        //Register image
        Data registerImageData = createInputData(registerImageValues);
        OneTimeWorkRequest registerImageRequest = new OneTimeWorkRequest.Builder(RegisterImageWorker.class)
                .setInputData(registerImageData).addTag(REGISTER_IMAGE).build();
        WorkContinuation continuation = mWorkManager.beginWith(registerImageRequest);


        if (cameraConfig.getEmbeddingAlgorithm() != null) {
            String processedFileName = mFile.getParent() + "/resized" + mFile.getName();

            Map<String, Object> processImageValuesMap = new HashMap<String, Object>() {{
                put(FILE_NAME, mFile.getPath());
                put(PROCESSED_IMAGE_FILE_NAME, processedFileName);
                put(PROCESSED_IMAGE_HEIGHT, cameraConfig.getEmbeddingAlgorithm().getOptimalImageHeight());
                put(PROCESSED_IMAGE_WIDTH, cameraConfig.getEmbeddingAlgorithm().getOptimalImageHeight());
            }};
            Map<String, Object> embeddingAdditionalValuesMap = new HashMap<String, Object>() {{
                put(EMBEDDING_ALG, cameraConfig.getEmbeddingAlgorithm().getRemoteServerName());
            }};

            //Procesado de imagen
            Data processImageData = createInputData(processImageValuesMap);
            OneTimeWorkRequest processImageRequest = new OneTimeWorkRequest.Builder(ProcessImageWorker.class)
                    .setInputData(processImageData).addTag(PROCESS_IMAGE).build();
            continuation = continuation.then(processImageRequest);
            //Obtención de vector embebido
            Data embeddingAdditionalData = createInputData(embeddingAdditionalValuesMap);
            OneTimeWorkRequest obtainEmbeddingRequest = new OneTimeWorkRequest.Builder(ObtainEmbeddingWorker.class)
                    .setInputData(embeddingAdditionalData).addTag(OBTAIN_EMBEDDED_IMAGE).addTag(REMOTE_WORK).setBackoffCriteria(BackoffPolicy.LINEAR, RETRY_DELAY, RETRY_DELAY_UNIT).build();
            continuation = continuation.then(obtainEmbeddingRequest);

        }
        continuation.enqueue();

    }


    public void executeExportToCSV(Experiment experiment){
        List<OneTimeWorkRequest> exportRegistersRequests = new ArrayList<>();
        //Creamos listado de exportadores que se ejecutaran en paralelo
        for (String element: ELEMENTS_TO_EXPORT) {
            Map<String, Object> inputDataValues = new HashMap<String, Object>() {{
                put(EXPERIMENT_ID, experiment.getInternalId());
                put(TABLE_NAME, element);
            }};

            Data inputData = createInputData(inputDataValues);
            OneTimeWorkRequest exportRequest = new OneTimeWorkRequest.Builder(ExportExperimentWorker.class)
                    .setInputData(inputData).addTag(EXPORT_REGISTERS).addTag(element).build();
            exportRegistersRequests.add(exportRequest);


            //mWorkManager.enqueue();

        }
        OneTimeWorkRequest zipRequest = new OneTimeWorkRequest.Builder(ZipExportedExperimentWorker.class)
                .addTag(ZIP_EXPORTED).build();

        mWorkManager.beginWith(exportRegistersRequests).then(zipRequest).enqueue();

    }



    private Data createInputData(Map<String, Object> valuesMap) {
        Data.Builder builder = new Data.Builder();
        for (Map.Entry<String, Object> entry : valuesMap.entrySet()) {
            if (entry.getKey() != null && entry.getValue() != null) {
                if (entry.getValue() instanceof String) {
                    builder.putString(entry.getKey(), (String) entry.getValue());
                } else if (entry.getValue() instanceof Integer) {
                    builder.putInt(entry.getKey(), (Integer) entry.getValue());
                } else if (entry.getValue() instanceof Long) {
                    builder.putLong(entry.getKey(), (Long) entry.getValue());
                } else if (entry.getValue() instanceof Float[]){
                    builder.putFloatArray(entry.getKey(), (float[]) entry.getValue());
                }else if (entry.getValue() instanceof String[]){
                    builder.putStringArray(entry.getKey(), (String[]) entry.getValue());
                }
            }
        }
        return builder.build();
    }

    public LiveData<List<WorkInfo>> getWorkInfoByTag(String tag) {
        return mWorkManager.getWorkInfosByTagLiveData(tag);
    }


    public int countWorksByState(List<WorkInfo> workInfos, WorkInfo.State state){
        int counter = 0;
        for (WorkInfo info : workInfos){
            if(info.getState().isFinished() && info.getState() == state)
            {
                counter++;
            }
        }
        return counter;
    }

    public int countSuccessWorks(List<WorkInfo> workInfos){
        return countWorksByState(workInfos, WorkInfo.State.SUCCEEDED);
    }

    public int countFailureWorks(List<WorkInfo> workInfos){
        return countWorksByState(workInfos, WorkInfo.State.FAILED);
    }


}
