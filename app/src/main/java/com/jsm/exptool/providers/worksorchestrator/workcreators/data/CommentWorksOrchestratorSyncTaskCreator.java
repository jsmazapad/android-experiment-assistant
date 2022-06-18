package com.jsm.exptool.providers.worksorchestrator.workcreators.data;


import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.REMOTE_SYNC_COMMENT_REGISTERS;

import androidx.work.rxjava3.RxWorker;

import com.jsm.exptool.entities.register.CommentRegister;
import com.jsm.exptool.providers.worksorchestrator.workcreators.WorksOrchestratorSyncTaskCreator;
import com.jsm.exptool.data.repositories.CommentRepository;
import com.jsm.exptool.providers.worksorchestrator.workers.sync.registers.SyncRemoteCommentRegistersWorker;

import java.util.List;


public class CommentWorksOrchestratorSyncTaskCreator extends WorksOrchestratorSyncTaskCreator<CommentRegister> {

   

    @Override
    protected Class<? extends RxWorker> getRegisterWorkerClass() {
        return SyncRemoteCommentRegistersWorker.class;
    }
    
    @Override
    protected String getRegisterTag() {
        return REMOTE_SYNC_COMMENT_REGISTERS;
    }

    @Override
    protected List<CommentRegister> getPendingRegisters(long experimentInternalId) {
        return CommentRepository.getSynchronouslyPendingSyncRegistersByExperimentId(experimentInternalId);
    }


}
