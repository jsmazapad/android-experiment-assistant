package com.jsm.exptool.ui.experiments.list.actions;

import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.FILE_NAME;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.EXPORT_REGISTERS;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.ZIP_EXPORTED;

import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ShareCompat;
import androidx.core.content.FileProvider;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.work.Data;
import androidx.work.WorkInfo;

import com.jsm.exptool.R;
import com.jsm.exptool.core.exceptions.BaseException;
import com.jsm.exptool.core.ui.base.BaseActivity;
import com.jsm.exptool.core.ui.loading.LoadingViewModel;
import com.jsm.exptool.core.libs.ModalMessage;
import com.jsm.exptool.entities.Experiment;
import com.jsm.exptool.entities.experimentconfig.SensorConfig;
import com.jsm.exptool.entities.eventbus.ExperimentListRefreshEvent;
import com.jsm.exptool.entities.experimentconfig.ExperimentConfiguration;
import com.jsm.exptool.providers.ExperimentActionsInterface;
import com.jsm.exptool.providers.ExperimentProvider;
import com.jsm.exptool.providers.TimeDisplayStringProvider;
import com.jsm.exptool.providers.worksorchestrator.WorksOrchestratorProvider;
import com.jsm.exptool.data.repositories.AudioRepository;
import com.jsm.exptool.data.repositories.CommentRepository;
import com.jsm.exptool.data.repositories.ImageRepository;
import com.jsm.exptool.data.repositories.SensorRepository;
import com.jsm.exptool.ui.experiments.list.ExperimentsListFragmentDirections;
import com.jsm.exptool.ui.main.MainActivity;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.List;

public class ExperimentListActionsDialogViewModel extends LoadingViewModel implements ExperimentActionsInterface {

    private final Experiment experiment;
    private final WorksOrchestratorProvider orchestratorProvider = WorksOrchestratorProvider.getInstance();
    private boolean needToRefreshList = false;

    public ExperimentListActionsDialogViewModel(Application app, Experiment experiment) {
        super(app);
        this.experiment = experiment;
        this.initViewVars();
    }
    private final MutableLiveData<Integer> sensorCount = new MutableLiveData<>(0);
    private final MutableLiveData<String> sensorCountValue = new MutableLiveData<>("");
    private final MutableLiveData<Integer> imageCount = new MutableLiveData<>(0);
    private final MutableLiveData<String> imageCountValue = new MutableLiveData<>("");
    private final MutableLiveData<Integer> embeddingCount = new MutableLiveData<>(0);
    private final MutableLiveData<String> embeddingCountValue = new MutableLiveData<>("");
    private final MutableLiveData<Integer> audioCount = new MutableLiveData<>(0);
    private final MutableLiveData<String> audioCountValue = new MutableLiveData<>("");
    //private MutableLiveData<Integer> locationCount = new MutableLiveData<>(0);
    //private MutableLiveData<String> locationValue = new MutableLiveData<>("");
    private final MutableLiveData<Integer> commentsCount = new MutableLiveData<>(0);
    private final MutableLiveData<String> commentsCountValue = new MutableLiveData<>("");

    private final MutableLiveData<Integer> endExperimentResponse = new MutableLiveData<>();

    private String sensorsEnabledText ="";
    private String imagesEnabledText = "";
    private String embeddingEnabledText = "";
    private String audioEnabledText = "";
    private String locationEnabledText = "";
    private String commentsEnabledText = "";

    private String sensorsListText = "";

    private String imagesConfigText = "";
    private String embeddingConfigText = "";
    private String audioConfigText = "";
    private String quickCommentsText = "";


    public MutableLiveData<String> getSensorCountValue() {
        return sensorCountValue;
    }

    public MutableLiveData<String> getImageCountValue() {
        return imageCountValue;
    }

    public MutableLiveData<String> getEmbeddingCountValue() {
        return embeddingCountValue;
    }

    public MutableLiveData<String> getAudioCountValue() {
        return audioCountValue;
    }

    public MutableLiveData<String> getCommentsCountValue() {
        return commentsCountValue;
    }

    public String getSensorsEnabledText() {
        return sensorsEnabledText;
    }

    public String getImagesEnabledText() {
        return imagesEnabledText;
    }

    public String getEmbeddingEnabledText() {
        return embeddingEnabledText;
    }

    public String getAudioEnabledText() {
        return audioEnabledText;
    }

    public String getLocationEnabledText() {
        return locationEnabledText;
    }

    public String getCommentsEnabledText() {
        return commentsEnabledText;
    }

    @Override
    public String getSensorsListText() {
        return sensorsListText;
    }

    @Override
    public String getImagesConfigText() {
        return imagesConfigText;
    }

    @Override
    public String getEmbeddingConfigText() {
        return embeddingConfigText;
    }

    @Override
    public String getAudioConfigText() {
        return audioConfigText;
    }

