package com.jsm.exptool.providers.worksorchestrator.workers.sync;

import static com.jsm.exptool.config.NetworkConstants.MAX_RETRIES;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.EXPERIMENT_EXTERNAL_ID;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.EXPERIMENT_ID;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.WorkerParameters;
import androidx.work.rxjava3.RxWorker;

import com.jsm.exptool.core.exceptions.BaseException;
import com.jsm.exptool.entities.Experiment;
import com.jsm.exptool.data.repositories.ExperimentsRepository;
import com.jsm.exptool.data.repositories.RemoteSyncRepository;

import io.reactivex.rxjava3.core.Single;


public class SyncRemoteExperimentWorker extends RxWorker {
    public SyncRemoteExperimentWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Single<Result> createWork() {
        Log.d("SYNC_REGISTER", String.format("Reintento num %d", getRunAttemptCount()));

        return Single.create(emitter -> {
            long experimentId = getInputData().getLong(EXPERIMENT_ID, -1);

            if (experimentId == -1) {
                //TODO Mejorar mensajes error
                emitter.onError(new BaseException("Error de parámetros", false));
                return;
            }

            Experiment experiment = ExperimentsRepository.getExperimentById(experimentId);

            if (experiment == null) {
                emitter.onError(new BaseException("Error en obtención de elemento de BBDD", false));
                return;
            }

            RemoteSyncRepository.syncExperiment(response -> {
                if (response.getError() != null) {
                    Log.w("SYNC_REGISTER", "error en response");

                    //emitter.onError(response.getError());
                    if (getRunAttemptCount() < MAX_RETRIES) {
                        Log.d("SYNC_REGISTER", String.format("Lanzado reintento %d", getRunAttemptCount() + 1));
                        emitter.onSuccess(Result.retry());
                    } else {
                        emitter.onError(response.getError());
                    }
                } else {
                    if (response.getResultElement() != null && response.getResultElement().getId() != null && !"".equals(response.getResultElement().getId())) {
                        assert experiment != null;
                        experiment.setExternalId(response.getResultElement().getId());
                        ExperimentsRepository.updateExperiment(experiment);
                        Data outputData = new Data.Builder()
                                .putString(EXPERIMENT_EXTERNAL_ID, experiment.getExternalId())
                                .putLong(EXPERIMENT_ID, experiment.getInternalId())
                                .build();
                        emitter.onSuccess(Result.success(outputData));
                    } else {
                        emitter.onError(new BaseException("Error en obtención de resultado del servidor", false));
                    }
                }
            }, experiment);
        });
    }
}
