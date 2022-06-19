package com.jsm.exptool.providers.worksorchestrator.workers.sync.files;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.WorkerParameters;

import com.jsm.exptool.entities.register.AudioRegister;
import com.jsm.exptool.data.repositories.AudioRepository;
import com.jsm.exptool.data.repositories.RemoteSyncRepository;
import com.jsm.exptool.providers.worksorchestrator.workers.sync.SyncRemoteExperimentFilesWorker;

import java.io.File;

import io.reactivex.rxjava3.core.SingleEmitter;

public class SyncRemoteAudioFileRegistersWorker extends SyncRemoteExperimentFilesWorker<AudioRegister> {
    public SyncRemoteAudioFileRegistersWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @Override
    protected void executeRemoteSync(SingleEmitter<Result> emitter, long experimentRegisterId, String experimentExternalId, File file) {
        RemoteSyncRepository.syncAudioFile(response -> executeInnerCallbackLogic(emitter, experimentRegisterId, response), experimentExternalId, file);
    }

    @Override
    protected int updateRegister(long experimentRegisterId) {
        return AudioRepository.updateRegisterFileSyncedByRegisterId(experimentRegisterId);
    }
}
