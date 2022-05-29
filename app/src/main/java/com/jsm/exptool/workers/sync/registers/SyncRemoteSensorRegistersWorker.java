package com.jsm.exptool.workers.sync.registers;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.WorkerParameters;


import com.jsm.exptool.model.register.AudioRegister;
import com.jsm.exptool.model.register.SensorRegister;

import com.jsm.exptool.repositories.AudioRepository;
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
    protected SensorRegister getRegister(long registerId) {
        return SensorRepository.getSynchronouslyRegisterById(registerId);
    }

    @Override
    protected void executeRemoteSync(SingleEmitter<Result> emitter, List<SensorRegister> pendingRegisters, long experimentExternalId, int numRegistersToupdate) {
        RemoteSyncRepository.syncSensorRegisters(response -> executeInnerCallbackLogic(emitter, pendingRegisters, response, numRegistersToupdate), experimentExternalId, pendingRegisters);
    }

    @Override
    protected void updateRegister(SensorRegister register) {
        SensorRepository.updateSensorRegister(register);
    }
}
