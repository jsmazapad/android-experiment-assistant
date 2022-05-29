package com.jsm.exptool.providers.syncworksorchestrator;

import static com.jsm.exptool.config.NetworkConstants.RETRY_DELAY;
import static com.jsm.exptool.config.NetworkConstants.RETRY_DELAY_UNIT;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.ACCURACY;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.ALTITUDE;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.EMBEDDING_ALG;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.EXPERIMENT_EXTERNAL_ID;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.EXPERIMENT_ID;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.DATE_TIMESTAMP;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.EXPERIMENT_MULTIMEDIA_PATHS;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.FILE_NAME;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.LATITUDE;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.LONGITUDE;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.PROCESSED_IMAGE_FILE_NAME;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.PROCESSED_IMAGE_HEIGHT;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.PROCESSED_IMAGE_WIDTH;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.SENSOR;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.SENSOR_NAME;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.TABLE_NAME;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.EXPORT_REGISTERS;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.OBTAIN_EMBEDDED_IMAGE;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.PERFORM_EXPERIMENT;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.PROCESS_IMAGE;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.REGISTER_AUDIO;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.REGISTER_IMAGE;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.REGISTER_LOCATION;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.REGISTER_SENSOR;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.REMOTE_SYNC_AUDIO_FILE_REGISTERS;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.REMOTE_SYNC_AUDIO_REGISTERS;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.REMOTE_SYNC_COMMENT_REGISTERS;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.REMOTE_SYNC_EXPERIMENT;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.REMOTE_SYNC_IMAGE_FILE_REGISTERS;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.REMOTE_SYNC_IMAGE_REGISTERS;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.REMOTE_SYNC_SENSORS_REGISTERS;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.REMOTE_SYNC_WORK;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.REMOTE_WORK;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.ZIP_EXPORTED;