    @Override
    public String getQuickCommentsText() {
        return quickCommentsText;
    }

    @Override
    public void initExperiment(Context context, Experiment experiment, Dialog alertDialog) {
        alertDialog.cancel();
        ((BaseActivity) context).getNavController().navigate(ExperimentsListFragmentDirections.actionNavExperimentsToNavPerformExperiment(experiment));
    }

    @Override
    public void viewExperimentData(Context context, Experiment experiment, Dialog alertDialog) {
        alertDialog.cancel();
        ((BaseActivity) context).getNavController().navigate(ExperimentsListFragmentDirections.actionNavExperimentsToNavViewExperiment(experiment));

    }

    @Override
    public void exportExperiment(Context context, Experiment experiment, Dialog alertDialog) {
        isLoading.setValue(true);
        orchestratorProvider.executeExportToCSV(context, experiment);
    }

    @Override
    public void syncExperiment(Context context, Experiment experiment, Dialog alertDialog) {
        alertDialog.cancel();
        ((BaseActivity) context).getNavController().navigate(ExperimentsListFragmentDirections.actionNavExperimentsToNavSyncExperiment(experiment));

    }

    @Override
    public void endExperiment(Context context, Experiment experiment, Dialog alertDialog) {
        ExperimentProvider.endExperiment(experiment, context, endExperimentResponse, Experiment.ExperimentStatus.FINISHED, false);
    }

    @Override
    public void continueExperiment(Context context, Experiment experiment, Dialog alertDialog) {
        //TODO Calcular duración basada en registros
        //TODO, al ampliar el experimento, no pisar la fecha de inicio del mismo
        ((MainActivity) context).getNavController().navigate(ExperimentsListFragmentDirections.actionNavExperimentsToNavPerformExperiment(experiment));
        alertDialog.cancel();

    }


    @Override
    public void deleteExperiment(Context context, Experiment experiment, Dialog alertDialog) {
        ModalMessage.showModalMessage(context, context.getString(R.string.warning_title), String.format(context.getString(R.string.warning_delete_experiment_format), experiment.getTitle()),
                null, (dialog, which) -> {
                    //TODO Realizar el borrado en segundo plano
                    String error = ExperimentProvider.deleteExperiment(getApplication(), experiment);
                    if (!"".equals(error)) {
                        ModalMessage.showError(context, error, null, null, null, null);
                    } else {
                        alertDialog.cancel();
                        EventBus.getDefault().post(new ExperimentListRefreshEvent(true));

                    }
                },
                null, ((dialog, which) -> {
                }));
    }

    @Override
    public void createExperimentByCopyingExperimentConfig(Context context, Experiment experiment, Dialog alertDialog) {
        Experiment experimentCopy = ExperimentProvider.createExperimentByCopyingExperimentConfiguration(experiment);
        ((MainActivity) context).getNavController().navigate(ExperimentsListFragmentDirections.actionNavExperimentsToNavExperimentCreate().setExperiment(experimentCopy));
        alertDialog.cancel();

    }


    public void initObservers(LifecycleOwner owner, Context context, AlertDialog dialog) {
        super.initObservers(owner);
        this.initOrchestratorObservers(owner, context);
        this.initExperimentActionsDialogObservers(owner, context, dialog);
    }

    private void initOrchestratorObservers(LifecycleOwner owner, Context context){
        LiveData<List<WorkInfo>> exportsWorkInfo = orchestratorProvider.getWorkInfoByTag(EXPORT_REGISTERS);
        exportsWorkInfo.observe(owner, workInfoList -> {
            if (orchestratorProvider.countFailureWorks(workInfoList) > 0) {
                isLoading.setValue(false);
                orchestratorProvider.finishJobsByTag(EXPORT_REGISTERS);
                error.setValue(new BaseException(getApplication().getString(R.string.export_error_text), false));
            }
        });

        LiveData<List<WorkInfo>> zipWorkInfo = orchestratorProvider.getWorkInfoByTag(ZIP_EXPORTED);
//        zipWorkInfo.observe(owner, workInfoList -> {
//            if (orchestratorProvider.countFailureWorks(workInfoList) > 0) {
//                isLoading.setValue(false);
//                if (!isShowingDialog()) {
//                    error.setValue(new BaseException(getApplication().getString(R.string.export_error_text), false));
//                }
//            } else if (orchestratorProvider.countSuccessWorks(workInfoList) > 0) {
//                isLoading.setValue(false);
//
//            }
//        });
        zipWorkInfo.observe(owner, workInfoList -> {
            if (orchestratorProvider.countFailureWorks(workInfoList) > 0) {
                isLoading.setValue(false);
                if (!isShowingDialog()) {
                    error.setValue(new BaseException(getApplication().getString(R.string.export_error_text), false));
                }
            } else if (orchestratorProvider.countSuccessWorks(workInfoList) > 0) {
                isLoading.setValue(false);
                Log.d("ZIPPEADO","Operación realizada con éxito");
                Data outputData = workInfoList.get(0).getOutputData();
                String filename = outputData.getString(FILE_NAME);
                shareZipped(context, filename);
            }
        });
    }

