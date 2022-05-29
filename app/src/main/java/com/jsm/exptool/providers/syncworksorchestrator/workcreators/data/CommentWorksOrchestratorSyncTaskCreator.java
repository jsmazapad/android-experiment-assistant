package com.jsm.exptool.providers.syncworksorchestrator.workcreators.data;


import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.REMOTE_SYNC_COMMENT_REGISTERS;

import androidx.work.rxjava3.RxWorker;

import com.jsm.exptool.model.register.CommentRegister;
import com.jsm.exptool.providers.syncworksorchestrator.workcreators.WorksOrchestratorSyncTaskCreator;
import com.jsm.exptool.workers.sync.registers.SyncRemoteCommentRegistersWorker;


public class CommentWorksOrchestratorSyncTaskCreator extends WorksOrchestratorSyncTaskCreator<CommentRegister> {

   

    @Override
    protected Class<? extends RxWorker> getRegisterWorkerClass() {
        return SyncRemoteCommentRegistersWorker.class;
    }
    
    @Override
    protected String getRegisterTag() {
        return REMOTE_SYNC_COMMENT_REGISTERS;
    }
    
    

}
