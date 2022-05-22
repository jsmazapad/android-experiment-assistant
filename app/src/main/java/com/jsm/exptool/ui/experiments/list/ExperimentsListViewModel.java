package com.jsm.exptool.ui.experiments.list;

import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.FILE_NAME;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.EXPORT_REGISTERS;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.ZIP_EXPORTED;

import androidx.appcompat.app.AlertDialog;

import android.app.Application;
import android.content.Context;
import android.net.Uri;

import androidx.core.app.ShareCompat;
import androidx.core.content.FileProvider;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavController;
import androidx.work.Data;
import androidx.work.WorkInfo;

import com.jsm.exptool.R;
import com.jsm.exptool.core.data.repositories.responses.ListResponse;
import com.jsm.exptool.core.exceptions.BaseException;
import com.jsm.exptool.core.ui.base.BaseActivity;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerViewModel;
import com.jsm.exptool.core.utils.ModalMessage;
import com.jsm.exptool.model.Experiment;
import com.jsm.exptool.model.SensorConfig;
import com.jsm.exptool.model.experimentconfig.ExperimentConfiguration;
import com.jsm.exptool.model.filters.FilterOption;
import com.jsm.exptool.providers.ExperimentActionsInterface;
import com.jsm.exptool.providers.ExperimentListFiltersProvider;
import com.jsm.exptool.providers.ExperimentProvider;
import com.jsm.exptool.providers.ExperimentResumeAndActionsDialogProvider;
import com.jsm.exptool.providers.TimeDisplayStringProvider;
import com.jsm.exptool.providers.WorksOrchestratorProvider;
import com.jsm.exptool.repositories.AudioRepository;
import com.jsm.exptool.repositories.CommentRepository;
import com.jsm.exptool.repositories.ExperimentsRepository;
import com.jsm.exptool.repositories.ImagesRepository;
import com.jsm.exptool.repositories.SensorsRepository;
import com.jsm.exptool.ui.experiments.create.audioconfiguration.BitrateSpinnerAdapter;
import com.jsm.exptool.ui.main.MainActivity;

import java.io.File;
import java.util.List;

//TODO Añadir total de Mb ocupado y restante a vista
public class ExperimentsListViewModel extends BaseRecyclerViewModel<Experiment, Experiment> implements ExperimentActionsInterface {


    private List<FilterOption<Experiment.ExperimentStatus, Boolean>> stateFilterOptions;
    private List<FilterOption<ExperimentListFiltersProvider.ConditionFilterOptions, Boolean>> conditionFilterOptions;
    private final MutableLiveData<String> filterResume = new MutableLiveData<>();
    private Experiment.ExperimentStatus statusFilterSelected;
    private FilterOption<ExperimentListFiltersProvider.ConditionFilterOptions, Boolean> conditionFilterSelected;
    private WorksOrchestratorProvider orchestratorProvider = WorksOrchestratorProvider.getInstance();
    private final MutableLiveData<String> zippedFilePath = new MutableLiveData<>();


    public ExperimentsListViewModel(Application app) {
        super(app);
        compoundFilterResumeText();
    }

    public List<FilterOption<Experiment.ExperimentStatus, Boolean>> getStateFilterOptions() {
        return stateFilterOptions;
    }

    public List<FilterOption<ExperimentListFiltersProvider.ConditionFilterOptions, Boolean>> getConditionFilterOptions() {
        return conditionFilterOptions;
    }

    public MutableLiveData<String> getFilterResume() {
        return filterResume;
    }

    public ExperimentListFiltersSpinnerAdapter<Experiment.ExperimentStatus, Boolean> getStatusFilterAdapter(Context context) {
        return new ExperimentListFiltersSpinnerAdapter<>(context, stateFilterOptions, android.R.layout.simple_spinner_dropdown_item);
    }
    public ExperimentListFiltersSpinnerAdapter<ExperimentListFiltersProvider.ConditionFilterOptions, Boolean> getConditionsFilterAdapter(Context context) {
        return new ExperimentListFiltersSpinnerAdapter<>(context, conditionFilterOptions, android.R.layout.simple_spinner_dropdown_item);
    }


    public MutableLiveData<String> getZippedFilePath() {
        return zippedFilePath;
    }

    @Override
    public List<Experiment> transformResponse(ListResponse<Experiment> response) {
        return response.getResultList();
    }

    @Override
    public void onItemSelected(int position, NavController navController, Context c) {
        //TODO Llamar a métodos de db en segundo plano para no sobrecargar el hilo principal
        //TODO asegurar que sólo se crea una instancia del elemento

        Experiment selectedExperiment = elements.getValue().get(position);

        openActionsDialog(c, selectedExperiment);


    }


    @Override
    public void setConstructorParameters(Object... args) {

        stateFilterOptions = ExperimentListFiltersProvider.getExperimentFilterStates();
        conditionFilterOptions = ExperimentListFiltersProvider.getSyncFilterStates();


    }

