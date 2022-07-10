package com.jsm.exptool.providers.worksorchestrator.workers.sync.registers;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.WorkerParameters;


import com.jsm.exptool.entities.register.SensorRegister;

import com.jsm.exptool.data.repositories.SensorRepository;
import com.jsm.exptool.data.repositories.RemoteSyncRepository;
import com.jsm.exptool.providers.worksorchestrator.workers.sync.SyncRemoteExperimentRegistersWorker;

import java.util.List;

import io.reactivex.rxjava3.core.SingleEmitter;

public class SyncRemoteSensorRegistersWorker extends SyncRemoteExperimentRegistersWorker<SensorRegister> {
    public SyncRemoteSensorRegistersWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @Override
    protected SensorRegister getRegister(long registerId) {
        return SensorRepository.getSynchronouslyRegisterById(registerId);
    }

    @Override
    protected void executeRemoteSync(SingleEmitter<Result> emitter, List<SensorRegister> pendingRegisters, String experimentExternalId, int numRegistersToUpdate) {
        RemoteSyncRepository.syncSensorRegisters(response -> executeInnerCallbackLogic(emitter, pendingRegisters, response, numRegistersToUpdate), experimentExternalId, pendingRegisters);
    }

    @Override
    protected void updateRegister(SensorRegister register) {
        SensorRepository.updateSensorRegister(register);
    }
}
