package com.jsm.exptool.providers.worksorchestrator.workers.sync.registers;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.WorkerParameters;

import com.jsm.exptool.entities.register.AudioRegister;
import com.jsm.exptool.data.repositories.AudioRepository;
import com.jsm.exptool.data.repositories.RemoteSyncRepository;
import com.jsm.exptool.providers.worksorchestrator.workers.sync.SyncRemoteExperimentRegistersWorker;

import java.util.List;

import io.reactivex.rxjava3.core.SingleEmitter;

public class SyncRemoteAudioRegistersWorker extends SyncRemoteExperimentRegistersWorker<AudioRegister> {
    public SyncRemoteAudioRegistersWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @Override
    protected AudioRegister getRegister(long registerId) {
        return AudioRepository.getSynchronouslyRegisterById(registerId);
    }

    @Override
    protected void executeRemoteSync(SingleEmitter<Result> emitter, List<AudioRegister> pendingRegisters, String experimentExternalId, int numRegistersToupdate) {
        RemoteSyncRepository.syncAudioRegisters(response -> executeInnerCallbackLogic(emitter, pendingRegisters, response, numRegistersToupdate), experimentExternalId, pendingRegisters);
    }

    @Override
    protected void updateRegister(AudioRegister register) {
        AudioRepository.updateAudioRegister(register);
    }
}