    @Override
    public void callRepositoryForData() {
        ExperimentListFiltersProvider.ConditionFilterOptions conditionFilterSelectedVar = conditionFilterSelected != null ? conditionFilterSelected.getFilterVar() : null;
        boolean conditionFilterSelectedValue = conditionFilterSelected != null ? conditionFilterSelected.getFilterValue() : false;
        ExperimentsRepository.getExperiments(apiResponseRepositoryHolder, statusFilterSelected, conditionFilterSelectedVar , conditionFilterSelectedValue);
    }

    public void onSelectedStateFilter(int position) {
        if (stateFilterOptions.size() < position)
            return;
        statusFilterSelected = stateFilterOptions.get(position).getFilterVar();
        compoundFilterResumeText();
        callRepositoryForData();

    }

    public void onSelectedConditionFilter(int position) {
        if (conditionFilterOptions.size() < position)
            return;
        conditionFilterSelected = conditionFilterOptions.get(position);
        compoundFilterResumeText();
        callRepositoryForData();

    }

    public void compoundFilterResumeText(){
        StringBuilder resumeBuilder = new StringBuilder();
        if(statusFilterSelected != null){
            resumeBuilder.append(getApplication().getString(R.string.status_filter_text)).append(": ");
            resumeBuilder.append(ExperimentProvider.getTranslatableStringFromExperimentStatus(statusFilterSelected, getApplication()));
        }

        if(statusFilterSelected != null && conditionFilterSelected.getFilterVar() != null)
        {
            resumeBuilder.append(", ");
        }

        if(conditionFilterSelected != null && conditionFilterSelected.getFilterVar() != null){
            resumeBuilder.append(getApplication().getString(R.string.condition_filter_text)).append(": ");
            resumeBuilder.append(getApplication().getString(conditionFilterSelected.getTitleTranslatableRes()));
        }

        if("".equals( resumeBuilder.toString())){
            resumeBuilder.append(getApplication().getString(R.string.no_filters));
        }

        filterResume.setValue(resumeBuilder.toString());
    }




    @Override
    public void initObservers(LifecycleOwner owner) {
        super.initObservers(owner);
        initExperimentActionsDialogObservers(owner);
        LiveData<List<WorkInfo>> exportsWorkInfo = orchestratorProvider.getWorkInfoByTag(EXPORT_REGISTERS);
        exportsWorkInfo.observe(owner, workInfoList -> {
            if (orchestratorProvider.countFailureWorks(workInfoList) > 0) {
                isLoading.setValue(false);
                orchestratorProvider.finishJobsByTag(EXPORT_REGISTERS);
                    error.setValue(new BaseException(getApplication().getString(R.string.export_error_text), false));
            }
        });

        LiveData<List<WorkInfo>> zipWorkInfo = orchestratorProvider.getWorkInfoByTag(ZIP_EXPORTED);
        zipWorkInfo.observe(owner, workInfoList -> {
            if (orchestratorProvider.countFailureWorks(workInfoList) > 0) {
                isLoading.setValue(false);
                if (!isShowingDialog()) {
                    error.setValue(new BaseException(getApplication().getString(R.string.export_error_text), false));
                }
            } else if (orchestratorProvider.countSuccessWorks(workInfoList) > 0) {
                isLoading.setValue(false);
                Data outputData = workInfoList.get(0).getOutputData();
                String filename = outputData.getString(FILE_NAME);
                zippedFilePath.setValue(filename);

            }
        });
    }

    @Override
    public void initExperiment(Context context, Experiment experiment, AlertDialog alertDialog) {
        alertDialog.cancel();
        ((BaseActivity) context).getNavController().navigate(ExperimentsListFragmentDirections.actionNavExperimentsToNavPerformExperiment(experiment));
    }

    @Override
    public void viewExperimentData(Context context, Experiment experiment, AlertDialog alertDialog) {
        alertDialog.cancel();
        ((BaseActivity) context).getNavController().navigate(ExperimentsListFragmentDirections.actionNavExperimentsToNavViewExperiment(experiment));

    }

    @Override
    public void exportExperiment(Context context, Experiment experiment, AlertDialog alertDialog) {
        //TODO, ver porque no muestra pantalla de carga
        isLoading.setValue(true);
        //orchestratorProvider.executeExportToCSV(context, experiment);
    }

    @Override
    public void syncExperiment(Context context, Experiment experiment, AlertDialog alertDialog) {

    }

    @Override
    public void endExperiment(Context context, Experiment experiment, AlertDialog alertDialog) {

        if (ExperimentProvider.endExperiment(experiment) > 0) {
            ModalMessage.showSuccessfulOperation(context, null);
            callRepositoryForData();
            alertDialog.cancel();

        } else {
            ModalMessage.showFailureOperation(context, null);
        }
    }

