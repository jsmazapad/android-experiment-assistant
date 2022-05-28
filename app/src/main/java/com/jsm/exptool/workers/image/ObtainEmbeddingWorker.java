package com.jsm.exptool.workers.image;

import static com.jsm.exptool.config.NetworkConstants.MAX_RETRIES;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.EMBEDDING_ALG;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.FILE_NAME;
import static com.jsm.exptool.config.WorkerPropertiesConstants.DataConstants.IMAGE_REGISTER_ID;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.WorkerParameters;
import androidx.work.rxjava3.RxWorker;

import com.jsm.exptool.core.exceptions.BaseException;
import com.jsm.exptool.model.register.ImageRegister;
import com.jsm.exptool.repositories.ImageRepository;

import java.io.File;

import io.reactivex.rxjava3.core.Single;

public class ObtainEmbeddingWorker extends RxWorker {
    public ObtainEmbeddingWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Single<Result> createWork() {
        Log.d("IMAGE_REGISTER", String.format("Reintento num %d", getRunAttemptCount()));

        return Single.create(emitter -> {
            String imageFilePath = getInputData().getString(FILE_NAME);
            long insertedRowId = getInputData().getLong(IMAGE_REGISTER_ID, -1);
            String embeddingAlg = getInputData().getString(EMBEDDING_ALG);
            if(imageFilePath == null || embeddingAlg == null || insertedRowId == -1){
                //TODO Mejorar mensajes error
                emitter.onError(new BaseException("Error de parámetros", false));
                Log.d("IMAGE_REGISTER", String.format("insertado con id %d", insertedRowId));
            }
            File file = new File(imageFilePath);
            ImageRepository.getEmbedding(response -> {
                if(response.getError() != null){
                    Log.e("IMAGE_REGISTER_ERROR", "error en response de embedding");

                    //emitter.onError(response.getError());
                    if(getRunAttemptCount()< MAX_RETRIES) {
                        Log.d("IMAGE_REGISTER", String.format("Lanzado reintento %d", getRunAttemptCount()+1));
                        emitter.onSuccess(Result.retry());
                    }else{
                        emitter.onError(response.getError());
                    }
                }else{
                    Log.d("IMAGE_REGISTER", String.format("callback de embedding con id %d", insertedRowId));
                    ImageRegister imageRegister = ImageRepository.getImageRegisterById(insertedRowId);

                    if (imageRegister != null) {
                        Log.d("IMAGE_REGISTER", String.format("registro de imagen obtenido con id %d", imageRegister.getInternalId()));
                        int numRegs = ImageRepository.registerImageEmbedding(imageRegister, response.getResultElement());
                        Log.d("IMAGE_REGISTER", String.format("embedding actualizado en %d registros", numRegs));

                    }else{
                        emitter.onError(new BaseException("Embedding: error en obtención de imageregister by id", false));

                        Log.e("IMAGE_REGISTER_ERROR", "error en obtención de imageregister by id");

                    }

                    emitter.onSuccess(Result.success());
                }
            }, file, embeddingAlg);
        });
    }
}
