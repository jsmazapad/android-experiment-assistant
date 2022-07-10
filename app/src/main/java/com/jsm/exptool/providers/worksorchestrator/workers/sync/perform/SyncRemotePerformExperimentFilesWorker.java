package com.jsm.exptool.providers.worksorchestrator.workers.sync.perform;

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
import com.jsm.exptool.data.repositories.AudioRepository;
import com.jsm.exptool.data.repositories.ImageRepository;
import com.jsm.exptool.data.repositories.RemoteSyncRepository;
import com.jsm.exptool.entities.eventbus.WorkFinishedEvent;
import com.jsm.exptool.entities.register.AudioRegister;
import com.jsm.exptool.entities.register.ImageRegister;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.Disposable;

public class SyncRemotePerformExperimentFilesWorker extends RxWorker {

    public interface UpdateElementInterface {
        void updateElement(long elementId);
    }


    public SyncRemotePerformExperimentFilesWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public Single<Result> createWork() {
        Log.d("SYNC_FILE_REGISTERS", String.format("Reintento num %d", getRunAttemptCount()));

        return Single.create(emitter -> {
            long experimentId = getInputData().getLong(EXPERIMENT_ID, -1);
            String experimentExternalId = getInputData().getString(EXPERIMENT_EXTERNAL_ID);


            if (experimentId == -1 || experimentExternalId == null || "".equals(experimentExternalId)) {

                EventBus.getDefault().post(new WorkFinishedEvent(getTags(), false, 1));
                emitter.onError(new BaseException("Error de parámetros", false));
                return;
            }
            AtomicInteger num = new AtomicInteger();
            List<ImageRegister> imageRegisters = ImageRepository.getSynchronouslyPendingFileSyncRegistersByExperimentId(experimentId);
            final Observable imageUploadObservable = Observable.create((ObservableOnSubscribe<Integer>) imageEmitter -> {
                syncImageFile(imageRegisters, imageEmitter, experimentExternalId, num.get());


            });
            imageUploadObservable.subscribe(new Observer<Integer>() {
                @Override
                public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

                }

                @Override
                public void onNext(Integer o) {
                    num.set(o);
                }

                @Override
                public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                }

                @Override
                public void onComplete() {
                    AtomicInteger numAudio = new AtomicInteger();
                    List<AudioRegister> audioRegisters = AudioRepository.getSynchronouslyPendingFileSyncRegistersByExperimentId(experimentId);
                    final Observable audioUploadObservable = Observable.create((ObservableOnSubscribe<Integer>) audioEmitter -> {
                        syncAudioFile(audioRegisters, audioEmitter, experimentExternalId, numAudio.get());


                    });

                    audioUploadObservable.subscribe(new Observer<Integer>() {
                        @Override
                        public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {
                        }

                        @Override
                        public void onNext(Integer o) {
                            numAudio.set(o);
                        }

                        @Override
                        public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {
                        }

                        @Override
                        public void onComplete() {
                            emitter.onSuccess(Result.success());
                        }
                    });

                }
            });




        });
    }

    private void syncImageFile(List<ImageRegister> registers, ObservableEmitter<Integer> emitter, String experimentExternalId, int num) {
        if (num < registers.size()) {
            ImageRegister register = registers.get(num);
            File file = new File(register.getFullPath());
            RemoteSyncRepository.syncImageFile(response -> executeInnerCallbackLogic(emitter, register.getInternalId(), response, (id) -> {
                ImageRepository.updateRegisterFileSyncedByRegisterId(register.getInternalId());
            }, num), experimentExternalId, file);
        } else {
            emitter.onComplete();
        }
    }

    private void syncAudioFile(List<AudioRegister> registers, ObservableEmitter<Integer> emitter, String experimentExternalId, int num) {
        if (num < registers.size()) {
            AudioRegister register = registers.get(num);
            File file = new File(register.getFullPath());
            RemoteSyncRepository.syncAudioFile(response -> executeInnerCallbackLogic(emitter, register.getInternalId(), response, (id) -> {
                AudioRepository.updateRegisterFileSyncedByRegisterId(register.getInternalId());
            }, num), experimentExternalId, file);
        } else {
            emitter.onComplete();
        }
    }


    protected void executeInnerCallbackLogic(ObservableEmitter<Integer> emitter, long experimentRegisterId, ElementResponse<RemoteSyncResponse> response, UpdateElementInterface updateElementCallback, int num) {
        if (response.getError() != null) {
            emitter.onNext(num + 1);
            Log.w("SYNC_REGISTER", "error en response");
            //No hay reintentos, se reintenta en la próxima sincronización
//            if (getRunAttemptCount() < MAX_RETRIES) {
//                Log.d("SYNC_REGISTER", String.format("Lanzado reintento %d", getRunAttemptCount() + 1));
//                //emitter.onSuccess(Result.retry());
//            } else {
//            }
        } else {
            if (response.getResultElement() != null) {
                updateElementCallback.updateElement(experimentRegisterId);
            }
            emitter.onNext(num + 1);
        }
    }

}
