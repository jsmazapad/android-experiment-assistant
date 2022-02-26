package com.jsm.exptool.workers.image;

import static com.jsm.exptool.config.WorkerPropertiesConstants.IMAGE_FILE_NAME;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.WorkerParameters;
import androidx.work.rxjava3.RxWorker;

import com.jsm.exptool.libs.camera.CameraProvider;
import com.jsm.exptool.libs.camera.ImageReceivedCallback;

import java.io.File;

import io.reactivex.rxjava3.core.Single;

public class ObtainImageWorker extends RxWorker {
    public ObtainImageWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Single<Result> createWork() {
        return Single.create(emitter -> {
            String imageFilePath = getInputData().getString(IMAGE_FILE_NAME);
            File file = new File(imageFilePath);
            CameraProvider cameraProvider = CameraProvider.getInstance();
            cameraProvider.takePicture(file, new ImageReceivedCallback() {
                @Override
                public void onImageReceived(File imageFile) {
                    Data outputData = new Data.Builder()
                            .putString(IMAGE_FILE_NAME, imageFile.getPath())
                            .build();
                    emitter.onSuccess(Result.success(outputData));
                }

                @Override
                public void onErrorReceived(Exception error) {
                    emitter.onSuccess(Result.failure());
                }
            });

        });
    }

}
