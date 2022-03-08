package com.jsm.exptool.ui.experiments.perform;

import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.OBTAIN_EMBEDDED_IMAGE;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.REGISTER_AUDIO;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.REGISTER_IMAGE;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.REGISTER_SENSOR;

import android.app.Application;
import android.content.Context;
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
import com.jsm.exptool.config.ConfigConstants;
import com.jsm.exptool.core.data.repositories.responses.ListResponse;
import com.jsm.exptool.core.exceptions.BaseException;
import com.jsm.exptool.core.ui.base.BaseFragment;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerViewModel;
import com.jsm.exptool.core.ui.loading.LoadingViewModel;
import com.jsm.exptool.core.utils.ModalMessage;
import com.jsm.exptool.libs.DeviceUtils;
import com.jsm.exptool.model.MySensor;
import com.jsm.exptool.model.experimentconfig.AudioConfig;
import com.jsm.exptool.model.experimentconfig.SensorsConfig;
import com.jsm.exptool.providers.AudioProvider;
import com.jsm.exptool.providers.CameraProvider;
import com.jsm.exptool.libs.camera.ImageReceivedCallback;
import com.jsm.exptool.model.experimentconfig.CameraConfig;
import com.jsm.exptool.model.Experiment;
import com.jsm.exptool.libs.requestpermissions.PermissionsResultCallBack;
import com.jsm.exptool.providers.RequestPermissionsProvider;
import com.jsm.exptool.providers.WorksOrchestratorProvider;
import com.jsm.exptool.repositories.ExperimentsRepository;
import com.jsm.exptool.libs.requestpermissions.RequestPermissionsInterface;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.SortedMap;
import java.util.Timer;
import java.util.TimerTask;

public class ExperimentPerformViewModel extends BaseRecyclerViewModel<MySensor, MySensor> {

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

    private final MutableLiveData<Integer> numAudios = new MutableLiveData<>(0);
    private final MutableLiveData<Boolean> audioCardEnabled = new MutableLiveData<>(false);

    private final MutableLiveData<Integer> numSensors = new MutableLiveData<>(0);
    private final MutableLiveData<Boolean> sensorCardEnabled = new MutableLiveData<>(false);

    private final MutableLiveData<Integer> updateElementInRecycler = new MutableLiveData<>();