import android.app.Application;
import android.content.Context;
import android.location.Location;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.work.BackoffPolicy;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkContinuation;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;
import androidx.work.WorkQuery;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jsm.exptool.R;
import com.jsm.exptool.config.ExportToCSVConfigOptions;
import com.jsm.exptool.libs.WorksOrchestratorUtils;
import com.jsm.exptool.model.Experiment;
import com.jsm.exptool.model.SensorConfig;
import com.jsm.exptool.model.experimentconfig.CameraConfig;
import com.jsm.exptool.providers.FilePathsProvider;
import com.jsm.exptool.providers.syncworksorchestrator.workcreators.multimedia.AudioWorksOrchestratorSyncTaskCreator;
import com.jsm.exptool.providers.syncworksorchestrator.workcreators.data.CommentWorksOrchestratorSyncTaskCreator;
import com.jsm.exptool.providers.syncworksorchestrator.workcreators.multimedia.ImageWorksOrchestratorSyncTaskCreator;
import com.jsm.exptool.providers.syncworksorchestrator.workcreators.data.SensorWorksOrchestratorSyncTaskCreator;
import com.jsm.exptool.workers.audio.RegisterAudioWorker;
import com.jsm.exptool.workers.export.ExportExperimentWorker;
import com.jsm.exptool.workers.export.ZipExportedExperimentWorker;
import com.jsm.exptool.workers.image.ObtainEmbeddingWorker;
import com.jsm.exptool.workers.image.ProcessImageWorker;
import com.jsm.exptool.workers.image.RegisterImageWorker;
import com.jsm.exptool.workers.location.RegisterLocationWorker;
import com.jsm.exptool.workers.sensor.RegisterSensorWorker;
import com.jsm.exptool.workers.sync.SyncRemoteExperimentWorker;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class WorksOrchestratorProvider {

    private WorkManager mWorkManager;

    private static WorksOrchestratorProvider INSTANCE = null;

    private WorksOrchestratorProvider() {
    }

    public static synchronized WorksOrchestratorProvider getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new WorksOrchestratorProvider();
        }
        return (INSTANCE);
    }

    public void init(Application application) {
        mWorkManager = WorkManager.getInstance(application);
        finishPendingJobs();
    }

    public void finishPendingJobs() {
        mWorkManager.cancelAllWork();
        mWorkManager.pruneWork();
    }

    public void finishJobsByTag(String tag) {
        mWorkManager.cancelAllWorkByTag(tag);
        mWorkManager.pruneWork();
    }

    /*
    REALIZACIÓN DE EXPERIMENTO
     */

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

        Data registerSensorData = WorksOrchestratorUtils.createInputData(registerSensorValues);
        OneTimeWorkRequest registerSensorRequest = new OneTimeWorkRequest.Builder(RegisterSensorWorker.class)
                .setInputData(registerSensorData).addTag(PERFORM_EXPERIMENT).addTag(REGISTER_SENSOR).build();
        mWorkManager.enqueue(registerSensorRequest);
    }

    //TODO Eliminar context de los métodos donde no se usa
    public void executeLocationChain(Context context, Location location, Date date, Experiment experiment) {


        Map<String, Object> registerLocationValues = new HashMap<String, Object>() {{
            put(LATITUDE, location.getLatitude());
            put(LONGITUDE, location.getLongitude());
            put(ALTITUDE, location.getAltitude());
            put(ACCURACY, location.getAccuracy());
            put(EXPERIMENT_ID, experiment.getInternalId());
            put(DATE_TIMESTAMP, date.getTime());
        }};

        Data registerSensorData = WorksOrchestratorUtils.createInputData(registerLocationValues);
        OneTimeWorkRequest registerLocationRequest = new OneTimeWorkRequest.Builder(RegisterLocationWorker.class)
                .setInputData(registerSensorData).addTag(PERFORM_EXPERIMENT).addTag(REGISTER_LOCATION).build();
        mWorkManager.enqueue(registerLocationRequest);
    }

    public void executeAudioChain(Context context, File mFile, Date date, Experiment experiment) {
        Map<String, Object> registerImageValues = new HashMap<String, Object>() {{
            put(FILE_NAME, mFile.getPath());
            put(EXPERIMENT_ID, experiment.getInternalId());
            put(DATE_TIMESTAMP, date.getTime());
        }};
        //Register image
        Data registerImageData = WorksOrchestratorUtils.createInputData(registerImageValues);
        OneTimeWorkRequest registerAudioRequest = new OneTimeWorkRequest.Builder(RegisterAudioWorker.class)
                .setInputData(registerImageData).addTag(PERFORM_EXPERIMENT).addTag(REGISTER_AUDIO).build();
        mWorkManager.enqueue(registerAudioRequest);
    }

    public void executeImageChain(Context context, File mFile, Date date, Experiment experiment) {
        Log.d("WORKER", "Image chain started");
        CameraConfig cameraConfig = experiment.getConfiguration().getCameraConfig();
        WorkContinuation continuation = null;

        Map<String, Object> registerImageValues = new HashMap<String, Object>() {{
            put(FILE_NAME, mFile.getPath());
            put(EXPERIMENT_ID, experiment.getInternalId());
            put(DATE_TIMESTAMP, date.getTime());
        }};

        //Register image
        Data registerImageData = WorksOrchestratorUtils.createInputData(registerImageValues);
        OneTimeWorkRequest registerImageRequest = new OneTimeWorkRequest.Builder(RegisterImageWorker.class)
                .setInputData(registerImageData).addTag(PERFORM_EXPERIMENT).addTag(REGISTER_IMAGE).build();
        continuation = mWorkManager.beginWith(registerImageRequest);


        if (cameraConfig.getEmbeddingAlgorithm() != null) {
            continuation = prepareEmbeddingChain(mFile, false, cameraConfig, continuation, null);
        }
        continuation.enqueue();

    }

    @NonNull
    public WorkContinuation prepareEmbeddingChain(File mFile, boolean onlyEmbedding, CameraConfig cameraConfig, WorkContinuation continuation, Map<String, Object> onlyEmbeddingInputDataValues) {
        //TODO Extraer resized a constante en provider
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

        if (onlyEmbedding && onlyEmbeddingInputDataValues != null){
            processImageValuesMap.putAll(onlyEmbeddingInputDataValues);
        }

        //Procesado de imagen
        Data processImageData = WorksOrchestratorUtils.createInputData(processImageValuesMap);
        OneTimeWorkRequest.Builder processImageRequestBuilder = new OneTimeWorkRequest.Builder(ProcessImageWorker.class)
                .setInputData(processImageData).addTag(PERFORM_EXPERIMENT).addTag(PROCESS_IMAGE);

        if(onlyEmbedding){
            //Añadimos etiqueta de trabajo remoto para poder observarlo junto a los otros trabajos
            processImageRequestBuilder.addTag(REMOTE_WORK);
        }

        OneTimeWorkRequest processImageRequest = processImageRequestBuilder.build();
        if (onlyEmbedding) {
            continuation = mWorkManager.beginWith(processImageRequest);
        } else {
            continuation = continuation.then(processImageRequest);
        }
        //Obtención de vector de datos embedding
        Data embeddingAdditionalData = WorksOrchestratorUtils.createInputData(embeddingAdditionalValuesMap);
        OneTimeWorkRequest obtainEmbeddingRequest = new OneTimeWorkRequest.Builder(ObtainEmbeddingWorker.class)
                .setInputData(embeddingAdditionalData).addTag(PERFORM_EXPERIMENT).addTag(OBTAIN_EMBEDDED_IMAGE).addTag(REMOTE_WORK).setBackoffCriteria(BackoffPolicy.LINEAR, RETRY_DELAY, RETRY_DELAY_UNIT).build();
        continuation = continuation.then(obtainEmbeddingRequest);
        return continuation;
    }

    /*
    EXPORTACIÓN A CSV
     */
    public void executeExportToCSV(Context context, Experiment experiment) {
        List<OneTimeWorkRequest> exportRegistersRequests = new ArrayList<>();
        //Creamos listado de exportadores que se ejecutaran en paralelo
        for (String element : ExportToCSVConfigOptions.EXPORT_TO_CSV_OPTIONS.keySet()) {
            Map<String, Object> inputDataValues = new HashMap<String, Object>() {{
                put(EXPERIMENT_ID, experiment.getInternalId());
                put(TABLE_NAME, element);
            }};

            Data inputData = WorksOrchestratorUtils.createInputData(inputDataValues);
            OneTimeWorkRequest exportRequest = new OneTimeWorkRequest.Builder(ExportExperimentWorker.class)
                    .setInputData(inputData).addTag(EXPORT_REGISTERS).addTag(element).build();
            exportRegistersRequests.add(exportRequest);


            //mWorkManager.enqueue();

        }
        Map<String, Object> zipInputDataValues = new HashMap<String, Object>() {{
            put(EXPERIMENT_MULTIMEDIA_PATHS, new String[]{
                    FilePathsProvider.getFilePathForExperimentItem(context, experiment.getInternalId(), FilePathsProvider.PathTypes.IMAGES).getPath(),
                    FilePathsProvider.getFilePathForExperimentItem(context, experiment.getInternalId(), FilePathsProvider.PathTypes.AUDIO).getPath()
            });


        }};
        Data zipInputData = WorksOrchestratorUtils.createInputData(zipInputDataValues);
        OneTimeWorkRequest zipRequest = new OneTimeWorkRequest.Builder(ZipExportedExperimentWorker.class)
                .setInputData(zipInputData).addTag(ZIP_EXPORTED).build();

        mWorkManager.beginWith(exportRegistersRequests).then(zipRequest).enqueue();

    }

    /*
    SINCRONIZACIÓN REMOTA
     */

    public void executeFullRemoteSync(Experiment experiment, boolean updateAlwaysExperiment, MutableLiveData<Boolean> initializationFinished) {

        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            Map<String, Object> experimentDataValues = new HashMap<String, Object>() {{
                put(EXPERIMENT_ID, experiment.getInternalId());
            }};
            Data experimentInputData = WorksOrchestratorUtils.createInputData(experimentDataValues);


            OneTimeWorkRequest syncExperimentRequest = new OneTimeWorkRequest.Builder(SyncRemoteExperimentWorker.class).setInputData(experimentInputData)
                    .addTag(REMOTE_WORK).addTag(REMOTE_SYNC_WORK).addTag(REMOTE_SYNC_EXPERIMENT).setBackoffCriteria(BackoffPolicy.LINEAR, RETRY_DELAY, RETRY_DELAY_UNIT).build();


            List<OneTimeWorkRequest> syncExperimentRegisters = new ArrayList<>();
            //Creamos listado de sincronizadores que se ejecutaran en paralelo

            Map<String, Object> registersInputDataValues = new HashMap<String, Object>() {
                {
                    put(EXPERIMENT_ID, experiment.getInternalId());
                    put(EXPERIMENT_EXTERNAL_ID, experiment.getExternalId());
                }
            };

            Data registersInputData = WorksOrchestratorUtils.createInputData(registersInputDataValues);

            if (experiment.getConfiguration() != null) {
                if (experiment.getConfiguration().isCameraEnabled()) {
                    //TODO Separar sincronización de registros en varias tareas según tamaño
                    new ImageWorksOrchestratorSyncTaskCreator().createSyncWorks(experiment, syncExperimentRegisters, registersInputDataValues, registersInputData);
                    if(experiment.getConfiguration().isEmbeddingEnabled()){

                    }
                }
                if (experiment.getConfiguration().isAudioEnabled()) {
                    new AudioWorksOrchestratorSyncTaskCreator().createSyncWorks(experiment, syncExperimentRegisters, registersInputDataValues, registersInputData);
                }
                if (experiment.getConfiguration().isSensorEnabled() || experiment.getConfiguration().isLocationEnabled()) {
                    new SensorWorksOrchestratorSyncTaskCreator().createSyncWorks(experiment, syncExperimentRegisters, registersInputDataValues, registersInputData);
                }

                new CommentWorksOrchestratorSyncTaskCreator().createSyncWorks(experiment, syncExperimentRegisters, registersInputDataValues, registersInputData);

            }


            if (experiment.getExternalId() < 1 || updateAlwaysExperiment) {
                mWorkManager.beginWith(syncExperimentRequest).then(syncExperimentRegisters).enqueue();
            } else {
                mWorkManager.enqueue(syncExperimentRegisters);
            }
            initializationFinished.setValue(true);
        });

    }

    public @StringRes
    int getRemoteStateTranslatableStringResFromWorkInfo(WorkInfo workInfo) {
        int translatableStringRes = 0;
        if (workInfo.getTags().contains(REMOTE_SYNC_IMAGE_REGISTERS)) {
            translatableStringRes = R.string.image_register;
        } else if (workInfo.getTags().contains(REMOTE_SYNC_IMAGE_FILE_REGISTERS)) {
            translatableStringRes = R.string.image_file;
        } else if (workInfo.getTags().contains(REMOTE_SYNC_AUDIO_REGISTERS)) {
            translatableStringRes = R.string.audio_register;
        } else if (workInfo.getTags().contains(REMOTE_SYNC_AUDIO_FILE_REGISTERS)) {
            translatableStringRes = R.string.audio_file;
        } else if (workInfo.getTags().contains(OBTAIN_EMBEDDED_IMAGE)) {
            translatableStringRes = R.string.embedding;
        } else if (workInfo.getTags().contains(REMOTE_SYNC_SENSORS_REGISTERS)) {
            translatableStringRes = R.string.sensor_register;
        } else if (workInfo.getTags().contains(REMOTE_SYNC_COMMENT_REGISTERS)) {
            translatableStringRes = R.string.comment_register;
        } else if (workInfo.getTags().contains(REMOTE_SYNC_EXPERIMENT)) {
            translatableStringRes = R.string.experiment_register;
        }else if (workInfo.getTags().contains(PROCESS_IMAGE)) {
            translatableStringRes = R.string.processing_image_for_embedding;
        }

        return translatableStringRes;
    }

    public boolean isSuccessWork(WorkInfo workInfo) {
        return workInfo.getState() == WorkInfo.State.SUCCEEDED;
    }


    public LiveData<List<WorkInfo>> getWorkInfoByTag(String tag) {
        return mWorkManager.getWorkInfosByTagLiveData(tag);
    }

    public LiveData<List<WorkInfo>> getWorkInfoByTagAndState(List<String> tags, List<WorkInfo.State> states) {
        return mWorkManager.getWorkInfosLiveData(WorkQuery.Builder.fromTags(tags).addStates(states).build());
    }

    public LiveData<List<WorkInfo>> getSuccessWorkInfoByTag(List<String> tags) {
        List<WorkInfo.State> completedStates = Collections.singletonList(WorkInfo.State.SUCCEEDED);
        return this.getWorkInfoByTagAndState(tags, completedStates);
    }

    public LiveData<List<WorkInfo>> getCompletedWorkInfoByTag(List<String> tags) {
        List<WorkInfo.State> completedStates = Arrays.asList(WorkInfo.State.SUCCEEDED, WorkInfo.State.FAILED, WorkInfo.State.CANCELLED);
        return this.getWorkInfoByTagAndState(tags, completedStates);
    }

    public int countPendingWorks(List<WorkInfo> workInfoList) {
        int counter = 0;
        for (WorkInfo info : workInfoList) {
            if (!info.getState().isFinished()) {
                counter++;
            }
        }
        return counter;
    }


    //TODO Reemplazar estos métodos por el uso de getWorkInfosLiveData
    public int countWorksByState(List<WorkInfo> workInfoList, WorkInfo.State state) {
        int counter = 0;
        for (WorkInfo info : workInfoList) {
            if (info.getState().isFinished() && info.getState().equals(state)) {
                counter++;
            }
        }
        return counter;
    }

    public int countSuccessWorks(List<WorkInfo> workInfoList) {
        return countWorksByState(workInfoList, WorkInfo.State.SUCCEEDED);
    }

    public int countFailureWorks(List<WorkInfo> workInfoList) {
        return countWorksByState(workInfoList, WorkInfo.State.FAILED);
    }


}
