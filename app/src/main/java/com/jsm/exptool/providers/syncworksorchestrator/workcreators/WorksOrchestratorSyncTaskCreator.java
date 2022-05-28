package com.jsm.exptool.providers.syncworksorchestrator.workcreators;

import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.EXPERIMENT_REGISTER_ID;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.FILE_NAME;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.REMOTE_SYNC_FILE_REGISTERS;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.REMOTE_SYNC_IMAGE_FILE_REGISTERS;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.REMOTE_SYNC_REGISTERS;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.REMOTE_SYNC_WORK;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.REMOTE_WORK;

import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.rxjava3.RxWorker;

import com.jsm.exptool.libs.WorksOrchestratorUtils;
import com.jsm.exptool.model.Experiment;
import com.jsm.exptool.model.register.ExperimentRegister;
import com.jsm.exptool.model.register.ImageRegister;
import com.jsm.exptool.model.register.MediaRegister;
import com.jsm.exptool.workers.sync.files.SyncRemoteImageFileRegistersWorker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class WorksOrchestratorSyncTaskCreator<T extends ExperimentRegister> {

    protected abstract Class<? extends RxWorker> getRegisterWorkerClass();
    protected abstract String getRegisterTag();

    public void createSyncImageWorks(Experiment experiment, List<OneTimeWorkRequest> syncExperimentRegisters, Map<String, Object> registersInputDataValues, Data registersExperimentInputData) {
        createSyncRegisterWork(experiment, syncExperimentRegisters, registersExperimentInputData);

    }

    private void createSyncRegisterWork(Experiment experiment, List<OneTimeWorkRequest> syncExperimentRegisters,  Data registersExperimentInputData) {
        OneTimeWorkRequest.Builder syncRegistersRequestBuilder = new OneTimeWorkRequest.Builder(getRegisterWorkerClass())
                .addTag(REMOTE_WORK).addTag(REMOTE_SYNC_WORK).addTag(REMOTE_SYNC_REGISTERS).addTag(getRegisterTag());


        if (experiment.getExternalId() > 0) {
            syncRegistersRequestBuilder.setInputData(registersExperimentInputData);
        }
        OneTimeWorkRequest syncRegistersRequest = syncRegistersRequestBuilder.build();
        syncExperimentRegisters.add(syncRegistersRequest);
    }





}
