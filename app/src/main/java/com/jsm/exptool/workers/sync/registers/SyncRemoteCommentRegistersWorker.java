package com.jsm.exptool.workers.sync.registers;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.WorkerParameters;

import com.jsm.exptool.model.register.CommentRegister;
import com.jsm.exptool.repositories.CommentRepository;
import com.jsm.exptool.repositories.RemoteSyncRepository;
import com.jsm.exptool.workers.sync.SyncRemoteExperimentRegistersWorker;

import java.util.List;

import io.reactivex.rxjava3.core.SingleEmitter;

public class SyncRemoteCommentRegistersWorker extends SyncRemoteExperimentRegistersWorker<CommentRegister> {
    public SyncRemoteCommentRegistersWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @Override
    protected List<CommentRegister> getPendingRegisters(long experimentId) {
        return CommentRepository.getSynchronouslyPendingSyncRegistersByExperimentId(experimentId);
    }

    @Override
    protected void executeRemoteSync(SingleEmitter<Result> emitter, List<CommentRegister> pendingRegisters, long experimentExternalId) {
        RemoteSyncRepository.syncCommentRegisters(response -> executeInnerCallbackLogic(emitter, pendingRegisters, response), experimentExternalId, pendingRegisters);
    }

    @Override
    protected void updateRegister(CommentRegister register) {
        CommentRepository.updateCommentRegister(register);
    }
}
