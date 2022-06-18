package com.jsm.exptool.providers.worksorchestrator.workers.sync;

import static com.jsm.exptool.config.NetworkConstants.MAX_RETRIES;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.EXPERIMENT_EXTERNAL_ID;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.EXPERIMENT_ID;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.REGISTER_IDS_TO_SYNC;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.UPDATED_REGISTERS_NUM;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.WorkerParameters;
import androidx.work.rxjava3.RxWorker;

import com.jsm.exptool.core.data.repositories.responses.ElementResponse;
import com.jsm.exptool.core.exceptions.BaseException;
import com.jsm.exptool.data.network.responses.RemoteSyncResponse;
import com.jsm.exptool.entities.register.ExperimentRegister;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleEmitter;


public abstract class SyncRemoteExperimentRegistersWorker<T extends ExperimentRegister> extends RxWorker {

    public SyncRemoteExperimentRegistersWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public Single<Result> createWork() {
        Log.d("SYNC_REGISTER", String.format("Reintento num %d", getRunAttemptCount()));

        return Single.create(emitter -> {
            long experimentId = getInputData().getLong(EXPERIMENT_ID, -1);
            long experimentExternalId = getInputData().getLong(EXPERIMENT_EXTERNAL_ID, -1);
            long [] registerIdsToSync = getInputData().getLongArray(REGISTER_IDS_TO_SYNC);

            if (experimentId == -1 || experimentExternalId == -1 || registerIdsToSync == null) {
                //TODO Mejorar mensajes error
                emitter.onError(new BaseException("Error de parámetros", false));
                return;
            }
            List<T> pendingRegisters = getPendingRegisters(registerIdsToSync);


            if (pendingRegisters == null) {
                emitter.onError(new BaseException("Error en obtención de elemento de BBDD", false));
            } else if (pendingRegisters.size() == 0) {
                emitter.onSuccess(Result.success());
            }

            List<T> finalPendingRegisters = pendingRegisters;
            executeRemoteSync(emitter, finalPendingRegisters, experimentExternalId, registerIdsToSync.length);

        });
    }

    protected List<T> getPendingRegisters(long[] registerIds){
        List<T> pendingRegisters = new ArrayList<>();
        for (long registerId:registerIds) {
            T register = getRegister(registerId);
            if(register != null){
                pendingRegisters.add(register);
            }
        }
        return pendingRegisters;
    }

    protected abstract  T getRegister(long registerId);

    protected abstract void executeRemoteSync(SingleEmitter<Result> emitter, List<T> pendingRegisters, long experimentExternalId, int numRegistersToupdate);

    protected abstract void updateRegister(T register);

    protected void executeInnerCallbackLogic(SingleEmitter<Result> emitter, List<T> pendingRegisters, ElementResponse<RemoteSyncResponse> response, int updatedRegistersNum) {
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
            if (response.getResultElement() != null) {
                for (T register : pendingRegisters) {
                    register.setDataRemoteSynced(true);
                    updateRegister(register);
                }

                Data outputData = new Data.Builder()
                        .putLong(UPDATED_REGISTERS_NUM, updatedRegistersNum)
                        .build();

                emitter.onSuccess(Result.success(outputData));
            } else {
                emitter.onError(new BaseException("Error en obtención de resultado del servidor", false));
            }
        }
    }


}
