package com.jsm.exptool.providers.worksorchestrator.workers.sync.files;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.WorkerParameters;

import com.jsm.exptool.entities.register.ImageRegister;
import com.jsm.exptool.data.repositories.ImageRepository;
import com.jsm.exptool.data.repositories.RemoteSyncRepository;
import com.jsm.exptool.providers.worksorchestrator.workers.sync.SyncRemoteExperimentFilesWorker;

import java.io.File;

import io.reactivex.rxjava3.core.SingleEmitter;

public class SyncRemoteImageFileRegistersWorker extends SyncRemoteExperimentFilesWorker<ImageRegister> {
    public SyncRemoteImageFileRegistersWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @Override
    protected void executeRemoteSync(SingleEmitter<Result> emitter, long experimentRegisterId, String experimentExternalId, File file) {
        RemoteSyncRepository.syncImageFile(response -> executeInnerCallbackLogic(emitter, experimentRegisterId, response), experimentExternalId, file);
    }

    @Override
    protected int updateRegister(long experimentRegisterId) {
        return ImageRepository.updateRegisterFileSyncedByRegisterId(experimentRegisterId);
    }
}
