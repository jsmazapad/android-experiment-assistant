package com.jsm.exptool.workers.sync.files;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.WorkerParameters;

import com.jsm.exptool.entities.register.AudioRegister;
import com.jsm.exptool.repositories.AudioRepository;
import com.jsm.exptool.repositories.RemoteSyncRepository;
import com.jsm.exptool.workers.sync.SyncRemoteExperimentFilesWorker;

import java.io.File;

import io.reactivex.rxjava3.core.SingleEmitter;

public class SyncRemoteAudioFileRegistersWorker extends SyncRemoteExperimentFilesWorker<AudioRegister> {
    public SyncRemoteAudioFileRegistersWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @Override
    protected void executeRemoteSync(SingleEmitter<Result> emitter, long experimentRegisterId, long experimentExternalId, File file) {
        RemoteSyncRepository.syncAudioFile(response -> executeInnerCallbackLogic(emitter, experimentRegisterId, response), experimentExternalId, file);
    }

    @Override
    protected int updateRegister(long experimentRegisterId) {
        return AudioRepository.updateRegisterFileSyncedByRegisterId(experimentRegisterId);
    }
}