    private void initExperimentActionsDialogObservers(LifecycleOwner lifecycleOwner, Context context, AlertDialog dialog) {
        sensorCount.observe(lifecycleOwner, countValue -> {
            if (countValue != null) {
                sensorCountValue.setValue(String.format(getApplication().getString(R.string.num_registers_format), countValue));
            }
        });
        imageCount.observe(lifecycleOwner, countValue -> {
            if (countValue != null) {
                imageCountValue.setValue(String.format(getApplication().getString(R.string.num_registers_format), countValue));
            }
        });
        embeddingCount.observe(lifecycleOwner, countValue -> {
            if (countValue != null) {
                embeddingCountValue.setValue(String.format(getApplication().getString(R.string.num_registers_format), countValue));
            }
        });
        audioCount.observe(lifecycleOwner, countValue -> {
            if (countValue != null) {
                audioCountValue.setValue(String.format(getApplication().getString(R.string.num_registers_format), countValue));
            }
        });
//        locationCount.observe(lifecycleOwner, countValue -> {
//            if (countValue != null) {
//                locationValue.setValue(String.format(getApplication().getString(R.string.num_registers_format), countValue));
//            }
//        });
        commentsCount.observe(lifecycleOwner, countValue -> {
            if (countValue != null) {
                commentsCountValue.setValue(String.format(getApplication().getString(R.string.num_registers_format), countValue));
            }
        });

        endExperimentResponse.observe(lifecycleOwner, countValue -> {
            if (countValue != null) {
                if (countValue > 0) {
                    ModalMessage.showSuccessfulOperation(context, null);
                    EventBus.getDefault().post(new ExperimentListRefreshEvent(true));
                    dialog.cancel();

                } else {
                    ModalMessage.showFailureOperation(context, null);
                }
            }
        });

    }

