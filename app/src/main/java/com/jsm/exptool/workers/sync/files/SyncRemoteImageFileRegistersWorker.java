package com.jsm.exptool.workers.sync.files;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.WorkerParameters;

import com.jsm.exptool.model.register.ImageRegister;
import com.jsm.exptool.repositories.ImageRepository;
import com.jsm.exptool.repositories.RemoteSyncRepository;
import com.jsm.exptool.workers.sync.SyncRemoteExperimentFilesWorker;

import java.io.File;

import io.reactivex.rxjava3.core.SingleEmitter;

public class SyncRemoteImageFileRegistersWorker extends SyncRemoteExperimentFilesWorker<ImageRegister> {
    public SyncRemoteImageFileRegistersWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @Override
    protected void executeRemoteSync(SingleEmitter<Result> emitter, long experimentRegisterId, long experimentExternalId, File file) {
        RemoteSyncRepository.syncImageFile(response -> executeInnerCallbackLogic(emitter, experimentRegisterId, response), experimentExternalId, file);
    }

    @Override
    protected int updateRegister(long experimentRegisterId) {
        return ImageRepository.updateRegisterFileSyncedByRegisterId(experimentRegisterId);
    }
}