    public ExperimentPerformViewModel(@NonNull Application application, Experiment experiment) {
        super(application);
        this.experiment = experiment;
        orchestratorProvider = WorksOrchestratorProvider.getInstance();
        orchestratorProvider.init(getApplication());
        initImageComponents();
        initAudioComponents();
        initSensorComponents();
        changeStateText.setValue(application.getString(R.string.perform_experiment_init_text));
        if(this.experiment.getConfiguration().isSensorEnabled()){
            apiResponseRepositoryHolder.setValue(new ListResponse<>(experiment.getConfiguration().getSensorConfig().getSensors()));
        }
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

    public MutableLiveData<String> getChangeStateText() {
        return changeStateText;
    }

    public MutableLiveData<Boolean> getEnableHandleExperimentButton() {
        return enableHandleExperimentButton;
    }

    public MutableLiveData<Integer> getUpdateElementInRecycler() {
        return updateElementInRecycler;
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

        if (saveExperiment(context)) {
            initImageCapture(context);
            initAudioRecording(context);
            initSensorRecording(context);
        } else {
            //TODO Mensaje de error con reintento
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
        if (saveExperiment(context)) {
            showResumeDialog(context);
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
        }
    }

    public void initAudioProvider(Context context, LifecycleOwner owner, RequestPermissionsInterface audioPermission) {
        if (experiment.getConfiguration().isCameraEnabled()) {
            if (RequestPermissionsProvider.handleCheckPermissionsForCamera(context, audioPermission))
                return;
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
                    File mFile = new File(context.getExternalFilesDir(null), date + "audio." + audioConfig.getRecordingOption().getFileExtension());
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

    private void initSensorRecording(Context context) {
        //TODO Quitar listeners de sensores al finalizar experimento
        if (experiment.getConfiguration().isSensorEnabled() && experiment.getConfiguration().getSensorConfig() != null
                && experiment.getConfiguration().getSensorConfig().getSensors() != null) {
            SensorsConfig sensorConfig = experiment.getConfiguration().getSensorConfig();
            Timer sensorTimer = new Timer();
            timers.add(sensorTimer);

                for (int i=0; i<sensorConfig.getSensors().size(); i++){
                final int position = i;
                MySensor sensor = sensorConfig.getSensors().get(position);
                sensor.initListener();
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

    private void initImageComponents() {
        CameraConfig cameraConfig = experiment.getConfiguration().getCameraConfig();
        imageCardEnabled.setValue(experiment.getConfiguration().isCameraEnabled());
        embeddedInfoEnabled.setValue(experiment.getConfiguration().isCameraEnabled() && cameraConfig.getEmbeddingAlgorithm() != null);

    }

    private void initAudioComponents() {

        audioCardEnabled.setValue(experiment.getConfiguration().isAudioEnabled());

    }

    private void initSensorComponents() {

        sensorCardEnabled.setValue(experiment.getConfiguration().isSensorEnabled());

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

        //AUDIO
        LiveData<List<WorkInfo>> registerAudioWorkInfo = orchestratorProvider.getWorkInfoByTag(REGISTER_AUDIO);
        registerAudioWorkInfo.observe(owner, workInfoList -> {
            numAudios.setValue(orchestratorProvider.countSuccessWorks(workInfoList));
        });

        LiveData<List<WorkInfo>> registerSensorWorkInfo = orchestratorProvider.getWorkInfoByTag(REGISTER_SENSOR);
        registerSensorWorkInfo.observe(owner, workInfoList -> {
            numSensors.setValue(orchestratorProvider.countSuccessWorks(workInfoList));
        });

    }

    public void showResumeDialog(Context context) {
        StringBuilder builder = new StringBuilder();
        if (experiment.getConfiguration().isCameraEnabled()) {
            builder.append(Html.fromHtml(String.format(context.getString(R.string.experiment_perform_resume_dialog_num_images), numImages.getValue())));
            if (experiment.getConfiguration().getCameraConfig().getEmbeddingAlgorithm() != null) {
                builder.append(Html.fromHtml(String.format(context.getString(R.string.experiment_perform_resume_dialog_num_embedded), numEmbeddings.getValue())));
            }
        }

        ModalMessage.showModalMessage(context, "Resumen del experimento", builder.toString(), null, (dialog, which) -> {
            //TODO Navegar a listado experimentos
        }, null, null);
    }

    public void handleRequestPermissions(BaseFragment fragment) {

        if (experiment.getConfiguration().isCameraEnabled())
            RequestPermissionsProvider.requestPermissionsForCamera(((ExperimentPerformFragment)fragment).cameraRequestPermissions);
        if (experiment.getConfiguration().isAudioEnabled())
            RequestPermissionsProvider.requestPermissionsForAudioRecording(((ExperimentPerformFragment)fragment).cameraRequestPermissions);

    }

    public void onCameraPermissionsAccepted(Fragment fragment) {
        if (experiment.getConfiguration().isCameraEnabled())
            initCameraProvider(fragment.getContext(), fragment.getViewLifecycleOwner(), (RequestPermissionsInterface) fragment, fragment.getView().findViewById(R.id.cameraPreview));

    }

    public void onAudioPermissionsAccepted(Fragment fragment) {

        if(experiment.getConfiguration().isAudioEnabled())
            initAudioProvider(fragment.getContext(), fragment.getViewLifecycleOwner(), (RequestPermissionsInterface) fragment);
    }

    public void onPermissionsError(List<String> rejectedPermissions, Fragment fragment) {
        //TODO Mejorar error, desconectar camara e informar
        handleError(new BaseException("Error en permisos", false), fragment.getContext());
    }


    @Override
    public List<MySensor> transformResponse(ListResponse<MySensor> response) {
        return response.getResultList();
    }

    @Override
    public void onItemSelected(int position, NavController navController, Context c) {

    }

    @Override
    public void setConstructorParameters(Object... args) {

    }

    @Override
    public void callRepositoryForData() {
       //El origen de datos no viene del repositorio
    }
}