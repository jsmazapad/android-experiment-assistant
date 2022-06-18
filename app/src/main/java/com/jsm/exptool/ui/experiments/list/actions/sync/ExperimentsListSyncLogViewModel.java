package com.jsm.exptool.ui.experiments.list.actions.sync;

import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.UPDATED_REGISTERS_NUM;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.REMOTE_WORK;

import android.app.Application;
import android.content.Context;

import androidx.annotation.StringRes;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavController;
import androidx.work.Data;
import androidx.work.WorkInfo;

import com.jsm.exptool.R;
import com.jsm.exptool.core.data.repositories.responses.ListResponse;
import com.jsm.exptool.core.ui.baserecycler.BaseRecyclerViewModel;
import com.jsm.exptool.entities.Experiment;
import com.jsm.exptool.providers.worksorchestrator.WorksOrchestratorProvider;
import com.jsm.exptool.data.repositories.AudioRepository;
import com.jsm.exptool.data.repositories.CommentRepository;
import com.jsm.exptool.data.repositories.ImageRepository;
import com.jsm.exptool.data.repositories.SensorRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExperimentsListSyncLogViewModel extends BaseRecyclerViewModel<ExperimentSyncStateRow, ExperimentSyncStateRow> {


    private final MutableLiveData<String> filterResume = new MutableLiveData<>();
    private final Experiment experiment;
    private final MutableLiveData<Integer> rowAdded = new MutableLiveData<>();

    private final MutableLiveData<String> imageRegisterCountsText = new MutableLiveData<>();
    private final MutableLiveData<String> imageFileRegisterCountsText = new MutableLiveData<>();
    private final MutableLiveData<String> embeddingRegisterCountsText = new MutableLiveData<>();
    private final MutableLiveData<String> audioRegisterCountsText = new MutableLiveData<>();
    private final MutableLiveData<String> audioFileRegisterCountsText = new MutableLiveData<>();
    private final MutableLiveData<String> sensorRegisterCountsText = new MutableLiveData<>();
    private final MutableLiveData<String> commentRegisterCountsText = new MutableLiveData<>();

    private final MutableLiveData<Boolean> imageEnabled = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> embeddingEnabled = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> audioEnabled = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> sensorEnabled = new MutableLiveData<>(false);

    private int successImageCount = 0;
    private int failureImageCount = 0;
    private int pendingImageCount = 0;
    private int successImageFilesCount = 0;
    private int failureImageFilesCount = 0;
    private int pendingImageFilesCount = 0;
    private int successAudioCount = 0;
    private int failureAudioCount = 0;
    private int pendingAudioCount = 0;
    private int successAudioFilesCount = 0;
    private int failureAudioFilesCount = 0;
    private int pendingAudioFilesCount = 0;
    private int successEmbeddingCount = 0;
    private int failureEmbeddingCount = 0;
    private int pendingEmbeddingCount = 0;
    private int successSensorCount = 0;
    private int failureSensorCount = 0;
    private int pendingSensorCount = 0;
    private int successCommentCount = 0;
    private int failureCommentCount = 0;
    private int pendingCommentCount = 0;


    private final MutableLiveData<Integer> imageCount = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> imageFileCount = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> audioCount = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> audioFileCount = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> sensorCount = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> embeddingCount = new MutableLiveData<>(0);
    private final MutableLiveData<Integer> commentsCount = new MutableLiveData<>(0);


    private final WorksOrchestratorProvider orchestratorProvider = WorksOrchestratorProvider.getInstance();
    private final MutableLiveData<Boolean> workPreparationReady = new MutableLiveData<>();


    public ExperimentsListSyncLogViewModel(Application app, Experiment experiment) {
        super(app);
        this.experiment = experiment;
        orchestratorProvider.finishPendingJobs();
        if (experiment.getConfiguration() != null) {
            imageEnabled.setValue(experiment.getConfiguration().isCameraEnabled());
            audioEnabled.setValue(experiment.getConfiguration().isAudioEnabled());
            sensorEnabled.setValue(experiment.getConfiguration().isSensorEnabled());
            embeddingEnabled.setValue(experiment.getConfiguration().isEmbeddingEnabled());

            //noinspection ConstantConditions
            if (imageEnabled.getValue()) {
                ImageRepository.countPendingSyncRegistersByExperimentId(experiment.getInternalId(), imageCount);
                ImageRepository.countPendingSyncFileRegistersByExperimentId(experiment.getInternalId(), imageFileCount);
                //noinspection ConstantConditions
                if (embeddingEnabled.getValue()) {
                    ImageRepository.countPendingSyncEmbeddingRegistersByExperimentId(experiment.getInternalId(), embeddingCount);
                }
            }
            //noinspection ConstantConditions
            if (audioEnabled.getValue()) {
                AudioRepository.countPendingSyncRegistersByExperimentId(experiment.getInternalId(), audioCount);
                AudioRepository.countPendingSyncFileRegistersByExperimentId(experiment.getInternalId(), audioFileCount);
            }
            //noinspection ConstantConditions
            if (sensorEnabled.getValue()) {
                SensorRepository.countPendingSyncRegistersByExperimentId(experiment.getInternalId(), sensorCount);
            }
            CommentRepository.countPendingSyncRegistersByExperimentId(experiment.getInternalId(), commentsCount);
        }
    }

    public MutableLiveData<Integer> getRowAdded() {
        return rowAdded;
    }

    public MutableLiveData<String> getImageRegisterCountsText() {
        return imageRegisterCountsText;
    }

    public MutableLiveData<String> getImageFileRegisterCountsText() {
        return imageFileRegisterCountsText;
    }

    public MutableLiveData<String> getEmbeddingRegisterCountsText() {
        return embeddingRegisterCountsText;
    }

    public MutableLiveData<String> getAudioRegisterCountsText() {
        return audioRegisterCountsText;
    }

    public MutableLiveData<String> getAudioFileRegisterCountsText() {
        return audioFileRegisterCountsText;
    }

    public MutableLiveData<String> getSensorRegisterCountsText() {
        return sensorRegisterCountsText;
    }

    public MutableLiveData<String> getCommentRegisterCountsText() {
        return commentRegisterCountsText;
    }

    public MutableLiveData<Boolean> getImageEnabled() {
        return imageEnabled;
    }

    public MutableLiveData<Boolean> getEmbeddingEnabled() {
        return embeddingEnabled;
    }

    public MutableLiveData<Boolean> getAudioEnabled() {
        return audioEnabled;
    }

    public MutableLiveData<Boolean> getSensorEnabled() {
        return sensorEnabled;
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
        if (elements.getValue() == null) {
            elements.setValue(new ArrayList<>());
        }
        apiResponseRepositoryHolder.setValue(new ListResponse<>(elements.getValue()));
    }

    public void syncExperiment() {
        workPreparationReady.setValue(false);
        orchestratorProvider.executeFullRemoteSync(experiment, true, workPreparationReady);
    }

    @Override
    public void initObservers(LifecycleOwner owner) {
        super.initObservers(owner);

        workPreparationReady.observe(owner, isReady ->{
            if(isReady != null){
                isLoading.setValue(!isReady);

            }
        });

        imageCount.observe(owner, count -> {
            pendingImageCount = count;
            setImageRegisterCounts();
        });
        imageFileCount.observe(owner, count -> {
            pendingImageFilesCount = count;
            setImageFileCounts();
        });
        audioCount.observe(owner, count -> {
            pendingAudioCount = count;
            setAudioRegisterCounts();
        });
        audioFileCount.observe(owner, count -> {
            pendingAudioFilesCount = count;
            setAudioFileCounts();
        });
        embeddingCount.observe(owner, count -> {
            pendingEmbeddingCount = count;
            setEmbeddingRegisterCounts();
        });
        sensorCount.observe(owner, count -> {
            pendingSensorCount = count;
            setSensorRegisterCounts();
        });
        commentsCount.observe(owner, count -> {
            pendingCommentCount = count;
            setCommentRegisterCounts();
        });


        LiveData<List<WorkInfo>> registerWorkInfo = orchestratorProvider.getCompletedWorkInfoByTag(Collections.singletonList(REMOTE_WORK));
        registerWorkInfo.observe(owner, workInfoList -> {
            if (workInfoList != null && workInfoList.size() > 0) {
                WorkInfo lastWorkInfo = workInfoList.get(workInfoList.size() - 1);
                String syncRowTypeTitle = "";
                int typeStringRes = orchestratorProvider.getRemoteStateTranslatableStringResFromWorkInfo(lastWorkInfo);
                if (typeStringRes > 0) {
                    syncRowTypeTitle = getApplication().getString(typeStringRes);
                }
                boolean successWork = false;
                @StringRes int stateStringRes = R.string.failure;
                if (orchestratorProvider.isSuccessWork(lastWorkInfo)) {
                    successWork = true;
                    stateStringRes = R.string.success;
                }
                Data outputData = lastWorkInfo.getOutputData();
                int numRegisters = lastWorkInfo.getOutputData().getInt(UPDATED_REGISTERS_NUM, 1);

                updateWorkCounter(successWork, typeStringRes, numRegisters);
                ExperimentSyncStateRow syncRow = new ExperimentSyncStateRow(syncRowTypeTitle, getApplication().getString(stateStringRes), successWork);
                if (elements.getValue() != null) {
                    int position = elements.getValue().size();
                    elements.getValue().add(syncRow);
                    rowAdded.setValue(position);
                }
            }
        });

    }

    private void updateWorkCounter(boolean successWork, int typeStringRes, int numRegisters) {
        //TODO Aplicar cuenta de registros basada en número maximo de ellos
        if (typeStringRes == R.string.image_register) {
            if (successWork) {
                successImageCount+=numRegisters;
            } else {
                failureImageCount+=numRegisters;
            }
            setImageRegisterCounts();

        } else if (typeStringRes == R.string.image_file) {
            if (successWork) {
                successImageFilesCount+=numRegisters;
            } else {
                failureImageFilesCount+=numRegisters;
            }
            setImageFileCounts();

        } else if (typeStringRes == R.string.audio_register) {
            if (successWork) {
                successAudioCount+=numRegisters;
            } else {
                failureAudioCount+=numRegisters;
            }
            setAudioRegisterCounts();
        } else if (typeStringRes == R.string.audio_file) {
            if (successWork) {
                successAudioFilesCount+=numRegisters;
            } else {
                failureAudioFilesCount+=numRegisters;
            }
            setAudioFileCounts();
        } else if (typeStringRes == R.string.embedding) {
            if (successWork) {
                successEmbeddingCount+=numRegisters;
            } else {
                failureEmbeddingCount+=numRegisters;
            }
            setEmbeddingRegisterCounts();
        } else if (typeStringRes == R.string.sensor_register) {
            if (successWork) {
                successSensorCount+=numRegisters;
            } else {
                failureSensorCount+=numRegisters;
            }
            setSensorRegisterCounts();
        } else if (typeStringRes == R.string.comment_register) {
            if (successWork) {
                successCommentCount+=numRegisters;
            } else {
                failureCommentCount+=numRegisters;
            }
            setCommentRegisterCounts();
        }else if (typeStringRes == R.string.processing_image_for_embedding) {
            if (!successWork) {
                //Solo cuenta si falla, porque el work para obtener embedding no se ejecutará
                failureEmbeddingCount+=numRegisters;
            }
            setEmbeddingRegisterCounts();
        }
    }

    private void setCommentRegisterCounts() {
        commentRegisterCountsText.setValue(String.format(getApplication().getString(R.string.sync_number_format), pendingCommentCount, successCommentCount, failureCommentCount));
    }

    private void setSensorRegisterCounts() {
        sensorRegisterCountsText.setValue(String.format(getApplication().getString(R.string.sync_number_format), pendingSensorCount, successSensorCount, failureSensorCount));
    }

    private void setEmbeddingRegisterCounts() {
        embeddingRegisterCountsText.setValue(String.format(getApplication().getString(R.string.sync_number_format), pendingEmbeddingCount, successEmbeddingCount, failureEmbeddingCount));
    }

    private void setAudioFileCounts() {
        audioFileRegisterCountsText.setValue(String.format(getApplication().getString(R.string.sync_number_format), pendingAudioFilesCount, successAudioFilesCount, failureAudioFilesCount));
    }

    private void setAudioRegisterCounts() {
        audioRegisterCountsText.setValue(String.format(getApplication().getString(R.string.sync_number_format), pendingAudioCount, successAudioCount, failureAudioCount));
    }

    private void setImageFileCounts() {
        imageFileRegisterCountsText.setValue(String.format(getApplication().getString(R.string.sync_number_format), pendingImageFilesCount, successImageFilesCount, failureImageFilesCount));
    }

    private void setImageRegisterCounts() {
        imageRegisterCountsText.setValue(String.format(getApplication().getString(R.string.sync_number_format), pendingImageCount, successImageCount, failureImageCount));
    }


}
