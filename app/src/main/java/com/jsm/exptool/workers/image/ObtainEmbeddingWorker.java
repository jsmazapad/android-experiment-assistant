package com.jsm.exptool.workers.image;

import static com.jsm.exptool.config.WorkerPropertiesConstants.EMBEDDED_ARRAY;
import static com.jsm.exptool.config.WorkerPropertiesConstants.IMAGE_FILE_NAME;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.content.res.TypedArrayUtils;
import androidx.work.Data;
import androidx.work.WorkerParameters;
import androidx.work.rxjava3.RxWorker;

import com.jsm.exptool.core.data.network.NetworkElementResponseCallback;
import com.jsm.exptool.core.data.repositories.responses.ElementResponse;
import com.jsm.exptool.libs.camera.CameraProvider;
import com.jsm.exptool.libs.camera.ImageReceivedCallback;
import com.jsm.exptool.model.ImageEmbeddingVector;
import com.jsm.exptool.repositories.EmbeddingRepository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
            File file = new File(imageFilePath);
            EmbeddingRepository.getEmbedding(response -> {
                if(response.getError() != null){
                    emitter.onError(response.getError());
                }else{

                    EmbeddingRepository.insertImageEmbedding(file,response.getResultElement() );

                    emitter.onSuccess(Result.success());
                }
            }, file);
        });
    }
}
