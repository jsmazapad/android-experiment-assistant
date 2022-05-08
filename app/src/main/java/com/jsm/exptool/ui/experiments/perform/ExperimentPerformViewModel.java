package com.jsm.exptool.ui.experiments.perform;

import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.OBTAIN_EMBEDDED_IMAGE;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.PERFORM_EXPERIMENT;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.REGISTER_AUDIO;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.REGISTER_IMAGE;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.REGISTER_LOCATION;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.REGISTER_SENSOR;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.REMOTE_WORK;

import android.app.Application;
import android.content.Context;
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
import com.jsm.exptool.core.data.repositories.responses.ListResponse;
import com.jsm.exptool.core.exceptions.BaseException;
import com.jsm.exptool.core.ui.base.BaseActivity;
import com.jsm.exptool.core.ui.base.BaseFragment;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerViewModel;
import com.jsm.exptool.core.utils.MemoryUtils;
import com.jsm.exptool.core.utils.ModalMessage;
import com.jsm.exptool.libs.DeviceUtils;
import com.jsm.exptool.model.CommentSuggestion;
import com.jsm.exptool.model.SensorConfig;
import com.jsm.exptool.model.experimentconfig.AudioConfig;
import com.jsm.exptool.model.experimentconfig.LocationConfig;
import com.jsm.exptool.model.experimentconfig.SensorsGlobalConfig;
import com.jsm.exptool.model.register.CommentRegister;
import com.jsm.exptool.providers.AudioProvider;
import com.jsm.exptool.providers.CameraProvider;
import com.jsm.exptool.libs.camera.ImageReceivedCallback;
import com.jsm.exptool.model.experimentconfig.CameraConfig;
import com.jsm.exptool.model.Experiment;
import com.jsm.exptool.providers.FilePathsProvider;
import com.jsm.exptool.providers.LocationProvider;
import com.jsm.exptool.providers.RequestPermissionsProvider;
import com.jsm.exptool.providers.WorksOrchestratorProvider;
import com.jsm.exptool.repositories.CommentRepository;
import com.jsm.exptool.repositories.CommentSuggestionsRepository;
import com.jsm.exptool.repositories.ExperimentsRepository;
import com.jsm.exptool.libs.requestpermissions.RequestPermissionsInterface;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ExperimentPerformViewModel extends BaseRecyclerViewModel<SensorConfig, SensorConfig> {

    private final Experiment experiment;
    private final WorksOrchestratorProvider orchestratorProvider;
    private List<Timer> timers = new ArrayList<>();
    private MutableLiveData<Boolean> experimentInitiated = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> enableHandleExperimentButton = new MutableLiveData<>(true);
    private final MutableLiveData<String> changeStateText = new MutableLiveData<>("");
    //Temporalización de experimento
    private long startTimeStamp = 0;

    //Gestión de espera a que terminen los trabajos en background para finalización de experimento
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
    //UBICACIÓN
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


    public ExperimentPerformViewModel(@NonNull Application application, Experiment experiment) {
        super(application);
        this.experiment = experiment;
        this.quickComments.setValue(experiment.getConfiguration() != null ? experiment.getConfiguration().getQuickComments() : null);
        orchestratorProvider = WorksOrchestratorProvider.getInstance();
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
        this.experiment.setInitDate(new Date());
        this.experiment.setSdkDevice(DeviceUtils.getDeviceSDK());
        this.experiment.setDevice(DeviceUtils.getDeviceModel());
        this.experiment.setStatus(Experiment.ExperimentStatus.INITIATED);

        this.startTimeStamp = System.currentTimeMillis();
        changeStateText.setValue(getApplication().getString(R.string.perform_experiment_finish_text));

        if (saveExperiment(context)) {
            initImageCapture(context);
            initAudioRecording(context);
            initSensorRecording(context);
            initLocationRecording(context);
            initCommentSuggestions();
        } else {
            //TODO Mensaje de error con reintento
        }
    }

    private void finishExperiment(Context context) {

        //Deshabilitamos botón
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
        //Paramos ubicación
        if (experiment.getConfiguration().isLocationEnabled())
            LocationProvider.getInstance().stopLocation();
        //TODO Extraer comportamiento a provider
        this.experiment.setEndDate(new Date());
        this.experiment.setStatus(Experiment.ExperimentStatus.FINISHED);

        long elapsedTime = System.currentTimeMillis() - startTimeStamp;
        this.experiment.setDuration(this.experiment.getDuration() + elapsedTime);

        //Si quedan experimentos pendientes mostramos mensaje
        if(orchestratorProvider.getWorkInfoByTag(PERFORM_EXPERIMENT).getValue() != null && orchestratorProvider.countPendingWorks(orchestratorProvider.getWorkInfoByTag(PERFORM_EXPERIMENT).getValue()) > 0){
            ModalMessage.showModalMessageWithThreeButtons(context, context.getString(R.string.pending_jobs_dialog_title), context.getString(R.string.pending_jobs_dialog_text),
                    context.getString(R.string.pending_jobs_dialog_option_wait), (dialog, which)->{
                waitPendingJobsAndFinish(context, false);
            }, context.getString(R.string.pending_jobs_dialog_option_end), (dialog, which)->{
                //Finalizamos trabajos y salvamos el experimento
                orchestratorProvider.finishPendingJobs();
                completeExperimentFinishing(context);
            }, context.getString(R.string.pending_jobs_dialog_option_wait_only_local), ((dialog, which) -> {
                //Volvemos a comprobar (los trabajos pueden haber finalizado mientras el usuario se decide)
                if (orchestratorProvider.getWorkInfoByTag(PERFORM_EXPERIMENT).getValue() != null && orchestratorProvider.countPendingWorks(orchestratorProvider.getWorkInfoByTag(PERFORM_EXPERIMENT).getValue()) > 0) {
                   waitPendingJobsAndFinish(context, true);
                }else{
                    completeExperimentFinishing(context);
                }
            }));
        }else{
            orchestratorProvider.finishPendingJobs();
            completeExperimentFinishing(context);
        }
    }

    /**
     * Espera a que terminen los trabajos pendientes
     * @param context
     * @param finishRemote Espera sólo a los trabajos locales, elimina los que dependan de servidores remotos
     */
    private void waitPendingJobsAndFinish(Context context, boolean finishRemote){
        //Volvemos a comprobar (los trabajos pueden haber finalizado antes de llegar aquí)
        if (orchestratorProvider.getWorkInfoByTag(PERFORM_EXPERIMENT).getValue() != null && orchestratorProvider.countPendingWorks(orchestratorProvider.getWorkInfoByTag(PERFORM_EXPERIMENT).getValue()) > 0) {
            //Colocamos el flag para esperar a que los trabajos finalicen
            ExperimentPerformViewModel.this.waitToFinishJobs = true;
            if(finishRemote){
                //Finalizamos los trabajos que dependen de servidor remoto
                orchestratorProvider.finishJobsByTag(REMOTE_WORK);
            }
        }else{
            completeExperimentFinishing(context);
        }
    }

    protected void completeExperimentFinishing(Context context){
        if(waitToFinishJobs){
            waitEndedToCompleteExperiment.setValue(false);
        }
        String size = MemoryUtils.getFormattedFileSize(FilePathsProvider.getExperimentFilePath(context, experiment.getInternalId()));
        this.experiment.setSize(size);
        if (saveExperiment(context)) {
            showResumeDialog(context);
        }
    }

    /**
     * Función que se ejecuta cuando el contador de trabajos pendientes llega a 0
     */
    private void onFinishedBackgroundJobs(){
        //Si tiene el flag de espera activado, actualizamos el LiveData para finalizar los trabajos
        if(waitToFinishJobs){
            waitEndedToCompleteExperiment.setValue(true);
        }

    }

    private boolean saveExperiment(Context context) {
        boolean operationSuccess = false;
        int updatedRows = ExperimentsRepository.updateExperiment(this.experiment);
        if (updatedRows < 1) {
            errorHandler.handleError(new BaseException(getApplication().getString(R.string.default_error_database_register_entity), true), context, () -> {
                saveExperiment(context);
            }, this);
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

            audioTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {

                    Log.d("WORKER", "Audio requested");
                    Date date = new Date();
                    File mFile = new File(FilePathsProvider.getFilePathForExperimentItem(context, experiment.getInternalId(), FilePathsProvider.PathTypes.AUDIO), FilePathsProvider.formatFileName(date + "." + audioConfig.getRecordingOption().getFileExtension()));
                    AudioProvider.getInstance().record(mFile, audioConfig.getRecordingOption());
                    //Tras un delay = duracion de la grabación, se ejecuta el timer para la grbación
                    audioTimer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            Log.e("AUDIOWORKER", "audiostop");
                            AudioProvider.getInstance().stopRecording();
                            orchestratorProvider.executeAudioChain(getApplication(), mFile, date, experiment);

                        }
                    }, audioConfig.getRecordingDuration());
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
                    if(location != null) {
                        locationValueString.postValue(String.format(formatString, location.getLatitude(), location.getLongitude(), location.getAltitude(), location.getAccuracy()));
                        orchestratorProvider.executeLocationChain(getApplication(), location, date, experiment);
                    }else{
                        Log.e("Experiment perform", "Error al obtener ubicación");
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

            if(orchestratorProvider.countPendingWorks(workInfoList) == 0 || (experiment.getStatus().equals(Experiment.ExperimentStatus.FINISHED) && workInfoList == null)){
                ExperimentPerformViewModel.this.onFinishedBackgroundJobs();
            }

        });
        //Image
        LiveData<List<WorkInfo>> registerImageWorkInfo = orchestratorProvider.getWorkInfoByTag(REGISTER_IMAGE);
        LiveData<List<WorkInfo>> embeddingImageWorkInfo = orchestratorProvider.getWorkInfoByTag(OBTAIN_EMBEDDED_IMAGE);
        registerImageWorkInfo.observe(owner, workInfoList -> {
            numImages.setValue(orchestratorProvider.countSuccessWorks(workInfoList));
            generateImageExtraInfo();
        });
        embeddingImageWorkInfo.observe(owner, workInfoList -> {
            numEmbeddings.setValue(orchestratorProvider.countSuccessWorks(workInfoList));
            generateImageExtraInfo();
        });

        //AUDIO
        LiveData<List<WorkInfo>> registerAudioWorkInfo = orchestratorProvider.getWorkInfoByTag(REGISTER_AUDIO);
        registerAudioWorkInfo.observe(owner, workInfoList -> {
            numAudios.setValue(orchestratorProvider.countSuccessWorks(workInfoList));
            audioCardExtraInfo.setValue(getApplication().getString(R.string.experiment_perform_num_audios) + " " + numAudios.getValue());

        });

        LiveData<List<WorkInfo>> registerSensorWorkInfo = orchestratorProvider.getWorkInfoByTag(REGISTER_SENSOR);
        registerSensorWorkInfo.observe(owner, workInfoList -> {
            numSensors.setValue(orchestratorProvider.countSuccessWorks(workInfoList));
            sensorCardExtraInfo.setValue(getApplication().getString(R.string.experiment_perform_num_sensors) + " " + numSensors.getValue());


        });

        LiveData<List<WorkInfo>> registerLocationWorkInfo = orchestratorProvider.getWorkInfoByTag(REGISTER_LOCATION);
        registerLocationWorkInfo.observe(owner, workInfoList -> {
            numLocations.setValue(orchestratorProvider.countSuccessWorks(workInfoList));
            locationCardExtraInfo.setValue(getApplication().getString(R.string.experiment_perform_num_locations) + " " + numSensors.getValue());
        });

    }

    private void generateImageExtraInfo() {
        String extraInfo = getApplication().getString(R.string.experiment_perform_images_number) + " " + numImages.getValue() + "\n" +
                getApplication().getString(R.string.experiment_perform_images_number) + " " + numImages.getValue();
        imageCardExtraInfo.setValue(extraInfo);

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

        if (experiment.getConfiguration().isLocationEnabled())
            builder.append(Html.fromHtml(String.format(context.getString(R.string.experiment_perform_resume_dialog_num_sensors), numSensors.getValue())));

        if (experiment.getConfiguration().isSensorEnabled())
            builder.append(Html.fromHtml(String.format(context.getString(R.string.experiment_perform_resume_dialog_num_locations), numLocations.getValue())));

        builder.append(Html.fromHtml(String.format(context.getString(R.string.experiment_perform_resume_dialog_num_comments), numComments.getValue())));

        ModalMessage.showModalMessage(context, "Resumen del experimento", builder.toString(), null, (dialog, which) -> {
            ((BaseActivity) context).getNavController().navigate(ExperimentPerformFragmentDirections.actionNavPerformExperimentToNavExperiments());
        }, null, null);
    }

    public void handleRequestPermissions(BaseFragment fragment) {

        if (experiment.getConfiguration().isCameraEnabled())
            RequestPermissionsProvider.requestPermissionsForCamera(((ExperimentPerformFragment) fragment).cameraRequestPermissions);
        if (experiment.getConfiguration().isAudioEnabled())
            RequestPermissionsProvider.requestPermissionsForAudioRecording(((ExperimentPerformFragment) fragment).audioRequestPermissions);
        if (experiment.getConfiguration().isLocationEnabled())
            RequestPermissionsProvider.requestPermissionsForLocationFine(((ExperimentPerformFragment) fragment).locationRequestPermissions);

    }

    public void onCameraPermissionsAccepted(Fragment fragment) {
        if (experiment.getConfiguration().isCameraEnabled())
            initCameraProvider(fragment.getContext(), fragment.getViewLifecycleOwner(), (RequestPermissionsInterface) fragment, fragment.getView().findViewById(R.id.cameraPreview));

    }

    public void onAudioPermissionsAccepted(Fragment fragment) {

        if (experiment.getConfiguration().isAudioEnabled())
            initAudioProvider(fragment.getContext(), fragment.getViewLifecycleOwner(), (RequestPermissionsInterface) fragment);
    }

    public void onLocationPermissionsAccepted(Fragment fragment) {

        if (experiment.getConfiguration().isLocationEnabled())
            initLocationProvider(fragment.getContext(), fragment.getViewLifecycleOwner(), (RequestPermissionsInterface) fragment);
    }

    public void onPermissionsError(List<String> rejectedPermissions, Fragment fragment) {
        //TODO Mejorar error, desconectar camara e informar
        handleError(new BaseException("Error en permisos", false), fragment.getContext());
    }

    public void saveCommentValue(String comment, boolean fromRegisterComment){
        if (experimentInitiated.getValue() && CommentRepository.registerComment(new CommentRegister(experiment.getInternalId(), new Date(), false, comment)) > 0) {
            numComments.setValue(numComments.getValue() + 1);
            commentCardExtraInfo.setValue(getApplication().getString(R.string.experiment_perform_num_comments) + " " + numComments.getValue());
            CommentSuggestion relatedComment = CommentSuggestionsRepository.selectCommentSuggestionByComment(comment);
            //Comprobamos que no venga de haber solicitado el registro del comentario para no sumarlo dos veces
            //Si existe el comentario se añade uno a la cuenta del número de veces empleado
            if(!fromRegisterComment && relatedComment != null){
                relatedComment.setUsedTimesCounter(relatedComment.getUsedTimesCounter()+1);
                CommentSuggestionsRepository.updateCommentSuggestion(relatedComment);
            }


        }
    }

    public void saveCommentValue(boolean fromRegisterComment) {

        String comment = commentValue.getValue();
        if(comment != null && !"".equals(comment)) {
            saveCommentValue(commentValue.getValue(), fromRegisterComment);
            commentValue.setValue("");
        }

    }

    public void saveCommentAndAddToSuggestions() {
        String comment = commentValue.getValue();
        if(comment != null && !"".equals(comment)) {
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
        if( quickComments.getValue() != null && quickComments.getValue().size() > position)
        saveCommentValue(quickComments.getValue().get(position), false);
    }

    @Override
    public void setConstructorParameters(Object... args) {

    }

    @Override
    public void callRepositoryForData() {
        //El origen de datos no viene del repositorio
    }

    @Override
    public void initObservers(LifecycleOwner owner) {
        super.initObservers(owner);
        suggestionsResponse.observe(owner, response -> {
            if (response != null && response.getResultList() != null) {
                suggestions.setValue(response.getResultList());
            }
        });
    }
}