    @Override
    public void continueExperiment(Context context, Experiment experiment, AlertDialog alertDialog) {
        //TODO Calcular duración basada en registros
        //TODO, al ampliar el experimento, no pisar la fecha de inicio del mismo
        ((MainActivity) context).getNavController().navigate(ExperimentsListFragmentDirections.actionNavExperimentsToNavPerformExperiment(experiment));
        alertDialog.cancel();

    }


    @Override
    public void deleteExperiment(Context context, Experiment experiment, AlertDialog alertDialog) {
        ModalMessage.showModalMessage(context, context.getString(R.string.warning_title), String.format(context.getString(R.string.warning_delete_experiment_format), experiment.getTitle()),
                null, (dialog, which) -> {
                    //TODO Realizar el borrado en segundo plano
                    String error = ExperimentProvider.deleteExperiment(getApplication(), experiment);
                    if (!"".equals(error)) {
                        ModalMessage.showError(context, error, null, null, null, null);
                    } else {
                        alertDialog.cancel();
                        getRepositoryData();
                    }
                },
                null, ((dialog, which) -> {
                }));
    }

    @Override
    public void createExperimentByCopyingExperimentConfig(Context context, Experiment experiment, AlertDialog alertDialog) {
        Experiment experimentCopy = ExperimentProvider.createExperimentByCopyingExperimentConfiguration(experiment);
        ((MainActivity) context).getNavController().navigate(ExperimentsListFragmentDirections.actionNavExperimentsToNavExperimentCreate().setExperiment(experimentCopy));
        alertDialog.cancel();

    }

    public void shareZipped(Context context, String fileName) {
        //TODO refactorizar authority
        Uri path = FileProvider.getUriForFile(context, "com.jsm.exptool.fileprovider", new File(fileName));
        ShareCompat.IntentBuilder intentBuilder = new ShareCompat.IntentBuilder(context).setStream(path).setChooserTitle(R.string.export_experiment_share_chooser_title).setType("*/*");
        intentBuilder.startChooser();
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


    private void initExperimentActionsDialogObservers(LifecycleOwner lifecycleOwner) {
        sensorCount.observe(lifecycleOwner, countValue -> {
            if (countValue != null) {
                sensorCountValue.setValue(String.format(getApplication().getString(R.string.num_registers_format), countValue));
            }
        });
        //TODO Ver porque no carga en vista ni imágenes ni embedding
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

    }

    //TODO Extraer código a delegado
    private void openActionsDialog(Context context, Experiment experiment) {
        //TODO, ver porque no carga los registros de images y embedding
        ExperimentConfiguration configuration = experiment.getConfiguration();
        if (configuration != null) {

            audioEnabledText = context.getString(experiment.getConfiguration().isAudioEnabled() ? R.string.yes_configured : R.string.no_configured);
            imagesEnabledText = context.getString(experiment.getConfiguration().isCameraEnabled() ? R.string.yes_configured : R.string.no_configured);
            embeddingEnabledText = context.getString(experiment.getConfiguration().isEmbeddingEnabled() ? R.string.yes_configured : R.string.no_configured);
            sensorsEnabledText = context.getString(experiment.getConfiguration().isSensorEnabled() ? R.string.yes_configured : R.string.no_configured);

            sensorsListText = "";
            if(configuration.isSensorEnabled() || configuration.isLocationEnabled())
            {
                StringBuilder sensorListAsStringBuilder = new StringBuilder();
                //Si tiene ubicación, añadimos a la lista de sensores
                if(configuration.isLocationEnabled()){
                    sensorListAsStringBuilder.append(context.getString(configuration.getLocationConfig().getNameStringResource()))
                            .append("(")
                            .append(context.getString(configuration.getLocationConfig().getLocationOption().getTitleTranslatableRes()))
                            .append("), ");
                }
                //Transformamos la lista de sensores en un string para mostrarlo en pantalla
                for (SensorConfig sensorConfig:experiment.getConfiguration().getSensorConfig().getSensors()) {
                    sensorListAsStringBuilder.append(context.getString(sensorConfig.getNameStringResource())).append(", ");
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
                    SensorsRepository.countRegistersByExperimentId(experiment.getInternalId(), sensorCount);

                } else {
                    sensorCountValue.setValue("");
                }
                if (configuration.isCameraEnabled()) {
                    imageCountValue.setValue(context.getString(R.string.data_loading_text));
                    ImagesRepository.countRegistersByExperimentId(experiment.getInternalId(), imageCount);

                } else {
                    imageCountValue.setValue("");
                }

                if (configuration.isEmbeddingEnabled()) {
                    embeddingCountValue.setValue(context.getString(R.string.data_loading_text));
                    ImagesRepository.countImagesWithEmbeddingsByExperimentId(experiment.getInternalId(), embeddingCount);
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


        ExperimentResumeAndActionsDialogProvider.createActionsDialog(context, experiment, this);
    }

}
