package com.jsm.exptool.workers.sync.registers;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.WorkerParameters;

import com.jsm.exptool.model.register.ImageRegister;
import com.jsm.exptool.repositories.ImageRepository;
import com.jsm.exptool.repositories.RemoteSyncRepository;
import com.jsm.exptool.workers.sync.SyncRemoteExperimentRegistersWorker;

import java.util.List;

import io.reactivex.rxjava3.core.SingleEmitter;

public class SyncRemoteImageRegistersWorker extends SyncRemoteExperimentRegistersWorker<ImageRegister> {
    public SyncRemoteImageRegistersWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @Override
    protected List<ImageRegister> getPendingRegisters(long experimentId) {
        return ImageRepository.getSynchronouslyPendingSyncRegistersByExperimentId(experimentId);
    }

    @Override
    protected void executeRemoteSync(SingleEmitter<Result> emitter, List<ImageRegister> pendingRegisters, long experimentExternalId) {
        RemoteSyncRepository.syncImageRegisters(response -> executeInnerCallbackLogic(emitter, pendingRegisters, response), experimentExternalId, pendingRegisters);
    }

    @Override
    protected void updateRegister(ImageRegister register) {
        ImageRepository.updateImageRegister(register);
    }
}
