package com.jsm.exptool.providers.worksorchestrator.workcreators;

import static com.jsm.exptool.config.ConfigConstants.REGISTERS_SYNC_LIMIT;
import static com.jsm.exptool.config.NetworkConstants.RETRY_DELAY;
import static com.jsm.exptool.config.NetworkConstants.RETRY_DELAY_UNIT;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.REGISTER_IDS_TO_SYNC;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.REMOTE_SYNC_REGISTERS;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.REMOTE_SYNC_WORK;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.REMOTE_WORK;

import androidx.work.BackoffPolicy;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.rxjava3.RxWorker;

import com.jsm.exptool.libs.WorksOrchestratorUtils;
import com.jsm.exptool.entities.Experiment;
import com.jsm.exptool.entities.register.ExperimentRegister;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class WorksOrchestratorSyncTaskCreator<T extends ExperimentRegister> {

    protected abstract Class<? extends RxWorker> getRegisterWorkerClass();
    protected abstract String getRegisterTag();
    protected abstract List<T> getPendingRegisters(long experimentInternalId);

    public void createSyncWorks(Experiment experiment, List<OneTimeWorkRequest> syncExperimentRegisters, Map<String, Object> registersInputDataValues) {
        createSyncRegisterWork(experiment, syncExperimentRegisters, registersInputDataValues);

    }
    //TODO establecer condición de red
    private void createSyncRegisterWork(Experiment experiment, List<OneTimeWorkRequest> syncExperimentRegisters,   Map<String, Object> registersInputDataValues) {
        List<T> pendingRegisters = getPendingRegisters(experiment.getInternalId());
        List<Long> pendingRegistersIdsForWork = new ArrayList<>(REGISTERS_SYNC_LIMIT);
        int numPendingRegisters = pendingRegisters.size();

            for (int i=0; i<numPendingRegisters; i++) {
                T register = pendingRegisters.get(i);
                pendingRegistersIdsForWork.add(register.getInternalId());
                if (pendingRegistersIdsForWork.size() == REGISTERS_SYNC_LIMIT || i == numPendingRegisters - 1){
                    // Crea tarea
                    Map<String, Object> workInputDataValues = new HashMap<>();
                    workInputDataValues.put(REGISTER_IDS_TO_SYNC, pendingRegistersIdsForWork.toArray(new Long[pendingRegistersIdsForWork.size()]));

                    if (experiment.getExternalId() > 0) {
                        workInputDataValues.putAll(registersInputDataValues);
                    }
                    Data registersInputData = WorksOrchestratorUtils.createInputData(workInputDataValues);
                    OneTimeWorkRequest.Builder syncRegistersRequestBuilder = new OneTimeWorkRequest.Builder(getRegisterWorkerClass())
                            .addTag(REMOTE_WORK).addTag(REMOTE_SYNC_WORK).addTag(REMOTE_SYNC_REGISTERS).addTag(getRegisterTag()).setInputData(registersInputData).setBackoffCriteria(BackoffPolicy.LINEAR, RETRY_DELAY, RETRY_DELAY_UNIT);
                    OneTimeWorkRequest syncRegistersRequest = syncRegistersRequestBuilder.build();
                    syncExperimentRegisters.add(syncRegistersRequest);
                    pendingRegistersIdsForWork.clear();
                }
            }
    }





}
