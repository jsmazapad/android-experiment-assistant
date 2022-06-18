package com.jsm.exptool.providers.worksorchestrator.workcreators.multimedia;


import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.REMOTE_SYNC_AUDIO_FILE_REGISTERS;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.REMOTE_SYNC_AUDIO_REGISTERS;

import androidx.work.rxjava3.RxWorker;

import com.jsm.exptool.entities.register.AudioRegister;
import com.jsm.exptool.providers.worksorchestrator.workcreators.MediaWorksOrchestratorSyncTaskCreator;
import com.jsm.exptool.data.repositories.AudioRepository;
import com.jsm.exptool.providers.worksorchestrator.workers.sync.files.SyncRemoteAudioFileRegistersWorker;
import com.jsm.exptool.providers.worksorchestrator.workers.sync.registers.SyncRemoteAudioRegistersWorker;

import java.util.List;

public class AudioWorksOrchestratorSyncTaskCreator extends MediaWorksOrchestratorSyncTaskCreator<AudioRegister> {

   

    @Override
    protected Class<? extends RxWorker> getRegisterWorkerClass() {
        return SyncRemoteAudioRegistersWorker.class;
    }

    @Override
    protected Class<? extends RxWorker> getFileRegisterWorkerClass() {
        return SyncRemoteAudioFileRegistersWorker.class;
    }

    @Override
    protected String getRegisterTag() {
        return REMOTE_SYNC_AUDIO_REGISTERS;
    }

    @Override
    protected List<AudioRegister> getPendingRegisters(long experimentInternalId) {
        return AudioRepository.getSynchronouslyPendingSyncRegistersByExperimentId(experimentInternalId);
    }

    @Override
    protected String getFileRegisterTag() {
        return REMOTE_SYNC_AUDIO_FILE_REGISTERS;
    }
    

    @Override
    protected List<AudioRegister> getPendingFileRegisters(long experimentInternalId) {
        return AudioRepository.getSynchronouslyPendingFileSyncRegistersByExperimentId(experimentInternalId);
    }


}
