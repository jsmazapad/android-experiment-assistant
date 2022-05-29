package com.jsm.exptool.workers.sync;

import static com.jsm.exptool.config.NetworkConstants.MAX_RETRIES;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.WorkerParameters;
import androidx.work.rxjava3.RxWorker;

import com.jsm.exptool.core.data.network.exceptions.InvalidSessionException;
import com.jsm.exptool.core.exceptions.BaseException;
import com.jsm.exptool.providers.SessionProvider;
import com.jsm.exptool.repositories.RemoteSyncRepository;

import io.reactivex.rxjava3.core.Single;


public class SyncRemoteLoginWorker extends RxWorker {
    public SyncRemoteLoginWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Single<Result> createWork() {

        return Single.create(emitter -> {

            RemoteSyncRepository.login(response -> {
                if (response.getError() != null) {
                    Log.w("SYNC_REGISTER", "error en response");

                    //Si el error es de tipo InvalidSessionException es que no se ha hecho login correctamente, no se reintenta
                    if (getRunAttemptCount() < MAX_RETRIES && !(response.getError() instanceof InvalidSessionException)) {
                        emitter.onSuccess(Result.retry());
                    } else {
                        emitter.onError(response.getError());
                    }
                } else {
                    if (response.getResultElement() != null && response.getResultElement().getToken() != null && !"".equals(response.getResultElement().getToken())) {
                        SessionProvider.setToken(response.getResultElement().getToken());
                        emitter.onSuccess(Result.success());
                    } else {
                        emitter.onError(new BaseException("Error en obtención de resultado del servidor", false));
                    }
                }
            }, false);
        });
    }
}
