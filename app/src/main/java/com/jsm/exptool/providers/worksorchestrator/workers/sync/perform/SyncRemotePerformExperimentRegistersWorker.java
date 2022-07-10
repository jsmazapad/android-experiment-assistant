package com.jsm.exptool.providers.worksorchestrator.workers.sync.perform;

import static com.jsm.exptool.config.ConfigConstants.REGISTERS_SYNC_LIMIT;
import static com.jsm.exptool.config.NetworkConstants.MAX_RETRIES;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.EXPERIMENT_EXTERNAL_ID;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.EXPERIMENT_ID;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.WorkerParameters;
import androidx.work.rxjava3.RxWorker;

import com.google.common.collect.Lists;
import com.jsm.exptool.core.data.repositories.responses.ElementResponse;
import com.jsm.exptool.core.exceptions.BaseException;
import com.jsm.exptool.data.network.responses.RemoteSyncResponse;
import com.jsm.exptool.data.repositories.AudioRepository;
import com.jsm.exptool.data.repositories.CommentRepository;
import com.jsm.exptool.data.repositories.ImageRepository;
import com.jsm.exptool.data.repositories.RemoteSyncRepository;
import com.jsm.exptool.data.repositories.SensorRepository;
import com.jsm.exptool.entities.eventbus.WorkFinishedEvent;
import com.jsm.exptool.entities.register.AudioRegister;
import com.jsm.exptool.entities.register.CommentRegister;
import com.jsm.exptool.entities.register.ExperimentRegister;
import com.jsm.exptool.entities.register.ImageRegister;
import com.jsm.exptool.entities.register.SensorRegister;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;

public class SyncRemotePerformExperimentRegistersWorker extends RxWorker {

    public interface UpdateElementInterface{
        <T extends ExperimentRegister> void updateElements(List<T> elements, T elementClass);
    }


    public SyncRemotePerformExperimentRegistersWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
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


            if (experimentId == -1 || experimentExternalId == null || "".equals(experimentExternalId) ) {
                //TODO Mejorar mensajes error
                EventBus.getDefault().post(new WorkFinishedEvent(getTags(), false, 1));
                emitter.onError(new BaseException("Error de parámetros", false));
                return;
            }
            AtomicInteger num = new AtomicInteger();
            List<List<ImageRegister>> imageRegisters = Lists.partition(ImageRepository.getSynchronouslyPendingFileSyncRegistersByExperimentId(experimentId), REGISTERS_SYNC_LIMIT);
            final Observable imageUploadObservable = Observable.create((ObservableOnSubscribe<Integer>) imageEmitter -> {
                syncImageRegisters(imageRegisters, imageEmitter, experimentExternalId, num.get());


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


                }
            });

            AtomicInteger numAudio = new AtomicInteger();
            List<AudioRegister> audioRegisters = AudioRepository.getSynchronouslyPendingFileSyncRegistersByExperimentId(experimentId);
            final Observable audioUploadObservable = Observable.create((ObservableOnSubscribe<Integer>) audioEmitter -> {
                syncAudioRegisters(audioRegisters, audioEmitter, experimentExternalId, numAudio.get());


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


        });
    }

    private void syncImageRegisters( List<List<ImageRegister>> registers, ObservableEmitter<Integer> emitter, String experimentExternalId, int num) {
        if (num < registers.size()) {
            List<ImageRegister> registersPortion = registers.get(num);
            RemoteSyncRepository.syncImageRegisters(response -> executeInnerCallbackLogic(emitter, response, (elements) -> {
                for (ImageRegister register:elements) {
                    ImageRepository.updateImageRegister(register);
                }

            }, num, registers), experimentExternalId, registersPortion );
        }else{
            emitter.onComplete();
        }
    }

    private void syncAudioRegisters(List<AudioRegister> registers, ObservableEmitter<Integer> emitter, String experimentExternalId, int num) {
        if (num < registers.size()) {
            AudioRegister register = registers.get(num);
            RemoteSyncRepository.syncAudioRegisters(response -> executeInnerCallbackLogic(emitter, register.getInternalId(), response, (id) -> {
                AudioRepository.updateAudioRegister(register);
            }, num, registers), experimentExternalId, registers);
        }else{
            emitter.onComplete();
        }
    }

    private void syncSensorRegisters(List<SensorRegister> registers, ObservableEmitter<Integer> emitter, String experimentExternalId, int num) {
        if (num < registers.size()) {
            SensorRegister register = registers.get(num);
            RemoteSyncRepository.syncSensorRegisters(response -> executeInnerCallbackLogic(emitter, register.getInternalId(), response, (id) -> {
                SensorRepository.updateSensorRegister(register);
            }, num, registers), experimentExternalId, registers);
        }else{
            emitter.onComplete();
        }
    }

    private void syncCommentRegisters(List<CommentRegister> registers, ObservableEmitter<Integer> emitter, String experimentExternalId, int num) {
        if (num < registers.size()) {
            CommentRegister register = registers.get(num);
            RemoteSyncRepository.syncCommentRegisters(response -> executeInnerCallbackLogic(emitter, register.getInternalId(), response, (id) -> {
                CommentRepository.updateCommentRegister(register);
            }, num, registers), experimentExternalId, registers);
        }else{
            emitter.onComplete();
        }
    }


    protected <T extends ExperimentRegister> void executeInnerCallbackLogic(ObservableEmitter<Integer> emitter,  ElementResponse<RemoteSyncResponse> response, UpdateElementInterface updateElementCallback, int num, List<T> pendingRegisters) {
        if (response.getError() != null) {
            emitter.onNext(num+1);
            Log.w("SYNC_REGISTER", "error en response");

            if (getRunAttemptCount() < MAX_RETRIES) {
                Log.d("SYNC_REGISTER", String.format("Lanzado reintento %d", getRunAttemptCount() + 1));
                //emitter.onSuccess(Result.retry());
            } else {
                return;
            }
        } else {
            if (response.getResultElement() != null) {
                for (T register : pendingRegisters) {
                    register.setDataRemoteSynced(true);
                    updateElementCallback.updateElement();
                }
            }
            emitter.onNext(num+1);
        }
    }

}
