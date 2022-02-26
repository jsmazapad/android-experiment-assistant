package com.jsm.exptool.workers.image;

import static com.jsm.exptool.config.WorkerPropertiesConstants.EXPERIMENT_ID;
import static com.jsm.exptool.config.WorkerPropertiesConstants.IMAGE_DATE_TIMESTAMP;
import static com.jsm.exptool.config.WorkerPropertiesConstants.IMAGE_FILE_NAME;
import static com.jsm.exptool.config.WorkerPropertiesConstants.IMAGE_REGISTER_ID;
import static com.jsm.exptool.config.WorkerPropertiesConstants.PROCESSED_IMAGE_FILE_NAME;
import static com.jsm.exptool.config.WorkerPropertiesConstants.PROCESSED_IMAGE_HEIGHT;
import static com.jsm.exptool.config.WorkerPropertiesConstants.PROCESSED_IMAGE_WIDTH;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.ListenableWorker;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.jsm.exptool.libs.ImageResizer;
import com.jsm.exptool.repositories.ImagesRepository;

import java.io.File;
import java.io.IOException;
import java.util.Date;


public class RegisterImageWorker extends Worker {
    public RegisterImageWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public ListenableWorker.Result doWork() {
        String imageFileName = getInputData().getString(IMAGE_FILE_NAME);
        long experimentId = getInputData().getLong(EXPERIMENT_ID, -1);
        long dateTimestamp = getInputData().getLong(IMAGE_DATE_TIMESTAMP, -1);
        if (imageFileName == null || experimentId == -1 || dateTimestamp == -1) {
            return ListenableWorker.Result.failure();
        }
        File imageFile = new File(imageFileName);
       //TODO Refactorizar para pasar un objeto limpio
        long insertedRowId = ImagesRepository.registerImageCapture(imageFile, experimentId, new Date(dateTimestamp));

        Data outputData = new Data.Builder()
                .putLong(IMAGE_REGISTER_ID, insertedRowId)
                .build();
        return ListenableWorker.Result.success(outputData);
    }
}
