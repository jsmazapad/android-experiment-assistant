package com.jsm.exptool.providers.worksorchestrator.workcreators.data;


import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.REMOTE_SYNC_SENSORS_REGISTERS;

import androidx.work.rxjava3.RxWorker;

import com.jsm.exptool.entities.register.SensorRegister;
import com.jsm.exptool.providers.worksorchestrator.workcreators.WorksOrchestratorSyncTaskCreator;
import com.jsm.exptool.data.repositories.SensorRepository;
import com.jsm.exptool.providers.worksorchestrator.workers.sync.registers.SyncRemoteSensorRegistersWorker;

import java.util.List;


public class SensorWorksOrchestratorSyncTaskCreator extends WorksOrchestratorSyncTaskCreator<SensorRegister> {

    @Override
    protected Class<? extends RxWorker> getRegisterWorkerClass() {
        return SyncRemoteSensorRegistersWorker.class;
    }

    @Override
    protected String getRegisterTag() {
        return REMOTE_SYNC_SENSORS_REGISTERS;
    }

    @Override
    protected List<SensorRegister> getPendingRegisters(long experimentInternalId) {
        return SensorRepository.getSynchronouslyPendingSyncRegistersByExperimentId(experimentInternalId);
    }

}
