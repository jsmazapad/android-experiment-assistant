package com.jsm.exptool.providers.syncworksorchestrator.workcreators;


import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.REMOTE_SYNC_IMAGE_FILE_REGISTERS;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.REMOTE_SYNC_IMAGE_REGISTERS;

import androidx.work.rxjava3.RxWorker;

import com.jsm.exptool.model.register.ImageRegister;
import com.jsm.exptool.repositories.ImageRepository;
import com.jsm.exptool.workers.sync.files.SyncRemoteImageFileRegistersWorker;
import com.jsm.exptool.workers.sync.registers.SyncRemoteImageRegistersWorker;

import java.util.List;

public abstract class ImageWorksOrchestratorSyncTaskCreator extends MediaWorksOrchestratorSyncTaskCreator<ImageRegister> {



    @Override
    protected Class<? extends RxWorker> getRegisterWorkerClass() {
        return SyncRemoteImageRegistersWorker.class;
    }

    @Override
    protected Class<? extends RxWorker> getFileRegisterWorkerClass() {
        return SyncRemoteImageFileRegistersWorker.class;
    }

    @Override
    protected String getRegisterTag() {
        return REMOTE_SYNC_IMAGE_REGISTERS;
    }

    @Override
    protected String getFileRegisterTag() {
        return REMOTE_SYNC_IMAGE_FILE_REGISTERS;
    }


    @Override
    protected List<ImageRegister> getPendingFileRegisters(long experimentInternalId) {
        return ImageRepository.getSynchronouslyPendingFileSyncRegistersByExperimentId(experimentInternalId);
    }


}
