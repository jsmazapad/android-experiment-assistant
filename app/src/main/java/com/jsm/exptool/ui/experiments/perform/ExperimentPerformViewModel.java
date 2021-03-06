package com.jsm.exptool.ui.experiments.perform;

import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.OBTAIN_EMBEDDED_IMAGE;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.PERFORM_EXPERIMENT;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.REGISTER_AUDIO;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.REGISTER_IMAGE;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.REGISTER_LOCATION;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.REGISTER_SENSOR;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.REMOTE_SYNC_EXPERIMENT;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.REMOTE_SYNC_WORK;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.REMOTE_WORK;

import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.location.Location;
import android.text.Html;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.camera.view.PreviewView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavController;
import androidx.work.WorkInfo;

import com.jsm.exptool.R;
import com.jsm.exptool.core.data.network.exceptions.InvalidSessionException;
import com.jsm.exptool.core.data.repositories.responses.ListResponse;
import com.jsm.exptool.core.exceptions.BaseException;
import com.jsm.exptool.core.ui.base.BaseActivity;
import com.jsm.exptool.core.ui.base.BaseFragment;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerViewModel;
import com.jsm.exptool.data.repositories.AudioRepository;
import com.jsm.exptool.data.repositories.ImageRepository;
import com.jsm.exptool.data.repositories.RemoteSyncRepository;
import com.jsm.exptool.data.repositories.SensorRepository;
import com.jsm.exptool.entities.eventbus.WorkFinishedEvent;
import com.jsm.exptool.entities.experimentconfig.RepeatableElementConfig;
import com.jsm.exptool.libs.MemoryUtils;
import com.jsm.exptool.core.libs.ModalMessage;
import com.jsm.exptool.libs.DeviceUtils;
import com.jsm.exptool.entities.CommentSuggestion;
import com.jsm.exptool.entities.experimentconfig.SensorConfig;
import com.jsm.exptool.entities.experimentconfig.AudioConfig;
import com.jsm.exptool.entities.experimentconfig.LocationConfig;
import com.jsm.exptool.entities.experimentconfig.SensorsGlobalConfig;
import com.jsm.exptool.entities.register.CommentRegister;
import com.jsm.exptool.providers.AudioProvider;
import com.jsm.exptool.providers.CameraProvider;
import com.jsm.exptool.libs.camera.ImageReceivedCallback;
import com.jsm.exptool.entities.experimentconfig.CameraConfig;
import com.jsm.exptool.entities.Experiment;
import com.jsm.exptool.providers.ExperimentProvider;
import com.jsm.exptool.providers.FilePathsProvider;
import com.jsm.exptool.providers.LocationProvider;
import com.jsm.exptool.providers.RequestPermissionsProvider;
import com.jsm.exptool.providers.worksorchestrator.WorksOrchestratorProvider;
import com.jsm.exptool.data.repositories.CommentRepository;
import com.jsm.exptool.data.repositories.CommentSuggestionsRepository;
import com.jsm.exptool.data.repositories.ExperimentsRepository;
import com.jsm.exptool.libs.requestpermissions.RequestPermissionsInterface;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ExperimentPerformViewModel extends BaseRecyclerViewModel<SensorConfig, SensorConfig> {

    private final Experiment experiment;
    private final WorksOrchestratorProvider orchestratorProvider;
    private List<Timer> timers = new ArrayList<>();
    private MutableLiveData<Boolean> experimentInitiated = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> enableHandleExperimentButton = new MutableLiveData<>(true);
    private final MutableLiveData<String> changeStateText = new MutableLiveData<>("");
    //Temporalizaci??n de experimento
    private long startTimeStamp = 0;

    //Gesti??n de espera a que terminen los trabajos en background para finalizaci??n de experimento
    private boolean waitToFinishJobs = false;
    private final MutableLiveData<Boolean> waitEndedToCompleteExperiment = new MutableLiveData<>(false);

    //IMAGEN
    private final MutableLiveData<Integer> numImages = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> numEmbeddings = new MutableLiveData<>(0);
    private final MutableLiveData<Boolean> imageCardEnabled = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> embeddedInfoEnabled = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> quickCommentsEnabled = new MutableLiveData<>(false);
    private final MutableLiveData<String> imageCardExtraInfo = new MutableLiveData<>();

    //AUDIO
    private final MutableLiveData<Integer> numAudios = new MutableLiveData<>(0);
    private final MutableLiveData<Boolean> audioCardEnabled = new MutableLiveData<>(false);
    private final MutableLiveData<String> audioCardExtraInfo = new MutableLiveData<>();
    //UBICACI??N
    private final MutableLiveData<Integer> numLocations = new MutableLiveData<>(0);
    private final MutableLiveData<Boolean> locationCardEnabled = new MutableLiveData<>(false);
    private final MutableLiveData<String> locationCardExtraInfo = new MutableLiveData<>();
    private final MutableLiveData<String> locationValueString = new MutableLiveData<>();
    //COMENTARIOS
    private MutableLiveData<String> commentValue = new MutableLiveData<>("");
    private MutableLiveData<List<String>> quickComments = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<String> commentCardExtraInfo = new MutableLiveData<>();
    private final MutableLiveData<Integer> numComments = new MutableLiveData<>(0);
    private final MutableLiveData<List<CommentSuggestion>> suggestions = new MutableLiveData<>();
    private final MutableLiveData<ListResponse<CommentSuggestion>> suggestionsResponse = new MutableLiveData<>();

    //SENSORES
    private final MutableLiveData<Integer> numSensors = new MutableLiveData<>(0);
    private final MutableLiveData<Boolean> sensorCardEnabled = new MutableLiveData<>(false);
    private final MutableLiveData<String> sensorCardExtraInfo = new MutableLiveData<>();

    private final MutableLiveData<Integer> updateElementInRecycler = new MutableLiveData<>();

    private RequestPermissionsProvider permissionsProvider;

    //Remote SYNC
    boolean remoteSyncEnabled = false;

    //Experiment ending flags
    MutableLiveData<Integer> experimentPrepared = new MutableLiveData<>();
    MutableLiveData<Integer> experimentFinished = new MutableLiveData<>();
    MutableLiveData<Boolean> experimentCountedOnFinish = new MutableLiveData<>();


    public ExperimentPerformViewModel(@NonNull Application application, Experiment experiment) {
        super(application);
        this.experiment = experiment;
        this.quickComments.setValue(experiment.getConfiguration() != null ? experiment.getConfiguration().getQuickComments() : null);
        orchestratorProvider = WorksOrchestratorProvider.getInstance();
        permissionsProvider = new RequestPermissionsProvider();
        if (this.experiment.getConfiguration().isCameraEnabled()) {
            permissionsProvider.addPermission(RequestPermissionsProvider.PermissionTypes.CAMERA);
        }
        if (this.experiment.getConfiguration().isAudioEnabled()) {
            permissionsProvider.addPermission(RequestPermissionsProvider.PermissionTypes.AUDIO);
        }
        if (this.experiment.getConfiguration().isLocationEnabled()) {
            permissionsProvider.addPermission(RequestPermissionsProvider.PermissionTypes.LOCATION);
        }

        initComponentsVisibility();
        changeStateText.setValue(application.getString(R.string.perform_experiment_init_text));
        if (this.experiment.getConfiguration().isSensorEnabled()) {
            apiResponseRepositoryHolder.setValue(new ListResponse<>(experiment.getConfiguration().getSensorConfig().getSensors()));
        }

        quickCommentsEnabled.setValue(this.quickComments.getValue() != null && this.quickComments.getValue().size() > 0);
    }

    public MutableLiveData<Integer> getNumImages() {
        return numImages;
    }

    public MutableLiveData<Integer> getNumLocations() {
        return numLocations;
    }

    public MutableLiveData<Boolean> getLocationCardEnabled() {
        return locationCardEnabled;
    }

    public MutableLiveData<String> getLocationCardExtraInfo() {
        return locationCardExtraInfo;
    }

    public MutableLiveData<String> getLocationValueString() {
        return locationValueString;
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

    public MutableLiveData<Integer> getNumAudios() {
        return numAudios;
    }

    public MutableLiveData<Boolean> getAudioCardEnabled() {
        return audioCardEnabled;
    }

    public MutableLiveData<Integer> getNumSensors() {
        return numSensors;
    }

    public MutableLiveData<Boolean> getSensorCardEnabled() {
        return sensorCardEnabled;
    }

    public MutableLiveData<Boolean> getQuickCommentsEnabled() {
        return quickCommentsEnabled;
    }

    public MutableLiveData<String> getChangeStateText() {
        return changeStateText;
    }

    public MutableLiveData<Boolean> getEnableHandleExperimentButton() {
        return enableHandleExperimentButton;
    }

    public MutableLiveData<String> getCommentValue() {
        return commentValue;
    }

    public MutableLiveData<List<CommentSuggestion>> getSuggestions() {
        return suggestions;
    }

    public void setCommentValue(MutableLiveData<String> commentValue) {
        this.commentValue = commentValue;
    }

    public MutableLiveData<Integer> getNumComments() {
        return numComments;
    }

    public MutableLiveData<String> getImageCardExtraInfo() {
        return imageCardExtraInfo;
    }

    public MutableLiveData<String> getAudioCardExtraInfo() {
        return audioCardExtraInfo;
    }

    public MutableLiveData<String> getCommentCardExtraInfo() {
        return commentCardExtraInfo;
    }

    public MutableLiveData<String> getSensorCardExtraInfo() {
        return sensorCardExtraInfo;
    }

    public MutableLiveData<Integer> getUpdateElementInRecycler() {
        return updateElementInRecycler;
    }

    public MutableLiveData<List<String>> getQuickComments() {
        return quickComments;
    }

    public MutableLiveData<Boolean> getExperimentInitiated() {
        return experimentInitiated;
    }

    public MutableLiveData<Boolean> getWaitEndedToCompleteExperiment() {
        return waitEndedToCompleteExperiment;
    }

    public void handleExperimentState(Context context) {
        if (!experimentInitiated.getValue()) {
            experimentInitiated.setValue(true);
            initExperiment(context);
        } else {
            finishExperiment(context);
        }
    }


    private void initExperiment(Context context) {
        //TODO Extraer comportamiento a provider
        isLoading.setValue(true);
        orchestratorProvider.finishPendingJobs();
        if (experiment.getStatus().equals(Experiment.ExperimentStatus.INITIATED)) {
            ExperimentProvider.endExperiment(experiment, context, experimentPrepared, Experiment.ExperimentStatus.INITIATED, false);
        } else {
            if (experiment.getStatus().equals(Experiment.ExperimentStatus.CREATED)) {
                this.experiment.setInitDate(new Date());
                this.experiment.setSdkDevice(DeviceUtils.getDeviceSDK());
                this.experiment.setDevice(DeviceUtils.getDeviceModel());
            }
            this.experiment.setStatus(Experiment.ExperimentStatus.INITIATED);
            this.experiment.setExportedPending(true);
            this.experiment.setSyncPending(true);
            experimentPrepared.setValue(1);
        }

    }

    private void finishExperiment(Context context) {

        //Deshabilitamos bot??n
        enableHandleExperimentButton.setValue(false);
        //Cancelamos timers
        for (Timer timer : timers) {
            timer.cancel();
        }
        timers = new ArrayList<>();
        //Paramos escucha de sensores
        if (experiment.getConfiguration().isSensorEnabled())
            for (SensorConfig sensor : elements.getValue()) {
                sensor.getSensorReader().cancelListener();
            }
        //Paramos ubicaci??n
        if (experiment.getConfiguration().isLocationEnabled())
            LocationProvider.getInstance().stopLocation();
        //TODO Extraer comportamiento a provider
        this.experiment.setEndDate(new Date());
        this.experiment.setStatus(Experiment.ExperimentStatus.FINISHED);


        long elapsedTime = System.currentTimeMillis() - startTimeStamp;
        this.experiment.setDuration(this.experiment.getDuration() + elapsedTime);

        //Si quedan experimentos pendientes mostramos mensaje
        if (orchestratorProvider.getWorkInfoByTag(PERFORM_EXPERIMENT).getValue() != null && orchestratorProvider.countPendingWorks(orchestratorProvider.getWorkInfoByTag(PERFORM_EXPERIMENT).getValue()) > 0) {
            ModalMessage.showModalMessageWithThreeButtons(context, context.getString(R.string.pending_jobs_dialog_title), context.getString(R.string.pending_jobs_dialog_text),
                    context.getString(R.string.pending_jobs_dialog_option_wait), (dialog, which) -> {
                        waitPendingJobsAndFinish(context, false);
                    }, context.getString(R.string.pending_jobs_dialog_option_end), (dialog, which) -> {
                        //Finalizamos trabajos y salvamos el experimento
                        orchestratorProvider.finishPendingJobs();
                        completeExperimentFinishing(context);
                    }, context.getString(R.string.pending_jobs_dialog_option_wait_only_local), ((dialog, which) -> {
                        //Volvemos a comprobar (los trabajos pueden haber finalizado mientras el usuario se decide)
                        if (orchestratorProvider.getWorkInfoByTag(PERFORM_EXPERIMENT).getValue() != null && orchestratorProvider.countPendingWorks(orchestratorProvider.getWorkInfoByTag(PERFORM_EXPERIMENT).getValue()) > 0) {
                            waitPendingJobsAndFinish(context, true);
                        } else {
                            completeExperimentFinishing(context);
                        }
                    }));
        } else {
            orchestratorProvider.finishPendingJobs();
            completeExperimentFinishing(context);
        }
    }

    /**
     * Espera a que terminen los trabajos pendientes
     *
     * @param context
     * @param finishRemote Espera s??lo a los trabajos locales, elimina los que dependan de servidores remotos
     */
    private void waitPendingJobsAndFinish(Context context, boolean finishRemote) {
        //Volvemos a comprobar (los trabajos pueden haber finalizado antes de llegar aqu??)
        if (orchestratorProvider.getWorkInfoByTag(PERFORM_EXPERIMENT).getValue() != null && orchestratorProvider.countPendingWorks(orchestratorProvider.getWorkInfoByTag(PERFORM_EXPERIMENT).getValue()) > 0) {
            //Colocamos el flag para esperar a que los trabajos finalicen
            ExperimentPerformViewModel.this.waitToFinishJobs = true;
            if (finishRemote) {
                //Finalizamos los trabajos que dependen de servidor remoto
                orchestratorProvider.finishJobsByTag(REMOTE_WORK);
            }
        } else {
            completeExperimentFinishing(context);
        }
    }

    protected void completeExperimentFinishing(Context context) {
        if (waitToFinishJobs) {
            waitEndedToCompleteExperiment.setValue(false);
        }
        String size = MemoryUtils.getFormattedFileSize(FilePathsProvider.getExperimentFilePath(context, experiment.getInternalId()));
        this.experiment.setSize(size);
        experiment.setExportedPending(true);
        isLoading.setValue(true);
        ExperimentProvider.endExperiment(experiment, context, experimentFinished, Experiment.ExperimentStatus.FINISHED, true);

    }

    /**
     * Funci??n que se ejecuta cuando el contador de trabajos pendientes llega a 0
     */
    private void onFinishedBackgroundJobs() {
        //Si tiene el flag de espera activado, actualizamos el LiveData para finalizar los trabajos
        if (waitToFinishJobs) {
            waitEndedToCompleteExperiment.setValue(true);
        }

    }

    private boolean saveExperiment(Context context) {
        boolean operationSuccess = false;
        this.experiment.setEmbeddingPending(numEmbeddings.getValue()<numImages.getValue());
        int updatedRows = ExperimentsRepository.updateExperiment(this.experiment);
        if (updatedRows < 1) {
//            errorHandler.handleError(new BaseException(getApplication().getString(R.string.default_error_database_register_entity), true), context, () -> {
//                saveExperiment(context);
//            }, this);
        } else {
            operationSuccess = true;
        }
        return operationSuccess;

    }


    public void initCameraProvider(Context context, LifecycleOwner owner, RequestPermissionsInterface cameraPermission, PreviewView previewView) {
        if (experiment.getConfiguration().isCameraEnabled()) {
            if (RequestPermissionsProvider.handleCheckPermissionsForCamera(context, cameraPermission))
                return;

            CameraProvider.getInstance().initCamera(context, previewView, null);
            previewView.getPreviewStreamState().observe(owner, streamState -> {
                isLoading.setValue(streamState.equals(PreviewView.StreamState.IDLE));
            });
        } else {
            isLoading.setValue(false);
        }
    }

    public void initRemoteSync(Context context) {
        if (experiment.getConfiguration().isRemoteSyncEnabled()) {
            isLoading.setValue(true);
            RemoteSyncRepository.login(response -> {
                isLoading.setValue(false);
                if (response.getError() != null) {
                    remoteSyncEnabled = false;
                    BaseException exceptionToShow;
                    if (response.getError() instanceof InvalidSessionException) {
                        exceptionToShow = new BaseException(context.getString(R.string.sync_credentials_error), false);
                    } else {
                        String errorString = context.getString(R.string.error_network_connection);
                        exceptionToShow = new BaseException(errorString, false);
                    }
                    handleError(exceptionToShow, context);
                } else {
                    remoteSyncEnabled = true;
                }
            }, false);
        } else {
            isLoading.setValue(false);
        }
    }

    public void initAudioProvider(Context context, LifecycleOwner owner, RequestPermissionsInterface audioPermission) {
        if (experiment.getConfiguration().isAudioEnabled()) {
            if (RequestPermissionsProvider.handleCheckPermissionsForAudio(context, audioPermission))
                return;
        }
    }

    public void initLocationProvider(Context context, LifecycleOwner owner, RequestPermissionsInterface locationPermission) {
        if (experiment.getConfiguration().isLocationEnabled()) {
            if (RequestPermissionsProvider.handleCheckPermissionsForLocationFine(context, locationPermission))
                return;

            LocationProvider.getInstance().initLocation(context, experiment.getConfiguration().getLocationConfig().getLocationOption());
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
                    File mFile = new File(FilePathsProvider.getFilePathForExperimentItem(context, experiment.getInternalId(), FilePathsProvider.PathTypes.IMAGES), FilePathsProvider.formatFileName(date + "pic.jpg"));
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

    private void initAudioRecording(Context context) {
        if (experiment.getConfiguration().isAudioEnabled() && experiment.getConfiguration().getAudioConfig() != null) {
            AudioConfig audioConfig = experiment.getConfiguration().getAudioConfig();
            Timer audioTimer = new Timer();
            timers.add(audioTimer);
            final File[] mFile = new File[1];
            final Date[] date = {null};
            audioTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    if (audioConfig.getRecordingDuration() == audioConfig.getInterval() && mFile[0] != null && date[0] != null) {
                        AudioProvider.getInstance().stopRecording();
                        orchestratorProvider.executeAudioChain(getApplication(), mFile[0], date[0], experiment);
                    }
                    Log.d("WORKER", "Audio requested");
                    date[0] = new Date();
                    mFile[0] = new File(FilePathsProvider.getFilePathForExperimentItem(context, experiment.getInternalId(), FilePathsProvider.PathTypes.AUDIO), FilePathsProvider.formatFileName(date[0] + "." + audioConfig.getRecordingOption().getFileExtension()));
                    AudioProvider.getInstance().record(mFile[0], audioConfig.getRecordingOption());
                    if (audioConfig.getRecordingDuration() < audioConfig.getInterval()) {
                        //Tras un delay = duracion de la grabaci??n, se ejecuta el timer para la grbaci??n
                        audioTimer.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                Log.w("AUDIOWORKER", "audiostop");
                                AudioProvider.getInstance().stopRecording();
                                orchestratorProvider.executeAudioChain(getApplication(), mFile[0], date[0], experiment);

                            }
                        }, audioConfig.getRecordingDuration());
                    }
                }
            }, 0, audioConfig.getInterval());
        }
    }

    private void initLocationRecording(Context context) {
        if (experiment.getConfiguration().isLocationEnabled() && experiment.getConfiguration().getLocationConfig() != null) {
            LocationConfig locationConfig = experiment.getConfiguration().getLocationConfig();
            Timer locationTimer = new Timer();
            timers.add(locationTimer);
            final String formatString = getApplication().getString(R.string.experiment_perform_location_format);

            locationTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {

                    Log.d("WORKER", "Location requested");
                    Date date = new Date();
                    Location location = LocationProvider.getInstance().getCurrentLocation();
                    if (location != null) {
                        locationValueString.postValue(String.format(formatString, location.getLatitude(), location.getLongitude(), location.getAltitude(), location.getAccuracy()));
                        orchestratorProvider.executeLocationChain(getApplication(), location, date, experiment);
                    } else {
                        Log.w("Experiment perform", "Error al obtener ubicaci??n");
                    }

                }
            }, 0, locationConfig.getInterval());
        }
    }

    private void initSensorRecording(Context context) {
        //TODO Quitar listeners de sensores al finalizar experimento
        if (experiment.getConfiguration().isSensorEnabled() && experiment.getConfiguration().getSensorConfig() != null
                && experiment.getConfiguration().getSensorConfig().getSensors() != null) {
            SensorsGlobalConfig sensorConfig = experiment.getConfiguration().getSensorConfig();
            Timer sensorTimer = new Timer();
            timers.add(sensorTimer);

            for (int i = 0; i < sensorConfig.getSensors().size(); i++) {
                final int position = i;
                SensorConfig sensor = sensorConfig.getSensors().get(position);
                sensor.getSensorReader().initListener();
                sensorTimer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        Date date = new Date();
                        elements.getValue().set(position, sensor);
                        updateElementInRecycler.postValue(position);
                        orchestratorProvider.executeSensorChain(getApplication(), sensor, date, experiment);

                    }
                }, 0, sensor.getInterval());
            }
        }
    }

    private void initRemoteSyncingProcess(Context context) {
        if (experiment.getConfiguration().isRemoteSyncEnabled() && experiment.getConfiguration().getRemoteSyncConfig() != null) {
            RepeatableElementConfig remoteSyncConfig = experiment.getConfiguration().getRemoteSyncConfig();
            Timer remoteSyncTimer = new Timer();
            timers.add(remoteSyncTimer);


            remoteSyncTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {

                    Log.d("WORKER", "RemoteSync requested");
                    orchestratorProvider.executeFullRemoteSync(experiment, true, new MutableLiveData<>(), false, true);


                }
            }, 0, remoteSyncConfig.getInterval());
        }
    }

    private void initComponentsVisibility() {
        CameraConfig cameraConfig = experiment.getConfiguration().getCameraConfig();
        imageCardEnabled.setValue(experiment.getConfiguration().isCameraEnabled());
        embeddedInfoEnabled.setValue(experiment.getConfiguration().isCameraEnabled() && cameraConfig.getEmbeddingAlgorithm() != null);

        audioCardEnabled.setValue(experiment.getConfiguration().isAudioEnabled());

        sensorCardEnabled.setValue(experiment.getConfiguration().isSensorEnabled());
        locationCardEnabled.setValue(experiment.getConfiguration().isLocationEnabled());

    }


    public void initWorkInfoObservers(LifecycleOwner owner) {
        //Pendientes
        LiveData<List<WorkInfo>> pendingWorkInfo = orchestratorProvider.getWorkInfoByTag(PERFORM_EXPERIMENT);
        pendingWorkInfo.observe(owner, workInfoList -> {

            if (orchestratorProvider.countPendingWorks(workInfoList) == 0 || (experiment.getStatus().equals(Experiment.ExperimentStatus.FINISHED) && workInfoList == null)) {
                ExperimentPerformViewModel.this.onFinishedBackgroundJobs();
            }

        });

    }

    private void generateImageExtraInfo() {
        String extraInfo = getApplication().getString(R.string.experiment_perform_images_number) + " " + numImages.getValue() + "\n" +
                getApplication().getString(R.string.experiment_perform_num_embeddings) + " " + numEmbeddings.getValue();
        imageCardExtraInfo.postValue(extraInfo);

    }


    public void showResumeDialog(Context context) {
        StringBuilder builder = new StringBuilder();
        if (experiment.getConfiguration().isCameraEnabled()) {
            builder.append(Html.fromHtml(String.format(context.getString(R.string.experiment_perform_resume_dialog_num_images), numImages.getValue())));
            if (experiment.getConfiguration().getCameraConfig().getEmbeddingAlgorithm() != null) {
                builder.append(Html.fromHtml(String.format(context.getString(R.string.experiment_perform_resume_dialog_num_embedded), numEmbeddings.getValue())));
            }
        }
        if (experiment.getConfiguration().isAudioEnabled())
            builder.append(Html.fromHtml(String.format(context.getString(R.string.experiment_perform_resume_dialog_num_audios), numAudios.getValue())));

        if (experiment.getConfiguration().isSensorEnabled())
            builder.append(Html.fromHtml(String.format(context.getString(R.string.experiment_perform_resume_dialog_num_sensors), numSensors.getValue())));

        if (experiment.getConfiguration().isLocationEnabled())
            builder.append(Html.fromHtml(String.format(context.getString(R.string.experiment_perform_resume_dialog_num_locations), numLocations.getValue())));

        builder.append(Html.fromHtml(String.format(context.getString(R.string.experiment_perform_resume_dialog_num_comments), numComments.getValue())));
        if(experiment.isSyncPending()) {
            builder.append(Html.fromHtml(context.getString(R.string.experiment_perform_resume_pending_sync)));
        }else{
            builder.append(Html.fromHtml(context.getString(R.string.experiment_perform_resume_complete_sync)));
        }
        ModalMessage.showModalMessage(context, "Resumen del experimento", builder.toString(), null, (dialog, which) -> {
            ((BaseActivity) context).getNavController().navigate(ExperimentPerformFragmentDirections.actionNavPerformExperimentToNavExperiments());
        }, null, null);
    }

    public void handleRequestPermissions(BaseFragment fragment) {

        if (experiment.getConfiguration().isCameraEnabled() && permissionsProvider.getPermissionsToCheckList().contains(RequestPermissionsProvider.PermissionTypes.CAMERA))
            RequestPermissionsProvider.requestPermissionsForCamera(((ExperimentPerformFragment) fragment).cameraRequestPermissions);
        if (experiment.getConfiguration().isAudioEnabled() && permissionsProvider.getPermissionsToCheckList().contains(RequestPermissionsProvider.PermissionTypes.AUDIO))
            RequestPermissionsProvider.requestPermissionsForAudioRecording(((ExperimentPerformFragment) fragment).audioRequestPermissions);
        if (experiment.getConfiguration().isLocationEnabled() && permissionsProvider.getPermissionsToCheckList().contains(RequestPermissionsProvider.PermissionTypes.LOCATION))
            RequestPermissionsProvider.requestPermissionsForLocationFine(((ExperimentPerformFragment) fragment).locationRequestPermissions);
    }

    public void checkIfRemoteSyncOnlyInitIsNecessary(Context context) {
        if (!experiment.getConfiguration().isCameraEnabled() && !experiment.getConfiguration().isAudioEnabled() && !experiment.getConfiguration().isLocationEnabled() && experiment.getConfiguration().isRemoteSyncEnabled()) {
            initRemoteSync(context);
        }
    }

    public void onCameraPermissionsAccepted(Fragment fragment) {
        permissionsProvider.removePermission(RequestPermissionsProvider.PermissionTypes.CAMERA);
        if (experiment.getConfiguration().isCameraEnabled())
            initCameraProvider(fragment.getContext(), fragment.getViewLifecycleOwner(), (RequestPermissionsInterface) fragment, fragment.getView().findViewById(R.id.cameraPreview));
        continueProcessingPermissions(fragment);

    }

    public void onAudioPermissionsAccepted(Fragment fragment) {
        permissionsProvider.removePermission(RequestPermissionsProvider.PermissionTypes.AUDIO);
        if (experiment.getConfiguration().isAudioEnabled())
            initAudioProvider(fragment.getContext(), fragment.getViewLifecycleOwner(), (RequestPermissionsInterface) fragment);
        continueProcessingPermissions(fragment);
    }

    public void onLocationPermissionsAccepted(Fragment fragment) {
        permissionsProvider.removePermission(RequestPermissionsProvider.PermissionTypes.LOCATION);
        if (experiment.getConfiguration().isLocationEnabled())
            initLocationProvider(fragment.getContext(), fragment.getViewLifecycleOwner(), (RequestPermissionsInterface) fragment);
        continueProcessingPermissions(fragment);
    }

    private void handlePermissionsError(List<String> rejectedPermissions, Fragment fragment) {

        ModalMessage.showError(fragment.getContext(), RequestPermissionsProvider.getPermissionErrorString(getApplication(), rejectedPermissions), null, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                continueProcessingPermissions(fragment);
            }
        }, null, null);
    }

    public void onCameraPermissionsRejected(List<String> rejectedPermissions, Fragment fragment) {
        permissionsProvider.removePermission(RequestPermissionsProvider.PermissionTypes.CAMERA);
        handlePermissionsError(rejectedPermissions, fragment);
    }

    public void onAudioPermissionsRejected(List<String> rejectedPermissions, Fragment fragment) {
        permissionsProvider.removePermission(RequestPermissionsProvider.PermissionTypes.AUDIO);
        handlePermissionsError(rejectedPermissions, fragment);
    }

    public void onLocationPermissionsRejected(List<String> rejectedPermissions, Fragment fragment) {
        permissionsProvider.removePermission(RequestPermissionsProvider.PermissionTypes.LOCATION);
        handlePermissionsError(rejectedPermissions, fragment);
    }

    private void continueProcessingPermissions(Fragment fragment) {
        if (permissionsProvider.getPermissionsToCheckList().size() > 0) {
            handleRequestPermissions((ExperimentPerformFragment) fragment);
        } else {
            initRemoteSync(fragment.getContext());
        }

    }

    public void saveCommentValue(String comment, boolean fromRegisterComment) {
        if (experimentInitiated.getValue() && CommentRepository.registerComment(new CommentRegister(experiment.getInternalId(), new Date(), false, comment)) > 0) {
            numComments.setValue(numComments.getValue() + 1);
            commentCardExtraInfo.setValue(getApplication().getString(R.string.experiment_perform_num_comments) + " " + numComments.getValue());
            CommentSuggestion relatedComment = CommentSuggestionsRepository.selectCommentSuggestionByComment(comment);
            //Comprobamos que no venga de haber solicitado el registro del comentario para no sumarlo dos veces
            //Si existe el comentario se a??ade uno a la cuenta del n??mero de veces empleado
            if (!fromRegisterComment && relatedComment != null) {
                relatedComment.setUsedTimesCounter(relatedComment.getUsedTimesCounter() + 1);
                CommentSuggestionsRepository.updateCommentSuggestion(relatedComment);
            }


        }
    }

    public void saveCommentValue(boolean fromRegisterComment) {

        String comment = commentValue.getValue();
        if (comment != null && !"".equals(comment)) {
            saveCommentValue(commentValue.getValue(), fromRegisterComment);
            commentValue.setValue("");
        }

    }

    public void saveCommentAndAddToSuggestions() {
        String comment = commentValue.getValue();
        if (comment != null && !"".equals(comment)) {
            saveCommentValue(true);
            CommentSuggestionsRepository.registerOrUpdateCommentSuggestion(new CommentSuggestion(1, comment));
            initCommentSuggestions();
        }


    }

    public void initCommentSuggestions() {
        CommentSuggestionsRepository.getCommentSuggestions(suggestionsResponse, null);
    }


    @Override
    public List<SensorConfig> transformResponse(ListResponse<SensorConfig> response) {
        return response.getResultList();
    }

    @Override
    public void onItemSelected(int position, NavController navController, Context c) {

    }

    public void onQuickCommentSelected(int position) {
        if (quickComments.getValue() != null && quickComments.getValue().size() > position)
            saveCommentValue(quickComments.getValue().get(position), false);
    }

    @Override
    public void setConstructorParameters(Object... args) {

    }

    @Override
    public void callRepositoryForData() {
        //El origen de datos no viene del repositorio
    }


    public void initObservers(LifecycleOwner owner, Context context) {
        //super.initObservers(owner);
        suggestionsResponse.observe(owner, response -> {
            if (response != null && response.getResultList() != null) {
                suggestions.setValue(response.getResultList());
            }
        });
        experimentPrepared.observe(owner, updateCount -> {
            if (updateCount != null) {
                isLoading.setValue(false);
                this.startTimeStamp = System.currentTimeMillis();
                changeStateText.setValue(getApplication().getString(R.string.perform_experiment_finish_text));

                saveExperiment(context);
                initImageCapture(context);
                initAudioRecording(context);
                initSensorRecording(context);
                initLocationRecording(context);
                initCommentSuggestions();
                initRemoteSyncingProcess(context);
                experimentPrepared.postValue(null);

            }
        });

        experimentFinished.observe(owner, updateCount -> {
            if (updateCount != null) {
                orchestratorProvider.finishPendingJobs();
                Executor executor = Executors.newSingleThreadExecutor();
                executor.execute(() -> {
                    try {
                        TimeUnit.SECONDS.sleep(2);
                    } catch (InterruptedException e) {
                        Log.e("Error en sleep", e.getMessage(), e);
                    }
                    int numImagesFromDB = ImageRepository.countRegistersByExperimentId(experiment.getInternalId());
                int numEmbeddingsFromDB = ImageRepository.countImagesWithEmbeddingsByExperimentId(experiment.getInternalId());
                int numSensorsFromDB = SensorRepository.countRegistersWithoutLocationByExperimentId(experiment.getInternalId());
                int numLocationsFromDB = SensorRepository.countRegistersByExperimentId(experiment.getInternalId()) - numSensorsFromDB;

                numImages.postValue(numImagesFromDB);
                numEmbeddings.postValue(numEmbeddingsFromDB);
                numSensors.postValue(numSensorsFromDB);
                numLocations.postValue(numLocationsFromDB);

                numAudios.postValue(AudioRepository.countRegistersByExperimentId(experiment.getInternalId()));
                numComments.postValue(CommentRepository.countRegistersByExperimentId(experiment.getInternalId()));
                isLoading.postValue(false);
                experimentCountedOnFinish.postValue(true);

                 });


            }
        });

        experimentCountedOnFinish.observe(owner, countFinished ->{
            if(countFinished != null && countFinished) {
                saveExperiment(context);
                showResumeDialog(context);
            }
        });
    }

    public void onMessageEvent(WorkFinishedEvent event) {
        //int typeStringRes = orchestratorProvider.getRemoteStateTranslatableStringResFromWorkTags(event.getTags());
        this.updateWorkCounter(event.isSuccessful(), event.getTags(), event.getNumRegisters(), event.getException());

    }

    private void updateWorkCounter(boolean successWork, Set<String> tags, int numRegisters, BaseException exception) {
        if (tags.contains(REMOTE_SYNC_WORK)) {
            if (tags.contains(REMOTE_SYNC_EXPERIMENT)) {
                Executor executor = Executors.newSingleThreadExecutor();
                executor.execute(() -> {
                    Experiment experimentUpdated = ExperimentsRepository.getExperimentById(experiment.getInternalId());
                    experiment.setExternalId(experimentUpdated.getExternalId());
                });
            }

        } else {
            if (tags.contains(REGISTER_AUDIO)) {
                numAudios.postValue(numAudios.getValue() + 1);
                audioCardExtraInfo.postValue(getApplication().getString(R.string.experiment_perform_num_audios) + " " + numAudios.getValue());
            } else if (tags.contains(REGISTER_IMAGE)) {
                numImages.postValue(numImages.getValue() + 1);
                generateImageExtraInfo();
            } else if (tags.contains(OBTAIN_EMBEDDED_IMAGE)) {
                numEmbeddings.postValue(numEmbeddings.getValue() + 1);
                generateImageExtraInfo();
            } else if (tags.contains(REGISTER_SENSOR)) {
                numSensors.postValue(numSensors.getValue() + 1);
                sensorCardExtraInfo.postValue(getApplication().getString(R.string.experiment_perform_num_sensors) + " " + numSensors.getValue());

            } else if (tags.contains(REGISTER_LOCATION)) {
                numLocations.postValue(numLocations.getValue() + 1);
                locationCardExtraInfo.postValue(getApplication().getString(R.string.experiment_perform_num_locations) + " " + numLocations.getValue());
            }
        }

    }


}