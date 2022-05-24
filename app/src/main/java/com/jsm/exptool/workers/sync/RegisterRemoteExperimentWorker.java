package com.jsm.exptool.workers.sync;

import static com.jsm.exptool.config.NetworkConstants.MAX_RETRIES;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.EXPERIMENT_ID;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.WorkerParameters;
import androidx.work.rxjava3.RxWorker;

import com.jsm.exptool.core.exceptions.BaseException;
import com.jsm.exptool.model.Experiment;
import com.jsm.exptool.repositories.ExperimentsRepository;
import com.jsm.exptool.repositories.RemoteSyncRepository;

import io.reactivex.rxjava3.core.Single;


public class RegisterRemoteExperimentWorker extends RxWorker {
    public RegisterRemoteExperimentWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Single<Result> createWork() {
        Log.d("SYNC_REGISTER", String.format("Reintento num %d", getRunAttemptCount()));

        return Single.create(emitter -> {
            long experimentId = getInputData().getLong(EXPERIMENT_ID, -1);

            if(experimentId == -1){
                //TODO Mejorar mensajes error
                emitter.onError(new BaseException("Error de parámetros", false));
            }

            Experiment experiment = ExperimentsRepository.getExperimentById(experimentId);

            if(experiment == null){
                emitter.onError(new BaseException("Error en obtención de elemento de BBDD", false));
            }

            RemoteSyncRepository.syncExperiment(response -> {
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
                    if (response.getResultElement() != null && response.getResultElement().getId() != null && response.getResultElement().getId() > 0) {
                        assert experiment != null;
                        experiment.setExternalId(response.getResultElement().getId());
                        ExperimentsRepository.updateExperiment(experiment);
                        emitter.onSuccess(Result.success());
                    }else{
                        emitter.onError(new BaseException("Error en obtención de resultado del servidor", false));
                    }
                }
            }, experiment);
        });
    }
}
