package com.jsm.exptool.workers.sync.registers;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.WorkerParameters;

import com.jsm.exptool.model.register.AudioRegister;
import com.jsm.exptool.model.register.ImageRegister;
import com.jsm.exptool.repositories.AudioRepository;
import com.jsm.exptool.repositories.RemoteSyncRepository;
import com.jsm.exptool.workers.sync.SyncRemoteExperimentRegistersWorker;

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
    protected void executeRemoteSync(SingleEmitter<Result> emitter, List<AudioRegister> pendingRegisters, long experimentExternalId, int numRegistersToupdate) {
        RemoteSyncRepository.syncAudioRegisters(response -> executeInnerCallbackLogic(emitter, pendingRegisters, response, numRegistersToupdate), experimentExternalId, pendingRegisters);
    }

    @Override
    protected void updateRegister(AudioRegister register) {
        AudioRepository.updateAudioRegister(register);
    }
}
