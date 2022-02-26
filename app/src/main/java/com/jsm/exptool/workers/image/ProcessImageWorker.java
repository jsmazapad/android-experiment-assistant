package com.jsm.exptool.workers.image;

import static com.jsm.exptool.config.WorkerPropertiesConstants.IMAGE_FILE_NAME;
import static com.jsm.exptool.config.WorkerPropertiesConstants.IMAGE_REGISTER_ID;
import static com.jsm.exptool.config.WorkerPropertiesConstants.PROCESSED_IMAGE_FILE_NAME;
import static com.jsm.exptool.config.WorkerPropertiesConstants.PROCESSED_IMAGE_HEIGHT;
import static com.jsm.exptool.config.WorkerPropertiesConstants.PROCESSED_IMAGE_WIDTH;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.jsm.exptool.libs.ImageResizer;

import java.io.File;
import java.io.IOException;

public class ProcessImageWorker extends Worker {
    public ProcessImageWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        String imageFileName = getInputData().getString(IMAGE_FILE_NAME);
        String targetImageFileName = getInputData().getString(PROCESSED_IMAGE_FILE_NAME);
        long insertedRowId = getInputData().getLong(IMAGE_REGISTER_ID, -1);
        int optimalWidth = getInputData().getInt(PROCESSED_IMAGE_WIDTH, 299);
        int optimalHeight = getInputData().getInt(PROCESSED_IMAGE_HEIGHT, 299);
        if(imageFileName == null || targetImageFileName == null || insertedRowId == -1){
            return Result.failure();
        }
        File imageFile = new File(imageFileName);
        File targetFile = new File(targetImageFileName);
        try {
            if (!targetFile.exists()) {
                targetFile.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImageResizer.resizeImageFile(imageFile, targetFile, Math.max(optimalWidth, optimalHeight));
        Data outputData = new Data.Builder()
                .putString(IMAGE_FILE_NAME, targetFile.getPath())
                .putLong(IMAGE_REGISTER_ID, insertedRowId)
                .build();
        return Result.success(outputData);
    }
}
