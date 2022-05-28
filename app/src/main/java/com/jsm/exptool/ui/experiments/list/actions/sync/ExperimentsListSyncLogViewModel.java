package com.jsm.exptool.ui.experiments.list.actions.sync;

import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.REMOTE_SYNC_AUDIO_FILE_REGISTERS;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.REMOTE_SYNC_AUDIO_REGISTERS;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.REMOTE_SYNC_COMMENT_REGISTERS;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.REMOTE_SYNC_IMAGE_FILE_REGISTERS;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.REMOTE_SYNC_IMAGE_REGISTERS;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.REMOTE_SYNC_SENSORS_REGISTERS;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.REMOTE_WORK;

import android.app.Application;
import android.content.Context;

import androidx.annotation.StringRes;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavController;
import androidx.work.WorkInfo;

import com.jsm.exptool.R;
import com.jsm.exptool.core.data.repositories.responses.ListResponse;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerViewModel;
import com.jsm.exptool.model.Experiment;
import com.jsm.exptool.providers.syncworksorchestrator.WorksOrchestratorProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExperimentsListSyncLogViewModel extends BaseRecyclerViewModel<ExperimentSyncStateRow, ExperimentSyncStateRow>  {


    private final MutableLiveData<String> filterResume = new MutableLiveData<>();
    private final Experiment experiment;
    private final MutableLiveData<Integer> rowAdded = new MutableLiveData<>();

    private final MutableLiveData<String> imageRegisterSuccessCount = new MutableLiveData<>();
    private final MutableLiveData<String> imageFileRegisterSuccessCount = new MutableLiveData<>();
    private final MutableLiveData<String> audioRegisterSuccessCount = new MutableLiveData<>();
    private final MutableLiveData<String> audioFileRegisterSuccessCount = new MutableLiveData<>();
    private final MutableLiveData<String> sensorRegisterSuccessCount = new MutableLiveData<>();
    private final MutableLiveData<String> commentRegisterSuccessCount = new MutableLiveData<>();

    private final MutableLiveData<Boolean> imageEnabled = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> embeddingEnabled = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> audioEnabled = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> sensorEnabled = new MutableLiveData<>(false);

    private int successImageCount = 0;
    private int failureImageCount = 0;
    private int successImageFilesCount = 0;
    private int failureImageFilesCount = 0;
    private int successAudioCount = 0;
    private int failureAudioCount = 0;
    private int successAudioFilesCount = 0;
    private int failureAudioFilesCount = 0;
    private int successEmbeddingCount = 0;
    private int failureEmbeddingCount = 0;
    private int successSensorCount = 0;
    private int failureSensorCount = 0;
    private int successCommentCount = 0;
    private int failureCommentCount = 0;


    private final MutableLiveData<Integer> sensorCount = new MutableLiveData<>(0);
    private final MutableLiveData<String> sensorCountValue = new MutableLiveData<>("");
    private final MutableLiveData<Integer> imageCount = new MutableLiveData<>(0);
    private final MutableLiveData<String> imageCountValue = new MutableLiveData<>("");
    private final MutableLiveData<Integer> embeddingCount = new MutableLiveData<>(0);
    private final MutableLiveData<String> embeddingCountValue = new MutableLiveData<>("");
    private final MutableLiveData<Integer> audioCount = new MutableLiveData<>(0);
    private final MutableLiveData<String> audioCountValue = new MutableLiveData<>("");
    private final MutableLiveData<Integer> commentsCount = new MutableLiveData<>(0);
    private final MutableLiveData<String> commentsCountValue = new MutableLiveData<>("");



    private final WorksOrchestratorProvider orchestratorProvider = WorksOrchestratorProvider.getInstance();


    public ExperimentsListSyncLogViewModel(Application app, Experiment experiment) {
        super(app);
        this.experiment = experiment;
        if(experiment.getConfiguration() != null){
            imageEnabled.setValue(experiment.getConfiguration().isCameraEnabled());
            audioEnabled.setValue(experiment.getConfiguration().isAudioEnabled());
            sensorEnabled.setValue(experiment.getConfiguration().isSensorEnabled());
            embeddingEnabled.setValue(experiment.getConfiguration().isEmbeddingEnabled());
        }
    }

    public MutableLiveData<Integer> getRowAdded() {
        return rowAdded;
    }

    public MutableLiveData<String> getImageRegisterSuccessCount() {
        return imageRegisterSuccessCount;
    }

    public MutableLiveData<String> getImageFileRegisterSuccessCount() {
        return imageFileRegisterSuccessCount;
    }

    public MutableLiveData<String> getAudioRegisterSuccessCount() {
        return audioRegisterSuccessCount;
    }

    public MutableLiveData<String> getAudioFileRegisterSuccessCount() {
        return audioFileRegisterSuccessCount;
    }

    public MutableLiveData<String> getSensorRegisterSuccessCount() {
        return sensorRegisterSuccessCount;
    }

    public MutableLiveData<String> getCommentRegisterSuccessCount() {
        return commentRegisterSuccessCount;
    }

    public WorksOrchestratorProvider getOrchestratorProvider() {
        return orchestratorProvider;
    }

    //TODO Eliminar o usar para resumen
    public MutableLiveData<String> getFilterResume() {
        return filterResume;
    }


    @Override
    public List<ExperimentSyncStateRow> transformResponse(ListResponse<ExperimentSyncStateRow> response) {
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
        if (elements.getValue() == null){
            elements.setValue(new ArrayList<>());
        }
        apiResponseRepositoryHolder.setValue(new ListResponse<>(elements.getValue()));
    }

    public void syncExperiment() {
        orchestratorProvider.executeFullRemoteSync(experiment, true);
    }

    @Override
    public void initObservers(LifecycleOwner owner) {
        super.initObservers(owner);
        //TODO Rellenar lista observando el orquestador
        if (experiment.getConfiguration() != null) {
            if(experiment.getConfiguration().isCameraEnabled()) {
                List<String> registerTags = Collections.singletonList(REMOTE_SYNC_IMAGE_REGISTERS);
                LiveData<List<WorkInfo>> registerWorkInfo = orchestratorProvider.getSuccessWorkInfoByTag(registerTags);
                registerWorkInfo.observe(owner, workInfoList -> {
                    if (workInfoList != null) {
                        successImageCount = workInfoList.size();
                        imageRegisterSuccessCount.setValue(String.format(getApplication().getString(R.string.sync_number_format), successImageCount));
                    }
                });
                List<String> registerFileTags = Collections.singletonList(REMOTE_SYNC_IMAGE_FILE_REGISTERS);
                LiveData<List<WorkInfo>> registerFileWorkInfo = orchestratorProvider.getSuccessWorkInfoByTag(registerFileTags);
                registerFileWorkInfo.observe(owner, workInfoList -> {
                    if (workInfoList != null) {
                        successImageFilesCount = workInfoList.size();
                        imageFileRegisterSuccessCount.setValue(String.format(getApplication().getString(R.string.sync_number_format), successImageFilesCount));
                    }
                });

                //TODO Añadir embedding
            }

            //TODO ¿Se puede hacer con un único observable para los trabajos completos?
            if(experiment.getConfiguration().isAudioEnabled()) {
                List<String> registerTags = Collections.singletonList(REMOTE_SYNC_AUDIO_REGISTERS);
                LiveData<List<WorkInfo>> registerWorkInfo = orchestratorProvider.getSuccessWorkInfoByTag(registerTags);
                registerWorkInfo.observe(owner, workInfoList -> {
                    if (workInfoList != null) {
                        successAudioCount = workInfoList.size();
                        audioRegisterSuccessCount.setValue(String.format(getApplication().getString(R.string.sync_number_format), workInfoList.size()));
                    }
                });

                List<String> registerFileTags = Collections.singletonList(REMOTE_SYNC_AUDIO_FILE_REGISTERS);
                LiveData<List<WorkInfo>> registerFileWorkInfo = orchestratorProvider.getSuccessWorkInfoByTag(registerFileTags);
                registerFileWorkInfo.observe(owner, workInfoList -> {
                    if (workInfoList != null) {
                        audioFileRegisterSuccessCount.setValue(String.format(getApplication().getString(R.string.sync_number_format), workInfoList.size()));
                    }
                });
            }

            if(experiment.getConfiguration().isSensorEnabled() || experiment.getConfiguration().isLocationEnabled()) {
                List<String> registerTags = Collections.singletonList(REMOTE_SYNC_SENSORS_REGISTERS);
                LiveData<List<WorkInfo>> registerWorkInfo = orchestratorProvider.getSuccessWorkInfoByTag(registerTags);
                registerWorkInfo.observe(owner, workInfoList -> {
                    if (workInfoList != null) {
                        sensorRegisterSuccessCount.setValue(String.format(getApplication().getString(R.string.sync_number_format), workInfoList.size()));
                    }
                });
            }

            List<String> commentRegisterTags = Collections.singletonList(REMOTE_SYNC_COMMENT_REGISTERS);
            LiveData<List<WorkInfo>> commentRegisterWorkInfo = orchestratorProvider.getSuccessWorkInfoByTag(commentRegisterTags);
            commentRegisterWorkInfo.observe(owner, workInfoList -> {
                if (workInfoList != null) {
                    commentRegisterSuccessCount.setValue(String.format(getApplication().getString(R.string.sync_number_format), workInfoList.size()));
                }
            });

            LiveData<List<WorkInfo>> registerWorkInfo = orchestratorProvider.getCompletedWorkInfoByTag(Collections.singletonList(REMOTE_WORK));
            registerWorkInfo.observe(owner, workInfoList -> {
                if (workInfoList != null && workInfoList.size() > 0) {
                    WorkInfo lastWorkInfo = workInfoList.get(workInfoList.size() - 1);
                    String syncRowTypeTitle = "";
                    int typeStringRes = orchestratorProvider.getRemoteStateTranslatableStringResFromWorkInfo(lastWorkInfo);
                    if (typeStringRes > 0){
                        syncRowTypeTitle = getApplication().getString(typeStringRes);
                    }
                    boolean successWork = false;
                    @StringRes int stateStringRes = R.string.failure;
                    if(orchestratorProvider.isSuccessWork(lastWorkInfo)){
                        successWork = true;
                        stateStringRes = R.string.success;
                    }
                    updateWorkCounter(successWork, stateStringRes);
                    ExperimentSyncStateRow syncRow = new ExperimentSyncStateRow(syncRowTypeTitle, getApplication().getString(stateStringRes), successWork);
                    if(elements.getValue() != null) {
                        int position = elements.getValue().size();
                        elements.getValue().add(syncRow);
                        rowAdded.setValue(position);
                    }
                }
            });

        }

    }

    private void updateWorkCounter(boolean successWork, int stateStringRes) {
        //TODO Aplicar cuenta de registros basada en número maximo de ellos
        if( stateStringRes == R.string.image_register){
            if (successWork) {
                successImageCount++;
            } else {
                failureImageCount++;
            }
        }else if( stateStringRes == R.string.image_file){
            if (successWork) {
                successImageFilesCount++;
            } else {
                failureImageFilesCount++;
            }
        }else if( stateStringRes == R.string.audio_register){
            if (successWork) {
                successAudioCount++;
            } else {
                failureAudioCount++;
            }
        }else if( stateStringRes == R.string.audio_file){
            if (successWork) {
                successAudioFilesCount++;
            } else {
                failureAudioFilesCount++;
            }
        }else if( stateStringRes == R.string.embedding){
            if (successWork) {
                successEmbeddingCount++;
            } else {
                failureEmbeddingCount++;
            }
        }else if( stateStringRes == R.string.sensor_register){
            if (successWork) {
                successSensorCount++;
            } else {
                failureSensorCount++;
            }
        }else if( stateStringRes == R.string.comment_register){
            if (successWork) {
                successCommentCount++;
            } else {
                failureCommentCount++;
            }
        }
    }


}
