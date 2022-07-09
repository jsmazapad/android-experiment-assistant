package com.jsm.exptool.providers.worksorchestrator.workcreators.multimedia;


import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.IMAGE_REGISTER_ID;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.REMOTE_SYNC_IMAGE_FILE_REGISTERS;
import static com.jsm.exptool.config.WorkerPropertiesConstants.WorkTagsConstants.REMOTE_SYNC_IMAGE_REGISTERS;

import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkContinuation;
import androidx.work.rxjava3.RxWorker;

import com.jsm.exptool.entities.Experiment;
import com.jsm.exptool.entities.register.ImageRegister;
import com.jsm.exptool.providers.worksorchestrator.WorksOrchestratorProvider;
import com.jsm.exptool.providers.worksorchestrator.workcreators.MediaWorksOrchestratorSyncTaskCreator;
import com.jsm.exptool.data.repositories.ImageRepository;
import com.jsm.exptool.providers.worksorchestrator.workers.sync.files.SyncRemoteImageFileRegistersWorker;
import com.jsm.exptool.providers.worksorchestrator.workers.sync.registers.SyncRemoteImageRegistersWorker;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImageWorksOrchestratorSyncTaskCreator extends MediaWorksOrchestratorSyncTaskCreator<ImageRegister> {


    public void createSyncWorks(Experiment experiment, List<OneTimeWorkRequest> syncExperimentRegisters, Map<String, Object> registersInputDataValues, boolean obtainPendingEmbedding) {
        if(experiment.getConfiguration().isEmbeddingEnabled() && obtainPendingEmbedding) {
            createEmbeddingWorks(experiment, syncExperimentRegisters);
        }
        super.createSyncWorks(experiment, syncExperimentRegisters, registersInputDataValues);

    }

    //TODO Quitar syncExperiment y ejecutar primero estos trabajos
    private void createEmbeddingWorks(Experiment experiment, List<OneTimeWorkRequest> syncExperimentRegisters) {
        List<ImageRegister> pendingRegisters = ImageRepository.getSynchronouslyPendingEmbeddingSyncRegistersByExperimentId(experiment.getInternalId());
        for (ImageRegister register : pendingRegisters) {

            Map<String, Object> registersEmbeddingInputDataValues =  new HashMap<>();
            registersEmbeddingInputDataValues.put(IMAGE_REGISTER_ID, register.getInternalId());

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