    private void initViewVars(){
        ExperimentConfiguration configuration = experiment.getConfiguration();
        Context context = getApplication();
        if (configuration != null) {


            audioEnabledText = context.getString(experiment.getConfiguration().isAudioEnabled() ? R.string.yes_configured : R.string.no_configured);
            imagesEnabledText = context.getString(experiment.getConfiguration().isCameraEnabled() ? R.string.yes_configured : R.string.no_configured);
            embeddingEnabledText = context.getString(experiment.getConfiguration().isEmbeddingEnabled() ? R.string.yes_configured : R.string.no_configured);
            sensorsEnabledText = context.getString(experiment.getConfiguration().isSensorEnabled() || experiment.getConfiguration().isLocationEnabled() ? R.string.yes_configured : R.string.no_configured);

            sensorsListText = "";
            if(configuration.isSensorEnabled() || configuration.isLocationEnabled())
            {
                StringBuilder sensorListAsStringBuilder = new StringBuilder();
                //Si tiene ubicación, añadimos a la lista de sensores
                if(configuration.isLocationEnabled()){
                    sensorListAsStringBuilder.append(context.getString(configuration.getLocationConfig().getNameStringRes()))
                            .append(" (")
                            .append(context.getString(configuration.getLocationConfig().getLocationOption().getTitleTranslatableRes()))
                            .append("), ");
                }
                //Transformamos la lista de sensores en un string para mostrarlo en pantalla
                for (SensorConfig sensorConfig:experiment.getConfiguration().getSensorConfig().getSensors()) {
                    sensorListAsStringBuilder.append(context.getString(sensorConfig.getNameStringRes())).append(", ");
                }

                String sensorListAsString = sensorListAsStringBuilder.toString();
                if(sensorListAsString.length() > 1){
                    sensorListAsString = sensorListAsString.substring(0, sensorListAsString.length()-2);
                }
                sensorsListText = sensorListAsString;
            }
            imagesConfigText = "";
            if(configuration.isCameraEnabled()){
                StringBuilder imagesConfigBuilder = new StringBuilder();
                imagesConfigBuilder.append(context.getString(R.string.experiment_actions_camera_selected_prepend_text)).append(" ")
                        .append(configuration.getCameraConfig().getCameraPosition().name()).append(", ");
                imagesConfigBuilder.append(context.getString(R.string.experiment_actions_flash_mode_prepend_text)).append(" ")
                        .append(configuration.getCameraConfig().getFlashMode().name()).append(", ");
                imagesConfigBuilder.append(context.getString(R.string.experiment_actions_frequency_prepend_text)).append(" ")
                        .append(TimeDisplayStringProvider.millisecondsToStringBestDisplay(configuration.getCameraConfig().getInterval()));
                imagesConfigText = imagesConfigBuilder.toString();
            }
            embeddingConfigText = "";
            if(configuration.isEmbeddingEnabled()){
                StringBuilder embeddingConfigBuilder = new StringBuilder();
                embeddingConfigBuilder.append(context.getString(R.string.experiment_actions_algorithm_prepend_text)).append(" ")
                        .append(configuration.getCameraConfig().getEmbeddingAlgorithm().getDisplayName());
                embeddingConfigText = embeddingConfigBuilder.toString();
            }

            audioConfigText = "";
            if(configuration.isAudioEnabled()){
                StringBuilder audioConfigBuilder = new StringBuilder();
                audioConfigBuilder.append(context.getString(R.string.experiment_actions_encoder_prepend_text)).append(" ")
                        .append(configuration.getAudioConfig().getRecordingOption().getDisplayName()).append(", ");
                audioConfigBuilder.append(configuration.getAudioConfig().getRecordingOption().getSelectedEncodingBitRate())
                        .append(context.getString(R.string.experiment_actions_bitrate_append_text)).append(", ");
                audioConfigBuilder.append(context.getString(R.string.experiment_actions_frequency_prepend_text)).append(" ")
                        .append(TimeDisplayStringProvider.millisecondsToStringBestDisplay(configuration.getAudioConfig().getInterval())).append(", ");;
                audioConfigBuilder.append(context.getString(R.string.experiment_actions_duration_prepend_text)).append(" ")
                        .append(TimeDisplayStringProvider.millisecondsToStringBestDisplay(configuration.getAudioConfig().getInterval()));
                audioConfigText = audioConfigBuilder.toString();
            }

            if(experiment.getStatus().equals(Experiment.ExperimentStatus.INITIATED) || experiment.getStatus().equals(Experiment.ExperimentStatus.FINISHED)) {
                if (configuration.isSensorEnabled() || configuration.isLocationEnabled()) {
                    sensorCountValue.setValue(context.getString(R.string.data_loading_text));
                    SensorRepository.countRegistersByExperimentId(experiment.getInternalId(), sensorCount);

                } else {
                    sensorCountValue.setValue("");
                }
                if (configuration.isCameraEnabled()) {
                    imageCountValue.setValue(context.getString(R.string.data_loading_text));
                    ImageRepository.countRegistersByExperimentId(experiment.getInternalId(), imageCount);

                } else {
                    imageCountValue.setValue("");
                }

                if (configuration.isEmbeddingEnabled()) {
                    embeddingCountValue.setValue(context.getString(R.string.data_loading_text));
                    ImageRepository.countImagesWithEmbeddingsByExperimentId(experiment.getInternalId(), embeddingCount);
                } else {
                    embeddingCountValue.setValue("");
                }
                if (configuration.isAudioEnabled()) {
                    audioCountValue.setValue(context.getString(R.string.data_loading_text));
                    AudioRepository.countRegistersByExperimentId(experiment.getInternalId(), audioCount);

                } else {
                    audioCountValue.setValue("");
                }
            }else{
                sensorCountValue.setValue("");
                imageCountValue.setValue("");
                embeddingCountValue.setValue("");
                audioCountValue.setValue("");
            }
        }
        commentsEnabledText = context.getString(R.string.yes_configured);
        commentsCountValue.setValue(context.getString(R.string.data_loading_text));
        CommentRepository.countRegistersByExperimentId(experiment.getInternalId(), commentsCount);
        quickCommentsText = "";
        if(configuration.getQuickComments() != null && configuration.getQuickComments().size() > 0){
            StringBuilder quickCommentsTextBuilder = new StringBuilder();
            for (String quickComment: configuration.getQuickComments()) {
                quickCommentsTextBuilder.append(quickComment).append(", ");
            }
            String quickCommentListAsString = quickCommentsTextBuilder.toString();
            if(quickCommentListAsString.length() > 1){
                quickCommentListAsString = quickCommentListAsString.substring(0, quickCommentListAsString.length()-2);
            }
            quickCommentsText = String.format(context.getString(R.string.experiment_actions_quick_comments_format_text), quickCommentListAsString);
        }
    }

    public void shareZipped(Context context, String fileName) {
        Log.d("ZIPPEADO","Lanzando diálogo de compartir");
        //NEXTTODO refactorizar authority
        Uri path = FileProvider.getUriForFile(context, "com.jsm.exptool.fileprovider", new File(fileName));
        ShareCompat.IntentBuilder intentBuilder = new ShareCompat.IntentBuilder(context).setStream(path).setChooserTitle(R.string.export_experiment_share_chooser_title).setType("application/zip");
        intentBuilder.startChooser();
    }

    public void cancelPendingWorks(){
        orchestratorProvider.finishPendingJobs();
    }
}
