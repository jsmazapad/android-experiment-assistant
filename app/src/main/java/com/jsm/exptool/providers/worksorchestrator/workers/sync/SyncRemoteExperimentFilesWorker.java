package com.jsm.exptool.providers.worksorchestrator.workers.sync;

import static com.jsm.exptool.config.NetworkConstants.MAX_RETRIES;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.EXPERIMENT_EXTERNAL_ID;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.EXPERIMENT_ID;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.FILE_NAME;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.EXPERIMENT_REGISTER_ID;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.WorkerParameters;
import androidx.work.rxjava3.RxWorker;

import com.jsm.exptool.core.data.repositories.responses.ElementResponse;
import com.jsm.exptool.core.exceptions.BaseException;
import com.jsm.exptool.data.network.responses.RemoteSyncResponse;
import com.jsm.exptool.entities.register.MediaRegister;

import java.io.File;

import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.SingleEmitter;

public abstract class SyncRemoteExperimentFilesWorker<T extends MediaRegister> extends RxWorker {

    private final T instanceOfType = (T) new MediaRegister();

    public SyncRemoteExperimentFilesWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
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
            long experimentRegisterId = getInputData().getLong(EXPERIMENT_REGISTER_ID, -1);
            String registerFilePath = getInputData().getString(FILE_NAME);

            if (experimentId == -1 || experimentExternalId == -1 || experimentRegisterId == -1 || registerFilePath == null) {
                //TODO Mejorar mensajes error
                emitter.onError(new BaseException("Error de parámetros", false));
                return;
            }
            File file = new File(registerFilePath);
            executeRemoteSync(emitter, experimentRegisterId, experimentExternalId, file);

        });
    }

    protected abstract void executeRemoteSync(SingleEmitter<Result> emitter, long experimentRegisterId, long experimentExternalId, File file);

    protected abstract int updateRegister(long experimentRegisterId);

    protected void executeInnerCallbackLogic(SingleEmitter<Result> emitter, long experimentRegisterId, ElementResponse<RemoteSyncResponse> response) {
        if (response.getError() != null) {
            Log.w("SYNC_REGISTER", "error en response");

            //emitter.onError(response.getError());
            if (getRunAttemptCount() < MAX_RETRIES) {
                Log.d("SYNC_REGISTER", String.format("Lanzado reintento %d", getRunAttemptCount() + 1));
                emitter.onSuccess(Result.retry());
            } else {
                emitter.onError(response.getError());
                return;
            }
        } else {

            if (response.getResultElement() != null) {
                int updatedNum = updateRegister(experimentRegisterId);

                if (updatedNum > 0) {
                    emitter.onSuccess(Result.success());
                } else {
                    emitter.onError(new BaseException("Error en la actualización de la base de datos", false));
                }
            } else {
                emitter.onError(new BaseException("Error en obtención de resultado del servidor", false));
            }
        }
    }

}
