package com.jsm.exptool.providers.worksorchestrator.workers.sync.registers;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.WorkerParameters;

import com.jsm.exptool.entities.register.CommentRegister;
import com.jsm.exptool.data.repositories.CommentRepository;
import com.jsm.exptool.data.repositories.RemoteSyncRepository;
import com.jsm.exptool.providers.worksorchestrator.workers.sync.SyncRemoteExperimentRegistersWorker;

import java.util.List;

import io.reactivex.rxjava3.core.SingleEmitter;

public class SyncRemoteCommentRegistersWorker extends SyncRemoteExperimentRegistersWorker<CommentRegister> {
    public SyncRemoteCommentRegistersWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @Override
    protected CommentRegister getRegister(long registerId) {
        return CommentRepository.getSynchronouslyRegisterById(registerId);
    }

    @Override
    protected void executeRemoteSync(SingleEmitter<Result> emitter, List<CommentRegister> pendingRegisters, String experimentExternalId, int numRegistersToupdate) {
        RemoteSyncRepository.syncCommentRegisters(response -> executeInnerCallbackLogic(emitter, pendingRegisters, response, numRegistersToupdate), experimentExternalId, pendingRegisters);
    }

    @Override
    protected void updateRegister(CommentRegister register) {
        CommentRepository.updateCommentRegister(register);
    }
}
