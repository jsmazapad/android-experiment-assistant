package com.jsm.exptool.workers.sync.registers;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.WorkerParameters;


import com.jsm.exptool.model.register.SensorRegister;

import com.jsm.exptool.repositories.SensorRepository;
import com.jsm.exptool.repositories.RemoteSyncRepository;
import com.jsm.exptool.workers.sync.SyncRemoteExperimentRegistersWorker;

import java.util.List;

import io.reactivex.rxjava3.core.SingleEmitter;

public class SyncRemoteSensorRegistersWorker extends SyncRemoteExperimentRegistersWorker<SensorRegister> {
    public SyncRemoteSensorRegistersWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @Override
    protected List<SensorRegister> getPendingRegisters(long experimentId) {
        return SensorRepository.getSynchronouslyPendingSyncRegistersByExperimentId(experimentId);
    }

    @Override
    protected void executeRemoteSync(SingleEmitter<Result> emitter, List<SensorRegister> pendingRegisters, long experimentExternalId) {
        RemoteSyncRepository.syncSensorRegisters(response -> executeInnerCallbackLogic(emitter, pendingRegisters, response), experimentExternalId, pendingRegisters);
    }

    @Override
    protected void updateRegister(SensorRegister register) {
        SensorRepository.updateSensorRegister(register);
    }
}
