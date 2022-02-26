package com.jsm.exptool.workers.image;

import static com.jsm.exptool.config.WorkerPropertiesConstants.EMBEDDING_ALG;
import static com.jsm.exptool.config.WorkerPropertiesConstants.IMAGE_FILE_NAME;
import static com.jsm.exptool.config.WorkerPropertiesConstants.IMAGE_REGISTER_ID;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.WorkerParameters;
import androidx.work.rxjava3.RxWorker;

import com.jsm.exptool.core.exceptions.BaseException;
import com.jsm.exptool.model.ImageRegister;
import com.jsm.exptool.repositories.ImagesRepository;

import java.io.File;

import io.reactivex.rxjava3.core.Single;

public class ObtainEmbeddingWorker extends RxWorker {
    public ObtainEmbeddingWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Single<Result> createWork() {
        return Single.create(emitter -> {
            String imageFilePath = getInputData().getString(IMAGE_FILE_NAME);
            long insertedRowId = getInputData().getLong(IMAGE_REGISTER_ID, -1);
            String embeddingAlg = getInputData().getString(EMBEDDING_ALG);
            if(imageFilePath == null || embeddingAlg == null || insertedRowId == -1){
                //TODO Mejorar mensajes error
                emitter.onError(new BaseException("Error de parámetros", false));
            }
            File file = new File(imageFilePath);
            ImagesRepository.getEmbedding(response -> {
                if(response.getError() != null){
                    emitter.onError(response.getError());
                }else{
                    ImageRegister imageRegister = ImagesRepository.getImageRegisterById(insertedRowId);
                    if (imageRegister != null) {
                        ImagesRepository.registerImageEmbedding(imageRegister, response.getResultElement());
                    }

                    emitter.onSuccess(Result.success());
                }
            }, file, embeddingAlg);
        });
    }
}
