package com.jsm.exptool.providers.syncworksorchestrator.workcreators.multimedia;


import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.EXPERIMENT_REGISTER_ID;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.FILE_NAME;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.IMAGE_REGISTER_ID;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.REMOTE_SYNC_FILE_REGISTERS;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.REMOTE_SYNC_IMAGE_FILE_REGISTERS;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.REMOTE_SYNC_IMAGE_REGISTERS;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.REMOTE_SYNC_WORK;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.REMOTE_WORK;

import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkContinuation;
import androidx.work.rxjava3.RxWorker;

import com.jsm.exptool.libs.WorksOrchestratorUtils;
import com.jsm.exptool.model.Experiment;
import com.jsm.exptool.model.register.ImageRegister;
import com.jsm.exptool.providers.syncworksorchestrator.WorksOrchestratorProvider;
import com.jsm.exptool.providers.syncworksorchestrator.workcreators.MediaWorksOrchestratorSyncTaskCreator;
import com.jsm.exptool.repositories.ImageRepository;
import com.jsm.exptool.workers.sync.files.SyncRemoteImageFileRegistersWorker;
import com.jsm.exptool.workers.sync.registers.SyncRemoteImageRegistersWorker;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImageWorksOrchestratorSyncTaskCreator extends MediaWorksOrchestratorSyncTaskCreator<ImageRegister> {

    @Override
    public void createSyncWorks(Experiment experiment, List<OneTimeWorkRequest> syncExperimentRegisters, Map<String, Object> registersInputDataValues) {
        if(experiment.getConfiguration().isEmbeddingEnabled()) {
            createEmbeddingWorks(experiment, syncExperimentRegisters);
        }
        super.createSyncWorks(experiment, syncExperimentRegisters, registersInputDataValues);

    }

    private void createEmbeddingWorks(Experiment experiment, List<OneTimeWorkRequest> syncExperimentRegisters) {
        List<ImageRegister> pendingRegisters = ImageRepository.getSynchronouslyPendingEmbeddingSyncRegistersByExperimentId(experiment.getInternalId());
        for (ImageRegister register : pendingRegisters) {

            Map<String, Object> registersEmbeddingInputDataValues = new HashMap<String, Object>() {
                {
                    put(IMAGE_REGISTER_ID, register.getInternalId());
                }
            };

            File file = new File(register.getFullPath());

            WorkContinuation continuation = WorksOrchestratorProvider.getInstance().prepareEmbeddingChain(file, true, experiment.getConfiguration().getCameraConfig(), null, registersEmbeddingInputDataValues );
            continuation.enqueue();


        }
    }

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
    protected List<ImageRegister> getPendingRegisters(long experimentInternalId) {
        return ImageRepository.getSynchronouslyPendingSyncRegistersByExperimentId(experimentInternalId);
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
