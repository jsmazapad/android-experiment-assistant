package com.jsm.exptool.providers.worksorchestrator.workers.sync.registers;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.WorkerParameters;

import com.jsm.exptool.entities.register.ImageRegister;
import com.jsm.exptool.data.repositories.ImageRepository;
import com.jsm.exptool.data.repositories.RemoteSyncRepository;
import com.jsm.exptool.providers.worksorchestrator.workers.sync.SyncRemoteExperimentRegistersWorker;

import java.util.List;

import io.reactivex.rxjava3.core.SingleEmitter;

public class SyncRemoteImageRegistersWorker extends SyncRemoteExperimentRegistersWorker<ImageRegister> {
    public SyncRemoteImageRegistersWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @Override
    protected ImageRegister getRegister(long registerId) {
        return ImageRepository.getSynchronouslyRegisterById(registerId);
    }

    @Override
    protected void executeRemoteSync(SingleEmitter<Result> emitter, List<ImageRegister> pendingRegisters, String experimentExternalId, int numRegistersToupdate) {
        RemoteSyncRepository.syncImageRegisters(response -> executeInnerCallbackLogic(emitter, pendingRegisters, response, numRegistersToupdate), experimentExternalId, pendingRegisters);
    }

    @Override
    protected void updateRegister(ImageRegister register) {
        if(register.getEmbedding() != null && register.getEmbedding().size() > 0){
            register.setEmbeddingRemoteSynced(true);
        }
        ImageRepository.updateImageRegister(register);
    }
}
