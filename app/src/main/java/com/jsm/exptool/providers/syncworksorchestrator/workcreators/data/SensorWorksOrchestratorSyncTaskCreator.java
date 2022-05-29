package com.jsm.exptool.providers.syncworksorchestrator.workcreators.data;


import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.REMOTE_SYNC_SENSORS_REGISTERS;

import androidx.work.rxjava3.RxWorker;

import com.jsm.exptool.model.register.SensorRegister;
import com.jsm.exptool.providers.syncworksorchestrator.workcreators.WorksOrchestratorSyncTaskCreator;
import com.jsm.exptool.repositories.ImageRepository;
import com.jsm.exptool.repositories.SensorRepository;
import com.jsm.exptool.workers.sync.registers.SyncRemoteSensorRegistersWorker;

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
