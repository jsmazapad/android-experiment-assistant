package com.jsm.exptool.workers.sync;

import static com.jsm.exptool.config.NetworkConstants.MAX_RETRIES;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.EXPERIMENT_EXTERNAL_ID;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.EXPERIMENT_ID;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.WorkerParameters;
import androidx.work.rxjava3.RxWorker;

import com.jsm.exptool.core.data.repositories.responses.ElementResponse;
import com.jsm.exptool.core.exceptions.BaseException;
import com.jsm.exptool.data.network.responses.RemoteSyncResponse;
import com.jsm.exptool.model.register.ExperimentRegister;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleEmitter;


public abstract class SyncRemoteExperimentRegistersWorker<T extends ExperimentRegister> extends RxWorker {

    public SyncRemoteExperimentRegistersWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    //TODO Pasar a fachada
    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public Single<Result> createWork() {
        Log.d("SYNC_REGISTER", String.format("Reintento num %d", getRunAttemptCount()));

        return Single.create(emitter -> {
            long experimentId = getInputData().getLong(EXPERIMENT_ID, -1);
            long experimentExternalId = getInputData().getLong(EXPERIMENT_EXTERNAL_ID, -1);

            if(experimentId == -1 || experimentExternalId == -1){
                //TODO Mejorar mensajes error
                emitter.onError(new BaseException("Error de parámetros", false));
            }
            List<T> pendingRegisters = getPendingRegisters(experimentId);
//            if (instanceOfType instanceof AudioRegister){
//                pendingRegisters = (List<T>) AudioRepository.getSynchronouslyPendingSyncRegistersByExperimentId(experimentId);
//            }else if (instanceOfType instanceof CommentRegister){
//                pendingRegisters = (List<T>) CommentRepository.getSynchronouslyPendingSyncRegistersByExperimentId(experimentId);
//            }else if (instanceOfType instanceof SensorRegister){
//                pendingRegisters = (List<T>) SensorsRepository.getSynchronouslyPendingSyncRegistersByExperimentId(experimentId);
//            }else if (instanceOfType instanceof ImageRegister){
//                pendingRegisters = (List<T>) ImagesRepository.getSynchronouslyPendingSyncRegistersByExperimentId(experimentId);
//            }else{
//                emitter.onError(new BaseException("Error de reconocimiento de tipo de registro", false));
//            }

            if(pendingRegisters == null){
                emitter.onError(new BaseException("Error en obtención de elemento de BBDD", false));
            }else if (pendingRegisters.size() == 0){
                emitter.onSuccess(Result.success());
            }

            List<T> finalPendingRegisters = pendingRegisters;

            executeRemoteSync(emitter, finalPendingRegisters, experimentExternalId );

//            if (instanceOfType instanceof AudioRegister){
//                RemoteSyncRepository.syncAudioRegisters(response -> executeInnerCallbackLogic(emitter, finalPendingRegisters, response), experimentExternalId, (List<AudioRegister>) pendingRegisters);
//            }else if (instanceOfType instanceof CommentRegister){
//                RemoteSyncRepository.syncCommentRegisters(response -> executeInnerCallbackLogic(emitter, finalPendingRegisters, response), experimentExternalId, (List<CommentRegister>) pendingRegisters);
//            }else if (instanceOfType instanceof SensorRegister){
//                RemoteSyncRepository.syncSensorRegisters(response -> executeInnerCallbackLogic(emitter, finalPendingRegisters, response), experimentExternalId, (List<SensorRegister>) pendingRegisters);
//            }else if (instanceOfType instanceof ImageRegister){
//                RemoteSyncRepository.syncImageRegisters(response -> executeInnerCallbackLogic(emitter, finalPendingRegisters, response), experimentExternalId, (List<ImageRegister>) pendingRegisters);
//            }else{
//                emitter.onError(new BaseException("Error de reconocimiento de tipo de registro", false));
//            }


        });
    }

    protected abstract List<T> getPendingRegisters(long experimentId);

    protected abstract void executeRemoteSync(SingleEmitter<Result> emitter, List<T> pendingRegisters, long experimentExternalId);

    protected abstract void updateRegister(T register);

    protected void executeInnerCallbackLogic(SingleEmitter<Result> emitter, List<T> pendingRegisters, ElementResponse<RemoteSyncResponse> response) {
        if(response.getError() != null){
            Log.e("SYNC_REGISTER", "error en response");

            //emitter.onError(response.getError());
            if(getRunAttemptCount()< MAX_RETRIES) {
                Log.d("SYNC_REGISTER", String.format("Lanzado reintento %d", getRunAttemptCount()+1));
                emitter.onSuccess(Result.retry());
            }else{
                emitter.onError(response.getError());
            }
        }else{
            if (response.getResultElement() != null) {
                for (T register: pendingRegisters) {
                    register.setDataRemoteSynced(true);
                    updateRegister(register);
//                    if (register instanceof AudioRegister){
//                        AudioRepository.updateAudioRegister((AudioRegister) register);
//                    }else if (register instanceof CommentRegister){
//                        CommentRepository.updateCommentRegister((CommentRegister) register);
//                    }else if (register instanceof SensorRegister){
//                        SensorsRepository.updateSensorRegister((SensorRegister) register);
//                    }else if (register instanceof ImageRegister){
//                        ImageRegister imageRegister = (ImageRegister) register;
//                        if(imageRegister.getEmbedding().size() > 0){
//                            imageRegister.setEmbeddingRemoteSynced(true);
//                        }
//                        ImagesRepository.updateImageRegister(imageRegister);
//                    }else{
//                        emitter.onError(new BaseException("Error de reconocimiento de tipo de registro", false));
//                    }

                }

                emitter.onSuccess(Result.success());
            }else{
                emitter.onError(new BaseException("Error en obtención de resultado del servidor", false));
            }
        }
    }


}
