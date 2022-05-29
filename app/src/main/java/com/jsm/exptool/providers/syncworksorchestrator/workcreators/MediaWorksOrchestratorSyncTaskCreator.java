package com.jsm.exptool.providers.syncworksorchestrator.workcreators;

import static com.jsm.exptool.config.NetworkConstants.RETRY_DELAY;
import static com.jsm.exptool.config.NetworkConstants.RETRY_DELAY_UNIT;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.EXPERIMENT_REGISTER_ID;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.FILE_NAME;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.REMOTE_SYNC_FILE_REGISTERS;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.REMOTE_SYNC_WORK;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.REMOTE_WORK;

import androidx.work.BackoffPolicy;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.rxjava3.RxWorker;

import com.jsm.exptool.libs.WorksOrchestratorUtils;
import com.jsm.exptool.model.Experiment;
import com.jsm.exptool.model.register.MediaRegister;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class MediaWorksOrchestratorSyncTaskCreator<T extends MediaRegister> extends WorksOrchestratorSyncTaskCreator<T> {


    protected abstract Class<? extends RxWorker> getFileRegisterWorkerClass();

    protected abstract String getFileRegisterTag();
    protected abstract List<T> getPendingFileRegisters(long experimentInternalId);

    @Override
    public void createSyncWorks(Experiment experiment, List<OneTimeWorkRequest> syncExperimentRegisters, Map<String, Object> registersInputDataValues) {
        super.createSyncWorks(experiment, syncExperimentRegisters, registersInputDataValues);
        createSyncFileRegisterWorks(experiment, syncExperimentRegisters, registersInputDataValues);

    }

    //TODO Refactorizar parámetros para no usar el Data
    private void createSyncFileRegisterWorks(Experiment experiment, List<OneTimeWorkRequest> syncExperimentRegisters, Map<String, Object> registersInputDataValues){
        List<T> pendingRegisters = getPendingFileRegisters(experiment.getInternalId());
        for (T register : pendingRegisters) {

            Map<String, Object> registersFileInputDataValues = new HashMap<String, Object>() {
                {
                    put(EXPERIMENT_REGISTER_ID, register.getInternalId());
                    put(FILE_NAME, register.getFullPath());
                }
            };

            OneTimeWorkRequest.Builder syncFileRegistersRequestBuilder = new OneTimeWorkRequest.Builder(getFileRegisterWorkerClass())
                    .addTag(REMOTE_WORK).addTag(REMOTE_SYNC_WORK).addTag(REMOTE_SYNC_FILE_REGISTERS).addTag(getFileRegisterTag()).setBackoffCriteria(BackoffPolicy.LINEAR, RETRY_DELAY, RETRY_DELAY_UNIT);
            if (experiment.getExternalId() > 0) {
                registersFileInputDataValues.putAll(registersInputDataValues);
            }
            Data registersFileInputData = WorksOrchestratorUtils.createInputData(registersFileInputDataValues);
            syncFileRegistersRequestBuilder.setInputData(registersFileInputData);
            OneTimeWorkRequest syncFileRegistersRequest = syncFileRegistersRequestBuilder.build();
            syncExperimentRegisters.add(syncFileRegistersRequest);
        }
    }

}
